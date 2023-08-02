package fr.battleshipfx.Controller;

import fr.battleshipfx.Utils.Utils;

public class ShootController {

    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public int shootBoat(String[][] board){
        int[] coord = Utils.processCoordinate(gameController.coord);
        int shootSucceed = gameController.playerHuman.getNbSuccessShoot();
        gameController.playerHuman.shoot(coord[0], coord[1], board);
        if(gameController.playerHuman.getNbSuccessShoot() > shootSucceed){
            return 1;
        }else if(gameController.playerHuman.getNbSuccessShoot() == shootSucceed){
            return 0;
        }
        return -1;
    }
}
