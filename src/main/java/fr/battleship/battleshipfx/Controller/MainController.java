package fr.battleship.battleshipfx.Controller;

import fr.battleship.battleshipfx.BattleShip;
import javafx.beans.binding.Bindings;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextField pseudo;
    @FXML private Button jouer;

    private String pseudonyme;

    private Stage stage;

    private GameController gameController;

    public MainController(Stage stage) {
        this.stage = stage;
        gameController = new GameController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameController.setController(this);

        jouer.disableProperty().bind(pseudo.textProperty().isEmpty());
        jouer.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            pseudonyme = pseudo.getText();
            FXMLLoader fxmlLoader = new FXMLLoader(BattleShip.class.getResource("Game.fxml"));
            try {
                stage.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } );

    }

    public String getPseudonyme() {
        return pseudonyme;
    }
}
