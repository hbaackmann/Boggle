package boggle;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

  /**                                                 Game Class
   *
   * The Game class instantiates an instance of the Pane Organizer, DictTrie, LineReader, Wordlist, and GameBoard classes
   * and stores each instance in a private variable. This allows the game to pass the required references to each class,
   * keep track of the current score, and ensure all components of the game freeze when the game is over. Additionally,
   * the Game class has a Timer private inner class, to keep track of the time elapsed since instantiation. To display
   * the score and time elapsed, the Game has its own Visualizer.
   **/

public class Game {

    private static App _app;
    private static int _dim;
    private static PaneOrganizer _pane;
    private static DictTrie _trie;
    private static GameBoard _gameBoard;
    private static  WordList _wordlist;
    private static GameInfoVis _vis;
    private static Timer _timer;
    private int _score;

     /*
    *       The Game() constructor initializes all of its private variables and starts the Game.
    *
    *   Input:   app -> the instance of App running the current game.
    *            dimension -> the dimension of the current game.
    *   Output:  nothing.
    */

    public Game (App app, int dimension)
    {
        _app = app;
        _dim = dimension;
        _score = 0;

        //Create PaneOrganizer
        _pane = new PaneOrganizer(this, _dim);

        //Create GameBoard
        _gameBoard = new GameBoard(_dim, _pane.getGamePane());

        //Create and fill Dictionary
        _trie = new DictTrie();
        this.fillDict();

        //Create Wordlist
        _wordlist = new WordList(_trie, _gameBoard, _dim, _pane.getWordPane(), this);

        //Create GameInfoVis and set welcome instructions.
        _vis = new GameInfoVis(_pane.getBottomPane());
        _vis.displayInstructions();

        //Create a Timer and start it
         _timer = new Timer(_vis);
    }

    /*
    *       An instance of the Game class calls the createNewGame() to call the App's createGame() to replace
    *   itself with a new instance of the Game class with the inputted dimensions.
    *
    *   Input: dim - the dimension of the game to be created.
    *   Output: nothing.
    */

    public void createNewGame(int dim)
    {
        this.newGame();
        _app.createGame(dim);
    }

    /*
   *        The awardPoints() method takes in a string and awards points based on the length of the string.
   *   It then calculates the new score and calls the GameInfoVis to update the label displaying the score.
   *
   *   Input: word - a valid word being added to the word list (must be more three or more letters).
   *   Output: nothing.
   */
    public void awardPoints(String word)
    {
        int points = (word.length() - 2);
        _score += points;
        _vis.updateScoreLabel(_score);

    }

    /*
    *       The fillDict() method creates a new Line Reader and uses it to fill the dictionary with its contents.
    *
    *   Input: nothing.
    *   Output: nothing.
    */

    private void fillDict()
    {
        LineReader reader = new LineReader(_trie);
        try
        {
            reader.addToDict("/OpenEnglishWordList.txt");
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
      }

    /*
    *       The newGame() method clears deselects all the BoggleSquares on the GameBoard,
    *   clears the display showing the currently selected word, prevents the user from
    *   interacting with the displays, and stops the timeline.
    *
    *   Input: nothing.
    *   Output: nothing.
    * */

    private void newGame()
    {
        _timer.stopTimeline();
        _gameBoard.gameOver();
        _wordlist.gameOver();

    }

        /*
    *       The gameOver() method clears deselects all the BoggleSquares on the GameBoard,
    *   clears the display showing the currently selected word, prevents the user from
    *   interacting with the displays, and displays the game over message.
    *
    *   Input: nothing.
    *   Output: nothing.
    * */

      public void gameOver()
      {
          _gameBoard.gameOver();
          _wordlist.gameOver();
          _vis.gameOver();
      }

     /*
    *       Getter method for the root of the scene.
    *
    *   Input: nothing.
    *   Output: BorderPane that is the root of the scene throughout the duration of the game.
    * */

    public Pane getRoot()
    {
        return _pane.getRoot();
    }

     /**                                                     Timer Class
      *
      *  The Timer class creates a TimeLine playing a keyframe of one second that uses the private inner class
      *  TimerUpdater that runs for three minutes and displays the seconds remaining as well a warning messages when
      *  user has either two minutes or one minute left.
      **/
    private class Timer
    {
        private Timeline _timeline;
        private GameInfoVis _vis;

        private Timer(GameInfoVis vis)
        {
            _vis = vis;
            this.setUpTimeLine();
        }

        public void stopTimeline()
        {
            _timeline.stop();
        }

        /*
        *       The getVis() method returns the Game's GameInfoDisplay so that the TimerUpdater can update the Time
        *   Remaining label.
        *
        *   Input: nothing.
        *   Output: _vis -> the GameInfoVis stored in the Game's _vis variable.
        * */

        public GameInfoVis getVis()
        {
            return _vis;
        }

        /*
        *       The setUpTimeLine() method creates a new KeyFrame with a TimerUpdater that updates the KeyFrame
        *   every second, then plays the timeline until the TimerUpdater stops it.
        *
        *   Input: nothing.
        *   Output: nothing.
        * */
        private void setUpTimeLine() {

            KeyFrame kf = new KeyFrame(Duration.seconds(1), new TimerUpdater());
            _timeline = new Timeline(kf);
            _timeline.setCycleCount(Timeline.INDEFINITE);
            _timeline.play();

        }

          /**                                            TimerUpdater Class
           *
           *  The TimerUpdater class ensures the TimeLine plays for three minutes, and calls the GameInfoVis to update
           *  the seconds remaining label as well a warning messages when user has either two minutes or one minute left.
           **/

        private class TimerUpdater implements EventHandler<ActionEvent> {

            private int _secondsRemaining = 180;

            public void handle(ActionEvent event)
            {
                if(_secondsRemaining > 0)
                {
                    _secondsRemaining--;

                    int min = (_secondsRemaining / 60);
                    int sec = (_secondsRemaining % 60);

                    //Display warning message telling the user when they have either two minutes or one minute left.
                    if(sec == 0)
                    {
                        if(min == 2)
                        {
                            _vis.setInfoText("Two minutes left!", "-fx-font: 20px Helvetica;\n");
                        }
                        else if (min == 1)
                        {
                            _vis.setInfoText("One minute left!", "-fx-font: 20px Helvetica;\n");
                        }
                    }

                    if ((sec == 50) && (min <= 1))
                    {
                        _vis.setInfoText(null, null);
                    }

                    //Display seconds remaining.
                    StringBuilder str = new StringBuilder();
                    str.append("Time Remaining:  ");
                    str.append(min);
                    str.append(":");
                    if(sec < 10)
                    {
                        str.append(0);
                    }
                    str.append(sec);
                    Timer.this.getVis().updateTimerLabel(str.toString());
                }
                else
                {
                    //Time's up!
                    _timeline.stop();
                    Timer.this.getVis().updateTimerLabel("Time's up!");
                    Game.this.gameOver();
                }
            }
        }
    }

}
