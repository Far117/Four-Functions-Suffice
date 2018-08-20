package Mathling;
import Main.Computable;
import dataTree.Expression;

public class Mathling
{
	private Expression expr = null;
	private double accuracy = 0;
	
	final static double minTest = -360,
						maxTest = 360;
	
	// 100% will always mutate the root node of the expression,
	// 0% will always mutate a terminal node
	private double mutationSignificance = 30;
	
	private static final double[] odds = {25, 25};
	
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
		for (int i = 0; i < 100; i++)
		{
			final double rand = RandomExpression.randMinMax(minTest, maxTest);
			this.accuracy += (this.collapse(rand) - f.compute(rand));
		}
		
		this.accuracy /= 100;
		this.accuracy = Math.abs(this.accuracy);
		
		return;
	}
	
	public Mathling getMutation()
	{
		return new Mathling(RandomExpression.mutateExpression(this.expr,
				this.mutationSignificance,
				Mathling.odds));
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
