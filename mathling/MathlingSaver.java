package mathling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Functionality for saving/loading {@code Mathling}s for further use.
 *
 * @see mathling.Mathling
 */
public class MathlingSaver
{
	/**
	 * Saves a {@code Mathling} to a file.
	 * 
	 * @param m		The {@code Mathling} to save.
	 * @param name	The name of the file to save to.
	 */
	public static void saveMathling(final Mathling m, final String name)
	{
		try
		{
			FileOutputStream file = new FileOutputStream(name);
			ObjectOutputStream objWriter = new ObjectOutputStream(file);

			objWriter.writeObject(m);
			objWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return;
	}
	
	/**
	 * Loads a {@code Mathling} from a specified file.
	 * @param name	The name of the file to load from.
	 * @return		The loaded {@code Mathling}. Will be null
	 * 				if not found.
	 */
	public static Mathling loadMathling(final String name)
	{
		try
		{
			FileInputStream file = new FileInputStream(name);
			ObjectInputStream objReader = new ObjectInputStream(file);
			
			Mathling loaded = (Mathling) objReader.readObject();
			objReader.close();
			
			return loaded;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
