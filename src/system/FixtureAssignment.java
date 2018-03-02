package system;

/**
 * @author Martin Dowling
 * 
 * A class that returns an object which
 * populates the Fixture Assignment table
 * with randomly shuffled team names
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FixtureAssignment {

	private final String[] Teams = { "England", "France", "Ireland", "Italia", "Scotland", "Wales" };
	private String[] TeamsShuffled;

	public FixtureAssignment(String[] teamsShuffled, FixtureRandomizer fr) {
		TeamsShuffled = fr.Shuffle(Teams);
	}

	public void AssignTeams() {

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
		} // end for loop
	}
}
