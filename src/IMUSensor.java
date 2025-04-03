public abstract class IMUSensor extends Sensor
{
	public	IMUSensor(Protocol protocol)
	{
		super(protocol);
	}

	// abstract methods to be implemented by subclasses
	public abstract float	getAccel();
	public abstract float	getRot();

	@Override public String	getSensType()
	{	// Return the type of the sensor
		return ("IMUSensor");
	}

	@Override public String	data2String()
	{	// Convert the data to a string format
		return (String.format("Accel: %.2f, Rot: %.2f", getAccel(), getRot()));
	}
}
