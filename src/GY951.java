public class GY951 extends IMUSensor
{
	public  GY951(Protocol protocol)
	{	// Check if the protocol is SPI or UART
		super(protocol);
		if (!protocol.getProtocolName().equals("SPI") && !protocol.getProtocolName().equals("UART"))
			throw new IllegalArgumentException("GY951 requires SPI or UART protocol.");
	}

	@Override public void   turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("GY951: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void   turnOff()	// Set the state OFF and write the necessary prompts
	{
		System.out.println("GY951: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String getName()	// Return the name of the device
	{
		return ("GY951");
	}

	@Override public float getAccel()	// Return the constant value from the sensor
	{
		protocol.read();
		return (1.00f);
	}

	@Override public float getRot()		// Return the constant value from the sensor
	{
		protocol.read();
		return (0.50f);
	}
}
