package system;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
/**
 * 
 * @author Martin Dowling
 * 
 * A blueprint for an object that 
 * takes an array of team names  
 * and randomly "shuffles" them
 * 
 * @see FixtureAssignment
 *
 */
public class FixtureRandomizer {
	
	public FixtureRandomizer() { 
		
	}

	// the shuffling method adapted from StackOverflow
	public String[] Shuffle(String[] array) {

		Random rnd = ThreadLocalRandom.current();
		for (int j = array.length - 1; j > 0; j--) {

			int index = rnd.nextInt(j + 1);
			// Simple swap
			String a = array[index];
			array[index] = array[j];
			array[j] = a;
		}
		return array;
	}
}
