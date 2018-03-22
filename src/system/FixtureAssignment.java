package system;

/**
 * @author Martin Dowling

 * 
 * A blueprint for an object that
 * populates the Fixture Assignment table in the Database
 * with randomly shuffled team names
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FixtureAssignment {

	private List<String> TeamsShuffled = new ArrayList<String>();


	public FixtureAssignment(ArrayList<String> teamsShuffled) {
		TeamsShuffled = teamsShuffled;

	}

	public void AssignTeams() {

		String myStatement = "INSERT INTO [Fixture Assignment] ([Team Number], [Team Name]) VALUES(?,?)";

		for (int i = 1; i <= 6; i++) {

			try (Connection conn = new DatabaseConnection().Maker()) {
				try (PreparedStatement s = conn.prepareStatement(myStatement);) {
					s.setInt(1, i);
					s.setString(2, TeamsShuffled.get(i-1));
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
				//TODO pass this exception up to the GUI?
			}
		} // end for loop
	}
}
