public class SparkFunMD extends MotorDriver
{
	public	SparkFunMD(Protocol protocol)
	{	// SPI protocol is required
		super(protocol);
		if (!protocol.getProtocolName().equals("SPI"))
			throw new IllegalArgumentException("SparkFunMD requires SPI protocol.");
	}

	@Override public void	turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("SparkFunMD: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()	// Set the state OFF and write the necessary prompts
	{
		System.out.println("SparkFunMD: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()	// Return the name of the device
	{
		return ("SparkFunMD");
	}

	@Override public void	setMotorSpeed(int speed)	// Set the speed of the motor
	{
		protocol.write(String.valueOf(speed));
		System.out.println("SparkFunMD: Setting speed to " + speed + ".");
	}
}
