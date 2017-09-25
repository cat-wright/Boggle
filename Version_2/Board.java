import java.util.*;

public class Board
{
  private Map<Coordinate, Character> board = new HashMap<>();
  private ArrayList<Character> candidates = new ArrayList<>();
  private ArrayList<Coordinate> possible = new ArrayList<>();
  private int B_S;
  private final int MAX_NUMS = 4;
  private BoggleBlock block = new BoggleBlock();

  private void set(int x, int y, char c)
  {
    Coordinate key = new Coordinate(x, y);
    key.setBS(B_S);
    board.put(key, c);
  }

  private Character get(int x, int y)
  {
    if ((x >= 0 && x < B_S) && (y >= 0 && y < B_S))
    {
      Character c = board.get(new Coordinate(x, y));
      return c;
    } else return '0';
  }

  int numOfChar(char c)
  {
    int finalCount = 0;
    for (Character ch : candidates) if (c == ch) finalCount++;
    return finalCount;
  }

  private void setBoard()
  {
    char boardCandidate;
    for (int i = 0; i < B_S; i++)
    {
      for (int j = 0; j < B_S; j++)
      {
        boardCandidate = block.returnChar();
        while (!checkValid(boardCandidate)) boardCandidate = block.returnChar();
        set(i, j, boardCandidate);
        candidates.add(boardCandidate);
      }
    }
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

  private void UNIT_TESTING()
  {
    System.out.println(toString());
    char ch = get(2, 3);
    System.out.println("Character at 2, 3: " + ch + ", " + numOfChar(ch) + " time/s");
    /*Iterator<Character> iter = candidates.iterator();
    while (iter.hasNext())
    {
      char next = iter.next();
      if (numOfChar(next) > MAX_NUMS) System.out.println("ERROR! TOO MANY " + next + "'s!");
    }*/
  }

  void findQs()
  {
    for (int i = 0; i < B_S; i++)
    {
      for (int j = 0; j < B_S; j++)
      {
        if (get(i, j) == 'q')
        {
          Coordinate key = new Coordinate(i, j);
          possible = key.buildPossibles();
          System.out.println("q at " + i + ", " + j);
          setU();
        }
      }
    }
  }

  void setU()
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
        System.out.println(coord.toString());
        break;
      }
    }
  }

  Board(int boardSize)
  {
    B_S = boardSize;
    setBoard();
    UNIT_TESTING();
    findQs();
    System.out.println(toString());
  }

  public static void main(String[] args)
  {
    Board b = new Board(5);
  }
}
