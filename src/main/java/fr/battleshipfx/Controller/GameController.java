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
    protected final HistoryController historyController;
    protected final RoundController roundController;

    protected static final int SIZE = 11;
    protected static final int SIZE_CELL = 44;
    public String[][] board_game;
    public String[][] board_bot;
    protected PlayerHuman playerHuman;
    public Bot bot;
    protected DisplayBoard displayBoard;

    protected int nb_round = 0;
    protected boolean first_bind = false;

    protected static final Background START = new Background(new BackgroundFill(Color.WHITE, null, null));
    protected static final Background SMALL = new Background(new BackgroundFill(Color.RED, null, null));
    protected static final Background MEDIUM = new Background(new BackgroundFill(Color.AQUA, null, null));
    protected static final Background LARGE = new Background(new BackgroundFill(Color.AQUAMARINE, null, null));
    protected static final Background LARGER = new Background(new BackgroundFill(Color.BLUE, null, null));
    protected static final Background DEAD = new Background(new BackgroundFill(Color.DARKRED, null, null));
    protected static final Background HIT = new Background(new BackgroundFill(Color.DARKGREEN, null, null));
    protected static final Background NOTHIT = new Background(new BackgroundFill(Color.GREEN, null, null));
    public GameController() {
        placeShipController = new PlaceShipController();
        shootController = new ShootController();
        boardController = new BoardController();
        botController = new BotController();
        historyController = new HistoryController();
        roundController = new RoundController();
        
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
        historyController.setGameController(this);
        roundController.setGameController(this);
        
        setupBind();

        boardController.createBoard();
        pseudo.setText(pseudo.getText() + " : " + MainController.getPseudonyme());

        startGame();
        setupListenerOnTextField();

        playWithTextField();
        boardController.playWhenClickingOnBoard();
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
        validation.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(roundController.placeShipAndDisplay(placeShipController.placeShip()) == -1) return;
            roundController.doShootAndDisplay(null);
            roundController.processWin();
        });
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }
}
