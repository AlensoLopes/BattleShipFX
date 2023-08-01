package fr.battleship.Board;

public class DisplayBoard extends CreateBoard{

/*    private final String BWHITE = "\033[0;107m";
    private final String BNORMAL = "\033[40m";*/
    public void displayBoard(String[][] array){
        System.out.print("  ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < array.length; i++) {
            System.out.print(i+" |");
            for (int j = 0; j < array.length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println(i+" |");
        }
        System.out.print("  ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
    }

    /*
    *  12345678910
    *
    *
    * */
}
