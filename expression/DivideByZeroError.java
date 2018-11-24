package expression;

/**
 * 
 * Used to signify when an evaluated {@code Expression} contains division
 * by zero.
 *
 * @see expression.Expression#collapse(double)
 */
public class DivideByZeroError extends Exception
{
	private static final long serialVersionUID = -9115507452453616424L;
	
	/**
	 * Standard error.
	 */
	public DivideByZeroError()
	{
		return;
	}
	
	/**
	 * Used when the error should print out a message, too.
	 * @param message	The error to display.
	 */
	public DivideByZeroError(String message)
	{
		System.out.println(message);
		return;
	}
}
