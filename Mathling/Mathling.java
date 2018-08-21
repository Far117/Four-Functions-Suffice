package Mathling;
import Main.Computable;
import dataTree.Expression;
import dataTree.ExpressionCopier;

public class Mathling
{
	private Expression expr = null;
	private double accuracy = 0;
	
	final static double minTest = -360,
						maxTest = 360;
	
	// 100% will always mutate the root node of the expression,
	// 0% will always mutate a terminal node
	private double mutationSignificance = 30;
	
	private static final double[] odds = {30, 30};
	
	public Mathling()
	{
		this.expr = RandomExpression.newExpression(odds);
		return;
	}
	
	public Mathling(Expression e)
	{
		this.expr = e;
		return;
	}
	
	public String printExpression()
	{
		return this.expr.print();
	}
	
	public double collapse(double input)
	{
		return this.expr.collapse(input);
	}
	
	public void calculateAccuracy(Computable f)
	{
		this.accuracy = 0;
		for (int i = (int) minTest; i < (int) maxTest; i++)
		{
			//final double rand = RandomExpression.randMinMax(minTest, maxTest);
			//this.accuracy += Math.abs((this.collapse(rand) - f.compute(rand)));
			this.accuracy += Math.abs((this.collapse(i) - f.compute(i)));
		}
		
		this.accuracy /= Math.abs((int) minTest - (int) maxTest);
		
		return;
	}
	
	public Mathling getMutation()
	{
		return new Mathling(
				RandomExpression.mutateExpression(
						ExpressionCopier.copy(this.expr),
						this.mutationSignificance,
						Mathling.odds));
	}
	
	public Expression getExpression()
	{
		return ExpressionCopier.copy(this.expr);
	}
	
	public double getAccuracy()
	{
		return this.accuracy;
	}
	
	public void setAccuracy(double newAccuracy)
	{
		this.accuracy = newAccuracy;
	}
	
}
