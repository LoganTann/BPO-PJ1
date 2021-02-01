package appli;

public class Application {

    public static void main(String[] args) {

        Paquet paquet = new Paquet(1, 5);
        paquet.pop(1);
        paquet.pop(5);

        Pile pileASC = new Pile(Pile.TypePile.ASC, paquet);
        Pile pileDESC = new Pile(Pile.TypePile.DESC, paquet);

        for(int i: pileASC.getRegPaquet().getPaquet()) {
            System.out.println(i);
        }

        System.out.println("-----------------");

        paquet.pop(3);
        for(int i: pileASC.getRegPaquet().getPaquet()) {
            System.out.println(i);
        }

    }

}
