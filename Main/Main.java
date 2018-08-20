package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import Mathling.Mathling;
import Mathling.MathlingComparator;
import Mathling.RandomExpression;


public class Main
{
	private static Mathling[] mathlings = new Mathling[100];
	
	
	private static double testFunction(double input)
	{
		return Math.sin(input);
	}
	
	private static void calculateAccuracies(Mathling[] mathlings)
	{
		for (Mathling m : mathlings)
			m.calculateAccuracy((x) -> testFunction(x));
	}
	
	/*
	 * Sorts the mathlings from best to worst. Then:
	 * 
	 * Mutates the best one 70 times
	 * Mutates the second best one 20 times
	 * Mutates the third best one 5 times
	 * 
	 * Keeps one random mathling
	 * Generates one new mathling
	 */
	private static void repopulateMathlings(Mathling[] mathlings)
	{
		Arrays.sort(mathlings, MathlingComparator.comparator);
		
		mathlings[3] = mathlings[(int) RandomExpression.randMinMax(3, 100)];
		mathlings[4] = new Mathling();
		
		for (int i = 5; i < 75; i++)
			mathlings[i] = mathlings[0].getMutation();
		
		for (int i = 75; i < 95; i++)
			mathlings[i] = mathlings[1].getMutation();
		
		for (int i = 95; i < 100; i++)
			mathlings[i] = mathlings[2].getMutation();
	}
	
	public static void main(String[] args)
	{
		/*Expression e = new Expression(
				new Expression(
						new Expression(1),
						'/',
						new Expression('x')),
				'-',
				new Expression(
						new Expression('x'),
						'/',
						new Expression(2)));*/
		
		//System.out.println(e.collapse(100));
		
		/*
		Mathling m1 = new Mathling();
		
		System.out.println(m1.printExpression());
		System.out.println("======================");
		
		Mathling m2 = m1.getMutation();
		
		System.out.println(m1.printExpression());
		System.out.println("======================");
		
		System.out.println(m2.printExpression());
		*/
		
		for (int i = 0; i < 100; i++)
		{
			mathlings[i] = new Mathling();
		}
		
		for (int i = 0; i < 1000000; i++)
		{
			calculateAccuracies(mathlings);
			repopulateMathlings(mathlings);
			
			if (i % 10000 == 0)
				writeResults(mathlings[0].getAccuracy(), mathlings[0].printExpression());
		}
		
		System.out.println("Winner with " + mathlings[0].getAccuracy() + ":");
		System.out.println(mathlings[0].printExpression());
		
		System.out.println(mathlings[0].getAccuracy());
		
		writeResults(mathlings[0].getAccuracy(), mathlings[0].printExpression());
		
	}
	
	private static void writeResults(double accuracy, String results)
	{
		String toWrite = "This function is accurate to within ";
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
}
