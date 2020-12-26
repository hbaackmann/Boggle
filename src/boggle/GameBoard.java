package boggle;

import javafx.scene.layout.GridPane;

/**                                                 GameBoard Class
 *
 *  The GameBoard class represents the logical abstraction of the the GameBoard. It is responsible for rolling the 'die'--
 *  which are represented by the class Dice. After a die is rolled, if it lands on a letter that has appeared more than 4 times
 *  on the board, it will be rolled until it lands on a valid letter. The GameBoard class also sets each vertex's
 *  references to it's neighbors. Once the GameBoard has created all the vertices and set their neighbors, it creates
 *  the GameBoard visualizer which maintains the same letters for the duration of the game.
 **/

public class GameBoard
{
    private Vertex[][] _vertices;
    private int _dim;
    private GameBoardVis _vis;
    private static Dice _dice;

    /*
    *       The constructor for the GameBoard initializes its private variables, creates the vertices representing each
    *  letter on the board, and displays the vertices by calling the visualizer's displayLetters() method.
    *
    *  Input: dimension - the dimension of the GameBoard for (immutable)
    *         pane - the GridPane used to hold the visual representation of the GameBoard
    *  Output: nothing.
    **/

    public GameBoard(int dimention, GridPane pane)
    {
        _dim = dimention;
        _vertices = new Vertex[_dim][_dim];
        _dice = new Dice(dimention);

        //Create Data
        this.createVertices();

        //Graphics, pass pane to visualizer.
        _vis = new GameBoardVis(pane, _dim);
        _vis.displayLetters(_vertices);
    }

    /*
    *       The gameOver() method disables the GameBoard's visualizer so that the user can no longer select
    *  words on a mouse drag.
    *
    *  Input: nothing.
    *  Output: nothing.
    **/

    public void gameOver()
    {
        _vis.gameOver();
    }

    /*
    *       The getVertices() method returns a Vertex[][] representing the GameBoard.
    *
    *  Input: nothing.
    *  Output: a 2D array of vertices representing the GameBoard.
    **/

    public Vertex[][] getVertices()
    {
        return _vertices;
    }

    /*
    *       The getGBVisualizer() method is a getter method for the visualizer of the GameBoard.
    *
    *  Input: nothing.
    *  Output: the visualizer for the GameBoard
    **/

    public GameBoardVis getGBVisualizer()
    {
        return _vis;
    }

    /*
    *       The createFrequencyArray() method is called by the GameBoard before creating the vertices to create an
    *  array that holds the frequency that each letter appears on the GameBoard. This is later used to ensure no
    *  letter appears more than four times.
    *
    *  Input:  nothing.
    *  Output: letterFrequencies - the an array, with each index corresponding to a lowercase letter's position
    *          in the alphabet. When a letter is used, the int value held at the letter's position in the array will be
    *          increased by one.
    **/

    private int[] createFrequencyArray()
    {
        int[] letterFrequencies = new int[26];
        for (int i = 0; i < 26; i++)
        {
            letterFrequencies[i] = 0;
        }
        return letterFrequencies;
    }

    /*
    *       The createFrequencyArray() method is called by the GameBoard before creating the vertices to create an
    *  array that holds the frequency that each letter appears on the GameBoard. This is later used to ensure no
    *  letter appears more than four times.
    *
    *  Input:  nothing.
    *  Output: letterFrequencies - the an array, with each index corresponding to a lowercase letter's position
    *          in the alphabet. When a letter is used, the int value held at the letter's position in the array will be
    *          increased by one.
    **/

    private void checkForQ(Vertex v)
    {
        if (v.getChar() == 'q')
        {
            Vertex neighbor = v.getNeighbors().iterator().next();
            neighbor.setChar('u');
        }
    }

     /*
    *       The addLetter() method uses the letterFrequencies array and the Dice class to re-roll a die if it has
    *  already appeared 4 times. It randomly draws a die from the dice upon called setNextDie() and rolls it, then
    *  sets vertex's char variable to whatever was rolled.
    *
    *  Input:  letterFrequencies - the an array, with each index corresponding to a lowercase letter's position
    *          in the alphabet.
    *          vertex - the vertex to be assigned a letter.
    *  Output: nothing.
    **/

    private void addLetter(Vertex vertex, int[] letterFrequencies)
    {
        char c;
        if (_dice.hasNextDie() == true)
        {
            _dice.setNextDie();
            c = _dice.rollCurDie();
            while (letterFrequencies[Constants.LC_ALPHABET.indexOf(c)] == 4)
            {
                c = _dice.rollCurDie();
            }
            letterFrequencies[Constants.LC_ALPHABET.indexOf(c)]++;
            vertex.setChar(c);
        }
    }

    /*
    *       The createVertices() method first creates the frequency array then instantiates all of the vertices
    *  on the gameBoard, storing them in the _vertices variable. It then adds letters, sets each vertex's neighbor,
    *  and checks for q to add an instance of u as its neighbor.
    *
    *  Input: nothing.
    *  Output: nothing.
    **/
    private void createVertices()
    {
        int[] freq = this.createFrequencyArray();

        for (int col = 0; col < _dim; col++)
        {
            for (int row = 0; row < _dim; row++)
            {
                _vertices[row][col] = new Vertex(row, col);
                this.addLetter(_vertices[row][col], freq);
            }
        }

        for (int col = 0; col < _dim; col++)
        {
            for (int row = 0; row < _dim; row++)
            {
                this.setNeighbors(_vertices[row][col], row, col);
                this.checkForQ(_vertices[row][col]);
            }
        }
    }

     /*
    *      The setNeighbors() method sets up to 8 of the possible neighbors for each Vertex in _vertices.
    *
    *  Input: v - the vertex whose neighbors will be set by this method.
    *         row - the row index of the Vertex in _vertices.
    *         col - the column index of the Vertex in _vertices.
    *  Output: nothing.
    **/

    private void setNeighbors(Vertex v, int row, int col)
    {
        int up = (row - 1);
        int down = (row + 1);
        int left = (col - 1);
        int right = (col + 1);

        if (up >= 0)
        {
            v.setNeighbor(_vertices[up][col]);
        }
        if (down < _dim)
        {
            v.setNeighbor(_vertices[down][col]);
        }
        if (left >= 0)
        {
            v.setNeighbor(_vertices[row][left]);

            if (down < _dim)
            {
                v.setNeighbor(_vertices[down][left]);
            }
            if (up >= 0)
            {
                v.setNeighbor(_vertices[up][left]);
            }
        }
        if (right < _dim)
        {
            v.setNeighbor(_vertices[row][right]);
            if (down < _dim)
            {
                v.setNeighbor(_vertices[down][right]);
            }
            if (up >= 0)
            {
                v.setNeighbor(_vertices[up][right]);
            }
        }
    }
}
