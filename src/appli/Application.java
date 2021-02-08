package appli;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Player NORD = new Player("NORD");
        Player SUD = new Player("SUD");
        boolean NORD_plays = true;

        while (true) {
            // affichage des infos des joueurs :
            System.out.println(NORD);
            System.out.println(SUD);
            // C'est le tour de nord ou sud :
            if (NORD_plays) {
                loop(NORD, SUD);
            } else {
                loop(SUD, NORD);
            }
            NORD_plays = !NORD_plays;
            // todo : gestion d'erreurs en cas de loserException ou winnerException
        }
    }

    public static void loop(Player me, Player you) {
        // TODO : throw un loserException ou winnerException (créer ces types d'exceptions)

        System.out.println(me.hand_toString());

        boolean error = false;
        String input;
        Scanner sc = new Scanner(System.in);

        // TODO : (si ne peut pas jouer deux cartes, alors throw PerduException(me.getname))

        // la boucle suivante sera arrêtée lorsque l'entrée sera valide et les coups joués.
        while (true) {
            System.out.print((error) ? "#> " : "> ");

            // TODO : try catch ? Le soucis, c'est que si on peut pas lire, on fais quoi ? car il ne faut pas afficher le prompt.
            input = sc.nextLine();

            ArrayList<Action> parsedActions = parseInput(input);
            error = parsedActions.size() < 2;
            if (error) continue;

            /** indique si on a posé une carte sur un tas ennemi
             en effet, on ne peut poser qu'une seule carte dans ce tas par tour ! */
            boolean playedEnemy = false;

            // TODO : me.save(); you.save();
            try {
                for (Action action: parsedActions) {
                    playedEnemy = action.handlePlayingInEnemyStack(playedEnemy);
                    action.execute(me, you);
                }
            } catch (BadMoveException err) {
                // TODO : me.restoreSave(); you.restoreSave();
                error = true;
                continue;
            }

            if (playedEnemy) {
                me.addCardsToHaveSixInHand();
            } else {
                me.pickCardAndAddInHand();
                me.pickCardAndAddInHand();
            }

            break;
        }

    }


    /**
     * Décompose et vérifie l'entrée de l'utilisateur en un tableau d'actions interprétables par le programme.
     * @param input (String) l'entrée de l'utilisateur
     * @return un ArrayList contenant chaque actions (type Action). Vide si entrée invalide.
     */
    public static ArrayList<Action> parseInput(String input) {
        // todo : rendre + beau ce code
        ArrayList<Action> actions = new ArrayList<Action>();

        String[] coups = input.split(" ");
        for (String coup : coups) {
            // 02v ou 04^’ ou 59v
            int card;
            try {
                card = Integer.parseInt(coup.substring(0, 1));
            } catch(NumberFormatException e) {
                actions.clear();
                return actions;
            }

            try {
                if (coup.charAt(2) != '^') {
                    if (coup.charAt(2) != 'v') {
                        actions.clear();
                        return actions;
                    }
                }
                if (coup.length() > 3 && coup.charAt(3) != '’') {
                    actions.clear();
                    return actions;
                }
            } catch(IndexOutOfBoundsException e) {
                actions.clear();
                return actions;
            }

            actions.add(new Action(card, (coup.charAt(2) == '^' ? Stack.TypeStack.ASC : Stack.TypeStack.DESC), coup.length() > 3));
        }
        return actions;
    }
}
