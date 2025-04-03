public class Wifi extends WirelessIO
{
	public	Wifi(Protocol protocol)
	{	// Wifi requires SPI or UART protocol
		super(protocol);
		if (!protocol.getProtocolName().equals("SPI") && !protocol.getProtocolName().equals("UART"))
			throw new IllegalArgumentException("Wifi requires SPI or UART protocol.");
	}

	@Override public void	turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("Wifi: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()	// Set the state OFF and write the necessary prompts
	{
		System.out.println("Wifi: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()	// Return the name of the device
	{
		return ("Wifi");
	}
}
