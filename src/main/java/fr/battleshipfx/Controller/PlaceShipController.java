package fr.battleshipfx.Controller;

import fr.battleship.Listeners.Warship;
import fr.battleship.Ship.Submarine;
import javafx.fxml.FXML;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceShipController {

    private GameController controller;

    public PlaceShipController() {

    }

    public void setController(GameController controller){
        this.controller = controller;
    }

    public int[] placeShip(){
        if(!isButtonCorrect()){
            return new int[]{};
        }
        int x = Integer.parseInt(controller.coord.getText().split(";")[0]);
        int y = Integer.parseInt(controller.coord.getText().split(";")[1]);
        Warship warship = new Warship();
        warship.placeSmallShip(x, y, new Submarine(), controller.board_game, false);
        return new int[]{x,y};
    }

    private boolean isButtonCorrect(){
        return controller.coord.getText().contains(";");
    }

    protected void disableButton(){
        if(!controller.coord.getText().startsWith("(") && !controller.coord.getText().endsWith(")")) controller.validation.setDisable(true);
    }



}
