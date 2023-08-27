module fr.battleship.battleshipfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires org.json;


    opens fr.battleshipfx to javafx.fxml;
    opens fr.battleshipfx.Controller;
    opens fr.battleshipfx.Utils;
    opens fr.battleship.Player;
    exports fr.battleshipfx;
}