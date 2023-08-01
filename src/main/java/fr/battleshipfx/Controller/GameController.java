package fr.battleshipfx.Controller;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Board.DisplayBoard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    
    @FXML private GridPane board;
    @FXML private BorderPane background;
    @FXML private Pane center;
    @FXML private VBox right;
    @FXML private VBox left;
    @FXML private Text pseudo;
    @FXML protected TextField coord;
    @FXML protected Button validation;
    
    private MainController controller;
    private PlaceShipController placeShipController;

    private static final int SIZE = 10;
    private static final int SIZE_CELL = 44;
    public String[][] board_game;

    private static final Background START = new Background(new BackgroundFill(Color.WHITE, null, null));
    protected static final Background SMALL = new Background(new BackgroundFill(Color.RED, null, null));
    protected static final Background MEDIUM = new Background(new BackgroundFill(Color.AQUA, null, null));
    protected static final Background LARGE = new Background(new BackgroundFill(Color.AQUAMARINE, null, null));
    protected static final Background LARGER = new Background(new BackgroundFill(Color.BLUE, null, null));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        placeShipController = new PlaceShipController();
        placeShipController.setController(this);
        board.prefHeightProperty().bind(center.heightProperty());
        board.prefWidthProperty().bind(center.widthProperty());

        createBoard();
        board_game = new CreateBoard().createBoard();
        pseudo.setText(pseudo.getText() + " : " + MainController.getPseudonyme());
        validation.disableProperty().bind(coord.textProperty().isEmpty());

        validation.setOnAction(actionEvent ->{
            int[] coordo = placeShipController.placeShip();
            DisplayBoard displayBoard = new DisplayBoard();
            displayBoard.displayBoard(board_game);
            updateBoard(coordo[0], coordo[1], 2, "V", "medium");
        });
    }

    private void createBoard(){
        initConstraints();
        addIndic();
        fillBoard();
    }

    private void initConstraints(){
        board.getRowConstraints().clear();
        board.getColumnConstraints().clear();
        board.getColumnConstraints().add(new ColumnConstraints(SIZE_CELL+1));
        board.getRowConstraints().add(new RowConstraints(SIZE_CELL+1));
        board.setGridLinesVisible(false);
        board.setAlignment(Pos.CENTER);
    }

    private void addIndic(){
        for (int i = 1; i < SIZE; i++) {
            Label label = new Label(String.valueOf(i));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setPrefSize(SIZE_CELL, SIZE_CELL);
            label.setAlignment(Pos.CENTER);
            label.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            board.add(label, i, 0);

            label = new Label(String.valueOf(Character.valueOf((char) (96 + i))));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setPrefSize(SIZE_CELL, SIZE_CELL);
            label.setAlignment(Pos.CENTER);
            label.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            board.add(label, 0, i);
        }
    }

    private void fillBoard(){
        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                Label label = new Label("("+i+";"+j+")");
                label.setPrefSize(SIZE_CELL, SIZE_CELL);
                label.setBackground(START);
                label.setTextFill(Color.BLACK);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setAlignment(Pos.CENTER);

                board.add(label, i, j);
            }
        }
    }
    
    public void setController(MainController controller) {
        this.controller = controller;
    }

    private void updateBoard(int x, int y, int size, String sens, String type){
        if(sens.equals("V")){
            int c = y;
            for (int i = 0; i < size; i++) {
                for (Node node : board.getChildren()) {
                    if (node instanceof Label
                            && GridPane.getColumnIndex(node) == x
                            && GridPane.getRowIndex(node) == c) {
                        ((Label)node).setText(type.toUpperCase());
                        switch (type){
                            case "small" -> ((Label)node).setBackground(SMALL);
                            case "medium" -> ((Label)node).setBackground(MEDIUM);
                            case "large" -> ((Label)node).setBackground(LARGE);
                            case "larger" -> ((Label)node).setBackground(LARGER);
                        }
                    }
                }
                c++;
            }
        }
    }
}
