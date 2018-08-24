package expression;

/**
 * Tools for copying {@code Expression}s, especially deep copies.
 * 
 * @see	expression.Expression
 */
public class ExpressionCopier
{
	/**
	 * Creates a deep copy of a given {@code Expression}.
	 * 
	 * @param e	The {@code Expression} to copy.
	 * @return	A deep copy of the {@code Expression}.
	 * @see		expression.Expression
	 */
	public static Expression copy(Expression e)
	{
		switch (e.getType())
		{
			case NUMBER:
				return new Expression(e.getNum());
			case VARIABLE:
				return new Expression(e.getVariable());
			default:
				break;
		}
		
		return new Expression(e.getLeft(),
				e.getOperator(),
				e.getRight());
	}
}
