package testClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabaseUpdate {

	public static void main(String[] args) {
		
		int TeamNum = 1;
		String Team = "'Wales'";
		int TeamPoints;
		
		//initialise connection, statement, and resultSet for current points for selected team
		String query1 = "SELECT Points FROM [League Table] WHERE TeamName = " + Team;
		String query2 = "SELECT Points FROM [League Table] WHERE TeamNo = " + TeamNum;
		try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");){
			try(Statement s = conn.createStatement();){
				try(ResultSet rs = s.executeQuery(query2);){

					rs.next();
					System.out.println(rs.getInt(1));
					TeamPoints = rs.getInt(1);
				
				}catch (SQLException e) {
					System.out.println("ResultSet Error.");
					e.printStackTrace();
				}			
			}catch (SQLException e) {
				System.out.println("Statement Error.");
				e.printStackTrace();
			}		
		}catch (SQLException e) {
			System.out.println("Connection Error.");
			e.printStackTrace();
		}	
	}
	
	//Calculate new result for selected team
	/*
	 * What's the strategy?
	 * Match has a number and two teams
	 * But the two TeamNumbers are hardcoded in the Results table
	 * Match m = new Match(int number){
	 * 		
	 * 
	 */

}
