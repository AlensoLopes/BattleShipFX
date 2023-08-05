package fr.battleshipfx.Utils;

import javafx.scene.control.TextField;

public class Utils {

    public static int[] processCoordinate(TextField textField){
        int a = textField.getText().toLowerCase().charAt(0) - 97;
        int b;
        try{
            b = Integer.parseInt(textField.getText().substring(1)) - 1;
        }catch (NumberFormatException e){
            return null;
        }
        return new int[]{a,b};
    }
}
