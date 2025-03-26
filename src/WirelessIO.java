public abstract class WirelessIO extends Device
{
	public	WirelessIO(Protocol protocol)
	{
		super(protocol);
	}

	@Override public String	getDevType()
	{
		return ("WirelessIO");
	}

	public void	sendData(String data)
	{
		protocol.write(data);
	}

	public String	recvData()
	{
		return protocol.read();
	}
}
