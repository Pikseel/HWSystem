public abstract class Sensor extends Device
{
	public	Sensor(Protocol protocol)
	{
		super(protocol);
	}

	@Override public String	getDevType()	// Return the type of the device
	{
		return (getSensType() + " Sensor");
	}

	// abstract methods to be implemented by subclasses
	public abstract String	getSensType();
	public abstract String	data2String();
}
