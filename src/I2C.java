import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class I2C implements Protocol
{
	private Stack<String>	logEntries = new Stack<>();
	private String	logDir;
	private int		portID;

	public	I2C(String logDir, int portID)
	{
		this.logDir = logDir;
		this.portID = portID;
		logEntries.push("Port Opened.");
	}

	@Override public String	getProtocolName()
	{
		return ("I2C");
	}

	@Override public String	read()
	{
		logEntries.push("Reading.");
		return ("Some Data");
	}

	@Override public void	write(String data)
	{
		logEntries.push("Writing \"" + data + "\".");
	}

	@Override public void	close()
	{
		try (FileWriter writer = new FileWriter(logDir + "/" + getProtocolName() + "_" + portID + ".log"))
		{
			while (!logEntries.isEmpty())
				writer.write(logEntries.pop() + "\n");
		}
		catch (IOException e)
		{
			System.err.println("Error writing log for " + getProtocolName() + "_" + portID);
		}
	}
}
