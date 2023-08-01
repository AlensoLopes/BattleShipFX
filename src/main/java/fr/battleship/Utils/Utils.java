package fr.battleship.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

    public static ArrayList<String> getTextFromFile(File f){
        ArrayList<String> text = new ArrayList<>();
        try {
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                text.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
