//***************************************//
// Catherine Wright                      //
//                                       //
// Board class holds the logic of the    //
// game board, and instantiates          //
// BoggleBlock class, Dictionary class,  //
// and coordinate class.                 //
//                                       //
// Uses: Creates a map of Coordinates    //
// to Character values.                  //
//***************************************//

import java.util.*;

class Board
{
  private Map<Coordinate, Character> board = new HashMap<>();
  private ArrayList<Character> candidates = new ArrayList<>(); //Board char candidates
  private ArrayList<Coordinate> possible = new ArrayList<>(); //Coordinates surrounding current
  private int B_S = 5; /* allocated to 5 originally for visited array */
  private final int MAX_NUMS = 4; //Maximum amount of same letter on board
  private BoggleBlock block = new BoggleBlock(); // generates characters
  private ArrayList<String> finalList = new ArrayList<>(); //Holds all words in board
  private boolean visited[][] = new boolean[B_S][B_S];

  /**
   * Board() constructor:
   * @param boardSize : sets boardsize to be used (4 or 5)
   *
   * Sets board size, generates boards and searches words
   * until a board with more than 50 words is generated.
   */
  Board(int boardSize)
  {
    B_S = boardSize;
    while(finalList.size() < 50)
    {
      setBoard();
      buildFinalList();
    }
  }

  @Override
  public String toString()
  {
    Collection<Character> value = board.values();
    Iterator<Character> iter = value.iterator();
    StringBuilder sB = new StringBuilder();
    while (iter.hasNext())
    {
      for (int i = 0; i < B_S; i++)
      {
        for (int j = 0; j < B_S; j++)
        {
          String next = iter.next().toString();
          sB.append(next + " ");
        }
        sB.append('\n');
      }
    }
    String boardString = sB.toString();
    return boardString;
  }

  //returns the final list
  ArrayList<String> returnFinalList()
  {
    return finalList;
  }

  /**
   * getC() method:
   * @param c : Coordinate key for map value to be returned
   * @return Character value at coordinate c
   */
  Character getC(Coordinate c)
  {
    return get(c.returnX(), c.returnY());
  }

  /**
   * outputFinalList() method:
   * No parameters
   * @return Final list as a string
   *
   * Uses a stringbuilder to create a list of all words
   * in the board and in the dictionary
   */
  String outputFinalList()
  {
    StringBuilder sb = new StringBuilder();
    for(String s : finalList)
    {
      sb.append(s);
      sb.append("\n");
    }
    String output = sb.toString();
    return output;
  }

  /**
   * get() method
   * @param x : x value of coordinate key
   * @param y : y value of coordinate key
   * @return Character value at key Coordinate(x, y)
   */
  private Character get(int x, int y)
  {
    if ((x >= 0 && x < B_S) && (y >= 0 && y < B_S))
    {
      Character c = board.get(new Coordinate(x, y));
      return c;
    } else return '0';
  }

  /**
   * numOfChar() method:
   * @param c : character to be counted
   * @return integer of how many of character c are currently on the board
   *
   * Used to ensure no more than MAX_NUMS chars are on a board
   */
  private int numOfChar(char c)
  {
    int finalCount = 0;
    for (Character ch : candidates) if (c == ch) finalCount++;
    return finalCount;
  }

  //USED IN UNIT TESTING - prints array of board coordinates visited
  private String printVisited()
  {
    StringBuilder sB = new StringBuilder();
    for(int i = 0; i < B_S; i++)
    {
      for(int j = 0; j < B_S; j++)
      {
        if(visited[i][j]) sB.append(1);
        else sB.append(0);
      }
      sB.append('\n');
    }
    return sB.toString();
  }

  /**
   * set() method:
   * @param x : x value of coordinate key
   * @param y : y value of coordinate key
   * @param c : character value to be put at coordinate(x, y)
   *
   * Used to build the board, creates coordinate (x, y), and places
   * coordinate value c at key created.
   */
  private void set(int x, int y, char c)
  {
    Coordinate key = new Coordinate(x, y);
    key.setBS(B_S);
    board.put(key, c);
  }

  /**
   * buildFinalList() method:
   * No parameters, no outputs
   *
   * Creates a new dictionary object.
   * Searches the board for each word in the dictionary.  If word is
   * in both the board and dictionary, adds it to finalList array
   */
  private void buildFinalList()
  {
    Dictionary dictionary = new Dictionary();
    for(String word : dictionary.returnDict()) findWord(word);
  }

  /**
   * findWord() method:
   * @param word : String to be searched for on board
   * no output
   *
   * Checks that the word is greater than 2 letters long,
   * if so the board is searched for the first letter
   * When first letter is found, recurseWord function is called
   * to search for the rest of the word
   */
  private void findWord(String word)
  {
    if(word.length() > 2)
    {
      for (int i = 0; i < B_S; i++)
      {
        for (int j = 0; j < B_S; j++)
        {
          if (get(i, j) == word.charAt(0))
          {
            recurseWord(new Coordinate(i, j), 1, word);
          }
          resetVisited();
        }
      }
    }
  }

  /**
   * recurseWord() method:
   * @param coord : coordinate on board to seach around
   * @param index : current place in word to search
   * @param word : String currently being searched for
   * No output
   *
   * Checks around coord for the next letter.  If found, runs recursively
   *             on next letter.  If index is size of word, word is added
   *             to finalList
   * Visited array keeps track of letters no longer available
   */
  private void recurseWord(Coordinate coord, int index, String word)
  {
    visited[coord.returnX()][coord.returnY()] = true;
    ArrayList<Coordinate> bP = coord.buildPossibles();
    for(Coordinate c : bP)
    {
      if(getC(c) == word.charAt(index))
      {
        if(!visited[c.returnX()][c.returnY()])
        {
          if (index == word.length() - 1)
          {
            visited[c.returnX()][c.returnY()] = true;
            if(!finalList.contains(word)) finalList.add(word);
          }
          else recurseWord(c, index + 1, word);
          visited[c.returnX()][c.returnY()] = false;
        }

      }
    }
  }

  /**
   * setBoard() method:
   *
   * Builds a new board based on current boardsize, calling
   * BoggleBlock instance, checking to be sure no more than MAX_NUMS
   * chars are on board, and finds q's, running findQs() method on board
   */
  private void setBoard()
  {
    board.clear();
    char boardCandidate;
    for (int i = 0; i < B_S; i++)
    {
      for (int j = 0; j < B_S; j++)
      {
        boardCandidate = block.returnChar();
        while (numOfChar(boardCandidate) > MAX_NUMS) boardCandidate = block.returnChar();
        set(i, j, boardCandidate);
        candidates.add(boardCandidate);
        resetVisited();
      }
    }
    findQs();
  }

  /**
   * Visited array keeps track of which characters are in use so they cannot
   * be used again in the same turn.  resetVisited() method sets all visited
   * places to false, to start searching for a new word.
   */
  private void resetVisited()
  {
    for(int i = 0; i < B_S; i++) for(int j = 0; j < B_S; j++) visited[i][j] = false;
  }

  /**
   * findQs() method:
   * No parameters or output
   *
   * Searches board for q's.  If found, builds possibles array
   * of all board coordinates surrounding the q coordinate.
   * Then runs setU() method on surrounding coordinates
   */
  private void findQs()
  {
    for (int i = 0; i < B_S; i++)
    {
      for (int j = 0; j < B_S; j++)
      {
        if (get(i, j) == 'q')
        {
          Coordinate key = new Coordinate(i, j);
          possible = key.buildPossibles();
          setU();
        }
      }
    }
  }

  /**
   * setU() method:
   * No parameters or output
   *
   * Randomly shuffles possibles array from findQs method.
   * For each coordinate in possibles, generates a random number.
   * If random number is greater than .5, board value at coordinate
   * is changed to a u, and the process stops
   */
  private void setU()
  {
    Collections.shuffle(possible);
    int x, y;
    for(Coordinate coord : possible)
    {
      x = coord.returnX();
      y = coord.returnY();
      double random = Math.random();
      if(random > .5){
        set(x, y, 'u');
        break;
      }
    }
  }
  //

  //USED IN UNIT TESTING - version 1 to ask for a word to be checked
  private String scanInput()
  {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter a word: ");
    return scanner.nextLine();
  }

  //USED IN UNIT TESTING - prints final list to screen
  private void printFinalList()
  {
    for(int i = 0; i < finalList.size(); i++) System.out.println(finalList.get(i));
  }
}

