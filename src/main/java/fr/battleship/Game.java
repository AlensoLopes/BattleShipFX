package fr.battleship;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Board.DisplayBoard;
import fr.battleship.Listeners.Warship;
import fr.battleship.Player.*;
import fr.battleship.Ship.*;
import fr.battleship.Utils.Utils;
import fr.battleship.Win.Win;


public class Game {
    static PlayerHuman p;
    static Botv2 b;
    static Warship[] objShip = {new Submarine(), new Torpedo(),
            new Cruiser(), new Armoured()};
    static String[][] board = new CreateBoard().createBoard();
    static String[][] board_bot = new CreateBoard().createBoard();
    static Warship w = new Warship();

    public static void game() {
        System.out.println("BattleShip Game ! ");
        boolean stop = false;
        int tour = 0;
        p = new PlayerHuman();
        b = new Botv2();
        p.setPlayerName();
        w.deployShip((Submarine) objShip[0], (Torpedo) objShip[1],
                (Cruiser) objShip[2], (Armoured) objShip[3], board);
        b.placeShipBot(board_bot);
        Utils.clearConsole();
        new DisplayBoard().displayBoard(board);
        while(!stop){
            tour++;
            System.out.println("Tour Number " + tour);
            p.shoot(board_bot);
            b.shoot(board);
            if(Win.getWinner(board, board_bot, b, p)) stop = true;
            Utils.clearConsole();
            if(!stop) {
                new DisplayBoard().displayBoard(board);
            }
        }
    }

}
