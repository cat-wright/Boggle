import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary extends Exception
{
  ArrayList<String> dictionary = new ArrayList<>();
  private boolean ERR_CHECK = true;
  private String checkOver = "Stop.";

  private void buildDictionary()
  {
    BufferedReader inFile;
    InputStream in = getClass().getResourceAsStream("OpenEnglishWordList.txt");
    //File file = new File("src/OpenEnglishWordList.txt");
    String str;
    try
    {
      inFile = new BufferedReader(new InputStreamReader(in));
      while ((str = inFile.readLine()) != null)
      {
        dictionary.add(str.trim());
      }
    }
    catch(IOException ex) { ex.printStackTrace(); }
  }

  private String scanInput()
  {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter a word: ");
    return scanner.nextLine();
  }

  private void checkInput()
  {
    String word = scanInput();
    while(!word.equals(checkOver))
    {
      if(ERR_CHECK) System.out.println(word);
      if(dictionary.contains(word)) System.out.println("true!");
      else System.out.println("false.");
      word = scanInput();
    }
  }

  boolean contains(String str)
  {
    if(dictionary.contains(str)) return true;
    else return false;
  }

  int maxLength()
  {
    int length = 0;
    for(String word : dictionary)
    {
      if(word.length() > length) length = word.length();
    }
    return length;
  }

  Dictionary()
  {
    buildDictionary();

    //checkInput();
  }
}
