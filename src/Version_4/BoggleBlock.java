//***************************************//
// Catherine Wright                      //
//                                       //
// BoggleBlock class is used to          //
// determine each block for the board.   //
//***************************************//

import java.util.ArrayList;
import java.util.Collections;

class BoggleBlock
{
  private ArrayList<Character> vowels = new ArrayList<>();
  private ArrayList<Character> consonants = new ArrayList<>();

  /**
   * BoggleBlock() constructor:
   *
   * creates array lists of characters that hold vowels and consonants,
   * and shuffles these arrays
   */
  BoggleBlock()
  {
    shuffledConsAndVowels();
  }

  /**
   * returnChar() method:
   * No parameters
   * @return char to be used in board
   *
   * Based on vowel probability, decides if next character will be
   * a vowel or consonant.  returns a member of the respective
   * array list, and reshuffles the list.
   */
  char returnChar()
  {
    final double VOWEL_PROB = .30;   //probability that a chosen char will be a vowel
    char toReturn;
    double random = Math.random();
    if(random <= VOWEL_PROB)
    {
      toReturn = vowels.get(2);
      Collections.shuffle(vowels);
    }
    else
    {
      toReturn = consonants.get(3);
      Collections.shuffle(consonants);
    }
    return toReturn;
  }

  /**
   * buildVowels() method:
   * No parameters, no output
   *
   * Brute force build of the vowels array list, adding each
   * separately
   */
  private void buildVowels()
  {
    vowels.add('a');
    vowels.add('e');
    vowels.add('i');
    vowels.add('o');
    vowels.add('u');
  }

  /**
   * shuffledConsAndVowels() method:
   * No parameters or output
   *
   * builds vowels and consonants array lists, and shuffles
   * both
   */
  private void shuffledConsAndVowels()
  {
    buildVowels();
    for(int i = 'a'; i <= 'z'; i++)
    {
      if(!vowels.contains((char)i)) consonants.add((char)i);
    }
    Collections.shuffle(vowels);
    Collections.shuffle(consonants);
  }
}
