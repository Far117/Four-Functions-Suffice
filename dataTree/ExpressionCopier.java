package dataTree;

public class ExpressionCopier
{
	public static Expression copy(Expression e)
	{
		switch (e.getType())
		{
			case NUMBER:
				return new Expression(e.getNum());
			case VARIABLE:
				return new Expression(e.getVariable());
		}
		return new Expression(e.getLeft(),
				e.getOperator(),
				e.getRight());
	}
}
