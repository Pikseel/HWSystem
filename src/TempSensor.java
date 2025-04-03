public abstract class TempSensor extends Sensor
{
	public	TempSensor(Protocol protocol)
	{
		super(protocol);
	}

	public abstract float	getTemp();

	@Override public String getSensType()	// Return the type of the sensor
	{
		return ("TempSensor");
	}

	@Override public String data2String()	// Convert the data to a string format
	{
		return (String.format("Temp: %.2fC", getTemp()));
	}
}
