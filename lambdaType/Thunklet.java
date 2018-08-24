package lambdaType;

import expression.Expression;
import mathling.MathlingMutator;

/**
 * A thunklet represents a sort of pseudo thunk. It turns an {@code Expression} 
 * into a series of nested lambdas which represent a computable form
 * of the {@code Expression}'s inner function. When many numbers need
 * to be passed through a single {@code Expression}, it is more efficient
 * to generate a thunklet and pass them through that than to {@code collapse}
 * the function each time. <br/><br/>
 * 
 * Note: However, when only running a few iterations, or when dealing with
 * purely numerical or variable {@code Expression} types, {@code collapse}
 * may be more efficient, as a thunklet has an initial investment cost
 * when it is created.
 *
 * @see expression.Expression#collapse(double)
 */
public class Thunklet
{
	/**
	 * Turns an {@code Expression} into a lambda recursively. Recursion
	 * terminates at the numerical and variable {@code Expression} types.
	 * 
	 * @param e	The {@code Expression} to be transformed.
	 * @return	A {@code Computable} lambda representing the {@code Expresion}.
	 * 
	 * @see lambdaType.Computable
	 */
	public static Computable makeThunklet(Expression e)
	{
		switch (e.getType())
		{
			case NUMBER:
				final double n = e.getNum();
				return (x) -> n;
			case VARIABLE:
				return (x) -> x;
			case COMPLEX:
				final Computable left = makeThunklet(e.getLeft());
				final Computable right = makeThunklet(e.getRight());
				
				return (x) -> e.makeFunction().compute(
								left.compute(x),
								right.compute(x));
		}
		
		return null;
	}
	
	/**
	 * A helper function for testing {@code Thunklet} performance.
	 */
	public static void testThunklet()
	{
		Expression e = MathlingMutator.newExpressionSafe(new double[] {15,15});
		System.out.println(e.print());
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 200; i++)
			e.collapse(i);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Collapse time: " + (endTime - startTime));
		
		startTime = System.currentTimeMillis();
		Computable t = makeThunklet(e);
		for (int i = 0; i < 200; i++)
			t.compute(i);
		endTime = System.currentTimeMillis();
		
		System.out.println("Thunk time: " + (endTime - startTime));
	}
}
