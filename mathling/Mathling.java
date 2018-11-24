package mathling;
import expression.DivideByZeroError;
import expression.Expression;
import expression.ExpressionCopier;
import expression.Simplifier;
import lambdaType.Computable;
import lambdaType.Thunklet;

import java.io.Serializable;

/**
 * A {@code Mathling} is a personification of a wrapper for an 
 * {@code Expression}. Their purpose it to track the accuracy of
 * an {@code Expression} in relation to some well-defined function
 * they're trying to emulate. A "good {@code Mathling}" will
 * therefore behave very closely to the function it approximates ---
 * the closer the better.
 * <br><br>
 * 
 * {@code Mathling}s' "aliveness" also helps to remind that 
 * these evolve while pure {@code Expression}s should remain immutable.
 * <br><br>
 * 
 * The simulation itself is conducted in {@link main.Main}
 * 
 * @see	expression.Expression
 * @see main.Main
 */
public class Mathling implements Serializable
{
	private static final long serialVersionUID = 29904252468444646L;
	
	private Expression expr = null;
	private double accuracy = 0;
	
	final static double minTest = -360,
						maxTest = 360;
	
	// 100% will always mutate the root node of the expression,
	// 0% will always mutate a terminal node
	private double mutationSignificance = 30;
	
	private static final double[] odds = {30, 30};
	
	/**
	 * Creates a {@code Mathling} with a randomized {@code Expression}.
	 * 
	 * @see mathling.MathlingMutator
	 */
	public Mathling()
	{
		this.expr = MathlingMutator.newExpressionSafe(odds);
		return;
	}
	
	/**
	 * Creates a {@code Mathling} with a predefined {@code Expression}.
	 * @param e	The {@code Expression} to assign to the new {@code Mathling}.
	 */
	public Mathling(Expression e)
	{
		this.expr = e;
		return;
	}
	
	/**
	 * Pretty-prints the inner {@code Expression}.
	 * @return	A stringified {@code Expression}.
	 * 
	 * @see expression.Expression#print()
	 */
	public String printExpression()
	{
		return this.expr.print();
	}
	
	/**
	 * A wrapper for the inner {@code Expression}'s }{@code collapse} method.
	 * 
	 * @param input	The real number to pass to this {@code Mathling}'s {@code Expression}.
	 * @return		The result of the {@code Expression}'s calculation.
	 */
	public double collapse(double input) throws DivideByZeroError
	{
		return this.expr.collapse(input);
	}
	
	/**
	 * Given a lambda, find the average difference between the
	 * {@code Expression}'s approximation and the goal function's result.
	 * <br><br>
	 * 
	 * Note: Calculates the error across the range of {@code minTest} and
	 * {@code maxTest}.
	 * @param f	The goal function to emulate.
	 * 
	 * @see	expression.Expression#collapse(double)
	 * @see lambdaType.Computable
	 */
	public void calculateAccuracy(Computable f)
	{
		this.accuracy = 0;
		int terms = Math.abs((int) minTest - (int) maxTest);
		//if (this.expr.getType() == ExpressionType.COMPLEX)
		//{
		//	calculateAccuracyThunklet(f);
		//	return;
		//}
		
		for (int i = (int) minTest; i < (int) maxTest; i++)
		{
			try {
				double bump = Math.abs(this.collapse(i) - f.compute(i));
				this.accuracy += bump;
			} catch (DivideByZeroError err) {
				Expression tryToSimplify = Simplifier.simplify(this.expr);
				if (!tryToSimplify.equivalent(this.expr))
				{
					this.expr = tryToSimplify;
					this.calculateAccuracy(f);
					return;
				}
				else
				{
					terms--;
				}
			}
		}
		
		if (terms > 0)
			this.accuracy /= terms;
		else
		{
			this.accuracy = 1000;
		}
			
		
		return;
	}
	
	/**
	 * Identical to {@link #calculateAccuracy(Computable)}, except this
	 * first creates a {@code Thunklet} for the {@code Expression} before
	 * using it to then calculate the accuracy.
	 * 
	 * @param f	The goal function to emulate.
	 * @see 	lambdaType.Thunklet
	 */
	public void calculateAccuracyThunklet(Computable f)
	{
		this.accuracy = 0;
		int terms = Math.abs((int) minTest - (int) maxTest);
		Computable thunklet = Thunklet.makeThunklet(this.expr);
		
		for (int i = (int) minTest; i < (int) maxTest; i += 1)
		{
			try {
				final double bump = Math.abs((thunklet.compute(i) - f.compute(i)));
				this.accuracy += bump;
			} catch (DivideByZeroError err) {
				terms--;
			}
		}
		
		this.accuracy /= terms;
		
		return;
	}
	
	/**
	 * Copies the inner {@code Expression} and returns a mutated form of it.
	 * @return	A mutated form of this {@code Mathling}'s {@code Expression}.
	 * 
	 * @see mathling.MathlingMutator#mutateExpression(Expression, double, double[])
	 */
	public Mathling getMutation()
	{
		return new Mathling(
				MathlingMutator.mutateExpression(
						ExpressionCopier.copy(this.expr),
						this.mutationSignificance,
						Mathling.odds));
	}
	
	/**
	 * @return	The {@code Mathling}'s {@code Expression}.
	 */
	public Expression getExpression()
	{
		return ExpressionCopier.copy(this.expr);
	}
	
	/**
	 * @return 	The accuracy of this {@code Mathling}, which is the
	 * 			average difference between its {@code Expression} and
	 * 			the goal function.
	 */
	public double getAccuracy()
	{
		return this.accuracy;
	}
	
	/**
	 * Forcefully alters this {@code Mathling}'s accuracy.
	 * @param newAccuracy	The accuracy to replace this one's.
	 */
	public void setAccuracy(double newAccuracy)
	{
		this.accuracy = newAccuracy;
	}
	
}
