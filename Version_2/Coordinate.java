import java.util.ArrayList;

public class Coordinate
{
  private int x;
  private int y;
  private static int BS;

  int returnX()
  {
    return x;
  }

  int returnY()
  {
    return y;
  }

  boolean isValid(int new_x, int new_y, int board_size)
  {
    if(new_x < 0 || new_y < 0) return false;
    if(new_x >= board_size || new_y >= board_size) return false;
    return true;
  }

  ArrayList<Coordinate> buildPossibles()
  {
    ArrayList<Coordinate> possibles = new ArrayList<>();
    if(isValid(x-1, y-1, BS)) possibles.add(new Coordinate(x-1, y-1));
    if(isValid(x-1, y, BS)) possibles.add(new Coordinate(x-1, y));
    if(isValid(x-1, y+1, BS)) possibles.add(new Coordinate(x-1, y+1));
    if(isValid(x, y-1, BS)) possibles.add(new Coordinate(x, y-1));
    if(isValid(x, y+1, BS)) possibles.add(new Coordinate(x, y+1));
    if(isValid(x+1, y-1, BS)) possibles.add(new Coordinate(x+1, y-1));
    if(isValid(x+1, y, BS)) possibles.add(new Coordinate(x+1, y));
    if(isValid(x+1, y+1, BS)) possibles.add(new Coordinate(x+1, y+1));
    return possibles;
  }

  void setBS(int boardSize)
  {
    BS = boardSize;
  }

  @Override
  public String toString()
  {
    return String.format("[%d, %d] ", x, y);
  }

  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null) return false;
    if (this.getClass() != o.getClass()) return false;
    Coordinate obj = (Coordinate) o;
    if (obj.returnX() != x || obj.returnY() != y) return false;
    return true;
  }

  @Override
  public int hashCode()
  {
    return BS*x + y;
  }

  Coordinate(int X, int Y)
  {
    this.x = X;
    this.y = Y;
  }
}
