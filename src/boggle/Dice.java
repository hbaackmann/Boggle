package boggle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**                                                 Dice Class
 *
 *   The Dice class is used to generate a simulation of the degree of randomness used in the actual boggle game.
 *   The randomness of the actual boggle game is constrained by either 16 or 25 dice falling in random locations
 *   on the GameBoard, plus randomly landing on one of their six sides. However, the creators of Boggle carefully
 *   chose the letter distribution of all the letters on the dice of the boggle board so I used that letter
 *   distribution (see Resources) to increase the likelihood of words being formed and simulate the actual game.
 **/

public class Dice {
    
    private DiceCreator _diceCreator;
    private ArrayList<String> _letters;
    private Die _curDie;

    /*
    *       The Dict() constructor initializes private variables.
    *
    *   Input:   dimension - dimension of current game.
    *   Output:  nothing.
    **/

    public Dice(int dimension)
    {
        _diceCreator = new DiceCreator(dimension);
        _letters = _diceCreator.getLetters();
    }

    /*
    *       The hasNextDie() method returns true if there are any dice left to roll, otherwise it returns false.
    *
    *   Input:   nothing.
    *   Output:  boolean determining indicating whether or not there are any dice left.
    **/

    public Boolean hasNextDie()
    {
        return _letters.size() >= 1;
    }

    /*
    *       The setNextDie() method randomly draws a die (to randomize the placement of each die on the GameBoard)
    *   and sets it as the current die.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    **/

    public void setNextDie()
    {
        int index = (int) (Math.random() * _letters.size());
        Die die = new Die(_letters.remove(index));
        _curDie = die;
    }

    /*
    *       The rollDie() rolls the current Die, allowing the GameBoard to re-roll the die if the letter appears more than
    *   4 times (although this disrupts randomness, it was the simplest way to fit the spec).
    *
    *   Input:   nothing.
    *   Output:  a character representing the letter facing up after the die was rolled.
    **/

    public char rollCurDie()
    {
        return _curDie.roll();
    }

    /**                                                 Die Class
     *
     *   The Die class is a private inner class used to logically represent a single die. Each die receives a string
     *   upon instantiation representing the letters on its six sides which it saves in its _letters variable. When
     *   the die is rolled, it randomly indexes into _letters and returns a char.
     **/

    private class Die
    {
        private char[] _letters;

        public Die(String letters)
        {
            _letters = letters.toCharArray();
        }

        public char roll()
        {
            int index = (int) (Math.random() * 6);
            char c = _letters[index];
            return c;
        }
    }

    /**                                                 DiceCreator Class
     *
     *   The DiceCreator class initializes and instantiates an immutable ArrayList<String> holding the letter distributions for
     *   dice. Upon request by the Dice class, it makes a mutable copy saved to the _copy variable and returns it.
     **/

    private class DiceCreator
    {
        private ArrayList<String> _letters;
        private ArrayList<String> _copy;
        
        public DiceCreator(int dimension)
        {
            //Initialize ArrayList holding the letters distributions for the letters boards (and a copy).
            _letters = new ArrayList<String>();
            _copy = new ArrayList<String>();

            //Add letters distributions.
            if(dimension == 4)
            {
                this.createBogLetters();
            }
            else
            {
                this.createBigBogLetters();
            }

            //Make OG letters immutable, while copy will be mutable.
            Collections.unmodifiableList(_letters);
        }

        public ArrayList<String> getLetters()
        {
            this.createCopy();
            return _copy;
        }

        private void createCopy()
        {
            Iterator<String> iterator = _letters.iterator();

            while(iterator.hasNext())
            {
                _copy.add(iterator.next());
            }
        }

        /* Boogle Board Letter Distribution */
        private void createBogLetters()
        {
            _letters.add("aaeegn");
            _letters.add("abbjoo");
            _letters.add("achops");
            _letters.add("affkps");
            _letters.add("aoqttw");
            _letters.add("cimotu");
            _letters.add("deilrx");
            _letters.add("delrvy");
            _letters.add("distty");
            _letters.add("eeghnw");
            _letters.add("eeinsu");
            _letters.add("ehrtvw");
            _letters.add("eiosst");
            _letters.add("elrtty");
            _letters.add("himnuq");
            _letters.add("hlnnrz");
        }

        private void createBigBogLetters() {
            _letters.add("aaafrs");
            _letters.add("aaeeee");
            _letters.add("aafirs");
            _letters.add("adennn");
            _letters.add("aeeeem");
            _letters.add("aeegmu");
            _letters.add("aegmnn");
            _letters.add("afirsy");
            _letters.add("bbjkxz");
            _letters.add("ccenst");
            _letters.add("eiilst");
            _letters.add("ceiqst");
            _letters.add("ddhnot");
            _letters.add("dhhlor");
            _letters.add("dhhnow");
            _letters.add("dhlrnor");
            _letters.add("eiiitt");
            _letters.add("eilpst");
            _letters.add("emotqt");
            _letters.add("ensssu");
            _letters.add("fiprsy");
            _letters.add("gorrvw");
            _letters.add("iprsyy");
            _letters.add("nootuw");
            _letters.add("ooottu");
        }
    }

}
