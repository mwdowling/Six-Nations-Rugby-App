package testClasses;

/**
 * @author Martin Dowling

 * 
 * A main method class which does the following: 
 * 
 * 	(1) assigns team names randomly to team numbers.
 * 
 * 	(2) Writes the names to the appropriate rows of 
 * 		the reference table [Fixture Assignment] in the database
 * 		where the team numbers are hard coded 
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import system.FixtureRandomizer;

public class TestDatabaseTeamNoAssignment {

	public static void main(String[] args) {

		final String[] Teams = { "England", "France", "Ireland", "Italia", "Scotland", "Wales" };
		String[] TeamsShuffled;
		FixtureRandomizer fr = new FixtureRandomizer();
		TeamsShuffled = fr.Shuffle(Teams);

		for (int i = 0; i < TeamsShuffled.length; i++) {
			System.out.print(TeamsShuffled[i] + ", ");
		}

		String myStatement = "INSERT INTO [Fixture Assignment] ([Team Number], [Team Name]) VALUES(?,?)";
		
		for (int i = 1; i <= 6; i++) {

			try (Connection conn = DriverManager
					.getConnection("jdbc:ucanaccess://C:/Users/Martin/My Documents/Six Nations.accdb");) {
				try (PreparedStatement s = conn.prepareStatement(myStatement);) {
					s.setInt(1, i);
					s.setString(2, TeamsShuffled[i - 1]);
					s.executeUpdate();
					s.close();
				} catch (SQLException e) {
					System.out.println("Error with PreparedStatement.");
					e.printStackTrace();
				}
				conn.close();

			} catch (SQLException e) {
				System.out.println("Error with Connection.");
				e.printStackTrace();
			}
		}//end for loop
	}
}
