package testClasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import system.Results;
import system.ResultsOfMatch;
import system.ResultsOfRound;

public class TestResultsTable {

	public static void main(String[] args) throws IOException {

		String DbFolder = "C:/Users/Martin/My Documents/Java Projects/SixNationsApp/";
		String Source = "Round1Result.txt";
		
		//Matches 1, 2, and 3
		Results rtRound = new ResultsOfRound(DbFolder + Source);
		rtRound.WriteResult();
		
		//Match 4
		Results rtMatch = new ResultsOfMatch(4, 41, 3, 46, 1);
		rtMatch.WriteResult();


	}

}
