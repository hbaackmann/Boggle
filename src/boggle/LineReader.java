package boggle;


import java.io.*;

/**                                                 LineReader Class
 *
 *      The LineReader class is instantiated locally by the Game class to read the contents of the file
 *      "OpenEnglishWordList.txt". Because each word is separated by a new line, this class simply reads
 *      one line and adds the line to the dictionary. The LineReader is passed a reference to the DictTrie
 *      it should add words to.
 **/

public class LineReader
{
    private static DictTrie _dict;

    /*
    *       The LineReader() constructor creates a new LineReader that can add lines of a file
    *   to the inputted DictTrie.
    *
    *   Input:  dictionary - the instance of DictTrie to be used during the lifetime of the LineReader.
    *   Output: nothing.
    * */

    public LineReader(DictTrie dictionary)
    {
        _dict = dictionary;
    }

    /*
    *       The addToDict() method takes in a file name and adds each line of the file to the _dict.
    *
    *   Input:  filename - name of the file to read into the dictionary.
    *   Output: nothing.
    * */

    public void addToDict(String filename) throws IOException
    {
        InputStream input = FileReader.class.getResourceAsStream(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        try
        {
            String line = reader.readLine();
            while (line != null) {
                _dict.add(line);
                line = reader.readLine();
            }
        } finally {
            reader.close();
        }
    }
}