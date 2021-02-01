package appli;

import java.util.ArrayList;

/**
 * @author Antoine <antoine@jiveoff.fr> on 01/02/2021
 * @project BPO-PJ1
 */
public class Player {

    private String name;

    /**
     * @brief pack : la pioche du joueur
     */
    private Pack pack;
    /**
     * @brief stack/ stackDESC : les deux pile de cartes du joueur
     */
    private Stack stackASC, stackDESC;
    private ArrayList<Integer> hand;

    /***
     * @brief Constructeur de l'entité Joueur
     * @param name son joli nom pour l'affichage
     */
    public Player(String name) {
        this.name = name;

        pack = new Pack(1, 60);
        stackASC = new Stack(Stack.TypeStack.ASC, pack);
        stackDESC = new Stack(Stack.TypeStack.DESC, pack);

        try {
            this.putDownCard(pack, stackASC, 1);
            this.putDownCard(pack, stackDESC, 60);
        } catch(Exception e) {
            System.out.println("Une erreur est survenue: " + e.getMessage());
        }

        this.generateHand();
    }

    private void generateHand() {

        this.hand = new ArrayList<Integer>();

        for(int i = 0; i < 6; ++i) {
            // TODO : ça pique les yeux
            this.hand.add(this.pack.getPack().get(0));
            this.pack.getPack().remove(0);
        }

    }

    public String toString() {
        // possible todo : string builder pour impressionner le prof mm si c'est moche
        return this.name + " " + stackASC.toString() + " " + stackDESC.toString() + " (m" + this.hand.size() + "p" + this.pack.getPack().size() + ")";
    }

    // todo : meilleur nom ou contenu au besoin
    public String handString() {
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
    // cartes NORD { 15 20 23 32 41 48 }

    /**
     * @brief Permet de déplacer une carte d'une pioche vers une pile de cartes
     * @param from Pack dont la carte sera prélevée
     * @param to Stack dont cette même carte va être déposée
     * @param card la **valeur** (et non l'index) de la carte qui sera prélevée
     * @throws Exception
     */
    private void putDownCard(Pack from, Stack to, int card) throws Exception {
        // TODO : faire une méthode exists() au lieu de from.getPack().indexOf(card)
        if (!from.exists(card)) {
            throw new Exception("La carte à déplacer n'existe pas dans le paquet à piocher");
        }
        if (to.exists(card)) {
            throw new Exception("La carte à déplacer existe déjà dans le paquet de destination");
        }
        // TODO : check la dizaine sur la stack

        from.removeCard(card);
        to.addCard(card);
    }

    /**
     * @brief permet de gérer l'action
     * @param number
     * @param source
     * @return
     */
    public boolean completeWithCards(int number, Pack source) {
        // todo
        return true;
    }

}
