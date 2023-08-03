package fr.battleshipfx.Utils;

import javafx.scene.control.TextField;

public class Utils {

    public static int[] processCoordinate(TextField textField){
        int a = textField.getText().toLowerCase().charAt(0) - 97;
        int b = Integer.parseInt(textField.getText().substring(1)) - 1;
        return new int[]{a,b};
    }
}
