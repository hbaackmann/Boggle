package boggle;

import java.util.Iterator;

/**                                                 DictTrie Class
 *
 *      The DictTrie class is a Dictionary represented by a Trie, or a tree with each node having exactly 26 children
 *  corresponding to the 26 letters in the alphabet. Additionally, the DictTrie class contains two private inner classes:
 *  CheckValidWord and Node. Each instance of a node class represents a letter in a certain position within a word
 *  nodes are instantiated by the DictTrie as words are added to the tree. The CheckValidWord class contains the algorithm
 *  that checks if words are valid (if they are both on the board and in the dictionary). By using a Trie, I was able to use
 *  a dynamic programming solution for finding a valid word by searching through the trie and the letters on the board
 *  simultaneously.
 **/

public class DictTrie
{
    private Node _root;
    private CheckValidWord _wordChecker = new CheckValidWord();

     /*
    *       The DictTrie() constructor initializes the root node and sets its text value to a null string.
    *
    *   Input:   nothing.
    *   Output:  nothing.
    */

    public DictTrie()
    {
        _root = new Node("");
    }

     /*
    *       The getIndex() method returns the index of the inputted char in the lowercase alphabet (each char
    *   corresponds to a unique index). It is used to consistently index find the correct node when indexing into
    *   a node's children.
    *
    *   Input:   c - the char to find the index of.
    *   Output:  int - the index of the char in a String containing lowercase alphabet.
    *   Error:   print message to console.
    */

    public int getIndex(char c)
    {
        if (Constants.LC_ALPHABET.indexOf(c) == -1)
        {
            System.out.println("Error: " + c + " is not a lowercase letter.");
        }
        return Constants.LC_ALPHABET.indexOf(c);
    }

     /*
    *       The add() method adds a word to the Dictionary. If the node represents the last letter of the
    *   word, its _isWord private variable is set to true so that DictTrie can determine if a series of letters
    *   is a word or not. Additionally, it sets the text variable of the final node to the word that was added.
    *
    *   Input:   word - a String representing the word to be added.
    *   Output:  nothing.
    */

    public void add(String word)
    {
        char[] letters = word.toCharArray();
        Node cur = _root;
        Node next;

        for (int i = 0; i < word.length(); i++)
        {
            int index = this.getIndex(letters[i]);
            if (cur.getChildren()[index] == null )
            {
                next = new Node("");
                cur.getChildren()[index] = next;
            }
            else
            {
                next = cur.getChildren()[this.getIndex(letters[i])];
            }
            cur = next;
        }

        cur.set_word(true);
        cur.set_txt(word);
    }

    /*
   *       The search() method searches for a word in the trie. Starting with the root as the current node, this method
   *   iterates through all the letters in the word and finds the child of the current node corresponding to the
   *   current letter in the word. If the child is null, there exists no word in the trie matching the inputted string
   *   and the function returns null, otherwise it returns the node corresponding to the last letter in the word.
   *
   *   Input: str - the string to be searched for in the trie (only lowercase letters are stored in trie).
   *   Output: the node corresponding to the last letter in the word or null if string was not found.
   */

    public Node search(String str)
    {
        char[] letters = str.toCharArray();
        Node cur = _root;

        for (int i = 0; i < str.length(); i++)
        {
            int index = this.getIndex(letters[i]);
            if ((index >= 0) && (cur.getChildren()[index] != null))
            {
                cur = cur.getChildren()[index];
            }
            else
            {
                return null;
            }
        }
        return cur;
    }

    /*
    *       The containsWord() method calls the search method to find the node corresponding to the last letter of the
    *   inputted word. If the node is not null, and the text stored on the node equals the inputted word, the word
    *   exists in the dictionary and the function returns true. If the word was not found, the function returns false.
    *
    *   Input: str - the string to be searched for in the trie (only lowercase letters are stored in trie).
    *   Output: a boolean indicating whether or not the word was found the the trie representing the dictionary.
    */
    public Boolean containsWord(String word)
    {
        Node node = this.search(word);
        Boolean success = false;

        if (node != null)
        {
            if(node.get_txt().equals(word))
            {
                success = true;
            }
        }

        return success;
    }

    /*
    *       The checkValidWordTyped() method calls the checkValidWordTyped() method of the instance of the CheckValidWord
    *   class stored in the DictTrie's _wordChecker variable.
    *
    *   Input:  word - a char[] representing the word to be validated.
    *           start - a vertex to start the search from (the vertex's letter always corresponds to the first letter of the word).
    *   Output: a boolean indicating whether or not the word was found in the dictionary and on the board.
    * */

    public Boolean checkValidWordTyped(char[] word, Vertex start)
    {
        return _wordChecker.checkValidWordTyped(word, start) == true;
    }

    /**                                                 CheckValidWord Class
     *
     *      The CheckValidWord class contains the algorithm that checks if words are valid (if they are both on the
     *  board and in the dictionary). By using a Trie, I was able to use a dynamic programming solution for finding a
     *  valid word by searching through the trie and the letters on the board simultaneously.
     **/

    private class CheckValidWord
    {
        private Boolean _wordFound;

        private CheckValidWord()
        {
            _wordFound = false;
        }


        public Boolean checkValidWordTyped(char[] word, Vertex start) {


            int index = DictTrie.this.getIndex(word[0]);
            Node node = _root.getChildren()[index];

            if (this.checkValidWordHelperTyped(word, start, node, 0) == true)
            {
                _wordFound = false;  // reset global variable
                return true;
            }
            else
            {
                return false;
            }
        }


        private Boolean checkValidWordHelperTyped(char[] word, Vertex vert, Node node, int i)
        {
            vert.setVisited(true);

            if ((i == (word.length - 1)) && (node.is_word() == true) && (word[i] == vert.getChar()))
            {
                _wordFound = true;
            }

            else if ((i < (word.length - 1)) && (word[i] == vert.getChar()) && (_wordFound == false))
            {
                Iterator<Vertex> neighbors = vert.getNeighbors().iterator();
                i++;
                int index = DictTrie.this.getIndex(word[i]);

                if (index >= 0)
                {
                    Node child = node.getChildren()[index];

                    while(neighbors.hasNext())
                    {
                        Vertex neighbor = neighbors.next();

                        if ((neighbor.getChar() == word[i]) && (neighbor.getVisited() == false) && (child != null))
                        {
                            this.checkValidWordHelperTyped(word, neighbor, child, i);
                        }
                    }
                }
            }

            vert.setVisited(false);

            return _wordFound;

        }
    }

    /**                                                 Node Class
     *
     *      The Node class represents a node in the trie. Each node contains an array of references to 26
     *  possible children. Each index corresponds to a lowercase letter-- in alphabetical order. By using
     *  the DictTrie's get index method, the child of a node corresponding to a given letter. If the node
     *  is a word, its _isWord variable is set to true and the word is stored in the node's _txt variable.
     **/

    private class Node
    {
        private Node[] _children;
        private Boolean _isWord;
        private String _txt;

        public Node(String string)
        {
            _children = new Node[26];
            _isWord = false;
            _txt = string;
        }

        public Node[] getChildren()
        { return _children;
        }

        public Boolean is_word()
        { return _isWord;
        }

        public String get_txt()
        { return _txt;
        }

        public void set_txt(String word)
        { _txt = word;
        }

        public void set_word(boolean b) {
            _isWord = b;
        }
    }

}
