public abstract class WirelessIO extends Device
{
	public	WirelessIO(Protocol protocol)
	{
		super(protocol);
	}

	@Override public String	getDevType()	// Return the type of the device
	{
		return ("WirelessIO");
	}

	public void	sendData(String data)		// Send data to the device
	{
		protocol.write(data);
	}

	public String	recvData()				// Receive data from the device
	{
		return protocol.read();
	}
}
