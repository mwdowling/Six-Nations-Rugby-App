package system;

public class SystemTest {

	public static void main(String[] args) {
	
		int MatchID = 1;
		int APoints = 34;
		int ATries = 4;
		int BPoints = 7; 
		int BTries = 1;
		
		ResultsTable resultsTable = new ResultsTable(MatchID, APoints, ATries, BPoints, BTries);
		resultsTable.WriteResult();
		
		Team A = new TeamA(resultsTable.ReturnResult()[1], resultsTable.ReturnResult()[2], resultsTable.ReturnResult()[3]);
		Team B = new TeamB(resultsTable.ReturnResult()[3], resultsTable.ReturnResult()[4], resultsTable.ReturnResult()[1]);

		LeagueTable leagueTable = new LeagueTable(resultsTable, A, B);
		leagueTable.WriteMatchUpdate();
		
		
	}

}
