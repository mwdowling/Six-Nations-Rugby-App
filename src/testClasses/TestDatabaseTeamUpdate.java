package testClasses;

import system.ResultsTable;
import system.Team;
import system.TeamA;

public class TestDatabaseTeamUpdate {

	public static void main(String[] args) {
		
		ResultsTable rt = new ResultsTable(1, 35, 2, 46, 1);
		Team A = new TeamA(rt.ReturnResult()[1], rt.ReturnResult()[2], rt.ReturnResult()[3]);
		
		int[] leagueTableArray = new int[9];
		int[] leagueTableArrayNew = new int[9];
		
		leagueTableArray = A.ResultsCurrent(rt);
		leagueTableArrayNew = A.UpdateResults(rt, leagueTableArray, A.ResultNew(), A.MatchPointsNew(A.ResultNew()));
		
		
		System.out.println(leagueTableArray[0] + "   \t\t" + leagueTableArray[1] + "\t\t" + leagueTableArray[2] + "\t"
				+ leagueTableArray[3] + "\t" + leagueTableArray[4] + "\t" + leagueTableArray[5] + "\t"
				+ leagueTableArray[6] + "\t\t" + leagueTableArray[7] + "\t" + leagueTableArray[8] + "\t");	
		
		System.out.println("Results of Match " + rt.ReturnResult()[0] + ": " 
				 				+ "TeamA points: " + rt.ReturnResult()[1] 
				 				+ "TeamA tries: " + rt.ReturnResult()[2] 
				 				+ "TeamB points: "+ rt.ReturnResult()[3] 
				 				+ "TeamB points: " + rt.ReturnResult()[4]);
		
	}
}
