package appli;

/**
 *
 * @author Antoine <antoine@jiveoff.fr> on 01/02/2021
 * @project BPO-PJ1
 */
public class Action {
    private final int card;
    private final Stack.TypeStack type;
    private final boolean playsInEnemyStack;

    /**
     * CLASSE Action : définit une action possible du joueur (tel que poser la carte 4 sur le paquet adverse).
     * Les méthodes permettent d'effectuer des vérifications de jouabilité. Les arguments de ce constructeurs
     * constituent les uniques variables privées de la classe
     * @param card      Numéro de la carte source qui sera déplacée
     * @param type      définit dans quel type de pile cible la carte sera posée
     * @param adverse   indique si la pile cible se trouve dans le camp ennemi (et traiter les règles supplémentaires en
     *                  conséquence)
     */
    Action(int card, Stack.TypeStack type, boolean adverse) {
        this.card = card;
        this.type = type;
        this.playsInEnemyStack = adverse;
    }

    public String toString() {
        StringBuilder retval = new StringBuilder();
        if (this.card < 10) retval.append(0);
        retval.append(this.card);
        retval.append( (this.type == Stack.TypeStack.ASC) ? "^" : "v");
        if (this.playsInEnemyStack) retval.append("’");
        return retval.toString();
    }

    public int getCard() {
        return card;
    }
    public Stack.TypeStack getType() {
        return type;
    }


    public boolean handlePlayingInEnemyStack(boolean currentState) throws BadMoveException {
        if (this.playsInEnemyStack) {
            if (currentState) {
                throw new BadMoveException("Second jeu dans une pile de l'adversaire invalide !");
            } else {
                currentState = true;
            }
        }
        return currentState;
    }

    private boolean multiplesOfTenValid(Stack stackAsc, Stack stackDesc) {
        int top = (this.type == Stack.TypeStack.ASC) ? stackAsc.getCardOnTop() : stackDesc.getCardOnTop();
        return this.card % 10 == top % 10;
    }

    // aurait pu être public, mais vu comment c'est appelé, bah autant laisser tel quel
    private void validMove (Player me, Player you) throws BadMoveException {
        if (this.playsInEnemyStack) {
            Stack StackAsc = you.getStack(Stack.TypeStack.ASC), StackDesc = you.getStack(Stack.TypeStack.DESC);
            if (this.card > StackAsc.getCardOnTop() || this.card < StackDesc.getCardOnTop()) {
                String sout = "Le coup ne respecte pas la règle « la carte doit être plus petite sur la pile ascendante"
                            + " adverse, et plus grande sur la pile descendante adverse », ou bien vous avez joué plus "
                            + "d'une fois la même carte \n";
                sout += String.format(
                            "Dump : DESC=%02d card=%02d ASC=%02d",
                            StackAsc.getCardOnTop(), this.card, StackDesc.getCardOnTop()
                );
                throw new BadMoveException(sout);
            }
        } else {
            Stack StackAsc = me.getStack(Stack.TypeStack.ASC), StackDesc = me.getStack(Stack.TypeStack.DESC);
            if (this.card < StackAsc.getCardOnTop() || this.card > StackDesc.getCardOnTop()) {
                int top = (this.type == Stack.TypeStack.ASC) ? StackAsc.getCardOnTop() : StackDesc.getCardOnTop();
                if (this.card % 10 != top % 10) {
                    String sout = "Le coup ne respecte pas la règle « la carte doit être plus grande sur la pile "
                            + "ascendante adverse, et plus petite sur la pile descendante adverse », ni la règle des"
                            + " multiples de dix, ou bien vous avez joué plus d'une fois la même carte \n";
                    sout += String.format("Dump : DESC=%02d card=%02d ASC=%02d",
                            this.card, StackAsc.getCardOnTop(), StackDesc.getCardOnTop());
                    throw new BadMoveException(sout);
                }
            }
        }
    }

    public void execute(Player me, Player you) throws BadMoveException {
        validMove(me, you);
        // si pas throw <=> si le coup + valeur de la carte est jouable :
        if (this.playsInEnemyStack) {
            you.putDown(me, this);
        } else {
            me.putDown(me,this);
        }
    }

    // todo : public static playable(int cardValue, Player me, Player you)
}
