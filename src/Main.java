import java.io.*;
import java.util.*;

public class Main
{
	public static void	main(String[] args) throws IOException
	{
		String	configFile = System.getenv("CONFIG_FILE");
		String	logDir = System.getenv("LOG_DIR");
		if (configFile == null || logDir == null)
		{
			System.err.println("CONFIG_FILE and LOG_DIR must be set");
			return ;
		}

		String			command;
		HWSystem		system = new HWSystem(configFile, logDir);
		BufferedReader	input = new BufferedReader(new InputStreamReader(System.in));
		Queue<String>	commands = new LinkedList<>();

		while ((command = input.readLine()) != null)
		{
			commands.add(command);
			if (command.equals("exit"))
				break ;
		}

		while (!commands.isEmpty())
		{
			system.executeCommand(commands.poll());
			if (!commands.isEmpty())
				System.out.println();
		}
		system.closePorts();
	}
}
