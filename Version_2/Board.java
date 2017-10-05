import java.util.*;

public class Board
{
  private Map<Coordinate, Character> board = new HashMap<>();
  private ArrayList<Character> candidates = new ArrayList<>();
  private ArrayList<Coordinate> possible = new ArrayList<>();
  //CHEATING allocated to 5 originally for visited array
  private int B_S = 5;
  private final int MAX_NUMS = 4;
  private BoggleBlock block = new BoggleBlock();
  private ArrayList<String> finalList = new ArrayList<>();
  protected boolean truth;
  private boolean visited[][] = new boolean[B_S][B_S];

  void setBoardSize(int BS)
  {
    B_S = BS;
  }

  private void set(int x, int y, char c)
  {
    Coordinate key = new Coordinate(x, y);
    key.setBS(B_S);
    board.put(key, c);
  }

  Character get(int x, int y)
  {
    if ((x >= 0 && x < B_S) && (y >= 0 && y < B_S))
    {
      Character c = board.get(new Coordinate(x, y));
      return c;
    } else return '0';
  }

  Character getC(Coordinate c)
  {
    return get(c.returnX(), c.returnY());
  }

  private int numOfChar(char c)
  {
    int finalCount = 0;
    for (Character ch : candidates) if (c == ch) finalCount++;
    return finalCount;
  }

  protected void findWord(String word)
  {
    truth = false;
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
  private void buildFinalList()
  {
    Dictionary dictionary = new Dictionary();
    for(String word : dictionary.returnDict()) findWord(word);
    printFinalList();
  }

  boolean returnTruth() { return truth; }

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
            truth = true;
            visited[c.returnX()][c.returnY()] = true;
            //System.out.println(word + '\n' + printVisited());
            finalList.add(word);
          }
          else recurseWord(c, index + 1, word);
          visited[c.returnX()][c.returnY()] = false;
        }

      }
    }
  }

  String printVisited()
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

  private void setBoard()
  {
    board.clear();
    char boardCandidate;
    for (int i = 0; i < B_S; i++)
    {
      for (int j = 0; j < B_S; j++)
      {
        boardCandidate = block.returnChar();
        while (!checkValid(boardCandidate) || numOfChar(boardCandidate) > MAX_NUMS)
        {
          boardCandidate = block.returnChar();
        }
        set(i, j, boardCandidate);
        candidates.add(boardCandidate);
        resetVisited();
      }
    }
    findQs();
  }

  void resetVisited()
  {
    for(int i = 0; i < B_S; i++) for(int j = 0; j < B_S; j++) visited[i][j] = false;
  }

  boolean checkValid(char b_Cand)
  {
    if (candidates.size() < MAX_NUMS) return true;
    else if (numOfChar(b_Cand) <= MAX_NUMS) return true;
    else return false;
  }

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
          //System.out.println("q at " + i + ", " + j);
          setU();
        }
      }
    }
  }

  private void setU()
  {
    Collections.shuffle(possible);
    int x; int y;
    for(Coordinate coord : possible)
    {
      x = coord.returnX();
      y = coord.returnY();
      double random = Math.random();
      if(random > .5){
        set(x, y, 'u');
        //System.out.println(coord.toString());
        break;
      }
    }
  }

  /*
  String scanInput()
  {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter a word: ");
    return scanner.nextLine();
  }
  */

  private void printFinalList()
  {
    for(int i = 0; i < finalList.size(); i++) System.out.println(finalList.get(i));
  }

  Board(int boardSize)
  {
    B_S = boardSize;
    while(finalList.size() < 50)
    {
      setBoard();
      buildFinalList();
    }
    //System.out.println(toString());
  }
}
