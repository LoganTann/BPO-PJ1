package appli;

import java.util.ArrayList;
import java.util.Collections;

public class Pack {

    private ArrayList<Integer> pack;

    public Pack(int min, int max) {
        // todo : assert min <= max
        this.pack = new ArrayList<Integer>();
        this.generatePack(min, max);
    }

    public void generatePack(int min, int max) {
        for(int i = min; i <= max; ++i) {
            pack.add(i);
        }
        Collections.shuffle(pack);
    }

    public ArrayList<Integer> getPack() {
        return this.pack;
    }

    public void removeCard(int card) {
        this.pack.remove((Integer) card);
    }

    public boolean exists(int card) {
        return this.pack.contains(card);
    }

}
