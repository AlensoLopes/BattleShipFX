package fr.battleshipfx.Controller;

import fr.battleship.Listeners.Warship;
import fr.battleship.Player.PlayerHuman;
import fr.battleship.Ship.Armoured;
import fr.battleship.Ship.Cruiser;
import fr.battleship.Ship.Submarine;
import fr.battleship.Ship.Torpedo;
import fr.battleshipfx.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class PlaceShipController {

    private GameController controller;
    protected boolean[] boatPlaced;
    protected int nbBoat;
    private Warship warship;

    public PlaceShipController() {
        boatPlaced = new boolean[]{false, false, false, false};
        warship = new Warship();
        nbBoat =0;
    }

    public void setController(GameController controller){
        this.controller = controller;
    }

    public String[] placeShip(){
        if(!canShipBePlaced()) return null;
        int[] coord = Utils.processCoordinate(controller.coord);
        int size = placeBoat(coord, searchWhichBoatNeedToBePlaced(), getAxis());
        if(size == -1) return null;

        return new String[]{String.valueOf(coord[0]), String.valueOf(coord[1]), String.valueOf(size),
                getAxis(), getBoatNameWithIndex(searchWhichBoatNeedToBePlaced())};
    }

    private boolean canShipBePlaced(){
        for (boolean b : boatPlaced) {
            if (!b) {
                return true;
            }
        }
        return false;
    }
    /*private int[] processCoordinate(){
        return new int[]{
                Integer.parseInt(controller.coord.getText().split(";")[0]),
                Integer.parseInt(controller.coord.getText().split(";")[1])};
    }*/

    private String getAxis(){
        return controller.axis.getText();
    }

    private int searchWhichBoatNeedToBePlaced(){
        for (int i = 0; i <= boatPlaced.length; i++) {
            if(!boatPlaced[i]){
                return i;
            }
        }
        return -1;

    }

    private String getBoatNameWithIndex(int index){
        String name = "";
        switch (index){
            case 0 -> name = "small";
            case 1 -> name = "medium";
            case 2 -> name = "large";
            case 3 -> name = "larger";
        }
        return name;
    }

    private int placeBoat(int[] coord, int index, String sens){
        if(index == -1) return -1;
        switch(index){
            case 0 -> warship.placeSmallShip(coord[0], coord[1], new Submarine(), controller.board_game, false);
            case 1 -> warship.placeMediumShip(coord[0], coord[1], sens, new Torpedo(), controller.board_game, false);
            case 2 -> warship.placeLargeShip(coord[0], coord[1], sens, new Cruiser(), controller.board_game, false);
            case 3 -> warship.placeLargiestShip(coord[0], coord[1], sens, new Armoured(), controller.board_game, false);
        }
        controller.coord.clear();
        return index+1;
    }

    private boolean isButtonCorrect(){
        return controller.coord.getText().contains(";");
    }

    protected void disableButton(){
        if(!controller.coord.getText().startsWith("(") && !controller.coord.getText().endsWith(")")) controller.validation.setDisable(true);
    }



}
