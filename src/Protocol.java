public interface Protocol
{	// This interface defines the methods for a protocol
	String	getProtocolName();
	String	read();
	void	write(String input);
	void	close();
}
