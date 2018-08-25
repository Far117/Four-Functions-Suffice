package main;

public class Main
{
	/**
	 * Searches an array of Strings for a query string, returning its
	 * index if found, or -1 if it isn't.
	 * <br><br>
	 * 
	 * Used for parsing the program's input arguments.
	 * 
	 * @param args		The search space. 
	 * @param query		The string to search for.
	 * @return			The index of the query if found, or -1 otherwise.
	 */
	private static int getArgPos(String[] args, String query)
	{
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].equals(query))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * For arguments which have a second part, such as 
	 * "--load {@code <fileName>}", this checks if the second part
	 * exists. If it doesn't, it returns a -1. Otherwise, this behaves
	 * identically to {@link #getArgPos(String[], String)}.
	 * 
	 * @param args 	The search space.
	 * @param query	The string to search for.
	 * @return		The index of the query if found and if there
	 * 				is another argument after it. Otherwise, a -1.
	 * 
	 * @see	#getArgPos(String[], String)
	 */
	private static int getArgPosSafe(String[] args, String query)
	{
		final int result = getArgPos(args, query);
		
		if (result != -1 && result < args.length)
			return result;
		else
			return -1;
	}
	
	/**
	 * Available arguments are:
	 * <pre>
	 * --load {@code <file name>}: loads a simulation from a saved Mathling
	 * </pre>
	 * @param args	The arguments to parse.
	 * 
	 * @see main.Simulation
	 */
	private static void parseInputs(String[] args)
	{
		final int loadFile = getArgPosSafe(args, "--load");
		
		if (loadFile != -1)
		{
			Simulation.loadSimulation(args[loadFile + 1]);
			return;
		}
		
		return;
	}
	public static void main(String[] args)
	{ 
		if (args.length == 0)
		{
			Simulation.runNewSimulation();
		}
		else
		{
			parseInputs(args);
		}
		
		return;
	}
}
