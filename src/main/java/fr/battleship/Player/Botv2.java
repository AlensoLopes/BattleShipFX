package fr.battleship.Player;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Board.DisplayBoard;
import fr.battleshipfx.Controller.GameController;

public class Botv2 extends Bot{

    private int[][] boatCoord;
    private int[][] neso;
    private boolean hit;
    private String[][] board_copy;
    private String style[];
    private int nbHit = 0;
    private int dim = 0;
    public static final String[][] size = new String[][]{{"1", "■"}, {"2","▄"},{"3","▀"}, {"4", "█"}};

    public Botv2() {
        super();
        boatCoord = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
        neso = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
        style = new String[4];
    }

    @Override
    public void placeShipBot(String[][] array) {
        super.placeShipBot(array);
    }

    @Override
    public boolean shoot(String[][] array) {
        return onShoot(array);
    }



    protected boolean onShoot(String[][] array){
        if(board_copy == null){
            board_copy = copy(array);
            return super.shoot(array);
        }
        int x = coordHit[0];
        int y = coordHit[1];
        new DisplayBoard().displayBoard(board_copy);
        if(board_copy[x][y].equals(" ") && boatCoord[dim][0] == -1){
            return super.shoot(array);
        }

        System.out.println("X : " + x + "Y : " + y + "XH : " + boatCoord[dim][0] + "YH" + boatCoord[dim][1]);
        if(boatCoord[dim][0] == -1){
            boatCoord[dim][0] = x;
            boatCoord[dim][1] = y;
            style[dim] = board_copy[x][y];
            nbHit++;
        }
        if(!board_copy[x][y].equals(style[dim])){
            style[dim+1] = board_copy[x][y];
            boatCoord[dim+1][dim] = x;
            boatCoord[dim+1][dim+1] = y;
        }else{
            for (int i = 0; i < size.length; i++) {
                if(style[dim].equals(size[i][1]) && nbHit == Integer.parseInt(size[i][0])){
                    dim++;
                    if(boatCoord[dim][0] == -1) {
                        neso = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
                        return super.shoot(array);
                    }
                    return onShoot(array);
                }
            }
        }
        System.out.println("here");

        return shoot(array);
    }

    private String[][] copy(String[][] array){
        String[][] copy = new String[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                copy[i][j] = array[i][j];
            }
        }
        return copy;
    }

    public static void main(String[] args) {

      }
}
