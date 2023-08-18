package fr.battleship.Player;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Board.DisplayBoard;

import java.util.Arrays;


public class Botv2 extends Bot{

    public int[][] boatCoord;
    private int[][] coordBot;
    private String[][] board_copy;
    private String[] style;
    private int nbHit = 0;
    private int[] notHit;
    private int[] axis;
    public int dim = 0;
    private int nb = 0;
    public static final String[][] size = new String[][]{{"1", "■"}, {"2","▄"},{"3","▀"}, {"4", "█"}};
    public static final int STARTBOARD = 0;
    public static final int ENDBOARD = 9;

    public Botv2() {
        super();
        initStartingVal();
    }

    private void initStartingVal(){
        boatCoord = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
        coordBot = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
        notHit = new int[]{-1,-1,-1,-1};
        style = new String[4];
        axis = new int[]{-1, -1};
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
        createCopy(array);

        int x = coordHit[0];
        int y = coordHit[1];

        if(isBotHit(x, y)) return super.shoot(array);

        processInitValOnNewBoat(x, y);

        for (String[] strings : size) {
           if(processSize(strings, dim, nbHit, array)) return onShoot(array);
        }

        doNotHitOutside();

        try{
            if(processHit(array, boatCoord[dim][0], boatCoord[dim][1]-1, 1, 0, 0)){
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);
            }else if(processHit(array, boatCoord[dim][0]+1, boatCoord[dim][1], 0, 1, 1)){
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);
            }else if(processHit(array, boatCoord[dim][0], boatCoord[dim][1]+1, 1, 0, 2)){
                return shoot(boatCoord[dim][0], boatCoord[dim][1], array);
            }else if(processHit(array, boatCoord[dim][0]-1, boatCoord[dim][1], 0, 1, 3)){
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
        if(style[dim].equals(size[size.length-1][1])
                && nbHit != Integer.parseInt(size[size.length-1][0])){
            updateValChangeCoord();
            return onShoot(array);
        }else if(style[dim].equals(size[size.length-2][1])
                && nbHit != Integer.parseInt(size[size.length-2][0])){
            updateValChangeCoord();
            return onShoot(array);
        }

        return super.shoot(array);
    }

    private boolean shouldCreateNewCopy(){
        return board_copy == null;
    }
    private void createCopy(String[][] array){
        if(shouldCreateNewCopy()){
            board_copy = copy(array);
            super.shoot(array);
        }
    }

    private boolean isBotHit(int x, int y){
        return board_copy[x][y].equals(" ") && boatCoord[dim][0] == -1;
    }


    private boolean shouldInitNewestVal(){
        return boatCoord[dim][0] == -1;
    }

    private void updateValNewBoat(int x, int y){
        boatCoord[dim][0] = x;
        boatCoord[dim][1] = y;
        style[dim] = board_copy[x][y];
        coordBot[nbHit][0] = boatCoord[dim][0];
        coordBot[nbHit][1] = boatCoord[dim][1];
        nbHit++;
    }
    private void processInitValOnNewBoat(int x, int y){
        if(shouldInitNewestVal()) updateValNewBoat(x, y);
    }

    private boolean checkCoord(int x, int y){
        return x >= 0 && x < CreateBoard.DIM || (y < 0 || y >= CreateBoard.DIM);
    }

    private String[][] copy(String[][] array){
        String[][] copy = new String[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, copy[i], 0, array.length);
        }
        return copy;
    }

    private boolean containsCoord(int x, int y, int[][] stock){
        for (int[] ints : stock) {
            if (ints[0] == x && ints[1] == y) return true;
        }
        return false;
    }

    private boolean shouldHandleSize(String[] size, int dim, int nbHit){
        return style[dim].equals(size[1]) && nbHit == Integer.parseInt(size[0]);
    }

    private boolean processSize(String[] size, int dim, int nbHit, String[][] array){
        if(dim < 3 && boatCoord[dim+1][0] == -1 && shouldHandleSize(size, dim, nbHit)) {
            resetData(array);
            return true;
        }
        return false;
    }

    private void resetData(String[][] array){
        nbHit = 0;
        notHit = new int[]{-1,-1,-1,-1};
        for (int[] ints : coordBot) {
            if (ints[0] != -1) {
                botHit.add(Integer.valueOf(ints[0] + String.valueOf(ints[1])));
            }
        }
        coordBot = new int[][]{{-1, -1},{-1, -1},{-1, -1},{-1, -1}};
        dim++;
        axis = new int[]{-1, -1};
        board_copy = copy(array);
        nb = 0;
    }

    private void doNotHitOutside(){
        if(boatCoord[dim][0] == STARTBOARD){
            notHit[3] = 1;
        }else if(boatCoord[dim][0] == ENDBOARD){
            notHit[1] = 1;
        }else if(boatCoord[dim][1] == STARTBOARD){
            notHit[0] = 1;
        }else if(boatCoord[dim][1] == ENDBOARD){
            notHit[2] = 1;
        }
    }
    private boolean canHit(int coordX, int coordY, int vertical, int horizontal, int numberHit){
        return checkCoord(coordX, coordY)
                && notHit[numberHit] != 1
                && !botHit.contains(Integer.valueOf(coordX + String.valueOf(coordY)))
                && ((axis[0] == -1 && axis[1] == -1) || (axis[0] == vertical && axis[1] == horizontal));
    }

    private boolean isBoardContainsBoatAtCoord(int coordX, int coordY){
        return board_copy[coordX][coordY].equals(style[dim])
                && !containsCoord(coordX, coordY, coordBot);
    }

    private void updateValHit(int coordX, int coordY, int vertical, int horizontal ){
        boatCoord[dim][0] = coordX;
        boatCoord[dim][1] = coordY;
        coordBot[nbHit][0] = boatCoord[dim][0];
        coordBot[nbHit][1] = boatCoord[dim][1];
        nbHit++;
        axis[0] = vertical;
        axis[1] = horizontal;
        coordHit[0] = coordX;
        coordHit[1] = coordY;
    }

    private boolean processHit(String[][] array, int coordX, int coordY, int vertical, int horizontal, int numberHit){
        if(canHit(coordX, coordY, vertical, horizontal, numberHit)){
            if(isBoardContainsBoatAtCoord(coordX, coordY)){
                updateValHit(coordX, coordY, vertical, horizontal);
            }else notHit[numberHit] = 1;
            return true;
        }
        return false;
    }


    private void updateValChangeCoord(){
        boatCoord[dim][0] = coordBot[nb][0];
        boatCoord[dim][1] = coordBot[nb][1];
        nb++;
        notHit = new int[]{-1,-1,-1,-1};
    }


    public static void main(String[] args) {
        int[] nb = new int[100];
        int a;
        for (int i = 0; i < 100; i++) {
            CreateBoard createBoard = new CreateBoard();
            DisplayBoard displayBoard = new DisplayBoard();
            Botv2 botv2 = new Botv2();
            String[][] board;
            board = createBoard.createBoard();
            botv2.placeShipBot(board);
            a = 0;
            while(PlayerHuman.getNbShipAlive(board) != 0){
                botv2.shoot(board);
                a++;
            }
            nb[i] = a;
            System.out.println("---------------");
        }
        int c = 0;
        int d = 0;
        for (int i = 0; i < nb.length; i++) {
            c += nb[i];
            System.out.printf("%d;", nb[i]);
            if(nb[i] < 80) d++;
        }
        System.out.println();
        System.out.println(c/nb.length);
        System.out.println(Arrays.stream(nb).min());
        System.out.println(Arrays.stream(nb).max());
        System.out.println(d);
    }

}