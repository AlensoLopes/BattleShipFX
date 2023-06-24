package fr.battleship.battleshipfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    
    @FXML private GridPane board;
    
    private MainController controller;

    private static final int SIZE = 9;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillBoard();
    }



    private void fillBoard(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Label label = new Label("("+i+";"+j+")");
                board.add(label, i, j);
            }
        }
    }
    
    public void setController(MainController controller) {
        this.controller = controller;
    }
}
