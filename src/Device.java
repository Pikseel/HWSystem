public abstract class Device
{
	protected Protocol  protocol;
	protected State     state = State.OFF;	// Default state is OFF

	public Device(Protocol protocol)
	{
		this.protocol = protocol;			// Set the protocol while creating the device
	}

	// abstract methods to be implemented by subclasses
	public abstract void    turnOn();
	public abstract void    turnOff();
	public abstract String  getName();
	public abstract String  getDevType();

	public State getState()					// Return the state of the device
	{
		return (state);
	}
}
