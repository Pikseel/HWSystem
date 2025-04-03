public class OLED extends Display
{
	public	OLED(Protocol protocol)		// Check if the protocol is SPI
	{
		super(protocol);
		if (!protocol.getProtocolName().equals("SPI"))
			throw new IllegalArgumentException("OLED requires SPI protocol.");
	}

	@Override public void	turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("OLED: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()	// Set the state OFF and write the necessary prompts
	{
		System.out.println("OLED: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()	// Return the name of the device
	{
		return ("OLED");
	}
}
