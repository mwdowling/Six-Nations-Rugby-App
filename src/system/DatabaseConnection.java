package system;

/**
 * @author Martin Dowling
 * 
 *  An object with a factory for making a
 *  ucanaccess JDBC connection to an MS Access database file
 *  for use throughout the system.
 *  
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	public DatabaseConnection(){
		
	}
	
	public final Connection Maker() throws SQLException{
		
		String UCan = "jdbc:ucanaccess://";
		String DbFolder = "C:/Users/Martin/My Documents/Java Projects/SixNationsApp/";
		String DbName = "Six Nations 2018.accdb";
		Connection Conn = DriverManager.getConnection(UCan + DbFolder + DbName);
		
		return Conn;
	}
}
