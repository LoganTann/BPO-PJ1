## drafts projet java

### déroulement (technique) d'une partie
**début de partie :**
* Génération de deux joueurs : NORD et SUD. Pour chaqun d'entre eux : 
* on génère son paquet de 60 __cartes numérotées de 1 à 60__,
* il pose devant lui la carte 1 et 60 pour former la pile ascendante et descendante
* il mélange les 58 cartes restantes 
* il prends 6 cartes en main pour commencer. Chaque joueur a donc six cartes dans sa main et 52 dans sa pioche.

**main loop :** les deux joueurs jouent chacun leurs tours.
* Affichage des deux piles de chaque personnages : 
	```
	NORD ^[01] v[60] (m6p52)
	SUD  ^[01] v[60] (m6p52)
	```
	Entre crochet la valeur de la carte se trouvant au sommet de la pile.  
	Une pile ascendante est désignée par le caractère `^` et une pile descendante par `v`.  
	La valeur des cartes est toujours présentée sur 2 chiffres (02 pour la carte de valeur 2 par exemple)  
	on indique le nombre de cartes dans sa main, et dans sa pioche
* Affichage des cartes que possède le joueur dans ses mains, présentées par ordre croissant, encadrées par des accolades et séparées par des espaces. Exemple :  
	```
	cartes NORD { 15 20 23 32 41 48 }
	```
* Affichage de l'invite de saisie `>`. 
* Lecture de l'entrée du joueur sous un format similaire au suivant : `15^ 20^ 48v`.  
  Si la saisie est erronée, alors aucun message n'est donné, mais on attend une nouvelle lecture avec l'affichage de l'invite de saisie par `#>`  
  Le joueur actuel doit poser au moins *deux cartes* parmi celles qu'il a dans sa __main__ sinon sa partie est perdue sur sa base ou celle de l'ennemi  
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


### format de rendu imposé : 

* page de garde avec nom + groupes binômes, objet du dossier
* table des matières
* intro
* Diagramme UML des classes de l'appli.
* Code java des tests unitaires
* Code Java complet du projet. Ordre pour faciliter la lecture. Aller des classes élémentaires aux plus complexes
* bilan de projet (ce qui peut être amélioré, ou difficultés)

Rapport en format PDF, dossier nommé src contenant tous les fichiers java, qui doivent refléter la décomposition en paquetages (aka. format d'un pj eclipse)


## le tout en pseudo code 
```
# pré diagramme UML + pseudo code possible du jeu

carte = int

objet jeu ? : ou app.
	- nombre de cartes
	main() :
		joueur("NORD")
		joueur("SUD")
		boolean tictac = true;
		while playing : 	
			sout NORD
			sout SUD
			if (tictac) {
				loop(NORD, SUD);
			} else {
				loop(SUD, NORD);
			}
			tictac = !tictac;
		
	loop(me, you) :
		// sortie
		sout me.handToString()
		
		// lecture entrée
		boolean error = false
		String input;
		(si ne peut pas jouer deux cartes, alors throw PerduException(me.getname))
		do {
			sout (error) ? "#>" : ">";
			input = sc.next("");
			error = !formatValide(input);
			
			if (error) continue; // relancer la lecture
			boolean playedEnemy = false;
			(pour chaque action : )
				si poser sur sa base : 
					si peut pas poser, error = true, continue
				si base adverse : 
					si playedEnemy, error = true, continue
					si peut pas poser, idem
					playedEnemy = true
			if (playedEnemy) (compléter 6 cartes)
			else (piocher 2 cases)
			idem pour la base de l'autre
			break; (car on aurait eu continue avant en cas de pb)
		} while (1)
		
		// interprétation du résultat
		
	isValid(input) : 
		true si entrée valide, false sinon
		tba

objet joueur : 
	- Paquet packet
	- ArrayList<int> hand;
	- Pile pileASC;
	- Pile pileDESC;
	- String name;
	// init
	constructeur (playerName) : 
		new Paquet()
		new pile(ASC)
		new pile(DESC)
		déplacer paquet[0] -> pileASC
		déplacer paquet[59] -> pileDESC
		paquet.shuffle()
		répéter 5 fois : déplacer paquet [0] -> main
		initialiser playerName
	
	// affichage
	toString() : 
		playerName
		+ pileASC.toString()
		+ pileDESC.toString()
		+ this.cardsCountToString()
	cardsCountToString() : "(m6p52)"
	handToString() : "cartes ${this.name} {" + foreach elems of this.hand + "}"
	
	// getters/setters
	getName()
	
objet Pile :
	- ...
	- order 
	
	int getTopValue()
	peutPoserSurMoi(coup, pile AutrePile):
		if this.topValue % 10 == coup % 10 return true;
		// note : redondance. Il suffirait d'inverser cette condition par fx suivante
		if this.order == DESC:
			return this.topValue > coup && autrePile.getTopValue < coup
		else return this.topValue < coup && autrePile.getTopValue > coup
		
	peutPoserSurAdversaire(coup):
		if this.order == asc:
			return this.topValue > coup && autrePile.getTopValue < coup
		else return this.topValue < coup && autrePile.getTopValue > coup
		

objet Paquet : 
	- ArrayList<int> content;
	- shuffle() : mélange le paquet de cartes

Initialisation de la partie : 
	- déclaration de NORD et SUD (joueur)


tests unitaires possible : 
	- vérification du push/pop des cartes + déplacement vers la pile d'un joueur
	- vérification de la bonne génération du paquet (sorted)
	- vérification du mélange des cartes
```
