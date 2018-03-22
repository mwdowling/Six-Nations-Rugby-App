package system;

/**
 * 
 * @author Martin Dowling
 * 
 * For a given match, this object will 
 * identify itself as Team 1 in that match
 * and create the values for Team 1 in an array
 * for loading into the League Table
 * as well as inheriting the behaviours of an abstract team
 * 
 * @see TeamAbstract
 */

public class TeamA extends TeamAbstract {
	
	private Results R;
	private int Row;
	
	public TeamA(Results results, int row, int pointsScored, int tries, int pointsConceded) {
		super(results, row, pointsScored, tries, pointsConceded);	
		R = results;
		Row = row;
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
	public int[] UpdateResults(int[] leagueTableArray, Result r, int matchPointsNew) {

		leagueTableArray[0]++;// add another game played

		// update wins, losses, and draws
		switch (r) {

		case WIN: {leagueTableArray[1]++;} break;
		case LOSS: {leagueTableArray[2]++;} break;
		case DRAW: {leagueTableArray[3]++;}

		}

		// update points scored, points conceded, and tries
		leagueTableArray[4] = leagueTableArray[4] + R.ReturnResult()[Row][1];
		leagueTableArray[5] = leagueTableArray[5] + R.ReturnResult()[Row][3];
		leagueTableArray[6] = leagueTableArray[6] + R.ReturnResult()[Row][2];

		// update bonus points
		leagueTableArray[7] = leagueTableArray[7] + matchPointsNew;

		return leagueTableArray;
	}
}
