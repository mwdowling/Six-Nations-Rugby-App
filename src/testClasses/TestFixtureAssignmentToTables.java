package testClasses;

/**
 * @author Martin Dowling
 * 
 * A main method class that: 
 * 
 * (1) retrieves the randomised team list stored in the database table [Fixture Assignment]
 * (2) writes the team names to the appropriate rows of the [Results] and [League Table]
 * 		tables in the databse, using the hard coded team numbers in those tables
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestFixtureAssignmentToTables {

	public static void main(String[] args) {

		List<String> TeamsShuffled = new ArrayList<String>();

		String uCan = "jdbc:ucanaccess://";
		String dbFolder = "C:/Users/Martin/My Documents/Java Projects/SixNationsApp/";
		String dbName = "Six Nations 2018.accdb";
		
		// Get the Shuffled Team List from the Database
		try (Connection conn = DriverManager
				.getConnection(uCan + dbFolder + dbName);) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery("SELECT * FROM [Fixture Assignment]");) {
					// write the resultSet to an array
					while (rs.next()) {
						TeamsShuffled.add(rs.getString(1));
						System.out.println("Adding: " + rs.getString(1));
					}
				} catch (SQLException e) {
					System.out.println("ResultSet Error.");
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("Statement Error.");
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println("Connection Error.");
			e.printStackTrace();
		}

		// Use the ArrayList to Update Team Names in Results
		
		//update the names of Team1 in each match
		String update1 = "UPDATE Results SET Team1Name = ? WHERE Team1No = ?";
		for (int i = 0; i < 6; i++) {
			try (Connection conn = DriverManager
					.getConnection(uCan + dbFolder + dbName);) {
				try (PreparedStatement s = conn.prepareStatement(update1);) {
					s.setString(1, TeamsShuffled.get(i));
					s.setInt(2, i + 1);
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
		} // end for loop
		
		//update the names of Team2 in each match
		String update2 = "UPDATE Results SET Team2Name = ? WHERE Team2No = ?";	
		for (int i = 0; i < 6; i++) {
			try (Connection conn = DriverManager
					.getConnection(uCan + dbFolder + dbName);) {
				try (PreparedStatement s = conn.prepareStatement(update2);) {
					s.setString(1, TeamsShuffled.get(i));
					s.setInt(2, i + 1);
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
		} // end for loop
		
		//update the six teams in the League Table
		String update3 = "UPDATE [League Table] SET [Team Name] = ? WHERE [Team Number] = ?";
		for (int i = 0; i < 6; i++) {

			try (Connection conn = DriverManager
					.getConnection(uCan + dbFolder + dbName);) {
				try (PreparedStatement s = conn.prepareStatement(update3);) {
					s.setString(1, TeamsShuffled.get(i));
					s.setInt(2, i + 1);
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
		}// end for loop
	}
}
