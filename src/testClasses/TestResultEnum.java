package testClasses;

import system.Results;
import system.ResultsOfMatch;
import system.Team;
import system.TeamA;

public class TestResultEnum {

	public static void main(String[] args) {

		int MatchID = 4;
		int APoints = 34;
		int ATries = 4;
		int BPoints = 7; 
		int BTries = 1;
		Results resultsOfMatch = new ResultsOfMatch(MatchID, APoints, ATries, BPoints, BTries);
		
		Team A = new TeamA(resultsOfMatch, 0, resultsOfMatch.ReturnResult()[0][1], 
				resultsOfMatch.ReturnResult()[0][2], 
				resultsOfMatch.ReturnResult()[0][3]);
		
		System.out.println(A.ResultNew());
	}

}
