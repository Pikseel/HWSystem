public class DHT11 extends TempSensor
{
	public	DHT11(Protocol protocol)
	{	// Check if the protocol is OneWire
		super(protocol);
		if (!protocol.getProtocolName().equals("OneWire"))
			throw new IllegalArgumentException("DHT11 requires OneWire protocol.");
	}

	@Override public void	turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("DHT11: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()	// Set the state OFF and write the necessary prompts
	{
		System.out.println("DHT11: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()	// Return the name of the device
	{
		return ("DHT11");
	}

	@Override public float getTemp() 	// Return the constant value from the sensor
	{
		protocol.read();
		return (24.00f);
	}
}
