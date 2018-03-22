package system;

/**
 * @author Martin Dowling
 * 
 * A blueprint for an object 
 * that find the names of 
 * Team 1 and Team 2 in a given match
 * 
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeamFinder {


	public TeamFinder() {

	}

	//find Team A for a given Match
	public String TeamA(int match) {

		String team = "X";
		//SQL query returns Team A of match
		String TeamA = "SELECT Team1Name FROM Results WHERE Match =" + match;

		try (Connection conn = new DatabaseConnection().Maker();) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery(TeamA);) {
					rs.next();
					team = rs.getNString(1);
					s.close();

				} catch (SQLException e) {
					System.out.println("Error with Statement.");
					e.printStackTrace();
				}

			} catch (SQLException e) {
				System.out.println("Error with Connection.");
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return team;
	}

	//Find Team B for a given Match
	public String TeamB(int match) {

		String team = "X";
		//SQL query returns Team A of match
		String TeamB = "SELECT Team2Name FROM Results WHERE Match =" + match;

		try (Connection conn = new DatabaseConnection().Maker();) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery(TeamB);) {
					rs.next();
					team = rs.getNString(1);
					s.close();

				} catch (SQLException e) {
					System.out.println("Error with Statement.");
					e.printStackTrace();
				}

			} catch (SQLException e) {
				System.out.println("Error with Connection.");
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return team;
	}
	
	//Find a team name from a given team number
	public String Team(int TeamNo){
		
		String team = "X";
		String query = "SELECT [Team Name] FROM Fixture Assignment] WHERE [Team Number] =" + TeamNo;

		try (Connection conn = new DatabaseConnection().Maker();) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery(query);) {
					rs.next();
					team = rs.getNString(1);
					s.close();

				} catch (SQLException e) {
					System.out.println("Error with Statement.");
					e.printStackTrace();
				}

			} catch (SQLException e) {
				System.out.println("Error with Connection.");
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return team;
		
	}
}
