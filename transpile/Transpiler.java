package transpile;

import dataTree.Expression;

public interface Transpiler
{
	public String transpile(Expression e);
	
	public static String expressionToString(Expression e)
	{
		switch (e.getType())
		{
			case NUMBER:
				return "" + e.getNum();
			case VARIABLE:
				return "" + e.getVariable();
			case EXPRESSION:
				return  "(" 								+
						expressionToString(e.getLeft()) 	+ " " +
						e.getOperator()						+ " " +
						expressionToString(e.getRight())	+
						")";
		}
		// Should never be reached, previous switch is total (barring null)
		return null;
	}
	
	public void transpileToFile(String fileName, Expression e);
}
