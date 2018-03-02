package system;

/**
 * @author Martin Dowling
 * 
 * Abstract model of the behaviour of a team.
 * One method for everything a team gets from a match
 * that is used to create database data.
 * 
 */

public interface Team {
	
	//override located in concrete TeamA and TeamB
	int TeamNo (int matchID);
	int[] UpdateResults(ResultsTable rt, int[] leagueTableArray, Result r, int matchPointsNew);
	
	//overrides located in TeamAbstract
	int[] ResultsCurrent(ResultsTable rt);
	Result ResultNew();
	int MatchPointsNew(Result r); 
	int GrandSlam(int wins);


	
}
