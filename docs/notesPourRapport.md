Comme les commentaires sont censés expliquer les points difficiles du code, et non donner un terrain d'expression libres
pour des justifications, en voici une catégorie dédiée.

Dans la fonction statique `Application.turn()` : un tour se définit en l'affichage d'un prompt, de la lecture, de
l'analyse (*parsing*) et de l'exécution d'un tour (une suite de coups).  Chacune de ces étapes dépendant des précédentes,
nous avons créé une boucle finine qui exécute `continue;` si un problème survient, afin de recommencer l'ensemble du
processus de tour. Arrivé à la fin de la boucle reviens à dire que toutes les étapes se sont déroulées avec succès.
On exécute donc le `break;`.  
Seulement, un `while(true)` n'est pas toujours très parlant ni beau à voir, c'est pourquoi nous avons replacé le `true`
par une valeur booléenne ayant un sens, et avons profité de l'unique présence du break à la fin de la boucle pour 
définir ce booléen à `false`.

Même si l'entrée respecte le bon format, il faut que le coup soit jouable. Par exemple, que la carte à déplacer existe
bien dans la main du joueur. C'est avec ce même exemple qu'un choix s'est posé :

1. **Sacrifier la complexité temporelle pour sauver la complexité spatiale**: Vérifier avec certitude que la carte à
   déplacer est présente (`player.hand.contins(card)`) dans la main du joueur pour chacun de ses coups, PUIS exécuter de manière réelle ces mêmes
   coups (`player.hand.remove(card)` et `opponent.hand.add(card)`).  
   Récap de complexité : On itère donc deux fois (jouabilité + exécution) sur la liste de coups et interruption +
   re-prompt pour l'entrée du joueur en cas d'erreur 
2. **Sacrifier la complexité spatiale pour sauver la complexité temporelle** : Supprimer la carte à déplacer de la main
   du joueur (avec `player.hand.remove(card)`), et profiter que cette méthode retourne `false` si aucune
   suppression n'a été effectuée (la carte à supprimer n'est donc pas présente dans la main). En cas d'absence de la
   carte, on interrompt l'exécution pour redemander l'entrée de l'utilisateur avec le prompt d'erreur.  
   Toutefois, la suppression de chaque coups précédent ayant bien été effective, il faudra donc veiller à sauvegarder l'état
   du jeu et ajouter la capacité de la restaurer si l'un des coups suivant se serait montré invalide.  
   Récap de complexité : on sauvegarde l'état du jeu, on itère une fois, et en cas d'erreur, restaurer la sauvegarde +
   re-prompt pour l'entrée du joueur
3. **Sacrifier de manière importante la complexité temporelle en cas d'erreur** : Jusque-là, ces sacrifices de 
   complexité se montrent constants peu importe si l'enchainement de coup réussit ou échoue.  
   Mais en tirant parti du stockage de chaque coup (issue de l'étape d'analyse de l'entrée), on pourrait en cas d'erreur
   appliquer une annulation de chaque coups joués en itérant de manière inverse.
   Récap de complexité : Méthode 2 (unique itération pour faire la vérification + exécution) et en cas d'erreur,
   itération inverse pour annuler le coup joué au lieu d'un système de savestates, re-prompt pour l'entrée du joueur
