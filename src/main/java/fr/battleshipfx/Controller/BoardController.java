package fr.battleshipfx.Controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.concurrent.atomic.AtomicReference;

public class BoardController {
    
    protected GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    protected void playWhenClickingOnBoard(){
        for(Node node : gameController.board.getChildren()) {
            if (node instanceof Label
                    && GridPane.getRowIndex(node) > 0
                    && GridPane.getColumnIndex(node) > 0) {

                node.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    gameController.roundController.placeShipAndDisplay(
                            gameController.placeShipController.placeShipOnClick(GridPane.getRowIndex(node) - 1,
                            GridPane.getColumnIndex(node) - 1, gameController.setupDialogForAxisWhenClickOnBoard()));

                    gameController.roundController.doShootAndDisplay(new int[]{GridPane.getRowIndex(node) - 1,
                            GridPane.getColumnIndex(node) - 1});

                    gameController.roundController.processWin();

                });
            }
        }
    }

    protected void updateBoardLabel(int x, int y, Background background, String text){
        Label label = (Label) searchNode(x, y);
        label.setBackground(background);
        if(text == null) label.setText(label.getText());
        else label.setText(text);
    }

    protected void createBoard(){
        initConstraints();
        addIndic();
        fillBoard();
    }

    protected void initConstraints(){
        gameController.board.getRowConstraints().clear();
        gameController.board.getColumnConstraints().clear();
        gameController.board.getColumnConstraints().add(new ColumnConstraints(GameController.SIZE_CELL +1));
        gameController.board.getRowConstraints().add(new RowConstraints(GameController.SIZE_CELL+1));
        gameController.board.setGridLinesVisible(false);
        gameController.board.setAlignment(Pos.CENTER);
    }

    protected void addIndic(){
        for (int i = 1; i < GameController.SIZE; i++) {
            Label label = new Label(String.valueOf(i));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setPrefSize(GameController.SIZE_CELL, GameController.SIZE_CELL);
            label.setAlignment(Pos.CENTER);
            label.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            gameController.board.add(label, i, 0);

            label = new Label(String.valueOf(Character.valueOf((char) (96 + i))).toUpperCase());
            label.setTextAlignment(TextAlignment.CENTER);
            label.setPrefSize(GameController.SIZE_CELL, GameController.SIZE_CELL);
            label.setAlignment(Pos.CENTER);
            label.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            gameController.board.add(label, 0, i);
        }
    }

    protected void fillBoard(){
        for (int i = 1; i < GameController.SIZE; i++) {
            for (int j = 1; j < GameController.SIZE; j++) {
                Label label = new Label(String.valueOf(Character.valueOf((char) (96+i))).toUpperCase() + j);
                label.setPrefSize(GameController.SIZE_CELL, GameController.SIZE_CELL);
                label.setBackground(GameController.START);
                label.setTextFill(Color.BLACK);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setAlignment(Pos.CENTER);

                gameController.board.add(label, j, i);
            }
        }
    }

    protected void updateBoard(int x, int y, int size, String sens, String type){
        int x_axis = x;
        int y_axis = y;
        if(sens == null){
            createLabelFromInput(searchNode(x_axis, y_axis), type);
            return;
        }
        for (int i = 0; i < size; i++) {
            createLabelFromInput(searchNode(x_axis,y_axis), type);
            if(sens.equals("V")) x_axis++;
            else y_axis++;
        }
    }

    protected Node searchNode(int x, int y){
        AtomicReference<Node> nodeAtomicReference = new AtomicReference<>();
        gameController.board.getChildren().forEach(node -> {
            if(node instanceof Label
                    && GridPane.getColumnIndex(node) == y
                    && GridPane.getRowIndex(node) == x) nodeAtomicReference.set(node);
        });
        return nodeAtomicReference.get();
    }

    protected void createLabelFromInput(Node node, String type){
        switch (type){
            case "small" -> ((Label)node).setBackground(GameController.SMALL);
            case "medium" -> ((Label)node).setBackground(GameController.MEDIUM);
            case "large" -> ((Label)node).setBackground(GameController.LARGE);
            case "larger" -> ((Label)node).setBackground(GameController.LARGER);
        }
        ((Label)node).setText(type.toUpperCase());
    }
}
