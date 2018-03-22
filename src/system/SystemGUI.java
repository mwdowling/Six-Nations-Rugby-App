package system;

/**
 * @author Martin Dowling
 *
 * Within a JFrame lies a navigable hierarchy of JPanels:
 * 
 *		(1) Welcome panel with links to SetFixture, EnterResults, and ViewSearch panels 
 *		(2) SetFixture panel for create a randomly generated fixture list 
 *		(3) EnterResults panel for user input of match and round results 
 *		(4) ViewSearch panel to view league table and query the tournament database
 *
 */

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import javax.swing.JTextArea;

public class SystemGUI {

	// the frame within which all panels are placed
	private JFrame frame;

	// the panels in the frame
	private JPanel JPWelcome;
	private JPanel JPEnterResults;
	private JPanel JPEnterMatchResult;
	private JPanel JPView;
	private JPanel JPSearch;

	// text fields in the panels
	private JTextField MatchNumberTextField;
	private JTextField RoundNumberTextField;
	private JTextField Team1PointsTextField;
	private JTextField Team1TriesTextField;
	private JTextField Team2PointsTextField;
	private JTextField Team2TriesTextField;
	private JTextField SQLTextField;

	// directory variable
	String DbFolder = "C:/Users/Martin/My Documents/Java Projects/SixNationsApp/";

	private final String[] Teams = { "England", "France", "Ireland", "Italia", "Scotland", "Wales" };
	ArrayList<String> TeamsShuffled = new ArrayList<String>();
	String RoundResultFile = "";
	String RoundResult = "";
	int MatchID = 0;

	// Application constructor with its huge initialize method (see below)
	public SystemGUI() {
		initialize();
	}

	// main method to launch the GUI.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SystemGUI window = new SystemGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Summary of the initialize() method: (1) initializes the frame (2)
	 * initializes the panels for the frame (3) places the panels in the frame
	 * (4) initializes buttons and labels on the panels (5) specifies behaviour
	 * of ActionListeners behind buttons (6) sets the visibility of panels
	 */
	private void initialize() {

		// initialize the frame
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		// initialize the panels
		JPWelcome = new JPanel();
		JPEnterResults = new JPanel();
		JPEnterMatchResult = new JPanel();
		JPView = new JPanel();
		JPSearch = new JPanel();
		

		// add the panels to the frame
		frame.getContentPane().add(JPWelcome);
		frame.getContentPane().add(JPEnterResults);
		frame.getContentPane().add(JPEnterMatchResult);
		frame.getContentPane().add(JPView);
		frame.getContentPane().add(JPSearch);
		
		

		
		// CODE FOR WELCOME PANEL

		// Welcome panel layout, label and buttons
		JPWelcome.setLayout(null);
		JPWelcome.setVisible(true);// default state has this panel visible
		JLabel lblWelcome = new JLabel("Welcome to the Six Nations Tournament");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcome.setBounds(60, 12, 769, 64);
		JPWelcome.add(lblWelcome);

		// Set Fixture button on Welcome panel makes SetFixture panel visible
		JButton btnWelcomeSetFixture = new JButton("Set Fixture");
		btnWelcomeSetFixture.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeSetFixture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO Warning message to prevent overwrite

				// Shuffle teams and assign to team numbers
				FixtureRandomizer fr = new FixtureRandomizer();
				TeamsShuffled = new ArrayList<String>(Arrays.asList(fr.Shuffle(Teams)));
				FixtureAssignment fa = new FixtureAssignment(TeamsShuffled);
				fa.AssignTeams();

				// Write assigned times to values in Results Table and League
				// Table
				FixtureAssignmentToTables fatt = new FixtureAssignmentToTables((ArrayList<String>) TeamsShuffled);
				fatt.Assign();

				// Display Fixture Assignment Table
				try (Connection conn = new DatabaseConnection().Maker();) {
					try (Statement s = conn.createStatement();) {
						try (ResultSet rs = s.executeQuery("SELECT * FROM [Fixture Assignment]");) {
							ResultSetMetaData rsmd = rs.getMetaData();

							String[] columns = new String[2];
							for (int i = 0; i < columns.length; i++) {
								columns[i] = rsmd.getColumnName(i + 1);
							}

							ArrayList<String[]> rows = new ArrayList<String[]>();
							while (rs.next()) {
								String[] row = new String[2];
								for (int i = 0; i < row.length; i++) {
									row[i] = rs.getString(i + 1);
								}
								;
								rows.add(row);
							}

							String[][] data = new String[6][2];
							for (int r = 0; r < rows.size(); r++) {
								for (int c = 0; c < columns.length; c++) {

									data[r][c] = rows.get(r)[c];
								}
							}
							JTable table = new JTable(data, columns);
							JScrollPane scrollPane = new JScrollPane(table);
							table.setFillsViewportHeight(true);

							JLabel lblHeading = new JLabel("Team Assignments");

							final JFrame frame = new JFrame();
							frame.getContentPane().setLayout(new BorderLayout());

							frame.getContentPane().add(lblHeading, BorderLayout.PAGE_START);
							frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

							// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							frame.setSize(550, 200);
							frame.setVisible(true);

						} catch (SQLException e1) {
							System.out.println("ResultSet Error.");
							e1.printStackTrace();
						}
					} catch (SQLException e1) {
						System.out.println("Statement Error.");
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					System.out.println("Connection Error.");
					e1.printStackTrace();
				}

			}
		});
		btnWelcomeSetFixture.setBounds(328, 110, 233, 61);
		JPWelcome.add(btnWelcomeSetFixture);

		// Enter Results Button Makes EnterResults panel visible
		JButton btnWelcomeEnterResults = new JButton("Enter Results");
		btnWelcomeEnterResults.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeEnterResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPWelcome.setVisible(false);
				JPEnterResults.setVisible(true);
			}
		});

		btnWelcomeEnterResults.setBounds(306, 213, 278, 61);
		JPWelcome.add(btnWelcomeEnterResults);

		// ViewSearch button on Welcome panel makes ViewSearch panel visible
		JButton btnWelcomeViewSearch = new JButton("View Results ");
		btnWelcomeViewSearch.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeViewSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPView.setVisible(true);
				JPWelcome.setVisible(false);
			}
		});
		btnWelcomeViewSearch.setBounds(304, 317, 281, 61);
		JPWelcome.add(btnWelcomeViewSearch);
		
		JButton btnSearchResults = new JButton(" Search Results");
		btnSearchResults.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSearchResults.setBounds(286, 416, 317, 61);
		JPWelcome.add(btnSearchResults);

		// CODE FOR ENTER RESULTS PANEL

		// Enter Results Panel layout and labels
		JPEnterResults.setLayout(null);
		JLabel lblResults = new JLabel("Enter Results");
		lblResults.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblResults.setBounds(301, 12, 287, 59);
		JPEnterResults.add(lblResults);

		// Labels and Text Fields for Input of Match Result
		JLabel label = new JLabel("Select Match Number");
		label.setFont(new Font("Tahoma", Font.PLAIN, 42));
		label.setBounds(35, 130, 408, 69);
		JPEnterResults.add(label);		
		JLabel lblOr = new JLabel("or");
		lblOr.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblOr.setBounds(205, 200, 38, 51);
		JPEnterResults.add(lblOr);

		MatchNumberTextField = new JTextField();
		MatchNumberTextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		MatchNumberTextField.setColumns(10);
		MatchNumberTextField.setBounds(453, 136, 89, 63);
		JPEnterResults.add(MatchNumberTextField);

		// Text Areas for display of team names on Match Result Panel
		JTextArea Team1TextArea = new JTextArea();
		Team1TextArea.setRows(3);
		Team1TextArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Team1TextArea.setEditable(false);
		Team1TextArea.setBounds(143, 239, 172, 39);
		JPEnterMatchResult.add(Team1TextArea);

		JTextArea Team2TextArea = new JTextArea();
		Team2TextArea.setRows(3);
		Team2TextArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Team2TextArea.setEditable(false);
		Team2TextArea.setBounds(143, 302, 175, 35);
		JPEnterMatchResult.add(Team2TextArea);

		// Button to configure and make visible select match based on its number
		JButton btnSubmitMatch = new JButton("submit");
		btnSubmitMatch.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSubmitMatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Use input from text field to find team names
				MatchID = Integer.parseInt(MatchNumberTextField.getText());
				TeamFinder teamFinder = new TeamFinder();
				// Assign team names in text areas of Enter Match Results
				Team1TextArea.append(teamFinder.TeamA(MatchID));
				Team2TextArea.append(teamFinder.TeamB(MatchID));
				
				// reset visibility
				JPEnterResults.setVisible(false);
				JPEnterMatchResult.setVisible(true);

			}
		});
		btnSubmitMatch.setBounds(559, 135, 158, 59);
		JPEnterResults.add(btnSubmitMatch);

		// Labels and Text Fields for Input of Round Result
		JLabel lblSelectRoundNumber = new JLabel("Select Round Number");
		lblSelectRoundNumber.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSelectRoundNumber.setBounds(35, 245, 408, 69);
		JPEnterResults.add(lblSelectRoundNumber);

		RoundNumberTextField = new JTextField();
		RoundNumberTextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		RoundNumberTextField.setColumns(10);
		RoundNumberTextField.setBounds(456, 251, 89, 63);
		JPEnterResults.add(RoundNumberTextField);

		JButton btnsubmitRound = new JButton("submit");
		btnsubmitRound.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnsubmitRound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPWelcome.setVisible(false);
				
				// Filechooser for Round Results
				JFileChooser JFCRoundResult = new JFileChooser();
				JFCRoundResult.setBounds(0, 0, 438, 241);
				JFCRoundResult.setCurrentDirectory(new File(DbFolder));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", "txt");
				JFCRoundResult.setFileFilter(filter);

				if (JFCRoundResult.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// assign the chosen file path to its address variable
					RoundResultFile = JFCRoundResult.getSelectedFile().getAbsolutePath();
				}

				else if (JFCRoundResult.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					JFCRoundResult.setVisible(false);
					JPWelcome.setVisible(true);
				}

				//Write file into database
				
				try {
					//update the Results Table
					Results resultsOfRound;
					resultsOfRound = new ResultsOfRound(RoundResultFile);
					resultsOfRound.WriteResult();
					
					//update the League Table
					for (int i = 0; i < 3; i++) {

						int row = i;

						Team A = new TeamA(resultsOfRound, row, resultsOfRound.ReturnResult()[row][1],
								resultsOfRound.ReturnResult()[row][2], resultsOfRound.ReturnResult()[row][3]);

						Team B = new TeamB(resultsOfRound, row, resultsOfRound.ReturnResult()[row][3],
								resultsOfRound.ReturnResult()[row][4], resultsOfRound.ReturnResult()[row][1]);

						LeagueTable leagueTable1 = new LeagueTable(resultsOfRound, row, A, B);

						leagueTable1.WriteMatchUpdate();

					}
				} catch (IOException e1) {

					e1.printStackTrace();
				}
	
			}
		});
		btnsubmitRound.setBounds(557, 250, 158, 59);
		JPEnterResults.add(btnsubmitRound);

		// Home from Enter Results Panel
		JButton btnHomeFromEnterResults = new JButton("Home");
		btnHomeFromEnterResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPEnterResults.setVisible(false);
				JPWelcome.setVisible(true);
			}
		});
		btnHomeFromEnterResults.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnHomeFromEnterResults.setBounds(693, 451, 123, 42);
		JPEnterResults.add(btnHomeFromEnterResults);
		
		// CODE FOR MATCH RESULT PANEL

		// Match Result panel and its labels
		JPEnterMatchResult.setLayout(null);
		JLabel lblMatchResult = new JLabel("Match Result");
		lblMatchResult.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblMatchResult.setBounds(314, 23, 262, 69);
		JPEnterMatchResult.add(lblMatchResult);

		// Labels for Teams, Points and Tries
		JLabel lblTeam = new JLabel("Team ");
		lblTeam.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblTeam.setBounds(143, 164, 157, 69);
		JPEnterMatchResult.add(lblTeam);

		JLabel lblPoints = new JLabel("Points");
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblPoints.setBounds(333, 173, 121, 51);
		JPEnterMatchResult.add(lblPoints);

		JLabel lblTries = new JLabel("Tries");
		lblTries.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblTries.setBounds(494, 173, 104, 51);
		JPEnterMatchResult.add(lblTries);

		// Text Fields for User input of Team 1 results
		Team1PointsTextField = new JTextField();
		Team1PointsTextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		Team1PointsTextField.setColumns(10);
		Team1PointsTextField.setBounds(333, 236, 116, 42);
		JPEnterMatchResult.add(Team1PointsTextField);

		Team1TriesTextField = new JTextField();
		Team1TriesTextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		Team1TriesTextField.setColumns(10);
		Team1TriesTextField.setBounds(482, 236, 116, 42);
		JPEnterMatchResult.add(Team1TriesTextField);

		// Text Fields for User input of Team 1 results
		Team2PointsTextField = new JTextField();
		Team2PointsTextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		Team2PointsTextField.setColumns(10);
		Team2PointsTextField.setBounds(333, 300, 116, 42);
		JPEnterMatchResult.add(Team2PointsTextField);

		Team2TriesTextField = new JTextField();
		Team2TriesTextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		Team2TriesTextField.setColumns(10);
		Team2TriesTextField.setBounds(482, 300, 116, 42);
		JPEnterMatchResult.add(Team2TriesTextField);

		// Submit Match Result button
		JButton btnSubmitMatchResult = new JButton("submit");
		btnSubmitMatchResult.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSubmitMatchResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Assign user input data to variables 
				int row = 0;
				int APoints = Integer.parseInt(Team1PointsTextField.getText());
				int ATries = Integer.parseInt(Team1TriesTextField.getText());
				int BPoints = Integer.parseInt(Team2PointsTextField.getText()); 
				int BTries = Integer.parseInt(Team2TriesTextField.getText());
				
				//Feed variables to Results constructor
				Results resultsOfMatch = new ResultsOfMatch(MatchID, APoints, ATries, BPoints, BTries);
				resultsOfMatch.WriteResult();
				
				//Feed resultOfMatch data to Team constructors
				Team A = new TeamA(resultsOfMatch, row, resultsOfMatch.ReturnResult()[row][1], 
									resultsOfMatch.ReturnResult()[row][2], 
									resultsOfMatch.ReturnResult()[row][3]);
				Team B = new TeamB(resultsOfMatch, row, resultsOfMatch.ReturnResult()[row][3], 
									resultsOfMatch.ReturnResult()[row][4], 
									resultsOfMatch.ReturnResult()[row][1]);

				LeagueTable leagueTable = new LeagueTable(resultsOfMatch, row, A, B);
				leagueTable.WriteMatchUpdate();	
				
				//reset visibility
				JPEnterMatchResult.setVisible(false);
				JPWelcome.setVisible(true);
				
			}
		});
		btnSubmitMatchResult.setBounds(366, 396, 158, 61);
		JPEnterMatchResult.add(btnSubmitMatchResult);

		// Home from MatchResult panel
		JButton btnHomeFromMatchResult = new JButton("Home");
		btnHomeFromMatchResult.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnHomeFromMatchResult.setBounds(736, 485, 141, 35);
		JPEnterMatchResult.add(btnHomeFromMatchResult);

		// CODE FOR VIEW PANEL

		// The View and Search Panel and its buttons
		JPView.setLayout(null);
		JPView.setVisible(false);
		JLabel lblViewSearch = new JLabel("View Results");
		lblViewSearch.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblViewSearch.setBounds(328, 12, 234, 51);
		JPView.add(lblViewSearch);
		
		JLabel lblRounds = new JLabel("Rounds");
		lblRounds.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblRounds.setBounds(376, 133, 137, 51);
		JPView.add(lblRounds);
		
		//The View Round Buttons display the respective round query tables in the database
		JButton btnViewRound1 = new JButton("1");
		btnViewRound1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DatabaseTable databaseTable = new DatabaseTable("SELECT * FROM [Round 1]"); 
				databaseTable.Display();
			}
		});
		btnViewRound1.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnViewRound1.setBounds(240, 202, 57, 61);
		JPView.add(btnViewRound1);


		JButton btnViewRound2 = new JButton("2");
		btnViewRound2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DatabaseTable databaseTable = new DatabaseTable("SELECT * FROM [Round 2]"); 
				databaseTable.Display();
			}
		});
		btnViewRound2.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnViewRound2.setBounds(328, 202, 57, 61);
		JPView.add(btnViewRound2);
		
		JButton btnViewRound3 = new JButton("3");
		btnViewRound3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DatabaseTable databaseTable = new DatabaseTable("SELECT * FROM [Round 3]"); 
				databaseTable.Display();
			}
		});
		btnViewRound3.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnViewRound3.setBounds(416, 202, 57, 61);
		JPView.add(btnViewRound3);

		JButton btnViewRound4 = new JButton("4");
		btnViewRound4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DatabaseTable databaseTable = new DatabaseTable("SELECT * FROM [Round 4]"); 
				databaseTable.Display();
			}
		});
		btnViewRound4.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnViewRound4.setBounds(505, 202, 57, 61);
		JPView.add(btnViewRound4);

		JButton btnViewRound5 = new JButton("5");
		btnViewRound5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DatabaseTable databaseTable = new DatabaseTable("SELECT * FROM [Round 5]"); 
				databaseTable.Display();
			}
		});
		btnViewRound5.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnViewRound5.setBounds(600, 202, 57, 61);
		JPView.add(btnViewRound5);

		// View League Table button displays league table
		JButton btnViewLeagueTable = new JButton("League Table");
		btnViewLeagueTable.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnViewLeagueTable.setBounds(304, 345, 282, 61);
		btnViewLeagueTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DatabaseTable databaseTable = new DatabaseTable("SELECT * FROM [League Table Query]"); 
				databaseTable.Display();

			}
		});
		JPView.add(btnViewLeagueTable);
		
		JButton btnGrandSlam = new JButton("Grand Slam");
		btnGrandSlam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GrandSlam grandSlam = new GrandSlam();
				grandSlam.Points(grandSlam.Team());
				
				if (grandSlam.Team() != 0){
					
					TeamFinder tf = new TeamFinder();
					
					JOptionPane.showMessageDialog(null, "THE GRAND SLAM WINNER IS. . . " + tf.Team(grandSlam.Team()));
					
				} else 
					
					JOptionPane.showMessageDialog(null, "NO GRAND SLAM WINNER");
					
				
			}
		});
		btnGrandSlam.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnGrandSlam.setBounds(304, 447, 282, 61);
		JPView.add(btnGrandSlam);


		// return home from View panel
		JButton btnHomeFromViewSearch = new JButton("Home");
		btnHomeFromViewSearch.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnHomeFromViewSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPView.setVisible(false);
				JPWelcome.setVisible(true);
			}
		});
		btnHomeFromViewSearch.setBounds(755, 505, 123, 42);
		JPView.add(btnHomeFromViewSearch);
		
		//CODE FOR SEARCH PANEL 
		
		//Search Panel and its Label
		JPSearch.setLayout(null);	
		JLabel lblEnterSqlQuery = new JLabel("Enter SQL Query: ");
		lblEnterSqlQuery.setBounds(283, 124, 337, 51);
		JPSearch.add(lblEnterSqlQuery);
		lblEnterSqlQuery.setFont(new Font("Tahoma", Font.PLAIN, 42));
				
		JLabel lblSearchResults = new JLabel("Search Results");
		lblSearchResults.setBounds(310, 31, 270, 51);
		lblSearchResults.setFont(new Font("Tahoma", Font.PLAIN, 42));
		JPSearch.add(lblSearchResults);
		
		//TextField for user input of SQL query 
		SQLTextField = new JTextField();
		SQLTextField.setBounds(55, 262, 785, 30);
		JPSearch.add(SQLTextField);
		SQLTextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		SQLTextField.setColumns(10);
				
		// Submit SQL query button
		JButton btnSubmitQuery = new JButton("submit query");
		btnSubmitQuery.setBounds(305, 355, 275, 61);
		JPSearch.add(btnSubmitQuery);
		btnSubmitQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DatabaseTable databaseTable = new DatabaseTable(SQLTextField.getText()); 
				databaseTable.Display();

			}
		});
		btnSubmitQuery.setFont(new Font("Tahoma", Font.PLAIN, 42));

	}
}
