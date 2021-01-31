## drafts projet java

### déroulement (technique) d'une partie
**début de partie :**
* génération de 2 paquets de 60 __cartes numérotées de 1 à 60__, 1 paquet par joueur
* il pose devant lui la carte 1 et 60 pour former la pile ascendante et descendante
* il mélange les 58 cartes restantes 
* il prends 6 cartes en main pour commencer. Chaque joueur a donc six cartes dans sa main et 52 dans sa pioche.

**main loop :** les deux joueurs jouent chacun leurs tours.
* Affichage des deux piles de chaque personnages
* Affichage des cartes que possède le joueur dans ses mains, présentées par ordre croissant, encadrées par des accolades et séparées par des espaces
* Affichage de l'invite de saisie `>`
* Lecture de l'entrée du joueur. Le joueur actuel doit poser au moins *deux cartes* parmi celles qu'il a dans sa __main__ sinon sa partie est perdue sur sa base ou celle de l'ennemi
	* pour pouvoir poser une carte sur sa base:  
		* il faut que celle-ci ait une valeur plus grande que celle qui est déjà posée.
		* OU que la valeur corresponde à la dizaine du dessous (si carte sur la table = 29, alors je peux poser la carte 19)
	*  pour pouvoir poser une carte sur sa base adverse :
		* il ne peut jouer qu'une seule carte
		* Elle doit être plus petite sur la pile ascendante, et plus grande sur la pile descendante.
* Si le joueur a joué uniquement sur ses propres bases, il pioche alors deux cartes. 
* Sinon, il doit poser x cartes tel que `[nombre de cartes en main] + x = 6` 

**Fin de partie :** 
* Si le joueur ne peut place au minimum deux cartes, alors il perd
* Sinon, si le nombre de cartes dans la main du joueur est égal à 0, alors il gagne
* Affichage du gagnant sous le format`partie finie, NORD a gagné`

### specs techniques imposées
* programme contenant le *main* doit être compris dans la classe `Application`  du paquetage`appli`
* les deux joueurs sont nommés respectivement NORD et SUD. NORD est le premier à jouer
* Les 2 piles formant la base d’un joueur sont présentées en indiquant (entre crochet) la valeur de la carte se trouvant au sommet de la pile.
* La valeur des cartes est toujours présentée sur 2 chiffres (02 pour la carte de valeur 2 par exemple)
* Une pile ascendante est désignée par le caractère `^` et une pile descendante par `v`. Initialement, la base de chaque joueur est égale a `^[01] v[60]`
* Pour chaque joueur est indiqué entre parenthèses le nombres de cartes qu'il a dans sa main, et dans sa pioche. 
* affichage attendu avant lecture d'entrée utilisateur : 
	 ```c
	NORD ^[01] v[60] (m6p52)
	SUD  ^[01] v[60] (m6p52)
	cartes NORD { 15 20 23 32 41 48 }
	>
	```
* exemple de format de lecture du coup : `15^ 20^ 48v`. Si la saisie est erronée, alors aucun message n'est donné, mais on attend une nouvelle lecture avec l'affichage de l'invite de saisie par `#>`


### format de rendu imposé : 

* page de garde avec nom + groupes binômes, objet du dossier
* table des matières
* intro
* Diagramme UML des classes de l'appli.
* Code java des tests unitaires
* Code Java complet du projet. Ordre pour faciliter la lecture. Aller des classes élémentaires aux plus complexes
* bilan de projet (ce qui peut être amélioré, ou difficultés)

Rapport en format PDF, dossier nommé src contenant tous les fichiers java, qui doivent refléter la décomposition en paquetages (aka. format d'un pj eclipse)
