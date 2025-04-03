import java.io.*;
import java.util.*;

public class Main
{
	public static void	main(String[] args) throws IOException
	{
		String	configFile = System.getenv("CONFIG_FILE");	// Get the config file from environment variable
		String	logDir = System.getenv("LOG_DIR");			// Get the log directory from environment variable
		if (configFile == null || logDir == null)				// Check if the config file and log directory are set
		{
			System.err.println("CONFIG_FILE and LOG_DIR must be set");
			return ;
		}

		String			command;														// Command to be executed
		HWSystem		system = new HWSystem(configFile, logDir);						// Create a new HWSystem object with the config file and log directory
		BufferedReader	input = new BufferedReader(new InputStreamReader(System.in));	// BufferedReader to read input from the console
		Queue<String>	commands = new LinkedList<>();									// Queue to store commands

		while ((command = input.readLine()) != null)			// Read commands from the console
		{
			commands.add(command);								// Add the command to the queue
			if (command.equals("exit"))
				break ;
		}

		while (!commands.isEmpty())								// Execute commands until the queue is empty

		{
			system.executeCommand(commands.poll());				// Execute the command at the front of the queue
			if (!commands.isEmpty())							// If there are more commands in the queue print newline
				System.out.println();
		}
		system.closePorts();									// Close all ports
	}
}
