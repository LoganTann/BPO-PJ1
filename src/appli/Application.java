package appli;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Player NORD = new Player("NORD");
        Player SUD = new Player("SUD");
        boolean tictac = true;

        while (true) {
            // affichage des infos des joueurs :
            System.out.println(NORD);
            System.out.println(SUD);
            // C'est le tour de nord ou sud :
            if (tictac) {
                loop(NORD, SUD);
            } else {
                loop(SUD, NORD);
            }
            // todo : gestion d'erreurs en cas de loserException ou winnerException
        }
    }

    public static void loop(Player me, Player you) {
        // TODO : throw un loserException ou winnerException (créer ces types d'exceptions

        System.out.println(me.handString());

        boolean error = false;
        String input;
        Scanner sc = new Scanner(System.in);

        // (si ne peut pas jouer deux cartes, alors throw PerduException(me.getname))

        // la boucle suivante sera arrêtée lorsque l'entrée sera valide et les coups joués.
        do {
            System.out.print((error) ? "#> " : "> ");
            input = sc.nextLine();
            // vrai si l'entrée ne respecte pas le format [carte][designation de pile]
            // Si tel est le cas, on va relancer la boucle <=> redemander l'entrée avec le prompt modifié
            // todo : meilleure solution
            ArrayList<Action> parsedActions = inputFormatValid(input);
            error = parsedActions.size() <= 0;
            if (error) continue;

            // indique si on a posé une carte sur un tas ennemi
            // en effet, on ne peut poser qu'une seule carte dans ce tas par tour !
            boolean playedEnemy = false;

            // pour chaque action entrée durant ce tour :
            /*
                interpréter le coup
				si posé sur sa base :
					si peut pas poser, error = true, continue
				si base adverse :
					si playedEnemy, error = true, continue
					si peut pas poser, idem
					playedEnemy = true

			if (playedEnemy) (compléter 6 cartes)
			else (piocher 2 cases)
			idem pour la base de l'autre
			break; (car on aurait eu continue avant en cas de pb)
             */

        } while (true);

    }

    /**
     * @brief Vérifie (actuellement) si l'entrée est valide
     * @param input
     * @return
     */
    public static ArrayList<Action> inputFormatValid(String input) {
        // todo : rendre + beau ce code
        ArrayList<Action> actions = new ArrayList<Action>();

        String[] coups = input.split(" ");
        for (String coup : coups) {
            // 02v ou 04^ ou 59v
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
        // TODO : coup jouable
        return actions;
    }
}
