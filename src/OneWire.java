import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OneWire implements Protocol
{
	private List<String>	logEntries = new ArrayList<>();
	private String	logDir;
	private int		portID;

	public	OneWire(String logDir, int portID)
	{
		this.logDir = logDir;
		this.portID = portID;
		logEntries.add("Port Opened.");
	}

	@Override public String	getProtocolName()
	{
		return ("OneWire");
	}

	@Override public String	read()
	{
		logEntries.add("Reading.");
		return ("Some Data");
	}

	@Override public void	write(String data)
	{
		logEntries.add("Writing \"" + data + "\".");
	}

	@Override public void	close()
	{
		try (FileWriter writer = new FileWriter(logDir + "/" + getProtocolName() + "_" + portID + ".log"))
		{
			for (int i = logEntries.size() - 1; i >= 0; i--)
				writer.write(logEntries.get(i) + "\n");
		}
		catch (IOException e)
		{
			System.err.println("Error writing log for " + getProtocolName() + "_" + portID);
		}
	}
}
