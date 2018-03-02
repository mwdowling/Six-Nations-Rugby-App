package system;

/**
 * 
 * @author Martin Dowling
 * 
 * This class returns an object that:
 * functions at the interface between user input and the database
 * to process the user input data for a match 
 * and update the database with the processed data 
 * 
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeagueTable {

	private ResultsTable MR;
	private Team A;
	private Team B;

	public LeagueTable(ResultsTable mr, Team a, Team b) {
		MR = mr;
		A = a;
		B = b;
	}

	public void WriteMatchUpdate() {

		int[] leagueTableArray = new int[9];
		leagueTableArray = A.ResultsCurrent(MR);
		int[] leagueTableArrayNew = new int[9];
		

		// exception if result already written (Completed = yes)
		boolean played = new MatchGuard(MR.ReturnResult()[0]).WasPlayed();

		if (played) {

			System.out.println("Illegal Entry. Match has already been played");

		} else {

			// update Team A results in the League Table
			leagueTableArrayNew = A.UpdateResults(MR, leagueTableArray, A.ResultNew(), A.MatchPointsNew(A.ResultNew()));
			String update1 = "UPDATE [League Table] SET "
					+ "[Games Played] = ?, Wins = ?, Losses = ?, Draws = ?, Scored = ?, Conceded = ?, Tries = ?, Points = ?"
					+ "WHERE [Team Number] = ?";

			try (Connection conn = DriverManager.getConnection(
					"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
				try (PreparedStatement s = conn.prepareStatement(update1);) {

					s.setInt(1, leagueTableArrayNew[1]);
					s.setInt(2, leagueTableArrayNew[2]);
					s.setInt(3, leagueTableArrayNew[3]);
					s.setInt(4, leagueTableArrayNew[4]);
					s.setInt(5, leagueTableArrayNew[5]);
					s.setInt(6, leagueTableArrayNew[6]);
					s.setInt(7, leagueTableArrayNew[7]);
					s.setInt(8, leagueTableArrayNew[8]);
					s.setInt(9, leagueTableArrayNew[0]);
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
			leagueTableArray = new int[9];
			leagueTableArray = B.ResultsCurrent(MR);
			leagueTableArrayNew = new int[9];
			leagueTableArrayNew = A.UpdateResults(MR, leagueTableArray, A.ResultNew(), A.MatchPointsNew(A.ResultNew()));
			
			String update2 = "UPDATE [League Table] SET "
					+ "[Games Played] = ?, Wins = ?, Losses = ?, Draws = ?, Scored = ?, Conceded = ?, Tries = ?, Points = ?"
					+ "WHERE [Team Number] = ?";

			try (Connection conn = DriverManager.getConnection(
					"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
				try (PreparedStatement s = conn.prepareStatement(update2);) {

					s.setInt(1, leagueTableArrayNew[1]);
					s.setInt(2, leagueTableArrayNew[2]);
					s.setInt(3, leagueTableArrayNew[3]);
					s.setInt(4, leagueTableArrayNew[4]);
					s.setInt(5, leagueTableArrayNew[5]);
					s.setInt(6, leagueTableArrayNew[6]);
					s.setInt(7, leagueTableArrayNew[7]);
					s.setInt(8, leagueTableArrayNew[8]);
					s.setInt(9, leagueTableArrayNew[0]);
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
}
