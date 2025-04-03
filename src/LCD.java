public class LCD extends Display
{
	public	LCD(Protocol protocol)		// Check if the protocol is I2C
	{
		super(protocol);
		if (!protocol.getProtocolName().equals("I2C"))
			throw new IllegalArgumentException("LCD requires I2C protocol.");
	}

	@Override public void	turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("LCD: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()	// Set the state OFF and write the necessary prompts
	{
		System.out.println("LCD: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()	// Return the name of the device
	{
		return ("LCD");
	}
}
