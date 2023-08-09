package fr.battleship.Player;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Board.DisplayBoard;
import fr.battleshipfx.Controller.GameController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Botv2 extends Bot{

    private int[][] boatCoord;
    private int[][] coordBot;
    private String[][] board_copy;
    private String style[];
    private int nbHit = 0;
    private int[] notHit;
    private int dim = 0;
    private int nb = 0;
    public static final String[][] size = new String[][]{{"1", "■"}, {"2","▄"},{"3","▀"}, {"4", "█"}};

    public Botv2() {
        super();
        boatCoord = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
        coordBot = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
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
        if(board_copy[x][y].equals(" ") && boatCoord[dim][0] == -1){
            return super.shoot(array);
        }

        if(boatCoord[dim][0] == -1){
            boatCoord[dim][0] = x;
            boatCoord[dim][1] = y;
            style[dim] = board_copy[x][y];
            coordBot[nbHit][0] = boatCoord[dim][0];
            coordBot[nbHit][1] = boatCoord[dim][1];
            nbHit++;
        }
        if(!board_copy[x][y].equals(style[dim])){
            style[dim+1] = board_copy[x][y];
            boatCoord[dim+1][0] = x;
            boatCoord[dim+1][1] = y;
        }else{
            for (int i = 0; i < size.length; i++) {
                    if(style[dim].equals(size[i][1]) && nbHit == Integer.parseInt(size[i][0])){
                        if(boatCoord[dim+1][0] == -1) {
                            nbHit = 0;
                            notHit = new int[]{-1,-1,-1,-1};
                            coordBot = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
                            dim++;
                            board_copy = array;
                            return super.shoot(array);
                        }
                    return onShoot(array);
                }
            }
        }
        try{
            if(checkCoord(array, boatCoord[dim][0], boatCoord[dim][1]-1)
                    && notHit[0] != 1
                    && !botHit.contains(Integer.valueOf(boatCoord[dim][0] + "" + (boatCoord[dim][1]-1)))){

                if(board_copy[boatCoord[dim][0]][boatCoord[dim][1]-1].equals(style[dim])
                && !containsCoord(boatCoord[dim][0], boatCoord[dim][1]-1, coordBot)){
                    boatCoord[dim][0] = boatCoord[dim][0];
                    boatCoord[dim][1] = boatCoord[dim][1]-1;
                    coordBot[nbHit][0] = boatCoord[dim][0];
                    coordBot[nbHit][1] = boatCoord[dim][1];
                    nbHit++;
                }else notHit[0] = 1;
                botHit.add(Integer.valueOf(boatCoord[dim][0] + String.valueOf(boatCoord[dim][1])));
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);

            }else if(checkCoord(array, boatCoord[dim][0]+1, boatCoord[dim][1])
                    && notHit[1] != 1
                    && !botHit.contains(Integer.valueOf((boatCoord[dim][0]+1) + String.valueOf(boatCoord[dim][1])))){

                if(board_copy[boatCoord[dim][0]+1][boatCoord[dim][1]].equals(style[dim])
                        && !containsCoord(boatCoord[dim][0]+1, boatCoord[dim][1], coordBot)){

                    boatCoord[dim][0] = boatCoord[dim][0]+1;
                    boatCoord[dim][1] = boatCoord[dim][1];

                    coordBot[nbHit][0] = boatCoord[dim][0];
                    coordBot[nbHit][1] = boatCoord[dim][1];
                    nbHit++;
                }else notHit[1] = 1;
                botHit.add(Integer.valueOf(boatCoord[dim][0] + String.valueOf(boatCoord[dim][1])));
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);

            }else if(checkCoord(array, boatCoord[dim][0], boatCoord[dim][1]+1)
                    && notHit[2] != 1
                    && !botHit.contains(Integer.valueOf(boatCoord[dim][0] + "" + (boatCoord[dim][1]+1)))){

                if(board_copy[boatCoord[dim][0]][boatCoord[dim][1]+1].equals(style[dim])
                        && !containsCoord(boatCoord[dim][0], boatCoord[dim][1]+1, coordBot)){
                    boatCoord[dim][0] = boatCoord[dim][0];
                    boatCoord[dim][1] = boatCoord[dim][1]+1;

                    coordBot[nbHit][0] = boatCoord[dim][0];
                    coordBot[nbHit][1] = boatCoord[dim][1];
                    nbHit++;

                }else notHit[2] = 1;
                botHit.add(Integer.valueOf(boatCoord[dim][0] + String.valueOf(boatCoord[dim][1])));
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);

            }else if(checkCoord(array, boatCoord[dim][0]-1, boatCoord[dim][1])
                    && notHit[3] != 1
                    && !botHit.contains(Integer.valueOf((boatCoord[dim][0]-1) + String.valueOf(boatCoord[dim][1])))){
                if(board_copy[boatCoord[dim][0]+1][boatCoord[dim][1]].equals(style[dim])
                        && !containsCoord(boatCoord[dim][0]-1, boatCoord[dim][1], coordBot)){
                    boatCoord[dim][0] = boatCoord[dim][0]-1;
                    boatCoord[dim][1] = boatCoord[dim][1];

                    coordBot[nbHit][0] = boatCoord[dim][0];
                    coordBot[nbHit][1] = boatCoord[dim][1];
                    nbHit++;
                }else notHit[3] = 1;
                botHit.add(Integer.valueOf(boatCoord[dim][0] + String.valueOf(boatCoord[dim][1])));
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);
            }
        }catch (ArrayIndexOutOfBoundsException | NumberFormatException ignored){
            for (int i = 0; i < notHit.length; i++) {
                if(notHit[i] == -1){
                    notHit[i] = 1;
                    return onShoot(array);
                }
            }
        }

        if(style[dim].equals(size[3][1]) && nbHit != Integer.parseInt(size[3][0])){
            boatCoord[dim][0] = coordBot[nb][0];
            boatCoord[dim][1] = coordBot[nb][1];
            nb++;
            notHit = new int[]{-1,-1,-1,-1};
            return onShoot(array);
        }else if(style[dim].equals(size[2][1]) && nbHit != Integer.parseInt(size[2][0])){
            boatCoord[dim][0] = coordBot[nb][0];
            boatCoord[dim][1] = coordBot[nb][1];
            nb++;
            notHit = new int[]{-1,-1,-1,-1};
            return onShoot(array);
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

    private boolean containsCoord(int x, int y, int[][] stock){
        for (int i = 0; i < stock.length; i++) {
            if(stock[i][0] == x && stock[i][1] == y) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int x = 1;
        int b = 2;
        int[] z = new int[]{1, 2, 3};
        System.out.println(x + String.valueOf(b));
        List<Integer> list = new ArrayList<>();

        list.add(Integer.valueOf(x + String.valueOf(z[1]+1)));
        if(list.contains(Integer.valueOf(x + String.valueOf(z[1]-1)))) System.out.println("a");
        else System.out.println("b");
        System.out.println(list.get(0));
        CreateBoard createBoard = new CreateBoard();
        DisplayBoard displayBoard = new DisplayBoard();
        int[] nb = new int[100];
        for (int i = 0; i < 100; i++) {
            String[][] board = createBoard.createBoard();
            Botv2 botv2 = new Botv2();
            botv2.placeShipBot(board);

            int a = 0;
            while(PlayerHuman.getNbShipAlive(board) != 0){
                botv2.shoot(board);
                a++;
            }
            nb[i] = a;
            System.out.println("---------------");
        }
        int a = 0;
        for (int i = 0; i < nb.length; i++) {
            a += nb[i];

        }
        System.out.println(a/nb.length);
    }
}
