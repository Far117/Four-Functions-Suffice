package mathling;

import expression.Expression;
import expression.ExpressionCopier;

/**
 * A collection of useful functions for mutating and generating {@code Mathling}s.
 *
 */
public class MathlingMutator
{
	private static final char var = 'x';
	private static final double max = 100,
								min = -100,
								shiftMin = -5,
								shiftMax = 5;
	
	/**
	 * Returns a {@code double} between two values.
	 * @param lower	The lower bound to generate between.
	 * @param upper	The upper bound to generate between.
	 * @return		The randomly-generated, bounded {@code double}.
	 */
	public static double randMinMax(double lower, double upper)
	{
		return lower + Math.random() * (upper - lower);
	}
	
	/**
	 * Generates a random operator, with equal probabilities for each.
	 * 
	 * @return The chosen operator.
	 * @see expression.Expression
	 */
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
	
	/**
	 * Recursively generates a new, random {@code Expression}, where the
	 * odds of each node being one of the three types of {@code Expression}s
	 * is determined by the input array.
	 * <br/><br/>
	 * 
	 * Currently this function has no way to stop when a tree becomes too
	 * large. This means it may ambitiously eat up all of the allotted stack
	 * memory. Bless its heart.
	 * 
	 * @param odds	The probability of each node being a certain type.
	 * 				{@code odds[0]} is the probability of the node being
	 * 				a numerical {@code Expression}. {@code odds[1]} is
	 * 				the probability of the node being a variable {@code Expression}.
	 * 				The probability of a complex {@code Expression} is determined
	 * 				from the remainder. For example, {@code [20, 30]} would mean
	 * 				a 20% chance of being a number, a 30% chance of being
	 * 				a variable, and a 50% chance of being a complex {@code Expression}.
	 * @return		The generated {@code Expression}.
	 * 
	 * @see expression.Expression
	 * @see #newExpressionSafe(double[])
	 */
	private static Expression newExpressionUnsafe(double[] odds) throws StackOverflowError
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
	
	/**
	 * This gets around the {@code StackOverflowError} by repeatedly
	 * retrying the function until it works.
	 * <br/><br/>
	 * 
	 * Note: This does affect the probability! Odds of {@code [0.1, 0.1]}
	 * will almost certainly overflow, and further repeats will almost
	 * certainly repeat until the first {@code Expression} generated is
	 * either a number or a variable. Therefore in this case, the
	 * probability of a complex {@code Expression} is almost 0% instead
	 * of the expected 99.8%. Increase the odds of the former two if
	 * this ends up happening.
	 * <br/><br/>
	 * 
	 * Testing still needs to be done to find the optimal odds for
	 * generation of complex {@code Expression}s.
	 * 
	 * @param odds	The probabilities to attempt to match. See 
	 * 				{@link #newExpressionUnsafe(double[])} for more details.
	 * @return		The safely-generated {@code Expression}.
	 * 
	 * @see	expression.Expression
	 * @see	#newExpressionUnsafe(double[])
	 */
	public static Expression newExpressionSafe(double[] odds)
	{
		// Who says Java is an unsafe language?
		try {
			return newExpressionUnsafe(odds);
		} catch (StackOverflowError err) {
			return newExpressionSafe(odds);
		}
	}
	
	/**
	 * A "poke" is a chance to change one or more of an {@code Expression}'s
	 * numerical nodes while leaving the structure of the tree fully intact.
	 * 
	 * @param e					The {@code Expression} to poke.
	 * @param aggressiveness	How likely it will be to poke any found
	 * 							number node.
	 * @return					The poked ({@code Expression}.
	 * 
	 * @see expression.Expression
	 * @see #mutateExpression(Expression, double, double[])
	 */
	public static Expression pokeExpression(Expression e,
											double aggressiveness)
	{
		final double roll = randMinMax(0, 100);
		
		switch (e.getType())
		{
			case NUMBER:
				if (roll <= aggressiveness)
					return new Expression(
							e.shiftNumber(randMinMax(shiftMin, shiftMax)));
				break;
			case COMPLEX:
				return new Expression(
						pokeExpression(e.getLeft(), aggressiveness),
						e.getOperator(),
						pokeExpression(e.getRight(), aggressiveness));
			default:
				break;
		}
		return e;
	}
	
	/**
	 * Unlike a poke, a full mutation does have a chance of (perhaps
	 * dramatically) altering the structure of an {@code Expression}.
	 *  
	 * @param e					The {@code Expression} to mutate.
	 * 
	 * @param aggressiveness	How likely this is to be just a poke,
	 * 							and if it is a mutation, how likely each
	 * 							node is to be the point of mutation.
	 * 							An aggressiveness of 100% will always
	 * 							mutate the root node, effectively creating
	 * 							an entirely new {@code Expression}.
	 * 							Alternatively, an aggressiveness of 0%
	 * 							will always alter a terminal node, thereby
	 * 							minimally altering the {@code Expression}.
	 * 
	 * @param odds				The odds to generate a new {@code Expression}
	 * 							with. See {@link mathling.MathlingMutator#newExpressionUnsafe(double[])}
	 * 							for more details.
	 * 
	 * @return					The mutated {@code Expression}.
	 * 
	 * @see	#pokeExpression(Expression, double)
	 * @see expression.Expression
	 * @see mathling.MathlingMutator#newExpressionUnsafe(double[])
	 */
	public static Expression mutateExpression(Expression e,
												double aggressiveness,
												double[] odds)
	{
		if (randMinMax(0, 100) >= aggressiveness)
			return pokeExpression(ExpressionCopier.copy(e), aggressiveness);
		
		
		switch (e.getType())
		{
			case NUMBER:
			case VARIABLE:
				return newExpressionSafe(odds);
			case COMPLEX:
				final double roll = randMinMax(0, 100);
				if (roll <= aggressiveness)
					return newExpressionSafe(odds);
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
