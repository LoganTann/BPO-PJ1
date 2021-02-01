package appli;

import java.util.ArrayList;
import java.util.Collections;

public class Paquet {

    private ArrayList<Integer> paquet;

    public Paquet(int min, int max) {
        this.paquet = new ArrayList<Integer>();
        this.genererPaquet(min, max);
    }

    public void genererPaquet(int min, int max) {
        for(int i = min; i <= max; ++i) {
            paquet.add(i);
        }
        Collections.shuffle(paquet);
    }

    public ArrayList<Integer> getPaquet() {
        return this.paquet;
    }

    public void pop(int carte) {
        this.paquet.remove((Integer) carte);
    }

}
