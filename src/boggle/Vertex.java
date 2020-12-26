package boggle;

import java.util.ArrayList;
import java.util.Iterator;

public class Vertex
{
    private ArrayList<Vertex> _neighbors;
    private int _row;
    private int _col;
    private char _c;
    private Boolean _isVisted;


    public Vertex(int row, int col){
        _row = row;
        _col = col;
        _neighbors = new ArrayList<Vertex>();
        _isVisted = false;
    }

    /*                           Getter and Setter Methods for _isVisited variable
    *
    * */

    public void setChar(char c)
    {
        _c = c;
    }

    public char getChar()
    {
        return _c;
    }

    /*                        Setter Method for Neighbors
    *
    * Input: Reference to a neighboring Vertex.
    * Output: None.
    * */

    public void setNeighbor(Vertex vertex)
    {
        _neighbors.add(vertex);
    }


    /*                         Getter Methods for Neighboring Vertices
    *
    * Input: None.
    * Output: ArrayList<Vertex> of the Vertex's neighbors.
    * */


    public ArrayList<Vertex> getNeighbors()
    {
        ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
        Iterator<Vertex> iterator = _neighbors.iterator();

        while (iterator.hasNext())
        {
            neighbors.add(iterator.next());
        }

       return neighbors;
    }


    /*                           Getter Methods for Coordinates of Vertex
    *
    * Input: None.
    * Output: An int representing the respective coordinate.
    * */

    public int getRow()
    { return _row;
    }

    public int getCol()
    { return _col;
    }

     /*                           Getter and Setter Methods for _isVisited variable
    *
    * */

    public void setVisited(Boolean bool)
    {  _isVisted = bool;
    }

    public Boolean getVisited()
    { return _isVisted;
    }

}