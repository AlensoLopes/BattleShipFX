package fr.battleshipfx.Controller;

public class BotController {

    private GameController gameController;

    public BotController() {
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public int shootBot(String[][] board){
        int shootSucceed = gameController.bot.getNbSuccessShoot();
        gameController.bot.shoot(board);
        if(gameController.bot.getNbSuccessShoot() > shootSucceed){
            return 1;
        }else if(gameController.bot.getNbSuccessShoot() == shootSucceed){
            return 0;
        }
        return -1;
    }
}
