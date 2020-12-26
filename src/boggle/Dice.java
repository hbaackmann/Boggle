package boggle;

import java.util.ArrayList;

/**
 * Dice Class
 * <p>
 * The Dice class is used to generate a simulation of the degree of randomness used in the actual boggle game.
 * The randomness of the actual boggle game is constrained by either 16 or 25 dice falling in random locations
 * on the GameBoard, plus randomly landing on one of their six sides. However, the creators of Boggle carefully
 * chose the letter distribution of all the letters on the dice of the boggle board so I used that letter
 * distribution (see Resources) to increase the likelihood of words being formed and simulate the actual game.
 **/

class Dice {

    private final ArrayList<Die> _dice;
    private Die _curDie;

    /*
    *       The Dict() constructor initializes private variables.
    *
    *   Input:   dimension - dimension of current game.
    *   Output:  nothing.
    **/

    Dice(int dimension) {
        // Generate correct number of dice based on the size of the board
        if (dimension == 4) {
            _dice = this.createBogDice();
        } else {
            _dice = this.createBigBogDice();
        }
    }

    /*
    *       The hasNextDie() method returns true if there are any dice left to roll, otherwise it returns false.
    *
    *   Input:   nothing.
    *   Output:  boolean determining indicating whether or not there are any dice left.
    **/

    Boolean hasNextDie() {
        return _dice.size() >= 1;
    }

    /*
    *       The setNextDie() method randomly draws a die (to randomize the placement of each die on the GameBoard)
    *   and sets it as the current die.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    **/

    void setNextDie() {
        int index = (int) (Math.random() * _dice.size());
        _curDie = _dice.remove(index);
    }

    /*
    *       The rollDie() rolls the current Die, allowing the GameBoard to re-roll the die if the letter appears more than
    *   4 times (although this disrupts randomness, it was the simplest way to fit the spec).
    *
    *   Input:   nothing.
    *   Output:  a character representing the letter facing up after the die was rolled.
    **/

    char rollCurDie() {
        return _curDie.roll();
    }

    /*  Boggle Board Letter Distribution:
    *
    *  Each group of six letters represents the letters that will appear on a single die.
    * */
    private ArrayList<Die> createBogDice() {
        ArrayList<Die> dice = new ArrayList<>();
        dice.add(new Die("aaeegn"));
        dice.add(new Die("abbjoo"));
        dice.add(new Die("achops"));
        dice.add(new Die("affkps"));
        dice.add(new Die("aoqttw"));
        dice.add(new Die("cimotu"));
        dice.add(new Die("deilrx"));
        dice.add(new Die("delrvy"));
        dice.add(new Die("distty"));
        dice.add(new Die("eeghnw"));
        dice.add(new Die("eeinsu"));
        dice.add(new Die("ehrtvw"));
        dice.add(new Die("eiosst"));
        dice.add(new Die("elrtty"));
        dice.add(new Die("himnuq"));
        dice.add(new Die("hlnnrz"));
        return dice;
    }

    private ArrayList<Die> createBigBogDice() {
        ArrayList<Die> dice = new ArrayList<>();
        dice.add(new Die("aaafrs"));
        dice.add(new Die("aaeeee"));
        dice.add(new Die("aafirs"));
        dice.add(new Die("adennn"));
        dice.add(new Die("aeeeem"));
        dice.add(new Die("aeegmu"));
        dice.add(new Die("aegmnn"));
        dice.add(new Die("afirsy"));
        dice.add(new Die("bbjkxz"));
        dice.add(new Die("ccenst"));
        dice.add(new Die("eiilst"));
        dice.add(new Die("ceiqst"));
        dice.add(new Die("ddhnot"));
        dice.add(new Die("dhhlor"));
        dice.add(new Die("dhhnow"));
        dice.add(new Die("dhlrnor"));
        dice.add(new Die("eiiitt"));
        dice.add(new Die("eilpst"));
        dice.add(new Die("emotqt"));
        dice.add(new Die("ensssu"));
        dice.add(new Die("fiprsy"));
        dice.add(new Die("gorrvw"));
        dice.add(new Die("iprsyy"));
        dice.add(new Die("nootuw"));
        dice.add(new Die("ooottu"));
        return dice;
    }

    /**
     * Die Class
     * <p>
     * The Die class is a private inner class used to logically represent a single die. Each die receives a string
     * upon instantiation representing the letters on its six sides which it saves in its _letters variable. When
     * the die is rolled, it randomly indexes into _letters and returns a char.
     **/

    private class Die {
        private final char[] _letters;

        Die(String letters) {
            _letters = letters.toCharArray();
        }

        char roll() {
            int index = (int) (Math.random() * 6);
            return _letters[index];
        }
    }

}
