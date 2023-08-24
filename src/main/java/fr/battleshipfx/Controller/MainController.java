package fr.battleshipfx.Controller;

import fr.battleshipfx.BattleShip;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TextField pseudo;
    @FXML private Button bot;


    private static String pseudonyme;

    protected static Stage stage;

    private final GameController gameController;

    public MainController(Stage stage) {
        MainController.stage = stage;
        gameController = new GameController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameController.setController(this);
        bot.disableProperty().bind(pseudo.textProperty().isEmpty());
        addEventHandler(bot);
    }

    public static String getPseudonyme() {
        return pseudonyme;
    }
    private void addEventHandler(Button button){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            pseudonyme = pseudo.getText();
            FXMLLoader fxmlLoader = new FXMLLoader(BattleShip.class.getResource("Game.fxml"));
            try {
                stage.setHeight(600);
                stage.setWidth(800);

                stage.setX(MainController.centerScene(stage)[0]);
                stage.setY(MainController.centerScene(stage)[1]);

                stage.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } );
    }

    public static double[] centerScene(Stage stage){
        Rectangle2D bound = Screen.getPrimary().getVisualBounds();
        double x = (bound.getWidth() - stage.getWidth()) / 2;
        double y = (bound.getHeight() - stage.getHeight()) / 2;
        return new double[]{x,y};
    }
}
