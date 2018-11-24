package expression;

/**
 * A collection of functions used to simplify {@code Expressions} into
 * more compact (but equivalent!) forms.
 * 
 * @see expression.Expression
 */
public class Simplifier
{
	/**
	 * In the case of {@code <Number> <Operator> <Number}, we can collapse
	 * the complex {@code Expression} into a single numerical {@code Expression}.
	 * 
	 * @param e	The {@code Expression} to attempt to collapse.
	 * @return	The potentially collapsed {@code Expression}. If the
	 * 			{@code Expression} was a number divided by 0, then a 0
	 * 			is returned instead.
	 */
	private static Expression checkIfTrivial(Expression e)
	{
		if (e.getType() == ExpressionType.COMPLEX)
		{
			if (e.getLeft().getType() == ExpressionType.NUMBER &&
					e.getRight().getType() == ExpressionType.NUMBER)
			{
				try {
					return new Expression(e.collapse(0));
				} catch (DivideByZeroError err) {
					return new Expression(0);
				}
				
			}
			else if (e.getLeft().getType()  	== ExpressionType.VARIABLE 	&&
					 e.getRight().getType() 	== ExpressionType.VARIABLE	&&
					 e.getOperator()			== '/')
			{
				return new Expression(1);
			}
			else if (e.getLeft().getType()  	== ExpressionType.VARIABLE 	&&
					 e.getRight().getType() 	== ExpressionType.VARIABLE	&&
					 e.getOperator()			== '-')
			{
				return new Expression(0);
			}
			else
			{
				return new Expression(	checkIfTrivial(e.getLeft()),
										e.getOperator(),
										checkIfTrivial(e.getRight()));
			}
			
		}
		
		return e;
	}
	
	public static Expression simplify(Expression e)
	{
		Expression check = checkIfTrivial(e);
		if (check != e)
			return check;
		
		
		
		return e;
	}
}
