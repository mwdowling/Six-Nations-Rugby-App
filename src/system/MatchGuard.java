package system;

/**
 *@author Martin Dowling
 * A class returning on object to guard the system
 * against overwriting a the results of a match that has 
 * already been written to the database, using the Complete field in the Results Table.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MatchGuard {

	private int MatchID;

	public MatchGuard(int matchID) {
		MatchID = matchID;
	}

	public boolean WasPlayed() {
		
		boolean played = false; 
		
		try (Connection conn = DriverManager.getConnection(
				"jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery("SELECT Complete FROM [Results] WHERE Match = " + MatchID)) {

					rs.next();
					played = rs.getBoolean(1);

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
		return played;
	}
}
