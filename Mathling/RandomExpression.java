package Mathling;

import dataTree.Expression;
import dataTree.ExpressionType;

public class RandomExpression
{
	private static final char var = 'x';
	private static final double max = 100,
								min = -100;
	
	public static double randMinMax(double lower, double upper)
	{
		return lower + Math.random() * (upper - lower);
	}
	
	private static char randomFunction()
	{
		final double roll = Math.random();
		
		if (roll <= 0.25)
			return '+';
		else if (roll <= 0.5)
			return '-';
		else if (roll <= 0.75)
			return '*';
		else
			return '/';
	}
	
	// Sometimes gets too ambitious and runs out of stack memory
	private static Expression newExpressionUnsafe(double[] odds)
	{
		final double roll = randMinMax(0, 100);
		if (roll <= odds[0])
			return new Expression(randMinMax(min, max));
		
		else if (roll <= (odds[0] + odds[1]))
			return new Expression(var);
		
		else
			return new Expression(
					newExpressionUnsafe(odds),
					randomFunction(),
					newExpressionUnsafe(odds));
	}
	
	// Psh who says Java isn't a safe language
	public static Expression newExpression(double[] odds)
	{
		try
		{
			return newExpressionUnsafe(odds);
		}
		catch (StackOverflowError err)
		{
			return newExpression(odds);
		}
	}
	
	public static Expression mutateExpression(Expression e,
												double aggressiveness,
												double[] odds)
	{
		switch (e.getType())
		{
			case NUMBER:
			case VARIABLE:
				return newExpression(odds);
			case EXPRESSION:
				final double roll = randMinMax(0, 100);
				if (roll <= aggressiveness)
					return newExpression(odds);
		}
		final double roll = randMinMax(0, 100);
		
		if (roll <= 50)
		{
			Expression newLeft = mutateExpression(
					e.getLeft(),
					aggressiveness,
					odds);
			return new Expression(newLeft,
					e.getOperator(),
					e.getRight());
		}
		else
		{
			Expression newRight = mutateExpression(e.getRight(),
					aggressiveness,
					odds);
			return new Expression(
					e.getLeft(),
					e.getOperator(),
					newRight);
		}	
	}
}
