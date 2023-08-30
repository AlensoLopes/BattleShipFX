package fr.battleshipfx;

import fr.battleshipfx.Controller.MainController;
import fr.battleshipfx.Database.CreateID;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        CreateID.createUUIDForPlayer();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}