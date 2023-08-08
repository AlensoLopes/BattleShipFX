package fr.battleship.Player;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Board.DisplayBoard;
import fr.battleshipfx.Controller.GameController;

import java.util.Objects;

public class Botv2 extends Bot{

    private int[][] boatCoord;
    private String[][] board_copy;
    private String style[];
    private int nbHit = 0;
    private int[] notHit;
    private int dim = 0;
    public static final String[][] size = new String[][]{{"1", "■"}, {"2","▄"},{"3","▀"}, {"4", "█"}};

    public Botv2() {
        super();
        boatCoord = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
        notHit = new int[]{-1,-1,-1,-1};
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
        //new DisplayBoard().displayBoard(board_copy);
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
                        nbHit = 0;
                        notHit = new int[]{-1,-1,-1,-1};
                        return super.shoot(array);
                    }
                    return onShoot(array);
                }
            }
        }
        try{
            if(checkCoord(array, boatCoord[dim][0], boatCoord[dim][1]-1) && notHit[0] != 1){
                if(board_copy[boatCoord[dim][0]][boatCoord[dim][1]-1].equals(style[dim])){
                    nbHit++;
                    boatCoord[dim][0] = boatCoord[dim][0];
                    boatCoord[dim][1] = boatCoord[dim][1]-1;

                }else notHit[0] = 1;
                System.out.println(boatCoord[dim][0] + "YH" + boatCoord[dim][1] + "NBHUT" + nbHit);
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);

            }else if(checkCoord(array, boatCoord[dim][0]+1, boatCoord[dim][1]) && notHit[1] != 1){
                if(board_copy[boatCoord[dim][0]+1][boatCoord[dim][1]].equals(style[dim])){
                    nbHit++;
                    boatCoord[dim][0] = boatCoord[dim][0]+1;
                    boatCoord[dim][1] = boatCoord[dim][1];

                }else notHit[1] = 1;
                System.out.println(boatCoord[dim][0] + "YH" + boatCoord[dim][1] + "NBHUT" + nbHit);
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);

            }else if(checkCoord(array, boatCoord[dim][0], boatCoord[dim][1]+1)&& notHit[2] != 1){
                if(board_copy[boatCoord[dim][0]][boatCoord[dim][1]+1].equals(style[dim])){
                    nbHit++;
                    boatCoord[dim][0] = boatCoord[dim][0];
                    boatCoord[dim][1] = boatCoord[dim][1]+1;

                }else notHit[2] = 1;
                System.out.println(boatCoord[dim][0] + "YH" + boatCoord[dim][1] + "NBHUT" + nbHit);
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);

            }else if(checkCoord(array, boatCoord[dim][0]-1, boatCoord[dim][1])&& notHit[3] != 1){
                if(board_copy[boatCoord[dim][0]+1][boatCoord[dim][1]].equals(style[dim])){
                    nbHit++;
                    boatCoord[dim][0] = boatCoord[dim][0]-1;
                    boatCoord[dim][1] = boatCoord[dim][1];
                }else notHit[3] = 1;
                System.out.println(boatCoord[dim][0] + "YH" + boatCoord[dim][1] + "NBHUT" + nbHit);
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);
            }
        }catch (ArrayIndexOutOfBoundsException ignored){

        }

        return super.shoot(array);
    }

    private boolean checkCoord(String[][] array, int x, int y){
        return x >= 0 && x < CreateBoard.DIM || (y < 0 || y >= CreateBoard.DIM);
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
        CreateBoard createBoard = new CreateBoard();
        DisplayBoard displayBoard = new DisplayBoard();
        String[][] board = createBoard.createBoard();
        Botv2 botv2 = new Botv2();
        botv2.placeShipBot(board);
        while(PlayerHuman.getNbShipAlive(board) != 0){
            botv2.shoot(board);
            displayBoard.displayBoard(board);
        }

    }
}
