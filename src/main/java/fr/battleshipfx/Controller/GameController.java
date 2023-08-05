package fr.battleshipfx.Controller;

import fr.battleship.Board.CreateBoard;
import fr.battleship.Board.DisplayBoard;
import fr.battleship.Player.Bot;
import fr.battleship.Player.PlayerHuman;
import fr.battleship.Win.Win;
import fr.battleshipfx.BattleShip;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicReference;

public class GameController implements Initializable {
    
    @FXML protected GridPane board;
    @FXML private BorderPane background;
    @FXML private Pane center;
    @FXML private VBox right;
    @FXML private VBox left;
    @FXML private Text pseudo;
    @FXML protected TextField coord;
    @FXML protected Button validation;
    @FXML protected TextField axis;
    @FXML public FlowPane pane;
    
    private MainController controller;
    protected final PlaceShipController placeShipController;
    protected final ShootController shootController;
    protected final BotController botController;
    protected final BoardController boardController;

    protected static final int SIZE = 11;
    protected static final int SIZE_CELL = 44;
    public String[][] board_game;
    public String[][] board_bot;
    protected PlayerHuman playerHuman;
    public Bot bot;
    private DisplayBoard displayBoard;

    private int nb_turn = 0;

    protected static final Background START = new Background(new BackgroundFill(Color.WHITE, null, null));
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
        boardController = new BoardController();
        botController = new BotController();
        playerHuman = new PlayerHuman();
        bot = new Bot();
        displayBoard = new DisplayBoard();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        placeShipController.setController(this);
        shootController.setGameController(this);
        botController.setGameController(this);
        boardController.setGameController(this);

        setupBind();

        boardController.createBoard();
        pseudo.setText(pseudo.getText() + " : " + MainController.getPseudonyme());

        startGame();
        setupListenerOnTextField();

        playWithTextField();
        boardController.playWhenClickingOnBoard();
    }


        /*
         * TODO
         *  BUG DISPLAY FIRST SHOOT AFTER PLACE ON CLICK
         * */



    protected boolean placeShipAndDisplay(String[] coordo){
        if(coordo == null) return false;
        if(!addHistoryOnError(coordo, "unplaced boat")) return false;
        displayBoard.displayBoard(board_game);


        addHistoryOnPlace(coordo);


        placeShipController.boatPlaced[Integer.parseInt(coordo[2])-1] = true;
        if(placeShipController.boatPlaced[0]){
            axis.setVisible(true);
            validation.textProperty().unbind();
            axis.disableProperty().bind(coord.textProperty().isEmpty());
            validation.disableProperty().bind(axis.textProperty().isEmpty());
        }

        boardController.updateBoard(Integer.parseInt(coordo[0]), Integer.parseInt(coordo[1]),
                Integer.parseInt(coordo[2]), coordo[3], coordo[4]);
        axis.clear();
        return true;
    }

    protected void addHistoryOnPlace(String[] coordo){
        if(!placeShipController.boatPlaced[0]){
            pane.getChildren().addAll(new Label("Boat placed : "), new Label(placeShipController.boatName.getName() + " in ("
                    + (char) (97 + Integer.parseInt(coordo[0]) - 1) + coordo[1] + ')'));
        }else{
            pane.getChildren().add(new Label(placeShipController.boatName.getName() + " in ("
                    + (char) (97 + (Integer.parseInt(coordo[0]) - 1)) + coordo[1] + ')'));
        }
    }
    protected void addHistoryOnShoot(int[] coordo, boolean hit, boolean bot){
        if(hit && !bot){
            pane.getChildren().addAll( new Label(playerHuman.getPlayerName() + " hit a boat in ("
                    + (char) (97 + (coordo[0])) + (coordo[1] + 1) + ')'));
            allBoatDestroy();
        }else if(!hit && !bot){
            pane.getChildren().add(new Label(playerHuman.getPlayerName() + " failed his shoot in ("
                    + (char) (97 + (coordo[0])) + (coordo[1] + 1) + ')'));
        }else if(hit && bot){
            pane.getChildren().add(new Label(this.bot.getBotName() + " hit a boat in ("
                    + (char) (97 + (coordo[0])) + (coordo[1] + 1) + ')'));
            allBoatDestroy();
        }else{
            pane.getChildren().add(new Label(this.bot.getBotName() + " failed his shoot in ("
                    + (char) (97 + (coordo[0])) + (coordo[1] + 1) + ')'));
        }

    }

    protected boolean addHistoryOnError(String[] coordo, String text){
        if(coordo[0] == null){
            Label label = new Label("An error occured, " + text + " ! ");
            label.setStyle("-fx-text-fill: red");
            pane.getChildren().add(label);
            return false;
        }
        return true;
    }

    protected void doShootAndDisplay(int[] coord){
        if(Win.getWinner(board_game, board_bot, bot, playerHuman)) return;
        if(!placeShipController.boatPlaced[placeShipController.boatPlaced.length-1]) return;

        axis.setVisible(false);
        validation.disableProperty().unbind();
        validation.disableProperty().bind(this.coord.textProperty().isEmpty());

        if(coord == null) proccessTurn();
        else processTurn(coord);

        displayBoard.displayBoard(board_game);
    }

    protected void processWin(){
        if(!Win.getWinner(board_game, board_bot, bot, playerHuman)) return;
        validation.disableProperty().unbind();
        validation.setDisable(true);
        coord.setDisable(true);
        replay(MainController.stage);
    }

    protected void replay(Stage stage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to start a new game ?");
        alert.getButtonTypes().setAll(
            new ButtonType("Yes with the same name"),
            new ButtonType("Yes but with another name"),
            new ButtonType("NO"));

        alert.showAndWait().ifPresent(response -> {
            if(response == alert.getButtonTypes().get(0)) {
                startGame();
                showAnotherFXML(null, "Game.fxml", stage, new BattleShip());
            } else if(response == alert.getButtonTypes().get(1)){
                stopGame();
                showAnotherFXML(new MainController(stage), "Main.fxml", stage, new BattleShip());
            }else{
                stage.close();
            }
        });
    }
    protected void showAnotherFXML(Object controller, String fxmlName, Stage stage, Object mainClass){
        FXMLLoader fxmlLoader = new FXMLLoader(mainClass.getClass().getResource(fxmlName));
        if(controller != null) fxmlLoader.setController(controller);
        try {
            stage.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String setupDialogForAxisWhenClickOnBoard(){
        TextInputDialog dialog = new TextInputDialog();
        Optional<String> res;
        boolean good = false;
        dialog.setTitle("Select your axis");

        if(placeShipController.boatPlaced[0] && !placeShipController.boatPlaced[placeShipController.boatPlaced.length-1]){
            dialog.getEditor().textProperty().addListener((observableValue, s, t1) -> {
                if(!s.matches("[a-zA-Z]*") || s.length() > 1) dialog.getEditor().setStyle("-fx-text-fill: red");
                else dialog.getEditor().setStyle("-fx-text-fill: black");
            });
            res = dialog.showAndWait();
            while(!good){
                if((res.isPresent() && res.get().equalsIgnoreCase("V"))
                        || (res.isPresent() && res.get().equalsIgnoreCase("H"))) good = true;
                else res = dialog.showAndWait();
            }
        }
        return dialog.getEditor().getText();
    }

    protected void startGame(){
        board_game = new CreateBoard().createBoard();
        board_bot = new CreateBoard().createBoard();
        playerHuman.setPlayerName(MainController.getPseudonyme());
        bot.placeShipBot(board_bot);
    }

    protected void stopGame(){
        board = null;
        board_bot = null;
    }

    protected void proccessTurn(){
        nb_turn++;
        pane.getChildren().add(new Label("Round : " + nb_turn));
        int shoot = shootController.shootBoat(board_bot);
        coord.clear();

        if(shoot == -1){
            System.out.println("Erreur");
            addHistoryOnError(new String[]{null}, "unlaunched shoot");
            nb_turn--;
            return;
        }
        int shootBot = botController.shootBot(board_game);
        if(shootBot == 1){
            boardController.updateBoardLabel(bot.coordHit[0] + 1, bot.coordHit[1] + 1, DEAD, null);
            addHistoryOnShoot(bot.coordHit, true, true);
        }else{
            addHistoryOnShoot(bot.coordHit, false, true);
        }
        pane.getChildren().add(new Label("End of Round : " + nb_turn));

    }

    protected void processTurn(int[] coord){
        nb_turn++;
        pane.getChildren().add(new Label("Round : " + nb_turn));
        int shoot = shootController.shootBoat(board_bot, coord);

        if(shoot == -1){
            addHistoryOnError(new String[]{null}, "unlaunched shoot");
            System.out.println("Erreur");
            nb_turn--;
            return;
        }
        int shootBot = botController.shootBot(board_game);
        if(shootBot == 1){
            boardController.updateBoardLabel(bot.coordHit[0] + 1, bot.coordHit[1] + 1, DEAD, null);
            addHistoryOnShoot(bot.coordHit, true, true);
        }else{
            addHistoryOnShoot(bot.coordHit, false, true);
        }
        pane.getChildren().add(new Label("End of Round : " + nb_turn));
    }
    public void setController(MainController controller) {
        this.controller = controller;
    }
    private void setupBind(){
        board.prefHeightProperty().bind(center.heightProperty());
        board.prefWidthProperty().bind(center.widthProperty());
        axis.setVisible(false);
        axis.setText("V");
        validation.disableProperty().bind(coord.textProperty().isEmpty());
    }
    private void setupListenerOnTextField(){
        coord.textProperty().addListener((observableValue, s, t1) -> {
            if(!s.matches("[a-zA-Z0-9]*") || s.length() > 2) coord.setStyle("-fx-text-fill: red");
            else coord.setStyle("-fx-text-fill: black");
        });

        axis.textProperty().addListener((observableValue, s, t1) -> {
            if(!s.matches("[a-zA-Z]*") || s.length() > 1) axis.setStyle("-fx-text-fill: red");
            else axis.setStyle("-fx-text-fill: black");
        });
    }
    private void playWithTextField(){
        validation.setOnAction(actionEvent ->{
            try{
                if(!placeShipAndDisplay(placeShipController.placeShip())) return;
                doShootAndDisplay(null);
                processWin();
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
        });
    }

    private void allBoatDestroy(){
        if(PlayerHuman.entireShip[0] == 1)pane.getChildren().add(new Label(MainController.getPseudonyme() + " destroy the entire boat"));
        else if(PlayerHuman.entireShip[1] == 1) pane.getChildren().add(new Label(bot.getBotName() + " destroy the entire boat"));
        else if(PlayerHuman.entireShip[0] == 0)pane.getChildren().add(new Label(MainController.getPseudonyme() + " haven't destroy the entire boat"));
        else if(PlayerHuman.entireShip[1] == 0) pane.getChildren().add(new Label(bot.getBotName() + " haven't destroy the entire boat"));
        PlayerHuman.entireShip[0] = 0;
        PlayerHuman.entireShip[1] = 0;
    }
}
