package system;

/**
 * @author Martin Dowling
 * 
 * A class that returns an object which 
 * takes user input match data and provides methods 
 * to output an array of that data
 * to write that data to the Results Table in the database
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultsOfMatch implements Results {

	//variables for user input of match results
	private int MatchID;
	private int APoints;
	private int ATries;
	private int BPoints;
	private int BTries;
	
	//constructor for user input of match results
	public ResultsOfMatch(int matchID, int aPoints, int aTries, int bPoints, int bTries) {

		MatchID = matchID;
		APoints = aPoints;
		ATries = aTries;
		BPoints = bPoints;
		BTries = bTries;
	}

	@Override
	public int[][] ReturnResult() {
	

		int[][] resultArray = new int[1][5];
		resultArray[0][0] = MatchID;
		resultArray[0][1] = APoints;
		resultArray[0][2] = ATries;
		resultArray[0][3] = BPoints;
		resultArray[0][4] = BTries;

		return resultArray;

	}


	@Override
	public void WriteResult() {

		// exception if result already written (Completed = yes)

		boolean played = new MatchGuard(MatchID).WasPlayed();

		if (played) {

			System.out.println("Illegal Entry. Match has already been played");

		} else {
			// write the output of the above method to Results table row = Match
			// ID;
			String update = "UPDATE Results SET "
					+ "Complete = ?, [Team1 Score] = ?, [Team1 Tries] = ?, [Team2 Score] = ?, [Team2 Tries] = ?"
					+ "WHERE Match = ?";

			try (Connection conn = new DatabaseConnection().Maker();) {
				try (PreparedStatement s = conn.prepareStatement(update);) {

					s.setBoolean(1, true);
					s.setInt(2, APoints);
					s.setInt(3, ATries);
					s.setInt(4, BPoints);
					s.setInt(5, BTries);
					s.setInt(6, MatchID);

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
