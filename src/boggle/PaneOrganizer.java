package boggle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 * PaneOrganizer
 * <p>
 * The PaneOrganizer is instantiated by the Game class before any of the Game's components are instantiated (such as
 * the GameBoard, Wordlist, Timer, etc). It is responsible for organizing all of the graphical elements of the application
 * using a border pane as the root of the scene to organize the top, right, left, and bottom of the GUI. It adds child Panes
 * to each of the aforementioned sections, the stores references to those Panes so that the Game can pass on the references
 * with getter methods. Additionally, the PaneOrganizer manages the menu that appears across the top of display, containing a
 * Quit button, a Boggle button (4x4), and a BigBoggle button (5x5).
 **/

class PaneOrganizer {

    private static javafx.scene.layout.BorderPane _root;
    private static javafx.scene.layout.GridPane _boardPane;
    private static javafx.scene.layout.HBox _bottomPane;
    private static javafx.scene.layout.GridPane _wordPane;
    private static Game _game;

    /*
    *       The PaneOrganizer() constructor stores the current game, initializes the _root variable with a new BorderPane,
    *   then creates Panes for the Menu (across the top), GameBoard, WordList, and GameInfoVis and adds them as children
    *   to the BorderPane.
    *
    *   Input:   game -> the instance of the Game class that instantiated the PaneOrganizer.
    *            dimension -> the dimension of the current game.
    *   Output:  nothing.
    **/

    PaneOrganizer(Game game) {
        _game = game;

        _root = new BorderPane();
        _root.setPrefSize(Constants.APP_WIDTH, Constants.APP_HEIGHT);
        _root.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        _root.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

        this.createMenu();
        this.createBoardPane();
        this.createWordListPane();
        this.createBottomPane();

    }

    /*
    *       The createNewGame() method calls the game to create a new Game with the inputted dimension.
    *   This method is called when the user selects one of the new game button in the menu bar.
    *
    *   Input:   dim -> the desired dimension for the new game.
    *   Output:  nothing.
    **/

    private void createNewGame(int dim) {
        _game.createNewGame(dim);
    }

    /*
    *       Getter Methods for all of the child Panes of the root Pane (the BorderPane organizing all the child Panes).
    *
    *   Input:   nothing.
    *   Output:  a child pane of BorderPane.
    **/

    GridPane getGamePane() {
        return _boardPane;
    }

    GridPane getWordPane() {
        return _wordPane;
    }

    HBox getBottomPane() {
        return _bottomPane;
    }

    Pane getRoot() {
        return _root;

    }

    /*
    *       The createMenu() method creates the Menu that is displayed across the top of the screen during the game.
    *   It contains a stylized title, a Quit button, a button labeled "new Boggle" that creates a 4x4 game, and a
    *   button labeled "new BigBoggle" that creates a 5x5 game.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    **/

    private void createMenu() {
        GridPane topPane = new GridPane();
        topPane.setAlignment(Pos.CENTER);
        topPane.setStyle("-fx-background-color: grey;");
        topPane.setPrefSize(Constants.TOP_PANE_WIDTH, Constants.TOP_PANE_HEIGHT);

        topPane.getColumnConstraints().add(new ColumnConstraints(120));
        topPane.getColumnConstraints().add(new ColumnConstraints(300));
        topPane.getColumnConstraints().add(new ColumnConstraints(65));
        topPane.getColumnConstraints().add(new ColumnConstraints(110));
        topPane.getColumnConstraints().add(new ColumnConstraints(120));


        Text title = new Text("Boggle!");
        Font font = Font.font("Comic Sans MS", FontWeight.BLACK, 34);
        title.setFont(font);
        title.setFill(Color.DARKORANGE);
        title.setStroke(Color.WHITE);
        title.setStrokeType(StrokeType.OUTSIDE);
        title.setStrokeWidth(1.5);
        topPane.add(title, 0, 0);
        GridPane.setHalignment(title, HPos.CENTER);

        //Create quit button.
        Button quitButton = new Button("Quit");
        quitButton.setStyle(Constants.BUTTONS_CSS);
        quitButton.setOnAction(new QuitHandler());
        topPane.add(quitButton, 2, 0);

        //Create generate new Boggle Game (4x4) button.
        Button boggleGame = new Button("New Boggle");
        boggleGame.setStyle(Constants.BUTTONS_CSS);
        boggleGame.setOnAction(new SmallBoggle());
        topPane.add(boggleGame, 3, 0);

        //Create generate new Big Boggle Game (5x5) button.
        Button bigBoggleGame = new Button("New BigBoggle");
        bigBoggleGame.setStyle(Constants.BUTTONS_CSS);
        bigBoggleGame.setOnAction(new BigBoggle());
        topPane.add(bigBoggleGame, 4, 0);

        _root.setTop(topPane);
    }

    /*
    *       The createBoardPane() method creates a GridPane to hold the visual representation of the letters
    *   on the GameBoard and stores it as the _boardPane. The _boardPane is passed to the GameBoardVis class
    *   which manages the visual representation of the GameBoard.
    *
    *   Input:   nothing
    *   Output:  nothing.
    **/

    private void createBoardPane() {
        _boardPane = new GridPane();
        _boardPane.setPrefSize(Constants.BOOGLE_BOARD_SIZE, Constants.BOOGLE_BOARD_SIZE);
        _boardPane.setAlignment(Pos.CENTER);
        _boardPane.setStyle("-fx-background-color: lightblue;");
        _root.setCenter(_boardPane);

    }

    /*
    *       The createWordListPane() method creates a GridPane to hold the visual representation of the words
    *   that have been guessed by the user, and store the GridPane as the _wordPane. The _wordPane is passed
    *   to the WordListVis class which manages the visual representation of the guessed words.
    *
    *   Input:   nothing
    *   Output:  nothing.
    **/

    private void createWordListPane() {
        _wordPane = new GridPane();
        _wordPane.setAlignment(Pos.CENTER_LEFT);
        _wordPane.setPrefSize(Constants.WORD_LIST_WIDTH, Constants.WORD_LIST_HEIGHT);
        _wordPane.setStyle("-fx-background-color: lightblue;");

        _root.setRight(_wordPane);
    }

    /*
    *       The createBottomPane() method creates a GridPane to hold the visual representation of the words
    *   that have been guessed by the user, and store the GridPane as the _wordPane. The _wordPane is passed
    *   to the WordListVis class which manages the visual representation of the guess words.
    *
    *   Input:   nothing
    *   Output:  nothing.
    **/

    private void createBottomPane() {
        _bottomPane = new HBox();
        _bottomPane.setPrefSize(Constants.BOTTOM_PANE_WIDTH, Constants.BOTTOM_PANE_HEIGHT);
        _bottomPane.setAlignment(Pos.CENTER);
        _bottomPane.setStyle("-fx-background-color: grey;");

        _root.setBottom(_bottomPane);
    }


    /**
     * QuitHandler Class
     * <p>
     * The QuitHandler class quits the app with exit status code 0.
     **/
    private class QuitHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            System.exit(0);
        }
    }

    /**
     * SmallBoggle Class
     * <p>
     * The SmallBoggle class creates a new 4x4 boggle game.
     **/
    private class SmallBoggle implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            PaneOrganizer.this.createNewGame(4);
        }
    }

    /**
     * BigBoggle Class
     * <p>
     * The SmallBoggle class creates a new 5x5 boggle game.
     **/
    private class BigBoggle implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            PaneOrganizer.this.createNewGame(5);
        }
    }
}


