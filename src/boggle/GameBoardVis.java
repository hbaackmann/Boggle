package boggle;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * GameBoardVis Class
 * <p>
 * An instance of the GameBoardVis class is instantiated by the GameBoard class to display the letters on the
 * GameBoard. Because the GameBoard is interactive, the GameBoardVis class contains a private inner class called
 * BoggleSquare that represents a smart object that knows the letter it represents, it's neighbors, and if it was
 * selected. If it was selected, a BoggleSquare will highlight itself add itself to the _selected variable. The _selected 
 * variable ensures a square is only selected once during the creation of a string, and can therefore only be appended 
 * to the current _word once. The _selected variable also allows the GameBoardVis to achieve the 'Delete' button's 
 * functionality. It allows the user to un-select the BoggleSquare that was most recently selected, and delete it from 
 * the current word until no selected letters remain in the Stack. The GameBoardVis Class communicates with the 
 * WordListVis class (there is a two way reference), so that the WordListVis' listeners for the Delete, Clear, and 
 * Submit buttons can call the the GameBoardVis and the GameBoardVis can call the WordListVis to update the word label 
 * indicating which letters were selected (in order).
 **/

class GameBoardVis {
    private final GridPane _pane;
    private final BoggleSquare[][] _squares;
    private final int _dim;
    private StringBuilder _word;
    private Boolean _letterlock;
    private Stack<BoggleSquare> _selected;
    private WordListVis _wordlistVis;

     /*
    *       The GameBoardVis() constructor initializes all of its private variables and then creates the grid of
    *   BoggleSquares the represent the visual abstraction of the squares on the GameBoard (or Dice on a physical
    *   Boggle gameboard).
    *
    *   Input:   pane -> the instance GridPane located on the left side of the PaneOrganizer's BorderPane.
    *            dimension -> the dimension of the current game.
    *   Output:  nothing.
    */

    GameBoardVis(GridPane pane, int dimension) {
        _pane = pane;
        _dim = dimension;
        _squares = new BoggleSquare[_dim][_dim];
        _word = new StringBuilder();

        _letterlock = false;
        _selected = new Stack<>();

        this.createGrid();
    }

     /*
    *       The displayLetters() method creates the BoggleSquares based on the Vertices that were created by the
    *   Gameboard, then adds them to the grid pane.
    *
    *   Input: vertices - a 2D Vertex array containing the vertices created by the GameBoard.
    *   Output:  nothing.
    */

    void displayLetters(Vertex[][] vertices) {
        for (int row = 0; row < _dim; row++) {
            for (int col = 0; col < _dim; col++) {
                _squares[row][col] = new BoggleSquare(vertices[row][col]);
                _pane.add(_squares[row][col].getPane(), col, row);
            }
        }

        this.setNeighbors();
    }

    /*
    *       The clearAllLetters() method deselects all currently selected setters by un-highlighting, resetting
    *  the _word, and _selected variables, and updating the word label.
    *
    *   Input: nothing.
    *   Output:  nothing.
    */

    void clearAllSelectedLetters() {

        while (_selected.size() > 0){
            _selected.pop().unhighlight();
        }
        assert _selected.size() == 0;
        _word = new StringBuilder();
        _wordlistVis.updateWordLabel("");
    }

    /*
    *       The clearLastSelectedLetter() method deselects the most recently selected BoggleSquare by un-highlighting
    *   it, resetting the _word, and _selected variables, and updating the word label. Since the
    *   BoggleSquares are added to the _selected Stack upon being added, the user can continuously clear the last
    *   select BoggleSquare by popping from the Stack until there are no squares selected.
    *
    *   Input: nothing.
    *   Output:  nothing.
    */

    void clearLastSelectedLetter() {
        if (!_selected.empty() && _word.length() > 0) {
            BoggleSquare last = _selected.pop();
            last.unhighlight();
            _word.deleteCharAt(_word.length() - 1);
            _word.trimToSize();
            _wordlistVis.updateWordLabel(_word.toString());
        }
    }

    /*
    *       The addCharToCurWord() method uses a private helper function to update the state of the GameBoardVis when
    *   a BoggleSquare is selected.
    *
    *   Input: square - BoggleSquare that was selected.
    *   Output:  nothing.
    */

    private void addCharToCurWord(BoggleSquare square) {
        if (_selected.empty()) {
            this.addCharHelper(square);
        } else if (_selected.search(square) == -1) {
            if (square.getNeighbors().contains(_selected.peek())) {
                this.addCharHelper(square);
            }
        }

    }

    /*
    *       The gameOver() method deselects all squares on the GameBoard and prevents the GameBoard from receiving
    *   further input with the _letterlock Boolean.
    *
    *   Input: nothing.
    *   Output:  nothing.
    */

    void gameOver() {
        this.clearAllSelectedLetters();
        _letterlock = true;
    }

     /*
    *                                                Setters and Getters
    *
    */

    void setWordListVis(WordListVis vis) {
        _wordlistVis = vis;
    }

    String getSelectedWord() {
        if (_word.length() > 0) {
            return _word.toString();
        }
        return null;
    }

    void setLetterLock(Boolean bool) {
        _letterlock = bool;
    }

    private Boolean getLetterLock() {
        return _letterlock;
    }

    /*
    *       The addCharHelper() updates the _word and _selected variables to keep track of when a
    *   BoggleSquare has been selected. It also highlights the inputted square and updates the word label display
    *   the word currently selected on the GameBoard.
    *
    *   Input:   square - the BoggleSquare that was selected.
    *   Output:  nothing.
    */

    private void addCharHelper(BoggleSquare square) {
        _word.append(square.getChar());
        _selected.push(square);
        square.highlight();
        _wordlistVis.updateWordLabel(_word.toString());
    }

    /*
    *       The createGrid() adds the row and column constraints to the GridPane stored in the _pane variable.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    */

    private void createGrid() {
        for (int i = 0; i < _dim; i++) {
            _pane.getRowConstraints().add(new RowConstraints(80));
        }

        for (int i = 0; i < _dim; i++) {
            _pane.getColumnConstraints().add(new ColumnConstraints(80));
        }
    }

    /*
    *       The setNeighbors() method sets each BoggleSquare's reference to its neighbors based on the neighbors stored
    *   in the Vertex that was used to create the BoggleSquare.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    */

    private void setNeighbors() {
        for (int row = 0; row < _dim; row++) {
            for (int col = 0; col < _dim; col++) {
                Iterator<Vertex> iterator = _squares[row][col].getVertNeighbors();

                while (iterator.hasNext()) {
                    Vertex cur = iterator.next();
                    _squares[row][col].setNeighbor(_squares[cur.getRow()][cur.getCol()]);
                }
            }
        }
    }

    /**
     * BoggleSquare Class
     * <p>
     * The BoggleSquare is a smart object that knows the letter it represents, it's neighbors, and if it was
     * selected. If it was selected, a BoggleSquare will call the GameBoardVis' addCharToCurWord() method and pass
     * in itself. If a mouse is dragged across a BoggleSquare, the square will be selected and added to the current
     * word being displayed by the word label. The GameBoardVis controls this feature by only allowing a square to
     * be selected if it neighbors the last selected square.
     **/

    private class BoggleSquare {
        private final char _c;
        private final StackPane _pane;
        private final Rectangle _background;
        private final Label _label;
        private final Vertex _vert;
        private final ArrayList<BoggleSquare> _neighbors;

        BoggleSquare(Vertex vert) {

            _pane = new StackPane();
            _pane.getStyleClass().add("cell");
            _background = new Rectangle(70, 70, Color.WHITE);
            _background.setStroke(Color.WHITE);
            _background.setStrokeWidth(2.0);
            _background.setStrokeLineJoin(StrokeLineJoin.ROUND);
            _label = new Label();

            _vert = vert;
            _neighbors = new ArrayList<>();

            _c = _vert.getChar();
            _label.setText(Character.toString(_c));

            this.setupEventHandler();
            _pane.getChildren().addAll(_background, _label);
        }


        void setNeighbor(BoggleSquare square) {
            _neighbors.add(square);
        }

        Iterator<Vertex> getVertNeighbors() {
            return _vert.getNeighbors().iterator();
        }

        ArrayList<BoggleSquare> getNeighbors() {
            return _neighbors;
        }

        char getChar() {
            return _c;
        }

        StackPane getPane() {
            return _pane;
        }

        void highlight() {
            _background.setFill(Color.LIGHTBLUE);
        }

        void unhighlight() {
            _background.setFill(Color.WHITE);
        }

        private void setupEventHandler() { _background.setOnMouseDragEntered(new OnDrag());}

        private class OnDrag implements EventHandler<MouseEvent> {
            public void handle(MouseEvent event) {
                if (!GameBoardVis.this.getLetterLock()) {
                    GameBoardVis.this.addCharToCurWord(BoggleSquare.this);
                }
            }
        }

    }
}
