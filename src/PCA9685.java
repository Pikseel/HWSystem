public class PCA9685 extends MotorDriver
{
	public	PCA9685(Protocol protocol)
	{	// I2C protocol is required for PCA9685
		super(protocol);
		if (!protocol.getProtocolName().equals("I2C"))
			throw new IllegalArgumentException("PCA9685 requires I2C protocol.");
	}

	@Override public void	turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("PCA9685: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()	// Set the state OFF and write the necessary prompts
	{
		System.out.println("PCA9685: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()	// Return the name of the device
	{
		return ("PCA9685");
	}

	@Override public void	setMotorSpeed(int speed)	// Set the speed of the motor
	{
		protocol.write(String.valueOf(speed));
		System.out.println("PCA9685: Setting speed to " + speed + ".");
	}
}
