package transpile;

import expression.Expression;

public class ToJava implements Transpiler
{
	private static final String firstHalf  = 
			"class FourFunctionsSuffice\n{\n\tpublic static double func(final double x)\n\t{\n\t\treturn ",
								secondHalf =
			";\n\t}\n}";
								
	@Override
	public String transpile(Expression e)
	{
		return firstHalf + Transpiler.expressionToString(e) + secondHalf;
	}
}
