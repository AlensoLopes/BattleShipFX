module fr.battleship.battleshipfx {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens fr.battleshipfx to javafx.fxml;
    opens fr.battleshipfx.Controller;
    opens fr.battleshipfx.Utils;
    opens fr.battleship.Player;
    exports fr.battleshipfx;
}