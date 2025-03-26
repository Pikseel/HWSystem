public class DHT11 extends TempSensor
{
	public	DHT11(Protocol protocol)
	{
		super(protocol);
		if (!protocol.getProtocolName().equals("OneWire"))
			throw new IllegalArgumentException("DHT11 requires OneWire protocol.");
	}

	@Override public void	turnOn()
	{
		System.out.println("DHT11: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()
	{
		System.out.println("DHT11: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()
	{
		return ("DHT11");
	}

	@Override public float getTemp() 
	{
		protocol.read();
		return (24.00f);
	}
}
