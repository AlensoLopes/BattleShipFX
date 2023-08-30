package fr.battleshipfx;

import fr.battleshipfx.Controller.MainController;
import fr.battleshipfx.Database.CreateID;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BattleShip extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        MainController controller = new MainController(stage);
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BattleShip");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("icon/world-war-two-battleship-cartoon-aloysius-patrimonio.jpg"))));
        CreateID.createUUIDForPlayer();
        stage.show();
    }

    /*TODO
    *  VIEW STATS
    * */

    public static void main(String[] args) {
        launch();
    }
}