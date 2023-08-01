package fr.battleship.Listeners;

import java.util.Scanner;

public class InputUser {
    static Scanner input = new Scanner(System.in);
    protected int reqTypeShip(){
        int nb = 0;
        int nbm = 1;
        int nbM = 4;
        boolean isGood = true;
        while(isGood){
            System.out.println("Select the ship you want to place : " +
                    "- Submarine : 1 " +
                    "- Torpedo : 2 " +
                    "- Cruiser : 3" +
                    "- Torpedo : 4");
            String line = input.nextLine();
            try{
                nb = Integer.parseInt(line);
            }catch (NumberFormatException e){
                System.err.println("Wrong input ! Input only integer numbers please : " + e.getMessage());
                continue;
            }
            if(checkIsInputNumberIsGood(nbm, nbM, line)) isGood = false;

        }
        return nb;
    }
    protected int reqAxis(){
        int nb = 0;
        int nbm = 1;
        int nbM = 2;
        boolean isGood = true;
        while(isGood){
            System.out.println("Select the axis : " +
                    "1 : Vertical " +
                    "2 : Horizontal");
            String line = input.nextLine();
            try{
                nb = Integer.parseInt(line);
            }catch (NumberFormatException e){
                System.err.println("Wrong input ! Input only integer numbers please : " + e.getMessage());
                continue;
            }
            if(checkIsInputNumberIsGood(nbm, nbM, line)) isGood = false;

        }
        return nb;
    }

    public static int reqCoordinate(String message){
        int nb = 0;
        int nbm = 0;
        int nbM = 9;
        boolean isGood = true;
        while(isGood){
            System.out.print(message);
            String line = input.nextLine();
            try{
                nb = Integer.parseInt(line);
            }catch (NumberFormatException e){
                System.err.println("Wrong input ! Input only integer numbers please : " + e.getMessage());
                continue;
            }
            if(checkIsInputNumberIsGood(nbm, nbM, line)) isGood = false;
        }
        return nb;
    }

    private static boolean checkIsInputNumberIsGood(int nbm, int nbM, String line){
        if(!(Integer.parseInt(line) >= nbm && Integer.parseInt(line) <= nbM)){
            System.err.println("Wrong input ! Input only integer numbers between : " + nbm +
                    " and " + nbM);
            return false;
        }
        return true;
    }
}