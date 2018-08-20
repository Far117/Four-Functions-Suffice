package Mathling;

import java.util.Comparator;

public class MathlingComparator
{
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
