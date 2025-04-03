import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class I2C implements Protocol
{
	private Stack<String>	logEntries = new Stack<>();	// Stack to store log entries
	private String			logDir;						// Directory to store logs
	private int				portID;						// Port ID for the I2C protocol

	public	I2C(String logDir, int portID)
	{	// Constructor to initialize the log directory and port ID
		this.logDir = logDir;
		this.portID = portID;
		logEntries.push("Port Opened.");	// Log the opening of the port
	}

	@Override public String	getProtocolName()	// Return the name of the protocol
	{
		return ("I2C");
	}

	@Override public String	read()				// Imitate reading data
	{
		logEntries.push("Reading.");
		return ("Some Data");
	}

	@Override public void	write(String data)	// Write data to the I2C protocol
	{
		logEntries.push("Writing \"" + data + "\".");
	}

	@Override public void	close()				// Close the I2C protocol
	{
		try (FileWriter writer = new FileWriter(logDir + "/" + getProtocolName() + "_" + portID + ".log"))
		{	// Write the log entries to file
			while (!logEntries.isEmpty())
				writer.write(logEntries.pop() + "\n");
		}
		catch (IOException e)
		{	// Handle any IO exceptions
			System.err.println("Error writing log for " + getProtocolName() + "_" + portID);
		}
	}
}
