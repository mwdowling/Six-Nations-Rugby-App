package testClasses;

import java.io.IOException;
import system.LeagueTable;
import system.Results;
import system.ResultsOfRound;
import system.Team;
import system.TeamA;
import system.TeamB;

public class TestLeagueTableRoundUpdate {

	public static void main(String[] args) throws IOException {

		String DbFolder = "C:/Users/Martin/My Documents/Java Projects/SixNationsApp/";
		String Source = "Round1Result.txt";
		String RoundResultFile = DbFolder + Source;
		Results resultsOfRound = new ResultsOfRound(RoundResultFile);
		resultsOfRound.WriteResult();

		for (int i = 0; i < 3; i++) {
			
			int row = i;

			Team A = new TeamA(resultsOfRound, row, resultsOfRound.ReturnResult()[row][1],
					resultsOfRound.ReturnResult()[row][2], resultsOfRound.ReturnResult()[row][3]);

			Team B = new TeamB(resultsOfRound, row, resultsOfRound.ReturnResult()[row][3],
					resultsOfRound.ReturnResult()[row][4], resultsOfRound.ReturnResult()[row][1]);

			LeagueTable leagueTable1 = new LeagueTable(resultsOfRound, row, A, B);
			
			leagueTable1.WriteMatchUpdate();
		
		}
	}

}
