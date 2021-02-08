package appli;

import java.util.ArrayList;
import java.util.Collections;

public class Pack {

    private ArrayList<Integer> pack;

    public Pack(int min, int max) {
        // todo : assert min <= max
        this.pack = new ArrayList<>();

        for (int i = min; i <= max; ++i) {
            pack.add(i);
        }
    }

    /* Fonctions pour intervenir sur le paquet ----------------- */

    /**
     * Mélange le paquet de carte.
     */
    public void shuffle() {
        Collections.shuffle(this.pack);
    }

    /**
     * Récupère une carte dans le paquet (autrement dit, supprime la carte donné sa position + retourne sa valeur)
     *
     * @param index L'indice de la carte. La carte concernée sera supprimée du paquet.
     * @return La valeur de la carte.
     * @implNote On spécifie la carte par son <b>indice</b> et non sa <b>valeur</b> ! On peut tirer partie (ou PAS...)
     *       de cette méthode d'implémentation.
     * @see this.getPack() pour récupérer le paquet. Cela pourrait être utile pour intervenir par <b>valeur</b> plutôt
     *      que par son <b>indice</b>.
     */
    public int pickCard(int index) {
        // TODO : vérif si y'a plus de cartes dans le paquet
        int retval = this.pack.get(index);
        this.pack.remove(index);
        return retval;
    }

    /**
     * Raccourci à pickCard() pour récupérer la dernière carte du paquet
     * @return la valeur de la dernière carte du paquet
     */
    public int pickLastCard() {
        // utilisation de la méthode getPackLength plutôt que this.pack.size() au cas où on change de méthode de stockage
        return this.pickCard( this.getPackLength() - 1);
    }

    /** Raccourci à pickCard() pour récupérer la première carte du paquet
     * @return la valeur de la première carte du paquet
     * @implNote Une fois le paquet mélangé, cette méthode est utile pour piocher une carte au hasard.
     */
    public int pickFirstCard() {
        return this.pickCard( 0);
    }


    /* Fonctions pour obtenir des infos sur le paquet ----------------- */

    public ArrayList<Integer> getPack() {
        return this.pack;
    }

    public int getPackLength() {
        return this.pack.size();
    }
}
