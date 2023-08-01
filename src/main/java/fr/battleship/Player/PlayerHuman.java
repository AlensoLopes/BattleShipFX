package fr.battleship.Player;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Listeners.InputUser;
import fr.battleship.Ship.Armoured;
import fr.battleship.Ship.Cruiser;
import fr.battleship.Ship.Submarine;
import fr.battleship.Ship.Torpedo;

import java.util.Objects;
import java.util.Scanner;

public class PlayerHuman extends Player{

    public PlayerHuman() {
    }

    @Override
    public void setPlayerName() {
        String name;
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a username : ");
        name = input.nextLine();

        System.out.println("Your username is now " + name);
        playerName = name;
    }

    public String getPlayerName(){
        return playerName;
    }

    public static int getNbShipAlive(String[][] board) {
        int nbShipAlive = 0;
        for (int i = 0; i < CreateBoard.DIM; i++) {
            for (int j = 0; j < CreateBoard.DIM; j++) {
                if(!board[i][j].equals(" ")) nbShipAlive++;
            }
        }
        return nbShipAlive;
    }

    @Override
    public boolean shoot(String[][] array) {
        int column = InputUser.reqCoordinate("Select the column where to shoot a ship : ");
        int line = InputUser.reqCoordinate("Select the line where to shoot a ship : ");
        return shoot(line, column, array);
    }

    @Override
    public boolean shoot(int x, int y, String[][] array) {
        incrementNbTotalShoot();
        return checkCoordAndHit(array, x, y, false);
    }


    protected static boolean checkCoordAndHit(String[][] array, int x, int y, boolean bot) {
        if(!(x >= 0 && x < CreateBoard.DIM) && (y>= 0 && y<CreateBoard.DIM)) return false;

        if(Objects.equals(array[x][y], " ") || array[x][y].equalsIgnoreCase(" ")){
            if(!bot) System.out.println("Player failed !");
            else System.out.println("Bot failed !");
            return false;
        }
        if(bot) System.out.println("Bot hit !");
        else System.out.println("Player Hit !");
        newDestroyBoat(x, y, array, new Submarine().getStyle(), bot);
        newDestroyBoat(x, y, array, new Torpedo().getStyle(), bot);
        newDestroyBoat(x, y, array, new Cruiser().getStyle(), bot);
        newDestroyBoat(x, y, array, new Armoured().getStyle(), bot);
        incrementNbSuccessShoot();
        return true;
    }


    private static void newDestroyBoat(int x, int y, String[][] array, String style, boolean bot){
        if(!array[x][y].equals(style)) return;
        destroyShip(x, y, array, style);
        if(numberOfThisShip(array, style) == 0 && !bot){
            System.out.println("You destroy the entire ship !");
            return;
        }else if (numberOfThisShip(array, style) == 0 && bot){
            System.out.println("Bot destroy the entire ship !");
            return;
        }
        if(!bot) System.out.println("You don't have destroy the entire ship !");
        else System.out.println("Bot don't have destroy the entire ship !");
    }

    private static int numberOfThisShip(String[][] array, String style){
        int number = 0;
        for (String[] strings : array) {
            for (int j = 0; j < array.length; j++) {
                if (strings[j].equals(style)) number++;
            }
        }
        return number;
    }

    private static void destroyShip(int x, int y, String[][] array, String style){
        if(array[x][y].equals(style)) array[x][y] = " ";
    }


    /*Below : old version of the destroy ship*/

   /* private static void hitSmallBoat(int x, int y, String[][] array){
        if(!(array[x][y].equals(new Submarine().getStyle()))) return;
        destroyShipV(x, y, array, new Submarine().getSize());
    }


    private static void hitMediumBoat(int x, int y, String[][] array) {
        Torpedo t = new Torpedo();
        checkAndDestroyV(x, y, array, t.getStyle(), t.getSize());
        checkAndDestroyH(x, y, array, t.getStyle(), t.getSize());
    }

    private static void hitLargeBoat(int x, int y, String[][] array){
        Cruiser c = new Cruiser();
        checkAndDestroyV(x, y, array, c.getStyle(), c.getSize());
        checkAndDestroyH(x, y, array, c.getStyle(), c.getSize());
    }

    private static void hitLargiestBoat(int x, int y, String[][] array){
        Armoured a = new Armoured();
        checkAndDestroyV(x, y, array, a.getStyle(), a.getSize());
        checkAndDestroyH(x, y, array, a.getStyle(), a.getSize());
    }
    private static void checkAndDestroyV(int x, int y, String[][] array, String style, int size){
        if(isAxisVertical(x, y, style, array, size)) destroyShipV(x, y, array, size);
    }
    private static void checkAndDestroyH(int x, int y, String[][] array, String style, int size){
        if(isAxisHorizontal(x, y, style, array, size)) destroyShipH(x, y, array, size);
    }

    private static boolean isAxisVertical(int x, int y, String style, String[][]array, int size){
        int v = 0;
        for (int i = 0; i < size; i++) {
            if(x+i >= 10) return false;
            if(array[x+i][y].equals(style)){
                v++;
            }
        }
        return v == size;
    }
    private static boolean isAxisHorizontal(int x, int y, String style, String[][]array, int size){
        int h = 0;
        for (int i = 0; i < size; i++) {
            if(y+i >= 10) return false;
            if(array[x][y+i].equals(style)){
                h++;
            }
        }
        return h == size;
    }
    private static void destroyShipV(int x, int y, String[][] array, int size) {
        for (int i = 0; i < size; i++) {
            array[x+i][y] = " ";
        }
    }
    private static void destroyShipH(int x, int y, String[][] array, int size){
        for (int i = 0; i < size; i++) {
            array[x][y+i] = " ";
        }
    }*/
}