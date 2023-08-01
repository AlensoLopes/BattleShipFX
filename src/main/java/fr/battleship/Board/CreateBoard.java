package fr.battleship.Board;

public class CreateBoard {
    public static final int DIM = 10;
    public String[][] array = new String[DIM][DIM];

    public String[][] createBoard(){
        for(int i = 0; i<array.length;i++){
            for(int j = 0; j<array.length; j++){
                array[i][j] = " ";
            }
        }
        return this.array;
    }
}
