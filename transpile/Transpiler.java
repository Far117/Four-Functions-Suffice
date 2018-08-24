package transpile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import expression.Expression;

/**
 * These transpilers' goal is to convert the {@code Expression} AST
 * into callable functions in various languages/forms.
 */
public interface Transpiler
{
	/**
	 * The abstract function which will perform the transpilation. A
	 * transpiler should be prepared to accept an arbitrary {@code Expression}
	 * and return a string containing the output code.
	 * 
	 * @param e	The {@code Expression} to be transpiled.
	 * @return	A string containing the transpiled code.
	 * 
	 * @see expression.Expression
	 */
	public String transpile(Expression e);
	
	/**
	 * An intermediary helper function which transforms an {@code Expression}
	 * into a general arithmetic form that many languages are happy to parse.
	 * 
	 * @param e	The {@code Expression} to be intermediately transformed.
	 * @return	An arithmetic representation of the {@code Expression},
	 * 			which respects its internal order of operations.
	 * 
	 * @see expression.Expression
	 */
	public static String expressionToString(Expression e)
	{
		switch (e.getType())
		{
			case NUMBER:
				return "" + e.getNum();
			case VARIABLE:
				return "" + e.getVariable();
			case COMPLEX:
				return  "(" 								+
						expressionToString(e.getLeft()) 	+ " " +
						e.getOperator()						+ " " +
						expressionToString(e.getRight())	+
						")";
		}
		// Should never be reached, previous switch is total (barring null)
		return null;
	}
	
	/**
	 * An wrapper function which will not only transpile an {@code Expression},
	 * but also save it as an appropriate source file.
	 * 
	 * @param fileName	The name of the destination file, including extention.
	 * @param e			The {@code Expression} to transpile.
	 * 
	 * @see	#transpile(Expression)
	 * @see expression.Expression
	 */
	default void transpileToFile(String fileName, Expression e)
	{
		try {
			Files.write(Paths.get(fileName), transpile(e).getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return;
	}
}
