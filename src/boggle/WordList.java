package boggle;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * WordList Class
 * <p>
 * The WordList class keeps track of the words that have been submitted then adds them to the WordListVis so the
 * user can see what they have already guessed as well as whether or not it the word was valid (with valid words
 * rendered in white and invalid rendered in red. The WordList refers to the trie to check if word are valid or
 * not. The WordList also passes a reference to the GameBoardVis to the WordListVis upon instantiation in order
 * to check if a word that was selected via a mouse drag click across the GameBoard is valid.
 **/

class WordList {

    private final DictTrie _trie;
    private final Vertex[][] _vertices;
    private final int _dim;
    private final ArrayList<String> _wordlist;
    private final WordListVis _visualizer;
    private final Game _game;

    /*
    *       The WordList() constructor initializes all of its private variables.
    *
    *   Input:   trie -> the DictTrie used to check if words are valid.
    *            gameBoard -> a reference to the GameBoard
    *            dim -> dimension of the GameBoard
    *            pane -> the GridPane organizing the WordListVis on the right of the BorderPane
    *            game -> reference to the instance of Game controlling the program
    *   Output:  nothing.
    */

    WordList(DictTrie trie, GameBoard gameBoard, int dim, GridPane pane, Game game) {
        /* Initialize private variables. */
        _trie = trie;
        _vertices = gameBoard.getVertices();
        _dim = dim;
        _wordlist = new ArrayList<>();
        _visualizer = new WordListVis(pane, this, gameBoard.getGBVisualizer());
        _game = game;

    }

    /*
    *       The searchBoard() method is used when a word is entered into the TextField of the WordListVis. This method
    *  takes in the word that was inputted into the TextField, then it finds letters on the board that match the first
    *  letter of the inputted word and calls the DictTrie's checkValidWorldTyped() method to dynamically search the
    *  board and the dictionary at the same time from there until the word is found.
    *
    *   Input:   str -> the string inputted by the user into the TextField
    *   Output:  Nothing
    */

    void searchBoard(String str) {
        char[] word = str.toCharArray();

        for (int row = 0; row < _dim; row++) {
            for (int col = 0; col < _dim; col++) {
                if (_vertices[row][col].getChar() == word[0]) {
                    if (_trie.checkValidWordTyped(word, _vertices[row][col])) {
                        this.wordFound(str, true);
                        return;
                    }
                }
            }
        }
        this.wordFound(str, false);
    }

    /*
    *       The checkSelectedLetters() method is used when the user hits submit after selecting letters on the GameBoard.
    *   Because the user can only select BoggleSquares that neighbor the last selected BoggleSquare, the only thing to
    *   be checked is if the word is contained in the DictTrie.
    *
    *   Input:   str -> the string inputted by the user via the GameBoard
    *   Output:  nothing.
    */

    void checkSelectedLetters(String str) {
        //Only neighboring cells can be added to current selected word on the GameBoard.
        if (_trie.containsWord(str)) {
            //If selected word is a word, it is automatically valid.
            this.wordFound(str, true);
        } else {
            //If selected word is not a word, it is invalid.
            this.wordFound(str, false);
        }

    }

    /*
    *       The gameOver() method disables the TextField and Buttons in the WordListVis.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    */

    void gameOver() {
        _visualizer.gameOver();
    }

    /*
    *       The wordFound() method is called by the WordList when a word is found to add it to the _wordlist variable
    *   (to ensure that guessed words are only displayed once) and update the visualizer.
    *
    *   Input:   str -> the word that was validated or invalidated.
    *            bool -> true if word is valid, false if word is invalid.
    *   Output:  nothing.
    */

    private void wordFound(String word, Boolean bool) {
        if (!_wordlist.contains(word)) {
            _wordlist.add(word);

            if (!bool) {
                _visualizer.addWord(word, false);
            } else {
                if (word.length() > 2) {
                    _visualizer.addWord(word, true);
                    _game.awardPoints(word);
                }
            }
        }
    }
}
