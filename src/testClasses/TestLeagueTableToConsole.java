package testClasses;

/**
 * 
 * @author Martin DOwling
 * 
 * A main method class to test 
 * updating an array of league table data
 * for the two specific teams (identified by TeamNo field)
 * in a specific match (identified by the ResultsTable object)
 * 
 * The elements of the array correspond to 
 * selected fields in the Database League Table
 * stored in the query League Table Update:
 * 
 * 0 TeamNo
 * 1 GamesPlayed 
 * 2 Wins
 * 3 Losses
 * 4 Draws
 * 5 Scored
 * 6 Conceded
 * 7 Tries
 * 8 TotalPoints
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import system.Results;
import system.ResultsOfMatch;
import system.Team;
import system.TeamA;
import system.TeamB;

public class TestLeagueTableToConsole {

	public static void main(String[] args) {

		// Object returning user input and writing to Results Table

		Results rt = new ResultsOfMatch(1, 41, 3, 46, 1);
		rt.WriteResult();

		// RESULTS FOR TEAM A

		Team A = new TeamA(rt, 0, rt.ReturnResult()[0][1], rt.ReturnResult()[0][2], rt.ReturnResult()[0][3]);
		int[] leagueTableArray = new int[8];
		String[] leagueTableFieldsArray = new String[8];
		int[] leagueTableArrayNew = new int[8];

		try (Connection conn = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery("SELECT [Games Played], Wins, Losses, Draws, Scored, Conceded, Tries, Points FROM [League Table] WHERE [Team Number] = "
						+ A.TeamNo(rt.ReturnResult()[0][0]))) {
					ResultSetMetaData rsmd = rs.getMetaData();
					// print an array of column names from metadata to console
					for (int i = 0; i < leagueTableFieldsArray.length; i++) {
						leagueTableFieldsArray[i] = rsmd.getColumnName(i + 1);
					}
					System.out.println(leagueTableFieldsArray[0] + "   \t" + leagueTableFieldsArray[1] + "\t"
							+ leagueTableFieldsArray[2] + "\t" + leagueTableFieldsArray[3] + "\t"
							+ leagueTableFieldsArray[4] + "\t" + leagueTableFieldsArray[5] + "\t"
							+ leagueTableFieldsArray[6] + "\t" + leagueTableFieldsArray[7] + "\t");
							//+ leagueTableFieldsArray[8] + "\t");

					// load and print league table array
					rs.next();
					for (int i = 0; i < leagueTableArray.length; i++) {
						leagueTableArray[i] = rs.getInt(i + 1);
					}

					System.out.println(leagueTableArray[0] + "   \t\t" + leagueTableArray[1] + "\t\t"
							+ leagueTableArray[2] + "\t" + leagueTableArray[3] + "\t" + leagueTableArray[4] + "\t"
							+ leagueTableArray[5] + "\t" + leagueTableArray[6] + "\t\t" + leagueTableArray[7] + "\t");

					// update the league table array
					leagueTableArrayNew = A.UpdateResults(leagueTableArray, A.ResultNew(),
							A.MatchPointsNew(A.ResultNew()));

					// print updated league table data for team 1
					System.out.println(leagueTableArrayNew[0] + "   \t\t" + leagueTableArrayNew[1] + "\t"
							+ leagueTableArrayNew[2] + "\t" + leagueTableArrayNew[3] + "\t" + leagueTableArrayNew[4]
							+ "\t" + leagueTableArrayNew[5] + "\t" + leagueTableArrayNew[6] + "\t\t"
							+ leagueTableArrayNew[7]);

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
		System.out.println();

		// write Team A results to the League Table
		String update = "UPDATE [League Table] SET "
				+ "[Games Played] = ?, Wins = ?, Losses = ?, Draws = ?, Scored = ?, Conceded = ?, Tries = ?, Points = ?"
				+ "WHERE [Team Number] = ?";

		try (Connection conn = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
			try (PreparedStatement s = conn.prepareStatement(update);) {

				s.setInt(1, leagueTableArrayNew[0]);
				s.setInt(2, leagueTableArrayNew[1]);
				s.setInt(3, leagueTableArrayNew[2]);
				s.setInt(4, leagueTableArrayNew[3]);
				s.setInt(5, leagueTableArrayNew[4]);
				s.setInt(6, leagueTableArrayNew[5]);
				s.setInt(7, leagueTableArrayNew[6]);
				s.setInt(8, leagueTableArrayNew[7]);
				s.setInt(9, A.TeamNo(rt.ReturnResult()[0][0]));
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

		// RESULTS FOR TEAM B
		Team B = new TeamB(rt, 0, rt.ReturnResult()[0][3], rt.ReturnResult()[0][4], rt.ReturnResult()[0][1]);
		leagueTableArray = new int[8];
		leagueTableFieldsArray = new String[8];
		leagueTableArrayNew = new int[8];

		try (Connection conn = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery("SELECT [Games Played], Wins, Losses, Draws, Scored, Conceded, Tries, Points  FROM [League Table] WHERE [Team Number] = "
						+ B.TeamNo(rt.ReturnResult()[0][0]))) {
					ResultSetMetaData rsmd = rs.getMetaData();
					// print column names from metadata
					for (int i = 0; i < leagueTableFieldsArray.length; i++) {
						leagueTableFieldsArray[i] = rsmd.getColumnName(i + 1);
					}
					System.out.println(leagueTableFieldsArray[0] + "   \t" + leagueTableFieldsArray[1] + "\t"
							+ leagueTableFieldsArray[2] + "\t" + leagueTableFieldsArray[3] + "\t"
							+ leagueTableFieldsArray[4] + "\t" + leagueTableFieldsArray[5] + "\t"
							+ leagueTableFieldsArray[6] + "\t" + leagueTableFieldsArray[7] + "\t");

					// print loaded array (identical)
					rs.next();
					for (int i = 0; i < leagueTableArray.length; i++) {
						leagueTableArray[i] = rs.getInt(i + 1);
					}

					System.out.println(leagueTableArray[0] + "   \t\t" + leagueTableArray[1] + "\t\t"
							+ leagueTableArray[2] + "\t" + leagueTableArray[3] + "\t" + leagueTableArray[4] + "\t"
							+ leagueTableArray[5] + "\t" + leagueTableArray[6] + "\t\t" + leagueTableArray[7] + "\t");

					// update the league table array
					leagueTableArrayNew = B.UpdateResults(leagueTableArray, B.ResultNew(),
							B.MatchPointsNew(B.ResultNew()));

					// print updated league table data for team 1
					System.out.println(leagueTableArrayNew[0] + "   \t\t" + leagueTableArrayNew[1] + "\t"
							+ leagueTableArrayNew[2] + "\t" + leagueTableArrayNew[3] + "\t" + leagueTableArrayNew[4]
							+ "\t" + leagueTableArrayNew[5] + "\t" + leagueTableArrayNew[6] + "\t\t"
							+ leagueTableArrayNew[7]);

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

		// update Team B results in the League Table
		String update2 = "UPDATE [League Table] SET "
				+ "[Games Played] = ?, Wins = ?, Losses = ?, Draws = ?, Scored = ?, Conceded = ?, Tries = ?, Points = ?"
				+ "WHERE [Team Number] = ?";

		try (Connection conn = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
			try (PreparedStatement s = conn.prepareStatement(update2);) {

				s.setInt(1, leagueTableArrayNew[0]);
				s.setInt(2, leagueTableArrayNew[1]);
				s.setInt(3, leagueTableArrayNew[2]);
				s.setInt(4, leagueTableArrayNew[3]);
				s.setInt(5, leagueTableArrayNew[4]);
				s.setInt(6, leagueTableArrayNew[5]);
				s.setInt(7, leagueTableArrayNew[6]);
				s.setInt(8, leagueTableArrayNew[7]);
				s.setInt(9, B.TeamNo(rt.ReturnResult()[0][0]));
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