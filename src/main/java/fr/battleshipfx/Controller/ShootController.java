package fr.battleshipfx.Controller;

import fr.battleshipfx.Utils.Utils;

public class ShootController {

    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public int shootBoat(String[][] board){
        return shootBoat(board, Utils.processCoordinate(gameController.coord));
    }

    public int shootBoat(String[][] board, int[] coord){
        int shootSucceed = gameController.playerHuman.getNbSuccessShoot();
        gameController.playerHuman.shoot(coord[0], coord[1], board);
        if(gameController.playerHuman.getNbSuccessShoot() > shootSucceed){
            gameController.boardController.updateBoardLabel(coord[0] + 1, coord[1] + 1, GameController.HIT, "HIT");
            return 1;
        }else if(gameController.playerHuman.getNbSuccessShoot() == shootSucceed){
            gameController.boardController.updateBoardLabel(coord[0] + 1, coord[1] + 1, GameController.NOTHIT, "NOT HIT");
            return 0;
        }
        return -1;
    }
}
