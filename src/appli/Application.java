package appli;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Player NORD = new Player("NORD");
        Player SUD = new Player("SUD");
        boolean NORD_plays = true;

        boolean isPlaying = true;
        while (isPlaying) {
            // affichage des infos des joueurs :
            System.out.println(NORD);
            System.out.println(SUD);

            try {
                // C'est le tour de nord ou sud :
                if (NORD_plays) {
                    turn(NORD, SUD);
                } else {
                    turn(SUD, NORD);
                }
            } catch (WinnerException thePlayer) {
                System.out.println("partie finie, " + thePlayer.toString() + " a gagné");
                isPlaying = false;
            } catch (LoserException thePlayer) {
                String winnerName = thePlayer.toString().equals(NORD.getName()) ? SUD.getName() : NORD.getName();
                System.out.println("partie finie, " + winnerName + " a gagné");
                isPlaying = false;
            }

            NORD_plays = !NORD_plays;
        }
    }

    public static void turn(Player me, Player opponent) throws WinnerException, LoserException {

        System.out.println(me.hand_toString());

        boolean showErrorPrompt = false, requestValidMove = true;
        String input;
        Scanner sc = new Scanner(System.in);

        // TODO : (si ne peut pas jouer deux cartes, alors throw new PerduException(me.getName())

        while (requestValidMove) {
            System.out.print((showErrorPrompt) ? "#> " : "> ");
            input = sc.nextLine();

            // Interprétation de l'entrée
            ArrayList<Action> parsedActions = parseInput(input);
            showErrorPrompt = parsedActions.size() < 2;
            if (showErrorPrompt) continue;

            // Exécution de l'entrée
            boolean playedEnemy = false;
            me.save();
            opponent.save();
            try {
                for (Action action: parsedActions) {
                    playedEnemy = action.handlePlayingInEnemyStack(playedEnemy);
                    action.execute(me, opponent);
                }
            } catch (BadMoveException err) {
                me.restoreSave();
                opponent.restoreSave();
                showErrorPrompt = true;
                continue;
            }

            // Conditions de fin de tour
            if (me.isHandEmpty()) {
                throw new WinnerException(me.getName());
            }
            if (playedEnemy) {
                me.addCardsToHaveSixInHand();
            } else {
                me.pickCardAndAddInHand();
                me.pickCardAndAddInHand();
            }

            // fin du tour
            requestValidMove = false;
        }

    }


    /**
     * Décompose et vérifie l'entrée de l'utilisateur en un tableau d'actions interprétables par le programme.
     * @param input (String) l'entrée de l'utilisateur
     * @return un ArrayList contenant chaque actions (type Action). Vide si entrée invalide.
     */
    public static ArrayList<Action> parseInput(String input) {
        // todo : rendre + beau ce code
        ArrayList<Action> actions = new ArrayList<>();

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
