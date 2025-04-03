public class Bluetooth extends WirelessIO
{
	public	Bluetooth(Protocol protocol)
	{
		super(protocol);
		if (!protocol.getProtocolName().equals("UART"))	// Check if the protocol is UART
			throw new IllegalArgumentException("Bluetooth requires UART protocol.");
	}

	@Override public void	turnOn()	// Set the state ON and write the necessary prompts
	{
		System.out.println("Bluetooth: Turning ON.");
		protocol.write("turnON");
		state = State.ON;
	}

	@Override public void turnOff()		// Set the state OFF and write the necessary prompts
	{
		System.out.println("Bluetooth: Turning OFF.");
		protocol.write("turnOFF");
		state = State.OFF;
	}

	@Override public String getName()	// Return the name of the device
	{
		return ("Bluetooth");
	}
}
