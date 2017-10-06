import java.util.ArrayList;
import java.util.Collections;

public class BoggleBlock
{
  private ArrayList<Character> vowels = new ArrayList<>();
  private ArrayList<Character> consonants = new ArrayList<>();
  private final double VOWEL_PROB = .35;

  private void buildVowels()
  {
    vowels.add('a');
    vowels.add('e');
    vowels.add('i');
    vowels.add('o');
    vowels.add('u');
  }

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

  char returnChar()
  {
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

  BoggleBlock()
  {
    shuffledConsAndVowels();
  }
}
