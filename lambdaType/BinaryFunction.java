package lambdaType;

/**
 * 
 * A {@code BinaryFunction} is meant to encapsulate functions such as
 * {@code x + y}, which take two inputs and produce one output.
 *
 */
public interface BinaryFunction
{
	double compute(double a, double b);
}
