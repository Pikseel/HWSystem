public class PCA9685 extends MotorDriver
{
	public	PCA9685(Protocol protocol)
	{
		super(protocol);
		if (!protocol.getProtocolName().equals("I2C"))
			throw new IllegalArgumentException("PCA9685 requires I2C protocol.");
	}

	@Override public void	turnOn()
	{
		System.out.println("PCA9685: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()
	{
		System.out.println("PCA9685: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()
	{
		return ("PCA9685");
	}

	@Override public void	setMotorSpeed(int speed)
	{
		protocol.write(String.valueOf(speed));
		System.out.println("PCA9685: Setting speed to " + speed + ".");
	}
}
