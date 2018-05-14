package system;

/**
 * 
 * @author Martin Dowling
 * 
 * This class returns an object that
 * updates the League Table in the Database
 * using information given by a Results object
 * and the behaviours of the two opposing team objects 
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeagueTable {

	private Results R;
	private int MatchID;
	private int Row;
	private Team A;
	private Team B;

	public LeagueTable(Results r, int row, Team a, Team b) {
		R = r;
		Row = row;
		MatchID = R.ReturnResult()[Row][0];
		A = a;
		B = b;	
	}

	public void WriteMatchUpdate() {

		int[] leagueTableArray = new int[8];
		leagueTableArray = A.ResultsCurrent();
		int[] leagueTableArrayNew = new int[8];

		// update Team A results in the League Table
		leagueTableArrayNew = A.UpdateResults(leagueTableArray, A.ResultNew(), A.MatchPointsNew(A.ResultNew()));

		String update1 = "UPDATE [League Table] SET "
				+ "[Games Played] = ?, Wins = ?, Losses = ?, Draws = ?, Scored = ?, Conceded = ?, Tries = ?, Points = ?"
				+ "WHERE [Team Number] = ?";

		try (Connection conn = new DatabaseConnection().Maker()) {
			try (PreparedStatement s = conn.prepareStatement(update1);) {

				s.setInt(1, leagueTableArrayNew[0]);
				s.setInt(2, leagueTableArrayNew[1]);
				s.setInt(3, leagueTableArrayNew[2]);
				s.setInt(4, leagueTableArrayNew[3]);
				s.setInt(5, leagueTableArrayNew[4]);
				s.setInt(6, leagueTableArrayNew[5]);
				s.setInt(7, leagueTableArrayNew[6]);
				s.setInt(8, leagueTableArrayNew[7]);
				s.setInt(9, A.TeamNo(MatchID));
				s.executeUpdate();
				s.close();

			} catch (SQLException e) {
				System.out.println("Error with Statement.");
				e.printStackTrace();
			}
			conn.close();

		} catch (SQLException e) {
			System.out.println("Error with Connection.");
			e.printStackTrace();
		}

		// update Team B results in the League Table
		leagueTableArray = new int[8];
		leagueTableArray = B.ResultsCurrent();
		leagueTableArrayNew = new int[8];
		leagueTableArrayNew = B.UpdateResults(leagueTableArray, B.ResultNew(), B.MatchPointsNew(B.ResultNew()));

		String update2 = "UPDATE [League Table] SET "
				+ "[Games Played] = ?, Wins = ?, Losses = ?, Draws = ?, Scored = ?, Conceded = ?, Tries = ?, Points = ?"
				+ "WHERE [Team Number] = ?";

		try (Connection conn = new DatabaseConnection().Maker();) {
			try (PreparedStatement s = conn.prepareStatement(update2);) {

				s.setInt(1, leagueTableArrayNew[0]);
				s.setInt(2, leagueTableArrayNew[1]);
				s.setInt(3, leagueTableArrayNew[2]);
				s.setInt(4, leagueTableArrayNew[3]);
				s.setInt(5, leagueTableArrayNew[4]);
				s.setInt(6, leagueTableArrayNew[5]);
				s.setInt(7, leagueTableArrayNew[6]);
				s.setInt(8, leagueTableArrayNew[7]);
				s.setInt(9, B.TeamNo(MatchID));
				s.executeUpdate();
				s.close();

			} catch (SQLException e) {
				System.out.println("Error with Statement.");
				e.printStackTrace();
			}
			conn.close();

		} catch (SQLException e) {
			System.out.println("Error with Connection.");
			e.printStackTrace();
		}
	}
}
