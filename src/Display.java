public abstract class Display extends Device
{
    public  Display(Protocol protocol)
    {
        super(protocol);
    }

    @Override public String getDevType()    // Return the type of the device
    {
        return ("Display");
    }

    public void printData(String data)      // Imitate the printing of data
    {
        protocol.write(data);
        System.out.println(getName() + ": Printing \"" + data + "\".");
    }
}
