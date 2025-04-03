public abstract class MotorDriver extends Device
{
	public  MotorDriver(Protocol protocol)
	{
		super(protocol);
	}

	@Override public String getDevType()	// Return the type of the device
	{
		return ("MotorDriver");
	}

	public abstract void    setMotorSpeed(int speed);
}
