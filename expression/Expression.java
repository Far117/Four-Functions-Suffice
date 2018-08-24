package expression;

import lambdaType.BinaryFunction;

/**
 *
 * An {@code Expression} is a tree which encapsulates arbitrary
 * mathematical functions according to a specific grammar.	<br/><br/>
 * 
 * The grammar is as follows:								<br/><br/>
 * 
 * Number		=	At least one digit.						<br/>
 * Variable 	=	A single letter.						<br/>
 * Operator 	=	Either '+', '-', '*', or '/'.			<br/><br/>
 *
 * Expression 	=	Either:									<br/>
 * 						{@code<Number>}		Or				<br/>
 * 						{@code<Variable>}	Or				<br/>
 * 						{@code<Expression>} {@code<Operator>} {@code<Expression>}
 * <br/><br/>
 * This grammar ensures that any function generated will be
 * valid and computable.<br/><br/>
 * 
 * Note: Testing for division by zero should still be done, as the
 * grammar does not account for that.
 */
public class Expression
{
	private ExpressionType type = null;
	private double num = 0;
	private char variable = ' ';
	
	private Expression left  = null;
	private char operator    = ' ';
	private Expression right = null;
	
	/**
	 * Builds an {@code Expression} that consists of a single real
	 * number. This translates to a function which returns only said 
	 * number.
	 * 
	 * @param n	The number the {@code Expression} will return.
	 */
	public Expression(double n)
	{
		this.type = ExpressionType.NUMBER;
		this.num = n;
		
		return;
	}
	
	/**
	 * Builds an {@code Expression} that consists of a variable. This
	 * translates to a function which returns its input.
	 * 
	 * @param v	The letter to use for the variable.
	 */
	public Expression(char v)
	{
		this.type = ExpressionType.VARIABLE;
		this.variable = v;
		
		return;
	}
	
	/**
	 * A binary tree of {@code Expression}s. Each side will evaluate to
	 * recursively until it hits the terminal nodes of either the 
	 * {@code Number} or {@code Variable Expression} type. The halves will
	 * be combined according to how the operator dictates.
	 *  
	 * @param l	The left branch.
	 * @param o	The combining operator.
	 * @param r	The right branch.
	 */
	public Expression(Expression l, char o, Expression r)
	{
		this.type = ExpressionType.COMPLEX;
		
		this.left = l;
		this.operator = o;
		this.right = r;
		
		return;
	}
	
	/**
	 * Pushes an input number through an {@code Expression}, and returns
	 * the calculated number. This is like treating the {@code Expression}
	 * as some {@code f(x)}, and calling it with an inputed real number.
	 * 
	 * If the function is simply a number, it ignores the input and
	 * returns said number.
	 * 
	 * If it's a variable, it returns the input.
	 * 
	 * If it's a complex {@code Expression}, the function is solved
	 * recursively by first figuring out how to implement the operator
	 * with {@link #solveExpression(double)}, then repeating the {@code collapse}
	 * on each half.
	 * 
	 * @param input	The real number to pass through the {@code Expression}.
	 * @return		The function's resulting calculated number.
	 * @see			#solveExpression(double)
	 */
	public double collapse(double input)
	{
		double result = 0;
		
		switch (this.type)
		{
			case NUMBER:
				result = this.num; 
				break;
			case VARIABLE:
				result = input;
				break;
			case COMPLEX:
				result = this.solveExpression(input);
				break;
		}
		
		return result;
	}
	
	/**
	 * Joins two halves of a complex {@code Expression} together,
	 * based on the operator.
	 * 
	 * @param input	The number inputed to the function
	 * @return		The results of the generated lambda's computation.
	 * 
	 * @see			BinaryFunction
	 * @see			#makeFunction()
	 */
	private double solveExpression(double input)
	{
		BinaryFunction f = makeFunction();
		return f.compute(	this.left.collapse(input),
							this.right.collapse(input));
	}
	
	/**
	 * Creates a lambda according to the {@code Expression}'s operator.
	 * Useful for turning an {@code Expression}'s operator into a
	 * computable function.
	 * 
	 * @return	The lambda based on this {@code Expression}'s operator.
	 */
	public BinaryFunction makeFunction()
	{
		switch (this.operator)
		{
			case '+':
				return (x, y) -> x + y;
			case '-':
				return (x, y) -> x - y;
			case '*':
				return (x, y) -> x * y;
			case '/':
				return (x, y) -> (y == 0)? 0 : x / y;
				
			default:
				System.out.println("Error: Unknown function " + this.operator);
				return (x, y) -> x;
		}
	}
	
	/**
	 * Returns a tweaked version of a numerical {@code Expression}.
	 * Useful for mutations.
	 * 
	 * @param amount	What to shift the number by.
	 * @return			The shifted number.
	 */
	public double shiftNumber(double amount)
	{
		return this.num += amount;
	}
	
	/**
	 * A wrapper function for {@link #print(int)}, used when
	 * you which to print at the head of an {@code Expression} tree.
	 * 
	 * @return	A stringified {@code Expression}.
	 * 
	 * @see 	#print(int)
	 */
	public String print()
	{
		return this.print(0);
	}
	
	/**
	 * Recursively pretty-prints this {@code Expression}.
	 * 
	 * @param tabs	How much to indent this section. Deeper sections
	 * 				are indented more.
	 * @return		A stringified {@code Expression}.
	 * 
	 * @see			#printTabs(int)
	 */
	public String print(int tabs)
	{
		String result = printTabs(tabs);
		
		switch (this.type)
		{
			case NUMBER:
				result += this.num;
				break;
			case VARIABLE:
				result += this.variable;
				break;
			case COMPLEX:
				result += this.operator + ":\n";
				result += this.left.print(tabs + 1) + "\n";
				result += this.right.print(tabs + 1) + "\n";
				break;
		}
		
		return result;
	}
	
	/**
	 * Prints the number of tabs inputed, repeatedly.
	 * 
	 * @param 	n	The number of tabs to print.
	 * @return		A string containing {@code n} tabs.
	 */
	private static String printTabs(int n)
	{
		String t = "";
		for (int i = 0; i < n; i++)
			t += '\t';
		
		return t;
	}
	
	/**
	 * @return The type of this {@code Expression}.
	 */
	public ExpressionType getType()
	{
		return this.type;
	}
	
	/**
	 * @return The number contained by this {@code Expression}.
	 * Note: Check if this is indeed a numerical {@code Expression}.
	 */
	public double getNum()
	{
		return this.num;
	}
	
	/**
	 * @return The character used for the variable of this {@code Expression}.
	 * Note: Check if this is indeed a variable {@code Expression}.
	 */
	public char getVariable()
	{
		return this.variable;
	}
	
	/**
	 * @return The left side of a complex {@code Expression}.
	 * Note: Check if this is indeed a complex {@code Expression}.
	 */
	public Expression getLeft()
	{
		return ExpressionCopier.copy(this.left);
	}
	
	/**
	 * @return The right side of a complex {@code Expression}.
	 * Note: Check if this is indeed a complex {@code Expression}.
	 */
	public Expression getRight()
	{
		return ExpressionCopier.copy(this.right);
	}
	
	/**
	 * @return The operator of a complex {@code Expression}.
	 * Note: Check if this is indeed a complex {@code Expression}.
	 */
	public char getOperator()
	{
		return this.operator;
	}
}
