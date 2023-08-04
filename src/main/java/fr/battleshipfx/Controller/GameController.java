package fr.battleshipfx.Controller;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Board.DisplayBoard;
import fr.battleship.Player.Bot;
import fr.battleship.Player.PlayerHuman;
import fr.battleship.Win.Win;
import fr.battleshipfx.BattleShip;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class GameController implements Initializable {
    
    @FXML private GridPane board;
    @FXML private BorderPane background;
    @FXML private Pane center;
    @FXML private VBox right;
    @FXML private VBox left;
    @FXML private Text pseudo;
    @FXML protected TextField coord;
    @FXML protected Button validation;
    @FXML protected TextField axis;
    
    private MainController controller;
    private final PlaceShipController placeShipController;
    private final ShootController shootController;
    private final BotController botController;

    private static final int SIZE = 11;
    private static final int SIZE_CELL = 44;
    public String[][] board_game;
    public String[][] board_bot;
    protected PlayerHuman playerHuman;
    protected Bot bot;

    private int nb_turn = 0;

    private static final Background START = new Background(new BackgroundFill(Color.WHITE, null, null));
    protected static final Background SMALL = new Background(new BackgroundFill(Color.RED, null, null));
    protected static final Background MEDIUM = new Background(new BackgroundFill(Color.AQUA, null, null));
    protected static final Background LARGE = new Background(new BackgroundFill(Color.AQUAMARINE, null, null));
    protected static final Background LARGER = new Background(new BackgroundFill(Color.BLUE, null, null));
    protected static final Background DEAD = new Background(new BackgroundFill(Color.DARKRED, null, null));
    protected static final Background HIT = new Background(new BackgroundFill(Color.DARKGREEN, null, null));
    protected static final Background NOTHIT = new Background(new BackgroundFill(Color.GREEN, null, null));
    public GameController() {
        this.placeShipController = new PlaceShipController();
        this.shootController = new ShootController();
        botController = new BotController();
        playerHuman = new PlayerHuman();
        bot = new Bot();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        placeShipController.setController(this);
        shootController.setGameController(this);
        botController.setGameController(this);

        board.prefHeightProperty().bind(center.heightProperty());
        board.prefWidthProperty().bind(center.widthProperty());

        createBoard();
        pseudo.setText(pseudo.getText() + " : " + MainController.getPseudonyme());

        axis.setVisible(false);
        axis.setText("V");
        validation.disableProperty().bind(coord.textProperty().isEmpty());


        startGame();

        coord.textProperty().addListener((observableValue, s, t1) -> {
            if(!s.matches("[a-zA-Z0-9]*") || s.length() > 2) coord.setStyle("-fx-text-fill: red");
            else coord.setStyle("-fx-text-fill: black");
        });

        axis.textProperty().addListener((observableValue, s, t1) -> {
            if(!s.matches("[a-zA-Z]*") || s.length() > 1) axis.setStyle("-fx-text-fill: red");
            else axis.setStyle("-fx-text-fill: black");
        });

        validation.setOnAction(actionEvent ->{
            try{
                String[] coordo = placeShipController.placeShip();
                DisplayBoard displayBoard = new DisplayBoard();

                if(coordo != null){
                    displayBoard.displayBoard(board_game);
                    placeShipController.boatPlaced[Integer.parseInt(coordo[2])-1] = true;
                    if(placeShipController.boatPlaced[0]){
                        axis.setVisible(true);
                        validation.textProperty().unbind();
                        axis.disableProperty().bind(coord.textProperty().isEmpty());
                        validation.disableProperty().bind(axis.textProperty().isEmpty());
                    }

                    updateBoard(Integer.parseInt(coordo[0]), Integer.parseInt(coordo[1]),
                            Integer.parseInt(coordo[2]), coordo[3], coordo[4]);
                    axis.clear();
                }
                if(placeShipController.boatPlaced[3]){
                    axis.setVisible(false);
                    validation.disableProperty().unbind();
                    validation.disableProperty().bind(coord.textProperty().isEmpty());
                    proccessTurn();
                    displayBoard.displayBoard(board_game);
                    if(Win.getWinner(board_game, board_bot, bot, playerHuman)){
                        validation.disableProperty().unbind();
                        validation.setDisable(true);
                        coord.setDisable(true);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
        });

        for(Node node : board.getChildren()){

            if(node instanceof Label
            && GridPane.getRowIndex(node) > 0
            && GridPane.getColumnIndex(node) > 0){
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    TextInputDialog dialog = new TextInputDialog();
                    Optional<String> res;
                    boolean good = false;
                    dialog.setTitle("Select your axis");

                    if(placeShipController.boatPlaced[0] && !placeShipController.boatPlaced[3]){
                        dialog.getEditor().textProperty().addListener((observableValue, s, t1) -> {
                            if(!s.matches("[a-zA-Z]*") || s.length() > 1) dialog.getEditor().setStyle("-fx-text-fill: red");
                            else dialog.getEditor().setStyle("-fx-text-fill: black");
                        });
                        res = dialog.showAndWait();
                        while(!good){
                            if(res.get().equalsIgnoreCase("V") || res.get().equalsIgnoreCase("H"))good = true;
                            else res = dialog.showAndWait();
                        }
                    }

                    String[] coordo = placeShipController.placeShipOnClick(GridPane.getRowIndex(node) -1, GridPane.getColumnIndex(node)-1, dialog.getEditor().getText());
                    DisplayBoard displayBoard = new DisplayBoard();

                    if(coordo != null){
                        displayBoard.displayBoard(board_game);
                        placeShipController.boatPlaced[Integer.parseInt(coordo[2])-1] = true;
                        if(placeShipController.boatPlaced[0]){
                            axis.setVisible(true);
                            validation.textProperty().unbind();
                            axis.disableProperty().bind(coord.textProperty().isEmpty());
                            validation.disableProperty().bind(axis.textProperty().isEmpty());
                        }

                        updateBoard(Integer.parseInt(coordo[0]), Integer.parseInt(coordo[1]),
                                Integer.parseInt(coordo[2]), coordo[3], coordo[4]);
                        axis.clear();
                    }else if(placeShipController.boatPlaced[3] && !Win.getWinner(board_game, board_bot, bot, playerHuman)){
                        axis.setVisible(false);
                        validation.disableProperty().unbind();
                        validation.disableProperty().bind(coord.textProperty().isEmpty());
                        proccessTurn(new int[]{GridPane.getRowIndex(node)-1, GridPane.getColumnIndex(node) -1});
                        displayBoard.displayBoard(board_game);

                    }else{
                        if(Win.getWinner(board_game, board_bot, bot, playerHuman)){
                            validation.disableProperty().unbind();
                            validation.setDisable(true);
                            coord.setDisable(true);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to start a new game ?");
                            ButtonType newGameWithSameName = new ButtonType("Yes with the same name");
                            ButtonType newGameWithotherName = new ButtonType("Yes but with another name");
                            ButtonType stop = new ButtonType("NO");
                            alert.getButtonTypes().setAll(newGameWithSameName, newGameWithotherName, stop);
                            alert.showAndWait().ifPresent(response -> {
                                if(response == newGameWithSameName) {
                                    startGame();
                                    FXMLLoader fxmlLoader = new FXMLLoader(BattleShip.class.getResource("Game.fxml"));
                                    try {
                                        MainController.stage.getScene().setRoot(fxmlLoader.load());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else if(response == newGameWithotherName){
                                    stopGame();
                                    MainController mainController = new MainController(MainController.stage);
                                    FXMLLoader fxmlLoader = new FXMLLoader(BattleShip.class.getResource("Main.fxml"));
                                    fxmlLoader.setController(mainController);
                                    try {
                                        MainController.stage.getScene().setRoot(fxmlLoader.load());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }else{
                                    MainController.stage.close();
                                }
                            });
                        }
                    }
                });

            }
        }


        /*
         * TODO
         *  CLEAN CODE PLACE MANUAL
         *  CLEAN CODE PLACE AUTO
         *  BUG DISPLAY FIRST SHOOT AFTER PLACE ON CLICK
         * */
    }

    private void startGame(){
        board_game = new CreateBoard().createBoard();
        board_bot = new CreateBoard().createBoard();
        playerHuman.setPlayerName(MainController.getPseudonyme());
        bot.placeShipBot(board_bot);
    }

    private void stopGame(){
        board = null;
        board_bot = null;
    }

    private void proccessTurn(){
        nb_turn++;
        int shoot = shootController.shootBoat(board_bot);
        coord.clear();

        if(shoot == -1) System.out.println("Erreur");
        int shootBot = botController.shootBot(board_game);
        if(shootBot == 1) updateBoardLabel(bot.coordHit[0] + 1, bot.coordHit[1] + 1, DEAD, null);
    }

    private void proccessTurn(int[] coord){
        nb_turn++;
        int shoot = shootController.shootBoat(board_bot, coord);

        if(shoot == -1) System.out.println("Erreur");
        int shootBot = botController.shootBot(board_game);
        if(shootBot == 1) updateBoardLabel(bot.coordHit[0] + 1, bot.coordHit[1] + 1, DEAD, null);
    }


    protected void updateBoardLabel(int x, int y, Background background, String text){
        Label label = (Label) searchNode(x, y);
        label.setBackground(background);
        if(text == null) label.setText(label.getText());
        else label.setText(text);
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

            label = new Label(String.valueOf(Character.valueOf((char) (96 + i))).toUpperCase());
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
                Label label = new Label(String.valueOf(Character.valueOf((char) (96+i))).toUpperCase() + j);
                label.setPrefSize(SIZE_CELL, SIZE_CELL);
                label.setBackground(START);
                label.setTextFill(Color.BLACK);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setAlignment(Pos.CENTER);

                board.add(label, j, i);
            }
        }
    }
    
    public void setController(MainController controller) {
        this.controller = controller;
    }

    private void updateBoard(int x, int y, int size, String sens, String type){
        int x_axis = x;
        int y_axis = y;

        for (int i = 0; i < size; i++) {
            createLabelFromInput(searchNode(x_axis,y_axis), type);
            if(sens.equals("V")) x_axis++;
            else y_axis++;
        }
    }

    private Node searchNode(int x, int y){
        AtomicReference<Node> nodeAtomicReference = new AtomicReference<>();
        board.getChildren().forEach(node -> {
            if(node instanceof Label
            && GridPane.getColumnIndex(node) == y
            && GridPane.getRowIndex(node) == x) nodeAtomicReference.set(node);
        });
        return nodeAtomicReference.get();
    }

    private void createLabelFromInput(Node node, String type){
        switch (type){
            case "small" -> ((Label)node).setBackground(SMALL);
            case "medium" -> ((Label)node).setBackground(MEDIUM);
            case "large" -> ((Label)node).setBackground(LARGE);
            case "larger" -> ((Label)node).setBackground(LARGER);
        }
        ((Label)node).setText(type.toUpperCase());
    }
}
