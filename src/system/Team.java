package system;

/**
 * @author Martin Dowling
 * 
 * Abstract model of the behaviour of a team.
 * A method for everything a team does which has a persistent result.
 */

import java.util.ArrayList;

public interface Team {

	String PlayedAgainst(String string);
	int ScoredPoints(int points);
	int ConcededPoints(int points);
	int ScoredTries(int tries);
	Result GotResult();
	int AddedMatchPoints (int pointsScored, int pointsConceded, int tries);
	int grandSlam(ArrayList<Result> results); 
}
