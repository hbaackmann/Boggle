package boggle;

/**
 * Constants Class
 * <p>
 * The Constants class contains the constant values used throughout the duration of the Program (including CSS
 * styling Strings).
 **/

class Constants {

    static final String LC_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    static final int APP_WIDTH = 750;
    static final int APP_HEIGHT = 650;

    static final int TOP_PANE_WIDTH = 700;
    static final int TOP_PANE_HEIGHT = 60;

    static final int BOOGLE_BOARD_SIZE = 400;

    static final int WORD_LIST_WIDTH = 300;
    static final int WORD_LIST_HEIGHT = 400;
    static final int NUM_WORD_LIST_COLS = 3;
    static final int NUM_WORD_LIST_ROWS = 15;

    static final int BOTTOM_PANE_WIDTH = 750;
    static final int BOTTOM_PANE_HEIGHT = 120;

    /* CSS strings for styling */

    static final String BUTTONS_CSS = "-fx-background-color: darkorange;\n"
            + "-fx-background-insets: 2;\n"
            + "-fx-border-radius: 3;\n"
            + "-fx-border-color: white;\n"
            + "-fx-border-width: 2;\n"
            + "-fx-text-fill: white;\n"
            + "-fx-font-weight: black;\n"
            + "-fx-font-family: Century Gothic;\n"
            + "-fx-font-size: 12px;\n"
            + "-fx-background-radius: 3;\n";

    static final String GAME_BUTTONS_CSS = "-fx-background-color: grey;\n"
            + "-fx-background-insets: 2;\n"
            + "-fx-background-radius: 4;\n"
            + "-fx-border-color: white;\n"
            + "-fx-border-width: 2;\n"
            + "-fx-text-fill: white;\n"
            + "-fx-font-weight: black;\n"
            + "-fx-font-family: Century Gothic;\n"
            + "-fx-font-size: 12px;\n"
            + "-fx-border-radius: 3;\n";

    static final String GAME_INFO_CSS = "-fx-background-color: white;\n"
            + "-fx-border-color: white;\n"
            + "-fx-border-width: 3;\n"
            + "-fx-text-fill: dimgrey;\n"
            + "-fx-font: 16px Century Gothic;\n"
            + "-fx-font-weight: 900;\n"
            + "-fx-background-radius: 3;\n"
            + "-fx-border-radius: 3;\n";

    static final String SELECTED_WORD_CSS = "-fx-text-fill: darkorange;\n"
            + "-fx-font-weight: bolder;\n"
            + "-fx-font: 20px Helvetica;\n"
            + "-fx-stroke: white;\n"
            + "-fx-stroke-width: 1;\n";

    static final String VALID_WORD_CSS = "-fx-background-color: lightblue;\n"
            + "-fx-text-fill: white;\n"
            + "-fx-font-weight: 900;\n"
            + "-fx-font: 16px Helvetica;\n";

    static final String INVALID_WORD_CSS = "-fx-background-color: lightblue;\n"
            + "-fx-text-fill: red;\n"
            + "-fx-font-weight: bold;\n"
            + "-fx-font: 16px Helvetica;\n";


}
