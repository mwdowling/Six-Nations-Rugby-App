package system;

/**
 * @author Martin Dowling
 * 
 * An abstract class with implementations of interface behaviours
 * The six teams in the tournament will inherit these attributes and behaviours.#
 * 
 * TODO appropriate constructors needed 
 */
import java.util.ArrayList;

public abstract class TeamAbstract implements Team{
	
	private int PointsScored;
	private int Tries;
	private int PointsConceded;
	private int MatchPoints; 
	

	@Override
	public String PlayedAgainst(String opponent) {
		
		return opponent;
	}

	@Override
	public int ScoredPoints(int points) {
		return points;
	}

	@Override
	public int ConcededPoints(int points) {
		return points;
	}

	@Override
	public int ScoredTries(int tries) {
		return tries;
	}

	@Override
	public Result GotResult() {
		
		Result thisResult = null;
		
		if (PointsScored == PointsConceded) {
			
			thisResult = Result.DRAW;
			
		} else if (PointsScored > PointsConceded) {
			thisResult = Result.WIN;
		
		} else
			thisResult = Result.LOSS;//String version would be Result.LOSS.getClass().getName()
			
		return thisResult;
	}
	
	@Override
	public int AddedMatchPoints(int pointsScored, int pointsConceded, int tries) {
		
		Result r = GotResult();
		
		switch(r){
		
		case WIN : {
			
			if (Tries > 3) {
				MatchPoints += 5;
			} else
				MatchPoints += 4;
		} break;

		case LOSS : {
			int scoreDifference = Math.abs(pointsScored - pointsConceded);
			
			if (Tries > 3  || scoreDifference < 8) {
				MatchPoints += 1;
			}
			
			if (Tries > 3  && scoreDifference < 8) {
				MatchPoints += 1;
			}
			
		} break;
		
		case DRAW : {
			if (Tries > 3) {
				MatchPoints += 3;
			} else
				MatchPoints += 2;
			}
		}
		
		return MatchPoints;
		
	}

	@Override
	public int grandSlam(ArrayList<Result> results) {
		
		for (Result r : results) {

			if (r == Result.DRAW || r == Result.LOSS) {
				break;
			} else MatchPoints += 3;		
		}
		
		return MatchPoints;
	}
}
