module fr.battleship.battleshipfx {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens fr.battleship.battleshipfx to javafx.fxml;
    opens fr.battleship.battleshipfx.Controller;
    exports fr.battleship.battleshipfx;
}