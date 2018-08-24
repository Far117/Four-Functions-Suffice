package transpile;

import expression.Expression;

public class ToJava implements Transpiler
{
	private static final String firstHalf  = 
			"class Main\n{\n\tpublic static void main(String[] args)\n\t{\n\t\tSystem.out.println(",
								secondHalf =
			");\n\t}\n}";
								
	@Override
	public String transpile(Expression e)
	{
		return firstHalf + Transpiler.expressionToString(e) + secondHalf;
	}
}
