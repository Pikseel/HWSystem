public abstract class TempSensor extends Sensor
{
	public	TempSensor(Protocol protocol)
	{
		super(protocol);
	}

	public abstract float	getTemp();

	@Override public String getSensType()
	{
		return ("TempSensor");
	}

	@Override public String data2String()
	{
		return (String.format("Temp: %.2fC", getTemp()));
	}
}
