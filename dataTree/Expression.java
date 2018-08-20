package dataTree;

public class Expression
{
	private ExpressionType type = null;
	private double num = 0;
	private char variable = ' ';
	
	private Expression left  = null;
	private char operator    = ' ';
	private Expression right = null;
	
	public Expression(double n)
	{
		this.type = ExpressionType.NUMBER;
		this.num = n;
		
		return;
	}
	
	public Expression(Expression l, char o, Expression r)
	{
		this.type = ExpressionType.EXPRESSION;
		
		this.left = l;
		this.operator = o;
		this.right = r;
		
		return;
	}
	
	public Expression(char v)
	{
		this.type = ExpressionType.VARIABLE;
		this.variable = v;
		
		return;
	}
	
	private double solveExpression(double input)
	{
		BinaryFunction f;
		switch (operator)
		{
			case '+':
				f = (x, y) -> x + y; break;
			case '-':
				f = (x, y) -> x - y; break;
			case '*':
				f = (x, y) -> x * y; break;
			case '/':
				f = (x, y) -> (y == 0)? 0 : x / y; break;
			default:
				f = (x, y) -> x;
				System.out.println("Error: Unknown function " + this.operator);
		}
		
		return f.compute(this.right.collapse(input), this.left.collapse(input));
	}
	
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
			case EXPRESSION:
				result = this.solveExpression(input);
				break;
		}
		
		return result;
	}
	
	public String print()
	{
		return this.print(0);
	}
	
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
			case EXPRESSION:
				result += this.operator + ":\n";
				result += this.left.print(tabs + 1) + "\n";
				result += this.right.print(tabs + 1) + "\n";
				break;
		}
		
		return result;
	}
	
	private static String printTabs(int tabs)
	{
		String t = "";
		for (int i = 0; i < tabs; i++)
			t += '\t';
		
		return t;
	}
	
	public ExpressionType getType()
	{
		return this.type;
	}
	
	public Expression getLeft()
	{
		return this.left;
	}
	
	public Expression getRight()
	{
		return this.right;
	}
	
	public char getOperator()
	{
		return this.operator;
	}
}
