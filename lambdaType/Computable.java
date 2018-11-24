package lambdaType;

import expression.DivideByZeroError;

/**
 * This is an interface meant to be used with lambdas, and
 * encapsulates lambdas representing {@code Real->Real} functions.
 *
 */
public interface Computable
{
	double compute(double input) throws DivideByZeroError;
}
