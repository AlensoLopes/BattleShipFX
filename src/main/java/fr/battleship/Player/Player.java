package fr.battleship.Player;


public abstract class Player {

    protected static String playerName;
    private int nbTotalShoot;
    private static int nbSuccessShoot;
    public static int nbShipAlive;


    public abstract void setPlayerName();


    protected void incrementNbTotalShoot(){
        this.nbTotalShoot++;
    }

    protected static void incrementNbSuccessShoot(){
        nbSuccessShoot++;
    }


    protected abstract boolean shoot(String[][] array);
    protected abstract boolean shoot(int x, int y, String[][] array);



}