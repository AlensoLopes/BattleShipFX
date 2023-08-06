package fr.battleshipfx.Controller;

import fr.battleshipfx.Utils.Utils;

import java.util.Objects;

public class ShootController {

    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public int shootBoat(String[][] board){
        return shootBoat(board, Objects.requireNonNull(Utils.processCoordinate(gameController.coord)));
    }

    public int shootBoat(String[][] board, int[] coord){
        int shootSucceed = gameController.playerHuman.getNbSuccessShoot();
        try{
            gameController.playerHuman.shoot(coord[0], coord[1], board);
        }catch (ArrayIndexOutOfBoundsException e){
            return -1;
        }

        if(gameController.playerHuman.getNbSuccessShoot() > shootSucceed){
            gameController.boardController.updateBoardLabel(coord[0] + 1, coord[1] + 1, GameController.HIT, "HIT");
            gameController.historyController.addHistoryOnShoot(coord, true, false);
            return 1;
        }else if(gameController.playerHuman.getNbSuccessShoot() == shootSucceed){
            gameController.boardController.updateBoardLabel(coord[0] + 1, coord[1] + 1, GameController.NOTHIT, "NOT HIT");
            gameController.historyController.addHistoryOnShoot(coord, false, false);
            return 0;
        }
        return -1;
    }
}
