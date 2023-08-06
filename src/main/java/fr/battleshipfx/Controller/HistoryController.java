package fr.battleshipfx.Controller;

import fr.battleship.Player.PlayerHuman;
import javafx.scene.control.Label;

public class HistoryController {

    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    protected void addHistoryOnPlace(String[] coordo){
        if(!gameController.placeShipController.boatPlaced[0]){
            gameController.pane.getChildren().addAll(new Label("Boat placed : "),
                    new Label(gameController.placeShipController.boatName.getName() + " in ("
                    + (char) (97 + Integer.parseInt(coordo[0]) - 1) + coordo[1] + ')'));
        }else{
            gameController.pane.getChildren().add(
                    new Label(gameController.placeShipController.boatName.getName() + " in ("
                    + (char) (97 + (Integer.parseInt(coordo[0]) - 1)) + coordo[1] + ')'));
        }
    }
    protected void addHistoryOnShoot(int[] coordo, boolean hit, boolean bot){
        if(hit && !bot){
            gameController.pane.getChildren().addAll(
                    new Label(gameController.playerHuman.getPlayerName() + " hit a boat in ("
                    + (char) (97 + (coordo[0])) + (coordo[1] + 1) + ')'));
            allBoatDestroy();
        }else if(!hit && !bot){
            gameController.pane.getChildren().add(
                    new Label(gameController.playerHuman.getPlayerName() + " failed his shoot in ("
                    + (char) (97 + (coordo[0])) + (coordo[1] + 1) + ')'));
        }else if(hit && bot){
            gameController.pane.getChildren().add(new Label(gameController.bot.getBotName() + " hit a boat in ("
                    + (char) (97 + (coordo[0])) + (coordo[1] + 1) + ')'));
            allBoatDestroy();
        }else{
            gameController.pane.getChildren().add(new Label(gameController.bot.getBotName() + " failed his shoot in ("
                    + (char) (97 + (coordo[0])) + (coordo[1] + 1) + ')'));
        }

    }

    protected boolean addHistoryOnError(String[] coordo, String text){
        if(coordo[0] == null){
            Label label = new Label("An error occured, " + text + " ! ");
            label.setStyle("-fx-text-fill: red");
            gameController.pane.getChildren().add(label);
            return false;
        }
        return true;
    }

    private void allBoatDestroy(){
        if(PlayerHuman.entireShip[0] == 1)gameController.pane.getChildren().add(new Label(MainController.getPseudonyme() + " destroy the entire boat"));
        else if(PlayerHuman.entireShip[1] == 1) gameController.pane.getChildren().add(new Label(gameController.bot.getBotName() + " destroy the entire boat"));
        else if(PlayerHuman.entireShip[0] == 0)gameController.pane.getChildren().add(new Label(MainController.getPseudonyme() + " haven't destroy the entire boat"));
        else if(PlayerHuman.entireShip[1] == 0) gameController.pane.getChildren().add(new Label(gameController.bot.getBotName() + " haven't destroy the entire boat"));
        PlayerHuman.entireShip[0] = 0;
        PlayerHuman.entireShip[1] = 0;
    }
}
