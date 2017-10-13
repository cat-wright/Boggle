//***************************************//
// Catherine Wright                      //
//                                       //
// Coordinate class is used to build     //
// the board, holding an x and y value   //
// and storing valid surrounding         //
// coordinates on the board              //
//                                       //
//***************************************//

import java.util.ArrayList;

public class Coordinate
{
  private int x, y;
  private static int BS;

  /**
   * Coordinate() constructor:
   * @param X x value to be used in instance
   * @param Y y value to be used in instance
   * No output
   *
   * Sets x and y
   */
  Coordinate(int X, int Y)
  {
    this.x = X;
    this.y = Y;
  }

  int returnX()
  {
    return x;
  }

  int returnY()
  {
    return y;
  }

  /**
   * setBS() method:
   * @param boardSize : current size of board ( 4 or 5 )
   * No output
   *
   * Sets static value of boardsize to be used
   */
  void setBS(int boardSize)
  {
    BS = boardSize;
  }

  /**
   * buildPossibles() method:
   * No parameters
   * @return array list of coordinates of possible surrounding coordinates
   *
   * Method creates an array list of all surrounding coordinates to this instance
   */
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

  @Override
  public String toString()
  {
    return String.format("[%d, %d] ", x, y);
  }

  @Override
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

  /**
   * isValid() method:
   * @param new_x : x value to be checked
   * @param new_y : y value to be checked
   * @param board_size : current size of board ( 4 or 5 )
   * @return boolean whether new coordinate is a valid place or not
   *
   * If new coordinate is outside board size or less than zero, method
   * returns false.  If new coordinate is inside the board, returns true
   */
  private boolean isValid(int new_x, int new_y, int board_size)
  {
    if(new_x < 0 || new_y < 0) return false;
    if(new_x >= board_size || new_y >= board_size) return false;
    return true;
  }
}