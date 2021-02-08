package appli;

/**
 * @author Antoine <antoine@jiveoff.fr> on 01/02/2021
 * @project BPO-PJ1
 */
public class Action {


    int card;
    Stack.TypeStack type;
    boolean playsInEnemyStack;

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
                throw new BadMoveException("Le coup ne respecte pas la règle « la carte doit être plus petite sur la pile ascendante adverse, et plus grande sur la pile descendante adverse » ");
            }
        } else {
            Stack StackAsc = me.getStack(Stack.TypeStack.ASC), StackDesc = me.getStack(Stack.TypeStack.DESC);
            if (this.card < StackAsc.getCardOnTop() || this.card > StackDesc.getCardOnTop()) {
                int top = (this.type == Stack.TypeStack.ASC) ? StackAsc.getCardOnTop() : StackDesc.getCardOnTop();
                if (this.card % 10 != top % 10) {
                    throw new BadMoveException("Le coup ne respecte ni la règle « la carte doit être plus grande sur la pile ascendante, et plus petite sur la pile descendante », ni la règle des mutiples de dix");
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
