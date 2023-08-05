package fr.battleshipfx.Controller;

import fr.battleship.Listeners.Warship;
import fr.battleship.Ship.Armoured;
import fr.battleship.Ship.Cruiser;
import fr.battleship.Ship.Submarine;
import fr.battleship.Ship.Torpedo;
import fr.battleshipfx.Utils.Utils;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.text.Format;


public class PlaceShipController {

    private GameController controller;
    protected boolean[] boatPlaced;
    protected int nbBoat;
    private final Warship warship;
    protected Warship boatName;

    public PlaceShipController() {
        boatPlaced = new boolean[Warship.nb_ship];
        for (int i = 0; i < Warship.nb_ship; i++) {
            boatPlaced[i] = false;
        }
        warship = new Warship();
        nbBoat =0;
    }

    public void setController(GameController controller){
        this.controller = controller;
    }

    public String[] placeShip(){
        if(!canShipBePlaced()) return null;
        int[] coord = Utils.processCoordinate(controller.coord);
        if(coord == null) return new String[]{null};

        int size = placeBoat(coord, searchWhichBoatNeedToBePlaced(), getAxis());
        if(size == -1) return null;


        return new String[]{String.valueOf(coord[0] + 1), String.valueOf(coord[1] + 1), String.valueOf(size),
                getAxis(), getBoatNameWithIndex(searchWhichBoatNeedToBePlaced())};
    }

    public String[] placeShipOnClick(int x, int y, String axis){
        if(!canShipBePlaced()) return null;
        int[] coord = new int[]{x,y};
        int size = placeBoat(coord, searchWhichBoatNeedToBePlaced(), axis);
        if(size == -1) return null;

        return new String[]{String.valueOf(coord[0] + 1), String.valueOf(coord[1] + 1), String.valueOf(size),
                axis, getBoatNameWithIndex(searchWhichBoatNeedToBePlaced())};
    }

    private boolean canShipBePlaced(){
        for (boolean b : boatPlaced) {
            if (!b) {
                return true;
            }
        }
        return false;
    }

    protected String getAxis(){
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
        boolean placed = false;
        if(index == -1) return -1;
        switch(index){
            case 0 -> placed = warship.placeSmallShip(coord[0], coord[1], (Submarine) (boatName =new Submarine()),
                    controller.board_game, false);
            case 1 -> placed = warship.placeMediumShip(coord[0], coord[1], sens, (Torpedo) (boatName = new Torpedo()),
                    controller.board_game, false);
            case 2 -> placed = warship.placeLargeShip(coord[0], coord[1], sens, (Cruiser) (boatName = new Cruiser()),
                    controller.board_game, false);
            case 3 -> placed = warship.placeLargiestShip(coord[0], coord[1], sens, (Armoured) (boatName = new Armoured()),
                    controller.board_game, false);
        }

        if(!placed) return -1;
        controller.coord.clear();
        return index+1;
    }
}
