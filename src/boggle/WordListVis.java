package boggle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * WordListVis Class
 * <p>
 * The WordListVis class represents the graphical abstraction of the WordList. It also formats and manages the submit,
 * delete, and clear buttons and contains private inner class that act as listeners for each button. To easily access
 * the next available label, the labels are initially added to a queue and removed throughout the game. An improvement
 * would be to have more labels if the user decided to expand the app, so that there would always be more labels.
 **/

class WordListVis {
    private final javafx.scene.layout.GridPane _pane;
    private final ArrayBlockingQueue<Label> _queue;
    private javafx.scene.control.TextField _txtField;
    private javafx.scene.control.Label _wordLabel;
    private javafx.scene.control.Button[] _buttons;
    private final WordList _wordlist;
    private final GameBoardVis _gbVis;

    /*
    *       The WordListVis() constructor initializes all of its private variables and sets the row and column constraints.
    *   Then it creates the text field where the user can enter words, creates the buttons that control the GameBoard
    *   (for formatting purposes), and finally creates the Labels that display the guessed words.
    *
    *   Input:   pane -> GridPane set as the right Pane in the BorderPane.
    *            wordlist -> the WordList that instantiated this instance of the WordListVis.
    *            gbVisulaiser -> reference to the GameBoard's visualizer to perform button functions.
    *   Output:  nothing.
    */

    WordListVis(GridPane pane, WordList wordlist, GameBoardVis gbVisualizer) {
        _pane = pane;
        _wordlist = wordlist;
        _gbVis = gbVisualizer;
        _gbVis.setWordListVis(this);
        _queue = new ArrayBlockingQueue<>((Constants.NUM_WORD_LIST_COLS * Constants.NUM_WORD_LIST_ROWS));

        this.createGrid();
        this.createTextFields();
        this.createButtons();
        this.createLabels();

    }

    void gameOver() {
        _wordLabel.setText("");
        _txtField.setEditable(false);
        _txtField.setOnKeyPressed(null);

        for (int i = 0; i < 3; i++) {
            _buttons[i].setOnAction(null);
        }

    }

    private GameBoardVis getGameBoard() {
        return _gbVis;
    }

    void updateWordLabel(String word) {
        _wordLabel.setText(word);
    }

    void addWord(String word, Boolean valid) {
        Label label = _queue.remove();

        if (valid) {
            label.setStyle(Constants.VALID_WORD_CSS);
        } else {
            label.setStyle(Constants.INVALID_WORD_CSS);
        }
        label.setText(word);
    }

    /*
    *       The createGrid() method initializes all of the _pane's row and column constraints.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    */

    private void createGrid() {
        for (int i = 0; i < Constants.NUM_WORD_LIST_COLS; i++) {
            ColumnConstraints col = new ColumnConstraints(90);
            _pane.getColumnConstraints().add(col);
        }

        // Set Row Constraint for Word Display (displays current word selected on GameBoard)
        _pane.getRowConstraints().add(new RowConstraints(30));

        //Set Row Constraint for TextField
        _pane.getRowConstraints().add(new RowConstraints(30));

        //Set Row Constraint for Buttons
        _pane.getRowConstraints().add(new RowConstraints(40));

        //Set Row Constraints for WordList
        for (int i = 0; i < (Constants.NUM_WORD_LIST_ROWS - 3); i++) {
            _pane.getRowConstraints().add(new RowConstraints(25));
        }
    }

    /*
    *       The createTextFields() method creates the label that displays the word currently selected on the GameBoard,
    *   and the TextField that the user can use to enter words they see on the board.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    */

    private void createTextFields() {
        _wordLabel = new Label();
        _wordLabel.setMinSize(270, 25);
        _wordLabel.setAlignment(Pos.TOP_CENTER);
        _wordLabel.setStyle(Constants.SELECTED_WORD_CSS);
        _pane.add(_wordLabel, 1, 0);
        GridPane.setHalignment(_wordLabel, HPos.CENTER);

        _txtField = new TextField();
        _txtField.setMinSize(270, 25);
        _txtField.setPromptText("Enter a word here or drag across letters!");
        _txtField.setOnKeyPressed(new KeyListener());
        _pane.add(_txtField, 0, 1);
        GridPane.setHalignment(_txtField, HPos.LEFT);

    }

    /*
    *       The createButtons() method creates the 'Submit', 'Delete', and 'Clear' buttons.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    */

    private void createButtons() {
        _buttons = new Button[3];

        _buttons[0] = new Button("Submit");
        _buttons[0].setMaxSize(70, 20);
        _buttons[0].setOnAction(new SubmitListener());
        _buttons[0].setStyle(Constants.GAME_BUTTONS_CSS);
        _pane.add(_buttons[0], 0, 2);
        GridPane.setHalignment(_buttons[0], HPos.CENTER);

        _buttons[1] = new Button("Delete");
        _buttons[1].setMaxSize(70, 20);
        _buttons[1].setOnAction(new DeleteListener());
        _buttons[1].setStyle(Constants.GAME_BUTTONS_CSS);
        _pane.add(_buttons[1], 1, 2);
        GridPane.setHalignment(_buttons[1], HPos.CENTER);

        _buttons[2] = new Button("Clear");
        _buttons[2].setMaxSize(70, 20);
        _buttons[2].setOnAction(new ClearListener());
        _buttons[2].setStyle(Constants.GAME_BUTTONS_CSS);
        _pane.add(_buttons[2], 2, 2);
        GridPane.setHalignment(_buttons[2], HPos.CENTER);
    }

    /*
    *       The createLabels() method creates the labels that display the guessed words and adds them to the _pane.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    */

    private void createLabels() {
        Label[][] _labels = new Label[Constants.NUM_WORD_LIST_COLS][(Constants.NUM_WORD_LIST_ROWS - 2)];

        for (int col = 0; col < Constants.NUM_WORD_LIST_COLS; col++) {
            for (int row = 0; row < (Constants.NUM_WORD_LIST_ROWS - 3); row++) {
                _labels[col][row] = new Label();
                _labels[col][row].setMinSize(80, 20);
                _labels[col][row].setAlignment(Pos.CENTER);

                _queue.add(_labels[col][row]);
                _pane.add(_labels[col][row], col, (row + 3));
                GridPane.setHalignment(_labels[col][row], HPos.CENTER);
                GridPane.setValignment(_labels[col][row], VPos.CENTER);
            }
        }
    }

    /**
     * SubmitListener Class
     * <p>
     * The SubmitListener class first checks if there is text in the TextField that the user has inputted, otherwise
     * it asks the GameBoardVis for the selected letters, checks if the word is valid, and clears either the TextField
     * or the GameBoard.
     **/

    private class SubmitListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            if ((_txtField.getText() != null) && (!_txtField.getText().isEmpty())) {
                _wordlist.searchBoard(_txtField.getText());
                _txtField.clear();
            } else if (WordListVis.this.getGameBoard().getSelectedWord() != null) {
                GameBoardVis gbVis = WordListVis.this.getGameBoard();
                _wordlist.checkSelectedLetters(gbVis.getSelectedWord());
                GameBoardVis gb = WordListVis.this.getGameBoard();
                gb.setLetterLock(true);
                gb.clearAllSelectedLetters();
                gb.setLetterLock(false);
            }
        }
    }

    /**
     * DeleteListener Class
     * <p>
     * The DeleteListener class deselects the last selected letter on the GameBoard.
     **/

    private class DeleteListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            GameBoardVis gb = WordListVis.this.getGameBoard();
            gb.setLetterLock(true);
            gb.clearLastSelectedLetter();
            gb.setLetterLock(false);
        }
    }

    /**
     * ClearListener Class
     * <p>
     * The ClearListener class deselects all of the selected letters on the GameBoard.
     **/

    private class ClearListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            GameBoardVis gb = WordListVis.this.getGameBoard();
            gb.setLetterLock(true);
            gb.clearAllSelectedLetters();
            gb.setLetterLock(false);
            _txtField.clear();
        }
    }

    /**
     * KeyListener Class
     * <p>
     * The KeyListener class checks the String inputted into the TextField when the enter key is pressed.
     **/

    private class KeyListener implements EventHandler<KeyEvent> {
        public void handle(KeyEvent key) {
            if ((_txtField.getText() != null) && (key.getCode() == KeyCode.ENTER)) {
                _wordlist.searchBoard(_txtField.getText());
                _txtField.clear();
            }
        }
    }
}
