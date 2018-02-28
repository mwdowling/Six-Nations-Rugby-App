package system;


import java.util.ArrayList;
import java.util.List;

/**
 *@author Martin Dowling
 *
 * This class declares and initialises a String variable for each of 15 matches
 * and declares two "fixture position" (fp1, fp2, fp3...fp6) String variables for each match
 * 
 * Justification:
 * the number of unique combinations of n elements r at a time is n!/((n-r)!r!
 * therefore the number of unique combinations of 6 teams 2 at a time is 6*5*4*3*2*1/4*3*2*1*2 = 6*5/2 = 15
 * 
 * fifteen unique matches between teams 1, 2, 3, 4, 5 and 6:
 *		m01 = 1 vs 2;
 *		m02 = 1 vs 3;
 *		m03 = 1 vs 4;
 *		m04 = 1 vs 5;
 *		m05 = 1 vs 6;
 *		m06 = 2 vs 3;
 *		m07 = 2 vs 4;
 *		m08 = 2 vs 5;
 *		m09 = 2 vs 6;
 *		m10 = 3 vs 4;
 *		m11 = 3 vs 5;
 *		m12 = 3 vs 6;
 *		m13 = 4 vs 5;
 *		m14 = 4 vs 6;
 *		m15 = 5 vs 6;
 *		
 *  organised in such a way that each team place once in every round:
 *  
 *  round 1: m01, m10, m15
 *  round 2: m02, m09, m13
 *  round 3: m03, m08, m12
 *  round 4: m04, m06, m14
 *  round 5: m05, m07, m11
 *  
 * There are two methods in this class, setMatches() and displaySchedule()
 * 
 * displaySchedule() places each variable in a table of five rounds with three matches each organised so that 
 * each team plays one match per round each team plays every other team exactly once 
 * 
 * setMatches() takes as arguments an instance of the ITeam interface for each of the six nations
 * setMatches uses a conditional logic to get the randomly generated fixture position of each team 
 * and assign it to the appropriate match fixture position 
 * 
 */

public final class Fixture {
	
	//6 teams in random order
	private final String[] ShuffledTeams = new String[6];
	
	//15 matches
	private final String[] Match01 = {ShuffledTeams[0], ShuffledTeams[1]};
	private final String[] Match02 = {ShuffledTeams[0], ShuffledTeams[2]};
	private final String[] Match03 = {ShuffledTeams[0], ShuffledTeams[3]};
	private final String[] Match04 = {ShuffledTeams[0], ShuffledTeams[4]};
	private final String[] Match05 = {ShuffledTeams[0], ShuffledTeams[5]};
	private final String[] Match06 = {ShuffledTeams[1], ShuffledTeams[2]};
	private final String[] Match07 = {ShuffledTeams[1], ShuffledTeams[3]};
	private final String[] Match08 = {ShuffledTeams[1], ShuffledTeams[4]};
	private final String[] Match09 = {ShuffledTeams[1], ShuffledTeams[5]};
	private final String[] Match10 = {ShuffledTeams[2], ShuffledTeams[3]};
	private final String[] Match11 = {ShuffledTeams[2], ShuffledTeams[4]};
	private final String[] Match12 = {ShuffledTeams[2], ShuffledTeams[5]};
	private final String[] Match13 = {ShuffledTeams[3], ShuffledTeams[4]};
	private final String[] Match14 = {ShuffledTeams[3], ShuffledTeams[5]};
	private final String[] Match15 = {ShuffledTeams[4], ShuffledTeams[5]};
	
	
	//5 rounds
	private List<String[]> Round1 = new ArrayList<String[]>();
	private List<String[]> Round2 = new ArrayList<String[]>();
	private List<String[]> Round3 = new ArrayList<String[]>();
	private List<String[]> Round4 = new ArrayList<String[]>();
	private List<String[]> Round5 = new ArrayList<String[]>();
	
	public final void SetRounds(){
		
		Round1.add(Match01); Round1.add(Match10); Round1.add(Match15);
		Round2.add(Match02); Round2.add(Match09); Round2.add(Match13);
		Round3.add(Match03); Round3.add(Match08); Round3.add(Match12);
		Round4.add(Match04); Round4.add(Match06); Round4.add(Match14);
		Round5.add(Match05); Round5.add(Match07); Round5.add(Match11);
	}
	
	public final void WriteRounds(ArrayList<String[]> round){
		
		//write the elements of the round to a file
		
	}
	
	public final void DisplayFixture(){
		
		System.out.println("Six Nations Schedule");
		System.out.println();
		System.out.println("Round\t|\tMatchA\t\t|\tMatchB\t\t|\tMatchC");
		System.out.println("________|_______________________|_______________________|_________________");
		System.out.println("Round 1 |match01  \t\t|match10 \t\t|match15");
		System.out.println("        |" + Round1.get(0)[0] + " v " + Round1.get(0)[1] +"\t|" +  Round1.get(0)[2] + " v " + Round1.get(0)[3] + "\t|" +  Round1.get(0)[4] + " v " + Round1.get(0)[5] );
		System.out.println("________|_______________________|_______________________|_________________");
		
		System.out.println("Round 2 |match02  \t\t|match09 \t\t|match13");
		System.out.println("        |" + Round2.get(0)[0] + " v " + Round2.get(0)[1] +"\t|" +  Round2.get(0)[2] + " v " + Round2.get(0)[3] + "\t|" +  Round2.get(0)[4] + " v " + Round2.get(0)[5] );
		System.out.println("________|_______________________|_______________________|_________________");
		
		System.out.println("Round 3 |match03  \t\t|match08 \t\t|match12");
		System.out.println("        |" + Round3.get(0)[0] + " v " + Round3.get(0)[1] +"\t|" +  Round3.get(0)[2] + " v " + Round3.get(0)[3] + "\t|" +  Round3.get(0)[4] + " v " + Round3.get(0)[5] );
		System.out.println("________|_______________________|_______________________|_________________");
		
		System.out.println("round 4 |match04  \t\t|match06 \t\t|match14");
		System.out.println("        |" + Round4.get(0)[0] + " v " + Round4.get(0)[1] +"\t|" +  Round4.get(0)[2] + " v " + Round4.get(0)[3] + "\t|" +  Round4.get(0)[4] + " v " + Round4.get(0)[5] );
		System.out.println("________|_______________________|_______________________|_________________");
		
		System.out.println("round 5 |match05  \t\t|match07 \t\t|match11");
		System.out.println("        |" + Round5.get(0)[0] + " v " + Round5.get(0)[1] +"\t|" +  Round5.get(0)[2] + " v " + Round5.get(0)[3] + "\t|" +  Round5.get(0)[4] + " v " + Round5.get(0)[5] );
	}
}

	