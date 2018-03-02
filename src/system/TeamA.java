package system;

public class TeamA extends TeamAbstract {
	
	//TODO no need for team number in constructor
	public TeamA(int pointsScored, int tries, int pointsConceded) {
		super(pointsScored, tries, pointsConceded);		
	}
	
	@Override
	public int TeamNo(int matchID) {

		int teamNum = 0;
		switch (matchID) {

		case 1: {teamNum = 1;} break;
		case 2: {teamNum = 3;} break;
		case 3: {teamNum = 5;} break;
		case 4: {teamNum = 1;} break;
		case 5: {teamNum = 2;} break;
		case 6: {teamNum = 4;} break;
		case 7: {teamNum = 1;} break;
		case 8: {teamNum = 2;} break;
		case 9: {teamNum = 3;} break;
		case 10: {teamNum = 1;} break;
		case 11: {teamNum = 2;} break;
		case 12: {teamNum = 4;} break;
		case 13: {teamNum = 1;} break;
		case 14: {teamNum = 2;} break;
		case 15: {teamNum = 3;} break;
		// TODO exception if wrong match number	
		
		}		
		return teamNum;
	}
	
	@Override
	public int[] UpdateResults(ResultsTable rt, int[] leagueTableArray, Result r, int matchPointsNew) {

		leagueTableArray[1]++;// add another game played

		// update wins, losses, and draws
		switch (r) {

		case WIN: {leagueTableArray[2]++;} break;
		case LOSS: {leagueTableArray[3]++;} break;
		case DRAW: {leagueTableArray[4]++;}

		}

		// update points scored, points conceded, and tries
		leagueTableArray[5] = leagueTableArray[5] + rt.ReturnResult()[1];
		leagueTableArray[6] = leagueTableArray[6] + rt.ReturnResult()[3];
		leagueTableArray[7] = leagueTableArray[7] + rt.ReturnResult()[2];

		// update bonus points
		leagueTableArray[8] = leagueTableArray[8] + matchPointsNew;

		return leagueTableArray;
	}
}