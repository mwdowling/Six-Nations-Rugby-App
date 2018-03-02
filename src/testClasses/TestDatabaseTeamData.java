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
import system.MatchGuard;
import system.ResultsTable;
import system.Team;
import system.TeamA;
import system.TeamB;

public class TestDatabaseTeamData {

	public static void main(String[] args) {

		// Object returning user input and writing to Results Table
		
		ResultsTable rt = new ResultsTable(1, 41, 3, 46, 1);
		rt.WriteResult();

		//Guard against overwriting a match result already recorded
		boolean played = new MatchGuard(rt.ReturnResult()[0]).WasPlayed();
		if (played) {
			System.out.println("Illegal Entry. Match has already been played");
		} else

		// RESULTS FOR TEAM A
		{
			Team A = new TeamA(rt.ReturnResult()[1], rt.ReturnResult()[2], rt.ReturnResult()[3]);
			int[] leagueTableArray = new int[9];
			String[] leagueTableFieldsArray = new String[9];
			int[] leagueTableArrayNew = new int[9];

			try (Connection conn = DriverManager.getConnection(
					"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
				try (Statement s = conn.createStatement();) {
					try (ResultSet rs = s.executeQuery("SELECT * FROM [League Table Update] WHERE [Team Number] = "
							+ A.TeamNo(rt.ReturnResult()[0]))) {
						ResultSetMetaData rsmd = rs.getMetaData();
						// print an array of column names from metadata to console
						for (int i = 0; i < leagueTableFieldsArray.length; i++) {
							leagueTableFieldsArray[i] = rsmd.getColumnName(i + 1);
						}
						System.out.println(leagueTableFieldsArray[0] + "   \t" + leagueTableFieldsArray[1] + "\t"
								+ leagueTableFieldsArray[2] + "\t" + leagueTableFieldsArray[3] + "\t"
								+ leagueTableFieldsArray[4] + "\t" + leagueTableFieldsArray[5] + "\t"
								+ leagueTableFieldsArray[6] + "\t" + leagueTableFieldsArray[7] + "\t"
								+ leagueTableFieldsArray[8] + "\t");

						// load and print leage table array 
						rs.next();
						for (int i = 0; i < leagueTableArray.length; i++) {
							leagueTableArray[i] = rs.getInt(i + 1);
						}

						System.out.println(leagueTableArray[0] + "   \t\t" + leagueTableArray[1] + "\t\t"
								+ leagueTableArray[2] + "\t" + leagueTableArray[3] + "\t" + leagueTableArray[4] + "\t"
								+ leagueTableArray[5] + "\t" + leagueTableArray[6] + "\t\t" + leagueTableArray[7] + "\t"
								+ leagueTableArray[8] + "\t");

						// update the league table array
						leagueTableArrayNew = A.UpdateResults(rt, leagueTableArray, A.ResultNew(),
								A.MatchPointsNew(A.ResultNew()));

						// print updated league table data for team 1
						System.out.println(leagueTableArrayNew[0] + "   \t\t" + leagueTableArrayNew[1] + "\t\t"
								+ leagueTableArrayNew[2] + "\t" + leagueTableArrayNew[3] + "\t" + leagueTableArrayNew[4]
								+ "\t" + leagueTableArrayNew[5] + "\t" + leagueTableArrayNew[6] + "\t\t"
								+ leagueTableArrayNew[7] + "\t" + leagueTableArrayNew[8] + "\t");

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

			// RESULTS FOR TEAM B
			Team B = new TeamB(rt.ReturnResult()[3], rt.ReturnResult()[4], rt.ReturnResult()[1]);
			leagueTableArray = new int[9];
			leagueTableFieldsArray = new String[9];
			leagueTableArrayNew = new int[9];

			try (Connection conn = DriverManager.getConnection(
					"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
				try (Statement s = conn.createStatement();) {
					try (ResultSet rs = s.executeQuery("SELECT * FROM [League Table Update] WHERE [Team Number] = "
							+ B.TeamNo(rt.ReturnResult()[0]))) {
						ResultSetMetaData rsmd = rs.getMetaData();
						// print column names from metadata
						for (int i = 0; i < leagueTableFieldsArray.length; i++) {
							leagueTableFieldsArray[i] = rsmd.getColumnName(i + 1);
						}
						System.out.println(leagueTableFieldsArray[0] + "   \t" + leagueTableFieldsArray[1] + "\t"
								+ leagueTableFieldsArray[2] + "\t" + leagueTableFieldsArray[3] + "\t"
								+ leagueTableFieldsArray[4] + "\t" + leagueTableFieldsArray[5] + "\t"
								+ leagueTableFieldsArray[6] + "\t" + leagueTableFieldsArray[7] + "\t"
								+ leagueTableFieldsArray[8] + "\t");

						// print loaded array (identical)
						rs.next();
						for (int i = 0; i < leagueTableArray.length; i++) {
							leagueTableArray[i] = rs.getInt(i + 1);
						}

						System.out.println(leagueTableArray[0] + "   \t\t" + leagueTableArray[1] + "\t\t"
								+ leagueTableArray[2] + "\t" + leagueTableArray[3] + "\t" + leagueTableArray[4] + "\t"
								+ leagueTableArray[5] + "\t" + leagueTableArray[6] + "\t\t" + leagueTableArray[7] + "\t"
								+ leagueTableArray[8] + "\t");

						// update the league table array
						leagueTableArrayNew = B.UpdateResults(rt, leagueTableArray, B.ResultNew(),
								B.MatchPointsNew(B.ResultNew()));

						// print updated league table data for team 1
						System.out.println(leagueTableArrayNew[0] + "   \t\t" + leagueTableArrayNew[1] + "\t\t"
								+ leagueTableArrayNew[2] + "\t" + leagueTableArrayNew[3] + "\t" + leagueTableArrayNew[4]
								+ "\t" + leagueTableArrayNew[5] + "\t" + leagueTableArrayNew[6] + "\t\t"
								+ leagueTableArrayNew[7] + "\t" + leagueTableArrayNew[8] + "\t");

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