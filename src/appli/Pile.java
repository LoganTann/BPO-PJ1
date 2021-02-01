package appli;

import java.util.ArrayList;

public class Pile {

    public enum TypePile { ASC, DESC };

    private TypePile type;
    private ArrayList<Integer> cartesPosees;
    private Paquet paquet;

    public Pile(TypePile type, Paquet p) {
        this.type = type;
        this.paquet = p;
        this.cartesPosees = new ArrayList<Integer>();
    }

    public Paquet getRegPaquet() {
        return this.paquet;
    }

    public ArrayList<Integer> getCartesPosees() {
        return this.cartesPosees;
    }

}
