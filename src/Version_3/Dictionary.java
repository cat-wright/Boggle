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

  ArrayList<String> returnDict()
  {
    return dictionary;
  }

  Dictionary()
  {
    buildDictionary();
  }
}
