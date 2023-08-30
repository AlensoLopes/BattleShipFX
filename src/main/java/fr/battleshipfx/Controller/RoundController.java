package fr.battleshipfx.Controller;

import fr.battleship.Player.PlayerHuman;
import fr.battleship.Win.Win;
import fr.battleshipfx.Database.GameDatabase;
import javafx.scene.control.Label;

public class RoundController {

    private GameController gameController;


    protected int placeShipAndDisplay(String[] coordo){
        if(coordo == null) return 0;
        if(!gameController.historyController.addHistoryOnError(coordo, "unplaced boat")) return -1;
        gameController.displayBoard.displayBoard(GameController.board_game);

        gameController.historyController.addHistoryOnPlace(coordo);

        gameController.placeShipController.boatPlaced[Integer.parseInt(coordo[2])-1] = true;
        if(gameController.placeShipController.boatPlaced[0]){
            gameController.axis.setVisible(true);
            gameController.validation.textProperty().unbind();
            gameController.axis.disableProperty().bind(gameController.coord.textProperty().isEmpty());
            gameController.validation.disableProperty().bind(gameController.axis.textProperty().isEmpty());
        }

        gameController.boardController.updateBoard(Integer.parseInt(coordo[0]), Integer.parseInt(coordo[1]),
                Integer.parseInt(coordo[2]), coordo[3], coordo[4]);
        gameController.axis.clear();
        return 1;
    }


    protected void doShootAndDisplay(int[] coord){
        if(Win.getWinner(GameController.board_game, gameController.board_bot,
                gameController.bot, gameController.playerHuman)) return;
        if(!gameController.placeShipController.boatPlaced[gameController.placeShipController.boatPlaced.length-1]) return;

        if(!gameController.first_bind){
            gameController.axis.setVisible(false);
            gameController.validation.disableProperty().unbind();
            gameController.validation.disableProperty().bind(gameController.coord.textProperty().isEmpty());
            gameController.first_bind = true;
        }

        if(gameController.nb_round == 0){
            gameController.nb_round++;
            return;
        }

        if(coord == null) proccessRound();
        else processRound(coord);

        //gameController.displayBoard.displayBoard(gameController.board_game);
    }

    protected void processWin(){
        if(!Win.getWinner(GameController.board_game, gameController.board_bot,
                gameController.bot, gameController.playerHuman)) return;

        gameController.validation.disableProperty().unbind();
        gameController.validation.setDisable(true);
        gameController.coord.setDisable(true);
        GameDatabase.insertGameInformation(gameController.nb_round, new int[]{
                PlayerHuman.getShipAlive(gameController.board_bot),
                PlayerHuman.getShipAlive(GameController.board_game)});
        gameController.replay(MainController.stage);
    }

    protected void proccessRound(){
        gameController.nb_round++;
        gameController.pane.getChildren().add(new Label("Round : " + gameController.nb_round));
        int shoot = gameController.shootController.shootBoat(gameController.board_bot);
        gameController.coord.clear();

        if(shoot == -1){
            System.out.println("Erreur");
            gameController.historyController.addHistoryOnError(new String[]{null}, "unlaunched shoot");
            gameController.nb_round--;
            return;
        }

        changeGUIAfterShoot();

    }

    protected void processRound(int[] coord){
        gameController.nb_round++;
        gameController.pane.getChildren().add(new Label("Round : " + gameController.nb_round));
        int shoot = gameController.shootController.shootBoat(gameController.board_bot, coord);

        if(shoot == -1){
            gameController.historyController.addHistoryOnError(new String[]{null}, "unlaunched shoot");
            System.out.println("Erreur");
            gameController.nb_round--;
            return;
        }

        changeGUIAfterShoot();
    }

    private void changeGUIAfterShoot(){
        int shootBot = gameController.botController.shootBot(GameController.board_game);
        if(shootBot == 1){
            gameController.boardController.updateBoardLabel(gameController.bot.coordHit[0] + 1,
                    gameController.bot.coordHit[1] + 1, GameController.DEAD, null);

            gameController.historyController.addHistoryOnShoot(gameController.bot.coordHit, true, true);
        }else{
            gameController.historyController.addHistoryOnShoot(gameController.bot.coordHit, false, true);
        }
        gameController.pane.getChildren().add(new Label("End of Round : " + gameController.nb_round));
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
