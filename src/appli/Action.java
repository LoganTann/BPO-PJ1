package appli;

/**
 * @author Antoine <antoine@jiveoff.fr> on 01/02/2021
 * @project BPO-PJ1
 */
public class Action {

    int card;
    Stack.TypeStack type;
    boolean adverse;

    Action(int card, Stack.TypeStack type, boolean adverse) {
        this.card = card;
        this.type = type;
        this.adverse = adverse;
    }

    //TODO : programmer l'action ?
}
