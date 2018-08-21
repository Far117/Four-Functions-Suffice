package transpile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import dataTree.Expression;

public class ToPython implements Transpiler
{
	private static String contents = "def func(x):\n\treturn ";

	@Override
	public String transpile(Expression e)
	{
		return contents + Transpiler.expressionToString(e);
	}

	@Override
	public void transpileToFile(String fileName, Expression e)
	{
		try {
			Files.write(Paths.get(fileName), transpile(e).getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return;
	}
}
