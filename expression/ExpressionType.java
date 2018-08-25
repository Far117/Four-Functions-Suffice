package expression;

/**
 * An enum outlining the three different types of {@code Expression}.<br><br>
 * 
 * The three types are as follows:<br><br>
 * 
 * Number:		A real number.<br>
 * 	Ex:			f(x) = 2<br><br>
 * 
 * Variable:	A variable function. For now, this is always an
 * 				identity function.<br>
 * 	Ex:			f(x) = x<br><br>
 * 
 * Complex:		A function with two halves joined by an operator.<br>
 * 	Ex:			f(x) = g(x) + h(x)
 *
 * @see	expression.Expression
 */
public enum ExpressionType
{
	NUMBER,
	VARIABLE,
	COMPLEX
}
