package system;

/**
 * @author Martin Dowling
 * 
 * A main method class that displays database queries 
 * in the console.
 * 
 * Two queries displayed here: 
 * 
 * (1) current league table
 * (2) round 1
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabaseDisplay {

	public static void main(String[] args) {

		
		//initialise connection, statement, and resultSet for League Table Query
		try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");){
			try(Statement s = conn.createStatement();){
				try(ResultSet rs = s.executeQuery("SELECT * FROM [League Table Query]");){
					ResultSetMetaData rsmd = rs.getMetaData();
					//print column names from metadata
					
					System.out.print(rsmd.getColumnName(1)+ "\t\t");	
					System.out.print(rsmd.getColumnName(2)+ "\t");	
					System.out.print(rsmd.getColumnName(3)+ "\t");	
					System.out.print(rsmd.getColumnName(4)+ "\t");	
					System.out.print(rsmd.getColumnName(5)+ "\t");	
					System.out.print(rsmd.getColumnName(6)+ "\t");	
					System.out.print(rsmd.getColumnName(7)+ "\t");	
					System.out.print(rsmd.getColumnName(8)+ "\t");
					System.out.print(rsmd.getColumnName(9)+ "\t");	
					System.out.println();
			
					//print the resultSet
					while (rs.next()) {
			
						System.out.println(rs.getString(1) + "   \t\t" 
											+ rs.getString(2)+ "\t" 
											+ rs.getString(3)+ "\t" 
											+ rs.getString(4)+ "\t"
											+ rs.getString(5)+ "\t"
											+ rs.getString(6)+ "\t\t"
											+ rs.getString(7)+ "\t\t"
											+ rs.getString(8)+ "\t"
											+ rs.getString(9)+ "\t");
					}					
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

		System.out.println();
		System.out.println();
		
		//initialise connection, statement, and resultSet for Round 1
		try(Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Martin/My Documents/Java Projects/SixNationsApp/Six Nations 2018.accdb");) {
			try(Statement s = conn.createStatement();){
				// test queries for all 5 rounds
				try(ResultSet rs = s.executeQuery("SELECT * FROM [Round 1]");){
					
					ResultSetMetaData rsmd = rs.getMetaData();
					
					//print column names from metadata
					System.out.print(rsmd.getColumnName(1)+ "\t");	
					System.out.print(rsmd.getColumnName(2)+ "\t");	
					System.out.print(rsmd.getColumnName(3)+ "\t\t");	
					System.out.print(rsmd.getColumnName(4)+ "\t");	
					System.out.print(rsmd.getColumnName(5)+ "\t\t");	
					System.out.print(rsmd.getColumnName(6)+ "\t");	

					System.out.println();
			
					//print the resultSet
					while (rs.next()) {
			
						System.out.println(rs.getString(1) + "\t" 
											+ rs.getString(2)+ "\t\t" 
											+ rs.getString(3)+ "   \t\t" 
											+ rs.getString(4)+ "\t\t"
											+ rs.getString(5)+ "   \t\t"
											+ rs.getString(6)+ "\t");
					}				
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
}
