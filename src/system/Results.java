package system;

/**
 * 
 * @author Martin Dowling
 * 
 * An interface to provide a polymorphic reference
 * to the objects which us the outputs of either
 * the ResultsOfMatch or
 * the ResultsOfRound.
 * 
 * Both of these objects return a 2D array
 * of results (though the ResultsOfMatch only uses 1 dimension)
 * 
 * Both of these objects write match data 
 * to the Results Table in the Database.
 *
 */
public interface Results {
	
	int[][] ReturnResult();
	void WriteResult();

}
