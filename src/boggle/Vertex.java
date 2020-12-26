package boggle;

import java.util.ArrayList;

class Vertex {
    private final ArrayList<Vertex> _neighbors;
    private final int _row;
    private final int _col;
    private char _c;
    private Boolean _isVisted;


    Vertex(int row, int col) {
        _row = row;
        _col = col;
        _neighbors = new ArrayList<>();
        _isVisted = false;
    }

    /*                           Getter and Setter Methods for _isVisited variable
    *
    * */

    void setChar(char c) {
        _c = c;
    }

    char getChar() {
        return _c;
    }

    /*                        Setter Method for Neighbors
    *
    * Input: Reference to a neighboring Vertex.
    * Output: None.
    * */

    void setNeighbor(Vertex vertex) {
        _neighbors.add(vertex);
    }


    /*                         Getter Methods for Neighboring Vertices
    *
    * Input: None.
    * Output: ArrayList<Vertex> of the Vertex's neighbors.
    * */


    ArrayList<Vertex> getNeighbors() {
        return _neighbors;
    }


    /*                           Getter Methods for Coordinates of Vertex
    *
    * Input: None.
    * Output: An int representing the respective coordinate.
    * */

    int getRow() {
        return _row;
    }

    int getCol() {
        return _col;
    }

     /*                           Getter and Setter Methods for _isVisited variable
    *
    * */

    void setVisited(Boolean bool) {
        _isVisted = bool;
    }

    Boolean getVisited() {
        return _isVisted;
    }

}