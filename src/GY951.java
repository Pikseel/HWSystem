public class GY951 extends IMUSensor
{
	public  GY951(Protocol protocol)
	{
		super(protocol);
		if (!protocol.getProtocolName().equals("SPI") && !protocol.getProtocolName().equals("UART"))
			throw new IllegalArgumentException("GY951 requires SPI or UART protocol.");
	}

	@Override public void   turnOn()
	{
		System.out.println("GY951: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void   turnOff()
	{
		System.out.println("GY951: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String getName()
	{
		return ("GY951");
	}

	@Override public float getAccel()
	{
		protocol.read();
		return (1.00f);
	}

	@Override public float getRot()
	{
		protocol.read();
		return (0.50f);
	}
}
