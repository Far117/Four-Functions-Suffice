package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import lambdaType.Computable;
import mathling.Mathling;
import mathling.MathlingComparator;
import mathling.MathlingMutator;
import mathling.MathlingSaver;
import transpile.ToJava;
import transpile.ToPython;

/**
 * Handles the overall structure of the simulation, such as generating
 * populations and handling successive iterations of evolution. Also
 * writes the results to a file occasionally.
 * 
 */
public class Simulation
{
	private static long thunkTime = 0,
						collapseTime = 0;

	/**
	* This contains the function which the simulation will attempt
	* to approximate. The results of this function will be tested
	* against the various simulations' functions in order to establish
	* accuracy.
	* 
	* @param input	The number to pass to the inner function.
	* @return		The inner function's results.
	* @see			#calculateAccuracies(Mathling[])
	*/
	private static double testFunction(double input)
	{
		return Math.sin(Math.toRadians(input));
	}
	
	/**
	 * Updates all of the inputed mathlings' internal
	 * accuracy scores.
	 * @param mathlings	The mathlings to update.
	 * @see mathling.Mathling#calculateAccuracy(Computable)
	 */
	private static void calculateAccuracies(Mathling[] mathlings)
	{
		long t1 = System.currentTimeMillis();
		for (Mathling m : mathlings)
		{
			m.calculateAccuracy((x) -> testFunction(x));
		}
		
		long t2 = System.currentTimeMillis();
		collapseTime += (t2 - t1);
		
		//t1 = System.currentTimeMillis();
		//for (Mathling m : mathlings)
		//	m.calculateAccuracyThunklet((x) -> testFunction(x));
		//t2 = System.currentTimeMillis();
		//thunkTime += (t2 - t1);
		
		return;
	}
	
	/**
	 * Sorts the mathlings from best to worst. Then:
	 * 
	 * Mutates the best one 70 times
	 * Mutates the second best one 20 times
	 * Mutates the third best one 5 times
	 * 
	 * Keeps one random mathling
	 * Generates one new mathling
	 * 
	 * @param mathlings	The mathling array to sort, cull, and repopulate
	 */
	private static void repopulateMathlings(Mathling[] mathlings)
	{
		Arrays.sort(mathlings, MathlingComparator.comparator);
		
		mathlings[3] = mathlings[(int) MathlingMutator.randMinMax(3, 100)];
		mathlings[4] = new Mathling();
		
		for (int i = 5; i < 75; i++)
			mathlings[i] = mathlings[0].getMutation();
		
		for (int i = 75; i < 95; i++)
			mathlings[i] = mathlings[1].getMutation();
		
		for (int i = 95; i < 100; i++)
			mathlings[i] = mathlings[2].getMutation();
	}
	
	@SuppressWarnings("unused")
	/**
	 * Intended to test children overwriting parents.
	 */
	private static void test()
	{
		Mathling[] mathlings = new Mathling[2];
		
		mathlings[0] = new Mathling();
		mathlings[1] = new Mathling();
		
		while (true)
		{
			for (Mathling m : mathlings)
				m.calculateAccuracy((x) -> testFunction(x));
			
			Arrays.sort(mathlings, MathlingComparator.comparator);
			
			mathlings[1] = mathlings[0].getMutation();
		}
		
	}
	
	/**
	 * Writes an {@code Expression} (which has been processed into
	 * a string) to a file.
	 *  
	 * @param accuracy	The average deviation of this expression from
	 * 					the actual function.
	 * @param results	The stringified expression.
	 * 
	 * @see				expression.Expression#print()
	 */
	private static void writeResults(double accuracy, String results)
	{
		String toWrite = "This function has an average error of ";
		toWrite += accuracy + ":\n\n" + results;
		
		try
		{
			Files.write(Paths.get("result.txt"), toWrite.getBytes());
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves a {@code Mathling}'s progress and transpiles it to 
	 * ASCII, Python, and Java. The saved {@code Mathling} is automatically
	 * saved to "current.mth"
	 * 
	 * @param best	The {@code Mathling} to save.
	 * 
	 * @see mathling.Mathling
	 * @see transpile.Transpiler
	 * @see mathling.MathlingSaver
	 */
	private static void saveProgress(Mathling best)
	{
		MathlingSaver.saveMathling(best, "current.mth");
		writeResults(best.getAccuracy(), best.printExpression());
		
		new ToPython().transpileToFile(
				"output.py", 
				best.getExpression());
		new ToJava().transpileToFile(
				"output.java",
				best.getExpression());
		
		return;
	}
	
	/**
	 * Continues a simulation, given an array of {@code Mathling}s to
	 * work with.
	 * 
	 * @param mathlings	The population to evolve.
	 * 
	 * @see	mathling.Mathling
	 */
	public static void runSimulation(Mathling[] mathlings)
	{
		for (int i = 0; i < 1000000000; i++)
		{
			calculateAccuracies(mathlings);
			repopulateMathlings(mathlings);
			
			if (i % 1000 == 0)
			{
				System.out.println("" + i + "\t" + mathlings[0].getAccuracy());
				saveProgress(mathlings[0]);
			}
		}
		
		System.out.println("Winner with " + mathlings[0].getAccuracy() + ":");
		System.out.println(mathlings[0].printExpression());
		
		System.out.println(mathlings[0].getAccuracy());
		
		saveProgress(mathlings[0]);
		
		return;
	}
	
	/**
	 * Starts a simulation from scratch with randomized {@code Mathling}s.
	 * 
	 * @see	mathling.Mathling
	 * @see #runSimulation(Mathling[])
	 */
	public static void runNewSimulation()
	{
		Mathling[] mathlings = new Mathling[100];
		for (int i = 0; i < 100; i++)
			mathlings[i] = new Mathling();
		
		runSimulation(mathlings);
		
		return;
	}
	
	/**
	 * Loads a {@code Mathling} from a file, then evolves it in
	 * a simulation. Useful for continuing where you left off in
	 * long-term simulations.
	 * 
	 * @param name	The name of the serialized {@code Mathling} to load.
	 * 
	 * @see	mathling.MathlingSaver#loadMathling(String)
	 * @see mathling.Mathling
	 * @see #runSimulation(Mathling[])
	 */
	public static void loadSimulation(String name)
	{
		Mathling loaded = MathlingSaver.loadMathling(name);
		
		if (loaded != null)
		{
			Mathling[] mathlings = new Mathling[100];
			
			for (int i = 0; i < 100; i++)
				mathlings[i] = loaded;
			
			runSimulation(mathlings);
			
			return;
		}
		else
		{
			System.out.println("Error loading mathling from " + name);
			return;
		}
	}
}
