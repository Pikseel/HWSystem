public class MPU6050 extends IMUSensor
{
	public	MPU6050(Protocol protocol)
	{
		super(protocol);
		if (!protocol.getProtocolName().equals("I2C"))
			throw new IllegalArgumentException("MPU6050 requires I2C protocol.");
	}

	@Override public void	turnOn()
	{
		System.out.println("MPU6050: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void	turnOff()
	{
		System.out.println("MPU6050: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String	getName()
	{
		return ("MPU6050");
	}

	@Override public float	getAccel()
	{
		protocol.read();
		return (1.00f);
	}

	@Override public float	getRot()
	{
		protocol.read();
		return (0.50f);
	}
}
