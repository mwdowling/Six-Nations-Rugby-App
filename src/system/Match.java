package system;
/**
 * 
 * @author Martin Dowling
 * 
 * A class returning an object that produces the 
 * the outcome of a match.
 *
 */
public class Match {

	int MatchID; //or is this a String?
	private Team Team1;
	private Team Team2;
	
	public Match(int matchID, Team team1, Team team2) {
		MatchID = matchID;
		Team1 = team1;
		Team2 = team2;
	}
	
	public void MatchResult(int team1Points, int team1Tries, int team2Points, int team2Tries){
		
		/*
		 * TODO Do all these methods write to files?
		 * 		Do I output an array which is then written to a file?   
		 */
		Team1.PlayedAgainst(Team2.getClass().getName());
		Team1.ScoredPoints(team1Points);
		Team1.ConcededPoints(team2Points);
		Team1.ScoredTries(team1Tries);
		Team1.GotResult();
		
		Team1.PlayedAgainst(Team1.getClass().getName());
		Team2.ScoredPoints(team1Points);
		Team2.ConcededPoints(team1Points);
		Team2.ScoredTries(team1Tries);
		Team2.GotResult();
		
	}
	
	
	
	
}
