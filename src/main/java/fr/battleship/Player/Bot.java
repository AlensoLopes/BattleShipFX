package fr.battleship.Player;

import fr.battleship.Listeners.Warship;
import fr.battleship.Ship.Armoured;
import fr.battleship.Ship.Cruiser;
import fr.battleship.Ship.Submarine;
import fr.battleship.Ship.Torpedo;
import fr.battleship.Utils.Utils;

import java.io.File;
import java.util.*;

public class Bot extends Player {

    public ArrayList<Integer> botHit = new ArrayList<>();
    public int[] coordHit = new int[2];
    protected String playerName;
    private int nbShipAlive = 4;

    public Bot() {
        setPlayerName();
    }


    public int getNbShipAlive(String[][] board) {
        return PlayerHuman.getNbShipAlive(board);
    }

    @Override
    public void setPlayerName() {
        try{
            Random r = new Random();
            File f = new File("src/botName/botName.json");
            ArrayList<String> text = Utils.getTextFromFile(f);

            int nb = r.nextInt(text.size());
            this.playerName = text.get(nb);
        }catch (Exception e){
            this.playerName = "Bot";
        }
    }

    public String getBotName() {
        return this.playerName;
    }


    private final ArrayList<String> lockbot = new ArrayList<>();

    public void placeShipBot(String[][] array){
        for (int i = 0; i < Warship.nb_ship;) {
            String sens = processAxis(randomAxis());
            int column = randomPos(array, sens);
            int line = randomPos(array, sens);
            checkIfPlaceShip(array, sens, column, line);
            i++;
        }
    }

    protected void checkIfPlaceShip(String[][] array, String sens, int column, int line) {
        if(!lockbot.contains("S")){
            new Warship().placeSmallShip(line, column, new Submarine(), array, true);
            if(isPlacementGood(array, new Submarine().getStyle())) lockbot.add("S");
        }else if(!lockbot.contains("M")){
            new Warship().placeMediumShip(line, column, sens, new Torpedo(), array, true);
            if(isPlacementGood(array, new Torpedo().getStyle())) lockbot.add("M");
        }else if(!lockbot.contains("L")){
            new Warship().placeLargeShip(line, column, sens, new Cruiser(), array, true);
            if(isPlacementGood(array, new Cruiser().getStyle())) lockbot.add("L");
        }else if(!lockbot.contains("La")){
            new Warship().placeLargiestShip(line, column, sens, new Armoured(), array, true);
            if(isPlacementGood(array, new Armoured().getStyle())) lockbot.add("La");
        }
    }

    protected String processAxis(int axis) {
        String sens = "";
        switch (axis) {
            case 0 -> sens = "V";
            case 1 -> sens = "H";
        }
        return sens;
    }

    protected int randomPos(String[][] array, String sens) {
        if(Objects.equals(sens, "H")) return new Random().nextInt(array.length - 4);
        return new Random().nextInt(array.length);
    }

    protected int randomAxis() {
        return new Random().nextInt(2);
    }

    protected boolean isPlacementGood(String[][] board, String style){
        for (String[] strings : board) {
            for (int j = 0; j < board.length; j++) {
                if (strings[j].equals(style)) return true;
            }
        }
        return false;
    }

    @Override
    public boolean shoot(String[][] array) {
        return doNotHitOnTwiceCoord(array);
    }

    @Override
    public boolean shoot(int x, int y, String[][] array) {
        return PlayerHuman.checkCoordAndHit(array, x, y, true);
    }

    protected boolean doNotHitOnTwiceCoord(String[][] array){
        int x = 0, y = 0, coord;
        boolean isGood = true;
        Random r = new Random();
        while(isGood){
            coord = r.nextInt(array.length * 10);
            if(!(coord < 10)){
                x = getDigitFromNumber(coord, 0, 1);
                y = getDigitFromNumber(coord, 1, 2);
            }else{
                x = 0;
                y = getDigitFromNumber(coord, 0, 1);
            }
            if(x == 0 && !botHit.contains(y)){
                botHit.add(y);
                isGood = false;
            }else if(!(botHit.contains(coord))){
                botHit.add(coord);
                isGood = false;
            }
        }
        coordHit[0] = x;
        coordHit[1] = y;
        return shoot(x, y, array);

    }

    protected static int getDigitFromNumber(int number, int start, int end){
        return Integer.parseInt(Integer.toString(number).substring(start, end));
    }

    @Override
    public int getNbSuccessShoot() {
        return super.getNbSuccessShoot();
    }
}