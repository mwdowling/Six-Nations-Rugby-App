package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GrandSlam {
	
	
	public GrandSlam() {
	
	}

	//returns Team Number of a Team with six wins or 0
	public int Team() {

		int TeamNo = 0; 

		try (Connection conn = new DatabaseConnection().Maker();) {
			try (Statement s = conn.createStatement();) {
				try (ResultSet rs = s.executeQuery("SELECT [Team Number], Wins FROM [League Table]")) {
				
					while(rs.next()){
						
						if (rs.getInt(2) == 5){
							
							TeamNo = rs.getInt(1);
						}						
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

		return TeamNo;

	}

	
	//Writes Grand Slam REsult to League Table if Team Number != 0	
	public void Points(int TeamNo) {

		int CurrentPoints = 0;
		
		if (TeamNo != 0) {
			
			try (Connection conn = new DatabaseConnection().Maker();) {
				try (Statement s = conn.createStatement();) {
					try (ResultSet rs = s.executeQuery("SELECT Points FROM [League Table] WHERE [Team Number] = " + TeamNo)) {
					
						rs.next();
						CurrentPoints = rs.getInt(1);
								

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
			
			String update1 = "UPDATE [League Table] SET [Grand Slam] = ?, Points = ?"
					+ "WHERE [Team Number] = ?";

			try (Connection conn = new DatabaseConnection().Maker()) {
				try (PreparedStatement s = conn.prepareStatement(update1);) {

					s.setBoolean(1, true);
					s.setInt(2, CurrentPoints + 3);
					s.setInt(3, TeamNo);
					s.executeUpdate();
					s.close();

				} catch (SQLException e) {
					System.out.println("Error with Statement.");
					e.printStackTrace();
				}

			} catch (SQLException e) {
				System.out.println("Error with Connection.");
				e.printStackTrace();
			}
		}		
	}
}
