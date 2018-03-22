package testClasses;

import java.io.IOException;

import system.LeagueTable;
import system.Results;
import system.ResultsOfMatch;
import system.Team;
import system.TeamA;
import system.TeamB;
import system.TeamFinder;

public class TestLeagueTableMatchUpdate {

	public static void main(String[] args) throws IOException {
	
		int row = 0;
		int MatchID = 4;
		int APoints = 34;
		int ATries = 4;
		int BPoints = 7; 
		int BTries = 1;
		
		TeamFinder teamFinder = new TeamFinder();
		System.out.println(teamFinder.TeamA(MatchID));
		System.out.println(teamFinder.TeamB(MatchID));
		
		Results resultsOfMatch = new ResultsOfMatch(MatchID, APoints, ATries, BPoints, BTries);
		resultsOfMatch.WriteResult();
		
		Team A = new TeamA(resultsOfMatch, row, resultsOfMatch.ReturnResult()[row][1], 
							resultsOfMatch.ReturnResult()[row][2], 
							resultsOfMatch.ReturnResult()[row][3]);
		Team B = new TeamB(resultsOfMatch, row, resultsOfMatch.ReturnResult()[row][3], 
							resultsOfMatch.ReturnResult()[row][4], 
							resultsOfMatch.ReturnResult()[row][1]);

		LeagueTable leagueTable = new LeagueTable(resultsOfMatch, row, A, B);
		leagueTable.WriteMatchUpdate();
	}
}
