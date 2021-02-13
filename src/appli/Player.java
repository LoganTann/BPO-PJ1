package appli;

import java.util.ArrayList;

/**
 * @author Antoine <antoine@jiveoff.fr> on 01/02/2021
 * @project BPO-PJ1
 */
public class Player {

    /** name : son nom (attendu NORD ou SUD)*/
    private final String name;

    /** pack : la pioche du joueur */
    private final Pack pack;

    /** stackASC / stackDESC : les deux pile de cartes du joueur */
    private final Stack stackASC, stackDESC;

    /** hand : les cartes qui se trouvent dans la main du joueur*/
    private final ArrayList<Integer> hand;

    /***
     * Constructeur de l'entité Joueur
     * @param name son joli nom pour l'affichage
     */
    public Player(String name) {
        this.name = name;

        this.pack = new Pack(1, 60);
        this.stackASC = new Stack(Stack.TypeStack.ASC);
        this.stackDESC = new Stack(Stack.TypeStack.DESC);

        this.stackASC.addCard( this.pack.pickCard(0) );
        this.stackASC.addCard( this.pack.pickLastCard() );

        // à partir du moment où on mélange, prendre la première carte équivaut à prendre une carte du paquet au hasard
        this.pack.shuffle();

        // génération de la main
        this.hand = new ArrayList<>();
        for(int i = 0; i < 6; ++i) {
            this.hand.add( this.pack.pickFirstCard() ); // < R to L :prendre la première carte et l'ajouter dans la main
        }
    }

    public String toString() {
        // possible todo : string builder pour impressionner le prof mm si c'est moche
        return this.name + " " + stackASC.toString() + " " + stackDESC.toString()
                + " (m" + this.hand.size() + "p" + this.pack.getPack().size() + ")";
    }

    public String getName() {
        return this.name;
    }

    public String hand_toString() {
        // cartes NORD { 15 20 23 32 41 48 }
        StringBuilder retval = new StringBuilder("cartes ");
        retval.append(this.name);
        retval.append(" { ");
        for (int carte : this.hand) {
            retval.append(carte < 10 ? ("0" + carte) : carte);
            retval.append(" ");
        }
        retval.append("}");
        return retval.toString();
    }

    public boolean isHandEmpty() {
        return this.hand.isEmpty();
    }

    public void addCardsToHaveSixInHand() {
        while (this.hand.size() < 6) {
            this.pickCardAndAddInHand();
        }
    }
    public void pickCardAndAddInHand() {
        this.hand.add( this.pack.pickCard(0) );
    }

    public Stack getStack(Stack.TypeStack type) {
        return (type == Stack.TypeStack.ASC) ? stackASC : stackDESC;
    }

    public boolean removeCardFromHand(int cardValue) {
        // cast nécessaire pour éviter confusion avec la surcharge .remove(int index);
        return this.hand.remove((Object) cardValue);
    }

    public void putDown(Player cardSource, Action theAction) throws BadMoveException {
        int card = theAction.getCard();
        if (!cardSource.removeCardFromHand(card)) {
            throw new BadMoveException("La carte du coup " + theAction.toString() + " n'existe même pas dans votre main...");
        }
        Stack target = this.getStack(theAction.getType());
        target.addCard(card);
    }

    public void restoreSave() {
    }

    public void save() {
    }
}
