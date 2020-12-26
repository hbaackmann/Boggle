package boggle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * App Class
 * <p>
 * The App class opens a welcome screen, prompting the user to select the size of their Boggle game. The App instantiates
 * a new Game (of the selected size), and keeps track of the current game. If the user decides to start a new game during
 * play, the Game will call the App to replace itself with a new instance of the Game class. Additionally, the App class
 * contains a DragDetector that tells the scene to allow drag clicks so that the user can select word on the screen by
 * dragging the mouse across neighboring letters.
 **/


public class App extends Application {

    private static Stage _stage;
    private Scene _scene;

    /*
    * The start() method launches the welcome screen and initializes the App's _stage variable.
    *
    * Input: The stage used the entire time the App is running.
    * Output: Nothing.
    * */
    public void start(Stage stage) {
        _stage = stage;
        this.createWelcomeScreen();
    }

    /*
    * This method creates and formats the welcome screen scene, then sets the stage.
    *
    * Input: Nothing.
    * Output: Nothing.
    * */
    private void createWelcomeScreen() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: grey;");

        Label label = new Label("Welcome to Boggle! Please select the size of your game.");
        label.setPrefSize(400, 50);
        label.setAlignment(Pos.CENTER);
        label.setStyle(Constants.BUTTONS_CSS);
        root.setTop(label);
        BorderPane.setMargin(label, new Insets(12, 12, 12, 12));

        Button boggle = new Button("4 x 4");
        boggle.setOnAction(new SmallBoggle());
        boggle.setStyle(Constants.BUTTONS_CSS);
        root.setLeft(boggle);
        BorderPane.setMargin(boggle, new Insets(12, 12, 12, 50));

        Button bigboggle = new Button("5 x 5");
        bigboggle.setOnAction(new BigBoggle());
        bigboggle.setStyle(Constants.BUTTONS_CSS);
        root.setRight(bigboggle);
        BorderPane.setMargin(bigboggle, new Insets(12, 50, 12, 12));

        _scene = new Scene(root, 400, 150, Color.WHITE);
        _stage.setScene(_scene);
        _stage.setTitle("Boggle!");
        _stage.show();
    }

     /*
    * The createGame() method is called by both the App and Game class. It sets the current game (which is
    * stored in the _game private variable) to a new Game, then creates a scene for that game and changes the
    * scene being displayed by the App's _stage.
    *
    * Input: Dimension of the new game being created.
    * Output: Nothing.
    * */

    void createGame(int dim) {
        Game _game = new Game(this, dim);
        _scene = new Scene(_game.getRoot(), Constants.APP_WIDTH, Constants.APP_HEIGHT, Color.BLACK);
        _scene.addEventFilter(MouseEvent.DRAG_DETECTED, new DragDetector());
        _stage.setScene(_scene);
    }

    /**
     * SmallBoggle
     * The Small Boggle event handler creates a new 4x4 Boggle game upon the user clicking
     * on the welcome screen or the Game menu.
     */
    private class SmallBoggle implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            App.this.createGame(4);
        }
    }

    /**
     * BigBoggle
     * The Big Boggle event handler creates a new 5x5 Boggle game upon the user clicking
     * on the welcome screen or the Game menu.
     */
    private class BigBoggle implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            App.this.createGame(5);
        }
    }


    /**
     * DragDetector
     * The DragDetector tells the scene to allow drag clicks so that the user can select word on the screen by
     * dragging the mouse across neighboring letters.
     */
    private class DragDetector implements EventHandler<MouseEvent> {
        public void handle(MouseEvent mouseEvent) {
            _scene.startFullDrag();
        }
    }

    /*
    * ----------------------------------------------- Mainline ---------------------------------------------------
    * */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
