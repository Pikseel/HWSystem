public class BME280 extends TempSensor
{
	public	BME280(Protocol protocol)
	{	// Check if the protocol is I2C or SPI
		super(protocol);
		if (!protocol.getProtocolName().equals("I2C") && !protocol.getProtocolName().equals("SPI"))
			throw new IllegalArgumentException("BME280 requires I2C or SPI protocol.");
	}

	@Override public void	turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("BME280: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()	// Set the state OFF and write the necessary prompts
	{
		System.out.println("BME280: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()	// Return the name of the device
	{
		return ("BME280");
	}

	@Override public float	getTemp()	// Return the constant value from the sensor
	{
		protocol.read();
		return (24.00f);
	}
}
