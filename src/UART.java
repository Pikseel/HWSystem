import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UART implements Protocol
{
	private List<String>	logEntries = new ArrayList<>();		// List to store log entries
	private String	logDir;						// Directory to store logs
	private int		portID;						// Port ID for the UART protocol

	public	UART(String logDir, int portID)		// Constructor to initialize the log directory and port ID
	{
		this.logDir = logDir;
		this.portID = portID;
		logEntries.add("Port Opened.");
	}

	@Override public String	getProtocolName()	// Return the name of the protocol
	{
		return ("UART");
	}

	@Override public String	read()				// Imitate reading data
	{
		logEntries.add("Reading.");
		return ("Some Data");
	}

	@Override public void	write(String data)	// Write data to the UART protocol
	{
		logEntries.add("Writing \"" + data + "\".");
	}

	@Override public void	close()				// Close the UART protocol
	{
		try (FileWriter writer = new FileWriter(logDir + "/" + getProtocolName() + "_" + portID + ".log"))
		{
			for (int i = logEntries.size() - 1; i >= 0; i--)
				writer.write(logEntries.get(i) + "\n");	// Write the log entries to file in reverse order
		}
		catch (IOException e)
		{
			System.err.println("Error writing log for " + getProtocolName() + "_" + portID);
		}
	}
}
