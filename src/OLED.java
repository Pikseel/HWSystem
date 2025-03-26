public class OLED extends Display
{
	public	OLED(Protocol protocol)
	{
		super(protocol);
		if (!protocol.getProtocolName().equals("SPI"))
			throw new IllegalArgumentException("OLED requires SPI protocol.");
	}

	@Override public void	turnOn()
	{
		System.out.println("OLED: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()
	{
		System.out.println("OLED: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()
	{
		return ("OLED");
	}
}
