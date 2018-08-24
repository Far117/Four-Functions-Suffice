package mathling;

import java.util.Comparator;

/**
 * Provides a function which compares the accuracy of two {@code Mathling}'s
 * for sorting purposes, in order to find the most accurate one.
 *
 */
public class MathlingComparator
{
	/**
	 * Finds the most accurate {@code Mathling}.
	 */
	public static Comparator<Mathling> comparator = new Comparator<Mathling>()
	{
		public int compare(Mathling m1, Mathling m2)
		{
			if (m1.getAccuracy() == m2.getAccuracy())
				return 0;
			else
				return (m2.getAccuracy() > m1.getAccuracy())? -1 : 1;
		}
	};
}
