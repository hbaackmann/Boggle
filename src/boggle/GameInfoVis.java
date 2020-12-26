package boggle;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;


/**
 * The GameInfoVis Class
 * <p>
 * The GameInfoVis class displays the current score and the exact time remaining. It also contains a text box that
 * initially displays instructions, then displays warning messages at two minutes and one minute left.
 **/

class GameInfoVis {
    private static HBox _pane;
    private static Label _timer;
    private static Label _score;
    private static Label _info;

    /*
    *       The constructor for the GameInfoVis sets up the bottom pane of the window, where the score and time remaining
    *   labels are displayed.
    *
    * Input: pane - the HBox that was instantiated by the PaneOrganizer and added to it's BorderPanes's bottom display.
    * Output: nothing.
    **/

    GameInfoVis(HBox pane) {
        _pane = pane;
        _pane.setAlignment(Pos.CENTER);
        _pane.setSpacing(35);
        this.createLabels();
    }


    /*
    *       The createLabels() method instantiates the timer, score, and info labels and adds them to the pane.
    *
    * Input: nothing.
    * Output: nothing.
    **/

    private void createLabels() {
        _timer = new Label("Time Remaining: 3:00");
        _timer.setMinSize(160, 40);
        _timer.setStyle(Constants.BUTTONS_CSS);
        _timer.setAlignment(Pos.CENTER);

        _score = new Label("Score:    ");
        _score.setMinSize(80, 40);
        _score.setStyle(Constants.BUTTONS_CSS);
        _score.setAlignment(Pos.CENTER);

        _info = new Label();
        _info.setMaxSize(400, 80);
        _info.setMinSize(400, 80);
        _info.setAlignment(Pos.CENTER);
        _info.setWrapText(true);
        _info.setTextAlignment(TextAlignment.CENTER);

        _pane.getChildren().addAll(_timer, _info, _score);
    }

    /*
    *       The setInfoText() method updates the text displayed by the info label.
    *
    * Input: str - the message to be displayed by the info label.
    *        size - a CSS string representing the size of the of the font.
    * Output: nothing.
    **/

    void setInfoText(String str, String size) {
        if ((str != null) && (size != null)) {
            String css = Constants.GAME_INFO_CSS + size;

            _info.setStyle(css);
            _info.setText(str);

        } else {
            _info.setStyle("");
            _info.setText("");
        }
    }

    /*
    *        The displayInstructions() method updates the info label to display instructions on how to use the program
    *   for the first minute of the game. It is called by the Game class upon setting up the Game.
    *
    * Input: nothing.
    * Output: nothing.
    **/

    void displayInstructions() {
        String instructions = "Welcome to Boggle!\n"
                + "Begin play by dragging a mouse click over adjacent letters to form words, then press enter. "
                + "To delete the last added letter, press delete."
                + "To deselect all letters on the board, press clear.";

        this.setInfoText(instructions, "-fx-font: 13px Helvetica;\n");
    }

    /*
    *       The updateTimerLabel() method updates the label (_timer) displaying the time remaining.
    *
    * Input: str - the message to be displayed by the timer label.
    * Output: nothing.
    **/

    void updateTimerLabel(String str) {
        _timer.setText(str);
    }

    /*
    *       The updateScoreLabel() method updates the label (_score) displaying the cumulative score.
    *
    * Input: score - the new cumulative score.
    * Output: nothing.
    **/

    void updateScoreLabel(int score) {
        _score.setText("Score:  " + score);

    }

     /*
    *       The gameOver() method sets the _info label to display a "Game Over!" message.
    *
    * Input: nothing.
    * Output: nothing.
    **/

    void gameOver() {
        this.setInfoText("    Game Over!    ", "-fx-font: 40px Helvetica;\n");
    }
}
