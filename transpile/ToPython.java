package transpile;

import expression.Expression;

/**
 * Transpiles an {@code Expression} to a Python function.
 * 
 * @see transpile.Transpiler
 */
public class ToPython implements Transpiler
{
	// Pythonic components
	private static String contents = "def func(x):\n\treturn ";

	@Override
	public String transpile(Expression e)
	{
		return contents + Transpiler.expressionToString(e);
	}
}
