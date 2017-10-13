//***************************************//
// Catherine Wright                      //
//                                       //
// Dictionary class reads the text file  //
// into an array, and determines         //
// if String words are in the dictionary //
//***************************************//

import java.io.*;
import java.util.ArrayList;

class Dictionary extends Exception
{
  private ArrayList<String> dictionary = new ArrayList<>(); //holds dictionary

  /**
   * Dictionary() constructor:
   *
   * builds the array list dictionary of strings to be
   * used later
   */
  Dictionary()
  {
    buildDictionary();
  }

  /**
   * returnDict() method:
   * No parameters
   * @return Array list of Strings: dictionary
   *
   * returns the dictionary to be used in word checking
   */
  ArrayList<String> returnDict()
  {
    return dictionary;
  }

  /**
   * buildDictionary method:
   * No parameters, no output
   *
   * If a read-in file exists, this method reads line by line into an
   * array list of strings.
   */
  private void buildDictionary()
  {
    BufferedReader inFile;
    InputStream in = getClass().getResourceAsStream("OpenEnglishWordList.txt");
    String str;
    try
    {
      inFile = new BufferedReader(new InputStreamReader(in));
      while ((str = inFile.readLine()) != null) dictionary.add(str.trim());
    }
    catch(IOException ex) { ex.printStackTrace(); }
  }
}
