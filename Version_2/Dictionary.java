import java.io.*;
import java.util.ArrayList;

/**
 * Author: Catherine Wright
 * Banner ID: 101617696
 * Project 2: Boggle
 * CS351 Fall 2017
 *
 * Dictionary class reads the dictionary text file OpenEnglishWordList.txt into
 * an array list of Strings
 */
public class Dictionary extends Exception
{
  ArrayList<String> dictionary = new ArrayList<>(); //holds dictionary

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

  boolean contains(String str)
  {
    if(dictionary.contains(str)) return true;
    return false;
  }

  //Do I need this method? Finds max word length in dictionary
  /*int maxLength()
  {
    int length = 0;
    for(String word : dictionary)
    {
      if(word.length() > length) length = word.length();
    }
    return length;
  }*/

  ArrayList<String> returnDict()
  {
    return dictionary;
  }

  Dictionary()
  {
    buildDictionary();
  }
}
