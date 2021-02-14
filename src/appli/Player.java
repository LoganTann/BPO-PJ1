package appli;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Antoine <antoine@jiveoff.fr> on 01/02/2021
 * @project BPO-PJ1
 */
public class Player {

    /** name : son nom (attendu NORD ou SUD)*/
    private final String name;

    /** pack : la pioche du joueur */
    private Pack pack;
    private Pack pack_save;

    /** stackASC / stackDESC : les deux piles de cartes du joueur */
    private Stack stackASC, stackDESC;
    private Stack stackASC_save, stackDESC_save;

    /** hand : les cartes qui se trouvent dans la main du joueur*/
    private ArrayList<Integer> hand;
    private ArrayList<Integer> hand_save;

    /***
     * Constructeur de l'entité Joueur
     * @param name son joli nom pour l'affichage
     */
    public Player(String name) {
        assert name.length() < 5 : "Le nom ne doit pas excéder 4 caractères. Valeur conseillée : NORD ou SUD.";
        this.name = name;

        this.pack = new Pack(1, 60);
        this.stackASC = new Stack(Stack.TypeStack.ASC);
        this.stackDESC = new Stack(Stack.TypeStack.DESC);

        this.stackASC.addCard( this.pack.pickCard(0) );
        this.stackDESC.addCard( this.pack.pickLastCard() );

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
        String retval = String.format("%-5s", this.name);
        retval += stackASC.toString() + " " + stackDESC.toString();
        retval += " (m" + this.hand.size() + "p" + this.pack.getPack().size() + ")";
        return retval;
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

    public boolean hadNoMoreCards() {
        return this.pack.isEmpty();
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
        return (type == Stack.TypeStack.ASC) ? this.stackASC : this.stackDESC;
    }

    public boolean removeCardFromHand(int cardValue) {
        // cast nécessaire pour éviter confusion avec la surcharge .remove(int index);
        return this.hand.remove((Object) cardValue);
    }
    public boolean canRemoveFromHand(int cardValue) {
        return this.hand.contains(cardValue);
    }

    public void sortHand() {
        Collections.sort(this.hand);
    }

    public void putDown(Player cardSource, Action theAction) throws BadMoveException {
        int card = theAction.getCard();
        if (!cardSource.removeCardFromHand(card)) {
            throw new BadMoveException("La carte du coup " + theAction.toString() + " n'existe même pas dans votre main...");
        }
        Stack target = this.getStack(theAction.getType());
        target.addCard(card);
    }

    public void save() {
        this.pack_save = new Pack(this.pack);
        this.stackASC_save = new Stack(this.stackASC);
        this.stackDESC_save = new Stack(this.stackDESC);
        this.hand_save = new ArrayList<>(this.hand);
    }
    public void restoreSave() {
        this.pack = this.pack_save;
        this.stackASC = this.stackASC_save;
        this.stackDESC = this.stackDESC_save;
        this.hand = this.hand_save;
    }


    /*                      /!\ ATTENTION : /!\
       ces setteurs sont utilisés seulement à des fins de tests unitaires
    */
    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public void setStackASC(Stack stackASC) {
        this.stackASC = stackASC;
    }

    public void setStackDESC(Stack stackDESC) {
        this.stackDESC = stackDESC;
    }

    public void setHand(ArrayList<Integer> hand) {
        this.hand = hand;
    }
}
