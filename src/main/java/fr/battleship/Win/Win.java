package fr.battleship.Win;

import fr.battleship.Player.Bot;
import fr.battleship.Player.PlayerHuman;

public class Win {

    public static String winner;

    public static boolean getWinner(String[][] board, String[][] board_bot, Bot b, PlayerHuman p){
        if(PlayerHuman.getNbShipAlive(board) == 0 && b.getNbShipAlive(board_bot) != 0){
            System.out.println("The Winner is " + b.getBotName());
            winner = b.getBotName();
            return true;
        }else if(PlayerHuman.getNbShipAlive(board) != 0 && b.getNbShipAlive(board_bot) == 0){
            System.out.println("The winner is " + p.getPlayerName());
            winner = p.getPlayerName();
            return true;
        }else if(PlayerHuman.getNbShipAlive(board) == 0 && b.getNbShipAlive(board_bot) == 0){
            System.out.println("The winners are both Player !");
            winner = "Both Player !";
            return true;
        }
        return false;
    }
}
