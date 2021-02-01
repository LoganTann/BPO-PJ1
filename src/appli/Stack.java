package appli;

import java.util.ArrayList;

public class Stack {

    public enum TypeStack { ASC, DESC };

    private TypeStack type;
    private ArrayList<Integer> playedCards;
    private Pack pack;

    public Stack(TypeStack type, Pack p) {
        this.type = type;
        this.pack = p;
        this.playedCards = new ArrayList<Integer>();
    }

    public Pack getPack() {
        return this.pack;
    }

    public void addCard(int carte) {
        this.playedCards.add(carte);
    }

    public ArrayList<Integer> getPlayedCards() {
        return this.playedCards;
    }

    public int getCardOnTop() {
        int i = this.playedCards.size();
        return this.playedCards.get(i - 1);
    }

    public boolean exists(int card) {
        return this.getPlayedCards().contains(card);
    }

    public String toString() {
        return ((type == TypeStack.ASC) ? "^" : "v") + "[" + this.getCardOnTop() + "]";
    }

}
