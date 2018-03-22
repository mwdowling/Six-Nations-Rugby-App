package system;

/**
 * @author Martin Dowling
 * 
 * An abstract class with implementations of interface behaviours. 
 * The opposing teams in each match 
 * inherit these attributes and behaviours.
 * 
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class TeamAbstract implements Team {

	private int MatchID;
	private int PointsScored;
	private int Tries;
	private int PointsConceded;
	private int MatchPoints;

	public TeamAbstract(Results results, int row, int pointsScored, int tries, int pointsConceded) {
		
		PointsScored = pointsScored;
		Tries = tries;
		PointsConceded = pointsConceded;
		MatchID = results.ReturnResult()[row][0];
		
	}

	@Override
	public int[] ResultsCurrent() {

		int[] leagueTableArray = new int[9];

		try (Connection conn = new DatabaseConnection().Maker();) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery("SELECT [Games Played], Wins, Losses, Draws, Scored, Conceded, Tries, [Grand Slam], Points FROM [League Table] WHERE [Team Number] = "
						+ this.TeamNo(MatchID))) {
					System.out.println("Match: " + MatchID);
					System.out.println("Team No: " + this.TeamNo(MatchID));
					// load resultSet into the array
					rs.next();
					
					for (int i = 0; i < leagueTableArray.length; i++) {
						
						leagueTableArray[i] = rs.getInt(i+1);
					}

				} catch (SQLException e) {
					System.out.println("ResultSet Error.");
					e.printStackTrace();
				}
				
			} catch (SQLException e) {
				System.out.println("Statement Error.");
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			System.out.println("Connection Error.");
			e.printStackTrace();
		}

		return leagueTableArray;

	}

	@Override
	public Result ResultNew() {

		Result thisResult = null;

		if (PointsScored == PointsConceded) {

			thisResult = Result.DRAW;

		} else if (PointsScored > PointsConceded) {
			
			thisResult = Result.WIN;

		} else
			
			thisResult = Result.LOSS;

		return thisResult;
	}

	@Override
	public int MatchPointsNew(Result r) {

		switch (r) {

		case WIN: {
			if (Tries > 3) {
				MatchPoints += 5;
			} else
				MatchPoints += 4;
		} break;

		case LOSS: {
			int scoreDifference = Math.abs(PointsScored - PointsConceded);
			
			if (Tries > 3 || scoreDifference < 8) {
				MatchPoints += 1;
			}
			if (Tries > 3 && scoreDifference < 8) {
				MatchPoints += 1;
			}
		} break;

		case DRAW: {
			if (Tries > 3) {
				MatchPoints += 3;
			} else
				MatchPoints += 2;
		}
		}// end of switch
		return MatchPoints;
	}

	@Override
	public int GrandSlam(int wins) {

		if (wins == 6) {
			MatchPoints += 3;
		}
		return MatchPoints;
	}
}
