package system;

/**
 * @author Martin Dowling
 * 
 * A class that returns an object which 
 * takes round data from a tab delimited file
 * with one line for each of three matches in a round
 * and provides methods to 
 * output an array of that data
 * and write that data to the Results Table in the database
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ResultsOfRound implements Results{
	
		//variables for round results from file
		private String RoundResult;
		String[][] ResultArray = new String[3][5];

		//constructor for round results from file
		public ResultsOfRound(String roundResultFile) throws IOException {
			RoundResult = new String(Files.readAllBytes(Paths.get(roundResultFile)));
		}
		
		@Override
		public int[][] ReturnResult() {
			
			int r = 3;
			int c = 5; 
			int[][] resultArray = new int[r][c];
			Scanner scanner = new Scanner(RoundResult);
			
			for (r = 0; r < 3; r++) {
				
				String line = scanner.nextLine();
				String[] lineArray = line.split("\t");
				
				for (c = 0; c < 5; c++) {

					resultArray[r][c] = Integer.parseInt(lineArray[c]);
				}
			}
			scanner.close();
			return resultArray;
			
		}

		@Override
		public void WriteResult(){
			
			// write the output of the above method to Results table row = Match ID;
			String update = "UPDATE Results SET "
					+ "Complete = ?, [Team1 Score] = ?, [Team1 Tries] = ?, [Team2 Score] = ?, [Team2 Tries] = ?"
					+ "WHERE Match = ?";

			Scanner scanner = new Scanner(RoundResult);
			

			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				String[] lineArray = line.split("\t");
				System.out.println(lineArray[0]);
				
				try (Connection conn = new DatabaseConnection().Maker();) {
					try (PreparedStatement s = conn.prepareStatement(update);) {

						s.setBoolean(1, true);
						s.setInt(2, Integer.parseInt(lineArray[1]));
						s.setInt(3, Integer.parseInt(lineArray[2]));
						s.setInt(4, Integer.parseInt(lineArray[3]));
						s.setInt(5, Integer.parseInt(lineArray[4]));
						s.setInt(6, Integer.parseInt(lineArray[0]));
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
			scanner.close();
		}
}
