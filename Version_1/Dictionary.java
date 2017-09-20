import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary extends Exception
{
  private ArrayList<String> dictionary = new ArrayList<>();
  private boolean ERR_CHECK = true;
  private String checkOver = "Stop.";

  private void buildDictionary()
  {
    BufferedReader inFile;
    File file = new File("OpenEnglishWordList.txt");
    String str;
    try
    {
      inFile = new BufferedReader(new FileReader(file));
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

  Dictionary()
  {
    buildDictionary();
    if (ERR_CHECK) System.out.println(dictionary.size());
    checkInput();
  }

  public static void main(String[] args)
  {
    Dictionary dict = new Dictionary();
  }
}
