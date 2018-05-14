package system;

/**
 * @author Martin Dowling
 * 
 * A blueprint fot an object that
 * populates the Results Table and League Table in the Database
 * with the team names that have been randomly 
 * assigned to team numbers
 * 
 * @see FixtureAssignment
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FixtureAssignmentToTables {

	List<String> TeamsShuffled = new ArrayList<String>();

	public FixtureAssignmentToTables(List<String> teamsShuffled) {
		TeamsShuffled = teamsShuffled;
	}

	public void Assign() {

		// Get the Shuffled Team List from the Database
		try (Connection conn = new DatabaseConnection().Maker();) {
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
		} catch (SQLException e) {
			System.out.println("Connection Error.");
			e.printStackTrace();
		}

		// Use the ArrayList to Update Team Names in Results

		// update the names of Team1 in each match
		String update1 = "UPDATE Results SET Team1Name = ? WHERE Team1No = ?";
		for (int i = 0; i < 6; i++) {
			try (Connection conn = new DatabaseConnection().Maker();) {
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

		// update the names of Team2 in each match
		String update2 = "UPDATE Results SET Team2Name = ? WHERE Team2No = ?";
		for (int i = 0; i < 6; i++) {
			try (Connection conn = new DatabaseConnection().Maker();) {
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

		// update the six teams in the League Table
		String update3 = "UPDATE [League Table] SET [Team Name] = ? WHERE [Team Number] = ?";
		for (int i = 0; i < 6; i++) {

			try (Connection conn = new DatabaseConnection().Maker();) {
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
		} // end for loop
	}
}
