package boggle;

/**                                                 Constants Class
 *
 *      The Constants class contains the constant values used throughout the duration of the Program (including CSS
 *      styling Strings).
 **/

public class Constants {

    public static final String LC_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static final int APP_WIDTH = 750;
    public static final int APP_HEIGHT = 650;

    public static final int TOP_PANE_WIDTH = 700;
    public static final int TOP_PANE_HEIGHT = 60;

    public static final int BOOGLE_BOARD_SIZE = 400;

    public static final int WORD_LIST_WIDTH = 300;
    public static final int WORD_LIST_HEIGHT = 400;
    public static final int NUM_WORD_LIST_COLS = 3;
    public static final int NUM_WORD_LIST_ROWS = 15;

    public static final int BOTTOM_PANE_WIDTH = 750;
    public static final int BOTTOM_PANE_HEIGHT = 120;

    /* CSS strings for styling */

    public static final String BUTTONS_CSS = "-fx-background-color: grey;\n"
                                            + "-fx-border-color: white;\n"
                                            + "-fx-border-width: 2;\n"
                                            + "-fx-text-fill: white;\n"
                                            + "-fx-font-weight: bold;\n"
                                            + "-fx-font: 12px Helvetica;\n"
                                            + "-fx-border-radius: 3\n";

    public static final String GAME_INFO_CSS = "-fx-background-color: grey;\n"
                                            + "-fx-border-color: darkorange;\n"
                                            + "-fx-border-width: 3;\n"
                                            + "-fx-text-fill: darkorange;\n"
                                            + "-fx-font-weight: 900;\n"
                                            + "-fx-background-radius: 3;\n"
                                            + "-fx-border-radius: 3;\n";

    public static final String SELECTED_WORD_CSS = "-fx-text-fill: darkorange;\n"
                                                + "-fx-font-weight: bolder;\n"
                                                + "-fx-font: 20px Helvetica;\n"
                                                + "-fx-stroke: white;\n"
                                                + "-fx-stroke-width: 1;\n";

   public static final String VALID_WORD_CSS =  "-fx-background-color: lightblue;\n"
                                               + "-fx-text-fill: white;\n"
                                               + "-fx-font-weight: 900;\n"
                                               + "-fx-font: 16px Helvetica;\n";

    public static final String INVALID_WORD_CSS =  "-fx-background-color: lightblue;\n"
                                                + "-fx-text-fill: red;\n"
                                                + "-fx-font-weight: bold;\n"
                                                + "-fx-font: 16px Helvetica;\n";


}
