package fr.battleshipfx.Utils;

import javafx.scene.control.TextField;

public class Utils {

    public static int[] processCoordinate(TextField textField){
            return new int[]{Integer.parseInt(textField.getText().split(";")[0]),
                    Integer.parseInt(textField.getText().split(";")[1])};
    }
}
