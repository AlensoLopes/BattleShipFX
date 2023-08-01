module fr.battleship.battleshipfx {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens fr.battleshipfx to javafx.fxml;
    opens fr.battleshipfx.Controller;
    exports fr.battleshipfx;
}