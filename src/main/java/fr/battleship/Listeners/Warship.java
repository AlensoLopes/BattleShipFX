package fr.battleship.Listeners;

import fr.battleship.Board.DisplayBoard;
import fr.battleship.Ship.*;
import fr.battleship.Utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class Warship extends DisplayBoard{
    protected int size;
    protected String style;
    public static int nb_ship = 4;
    private final ArrayList<String> lock = new ArrayList<>();
    public void deployShip(Submarine s, Torpedo t,
                           Cruiser c, Armoured a, String[][] array){
        System.out.println();
        System.out.println("You need to deploy your ship !");
        for(int i = 1; i<=nb_ship;){
            ProcessInput input = new ProcessInput();
            String tmp = input.processInputTypeShip();
            String sens = "";

            if(!(tmp.equals("A"))) sens = input.processInputAxis();

            int column = InputUser.reqCoordinate("Select the column where to place your ship : ");
            int line = InputUser.reqCoordinate("Select the line where to place your ship : ");
            switch (tmp){
                case "A" -> placeSmallShip(line, column, s, array, false);
                case "B" -> placeMediumShip(line, column, sens, t, array, false);
                case "C" -> placeLargeShip(line, column, sens, c, array, false);
                case "D" -> placeLargiestShip(line, column, sens, a, array, false);
                default -> {
                }
            }
            Utils.clearConsole();
            new DisplayBoard().displayBoard(array);
            i++;
        }
    }

    public boolean placeSmallShip(int x, int y, Submarine s, String[][] array, boolean bot) {
        String type = "S";
        if (lockCheckWithType(type)) return false;
        if(!((x >= 0 && x < DIM) && (y >= 0 && y < DIM) &&
                (Objects.equals(array[x][y], " ")))){
            errorPlaceShip(x, y, array, type, bot);
            return false;
        }
        array[x][y] = s.style;
        lock.add("S");
        /*displayBoard(array);*/
        return true;
    }

    public boolean placeMediumShip(int x, int y, String sens, Torpedo t, String[][] array, boolean bot){
        String type = "M";
        return callPlaceShip(x, y, t.getSize(), sens, t.getStyle(), array, type, bot);
    }

    public boolean placeMediumShip(int[] coord, Torpedo t, String[][] array){
        String type = "M";
        for(int i = 0; i<t.size; i+=2){
            for (int j = i+1; j < size; j++) {
                if (lockCheckWithType(type)) return false;
                if(!((coord[i] >= 0 && coord[i] < DIM) && (coord[j] >= 0 && coord[j] < DIM) &&
                        (Objects.equals(array[coord[i]][coord[j]], " ")))){
                    errorPlaceShip(coord[i], coord[j], array, type, false);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean placeLargeShip(int x, int y, String sens,
                               Cruiser c, String[][] array, boolean bot){
        String type = "L";
        return callPlaceShip(x, y, c.getSize(), sens, c.getStyle(), array, type, bot);
    }

    public boolean placeLargiestShip(int x, int y, String sens,
                                  Armoured a, String[][] array, boolean bot){
        String type = "La";
        return callPlaceShip(x, y, a.getSize(), sens, a.getStyle(), array, type, bot);
    }

    private boolean callPlaceShip(int x, int y, int size, String sens,
                               String style, String[][] array, String type, boolean bot){
        if (lockCheckWithType(type)) return false;
        boolean e = checkError(x, y, size, sens, array);
        place_check_vertical(x, y, sens, size, e, style, array, type, bot);
        place_check_horizontal(x, y, sens, size, e, style, array, type);
        /*displayBoard(array);*/
        return e;
    }

    private void shipLock(){
        System.out.println("You already place that type of ship !");
        nb_ship++;
    }
    private boolean lockCheckWithType(String type) {
        if(lock.contains(type)){
            shipLock();
            return true;
        }
        return false;
    }

    private void place_check_horizontal(int x, int y, String sens, int size, boolean e,
                                        String style, String[][] array, String type) {
        if(!sens.equals("H")) return;
        for (int i = 0; i < size; i++) {
            if(e){
                array[x][y +i] = style;
                lock.add(type);
            }else errorPlaceShip(x, y, array, type, false);
        }
    }

    private void place_check_vertical(int x, int y, String sens, int size, boolean e,
                                      String style, String[][] array, String type, boolean bot) {

        if(!sens.equals("V")) return;
        for (int i = 0; i < size; i++) {
            if(e){
                array[x + i][y] = style;
                lock.add(type);
            } else errorPlaceShip(x, y, array, type, bot);
        }
    }

    private void errorPlaceShip(int x, int y, String[][] array, String type, boolean bot){
        if((x >= 0 && x < DIM) && (y >= 0 && y < DIM) && (!Objects.equals(array[x][y], " "))
        && !bot){
            System.out.println("You can't place a same ship on the same location !");
            lock.remove(type);
        }else if((x < 0 || x >=DIM) && !bot || (y < 0 || y >= DIM) && !bot) {
            System.out.println("You can't place ship outside the board");
            lock.remove(type);
        }
        nb_ship++;
    }
    private boolean checkError(int x, int y, int size, String sens, String[][] array) {
        int no_error = 0;
        if (sens.equals("V")) {
            for (int i = 0; i < size; i++) {
                if ((x + i >= 0 && x + i < DIM) && (y >= 0 && y < DIM) && (Objects.equals(array[x + i][y], " "))) {
                    no_error++;
                }
            }
            return no_error == size;
        }
        for (int i = 0; i < size; i++) {
            if ((x >= 0 && x < DIM) && (y + i >= 0 && y + i < DIM) && (Objects.equals(array[x][y + i], " "))) {
                no_error++;
            }
        }
        return no_error == size;
    }

}