package system;

public class SystemTest {

	public static void main(String[] args) {
	
		int MatchID = 1;
		int APoints = 34;
		int ATries = 4;
		int BPoints = 7; 
		int BTries = 1;
		
		ResultsTable rt = new ResultsTable(MatchID, APoints, ATries, BPoints, BTries);
		rt.WriteResult();
		
		Team A = new TeamA(rt.ReturnResult()[1], rt.ReturnResult()[2], rt.ReturnResult()[3]);
		Team B = new TeamB(rt.ReturnResult()[3], rt.ReturnResult()[4], rt.ReturnResult()[1]);

		LeagueTable lt = new LeagueTable(rt, A, B);
		lt.WriteMatchUpdate();
		
		
	}

}
