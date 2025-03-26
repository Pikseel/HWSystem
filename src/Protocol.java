public interface Protocol
{
	String	getProtocolName();
	String	read();
	void	write(String input);
	void	close();
}
