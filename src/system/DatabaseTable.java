package system;

/**
 * @author Martin Dowling
 * 
 * Blueprint for an object that creates and displays a table
 * from a SQL SELECT FROM query.  
 */
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DatabaseTable {
	
	String Query;

	public DatabaseTable(String query) {
		Query = query;
	}
	
	public void Display(){
		
		try (Connection conn = new DatabaseConnection().Maker();){
			try(Statement s = conn.createStatement();){
				try(ResultSet rs = s.executeQuery(Query);){
					
					//Create first row of column names 
					ResultSetMetaData rsmd = rs.getMetaData();
					String[] columns =  new String[rsmd.getColumnCount()];
					for (int i = 0; i < columns.length; i ++){
						columns[i] = rsmd.getColumnName(i+1);
					}
					
					//Create a List of Arrays of each row
					int rowCount = 0;
					ArrayList<String[]> rows = new ArrayList<String[]>();
					while (rs.next()) {
						String[] row =  new String[rsmd.getColumnCount()];
						for (int i = 0; i < row.length; i ++){
							row[i] = rs.getString(i+1);
						};
						rows.add(row);
						rowCount++;
					}
				
					//Populate a 2D array
					String[][] data = new String [rowCount][rsmd.getColumnCount()]; 
					for (int r = 0; r < rows.size(); r++) {
						for (int c = 0; c < columns.length; c++) {
						
							data[r][c] = rows.get(r)[c];
						}	
					}
					
					//Create a JTable using the row of columns and the 2D Array
					JTable table = new JTable(data, columns);
			        JScrollPane scrollPane = new JScrollPane(table);
			        table.setFillsViewportHeight(true);
			        JLabel lblHeading = new JLabel("Six Nations Tournament 2018");
			     
			        //Place JTable in a JFrame and make it visible
			        final JFrame frame = new JFrame();
			        frame.getContentPane().setLayout(new BorderLayout());		 
			        frame.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
			        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.setSize(550, 200);
			        frame.setVisible(true);
					
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
