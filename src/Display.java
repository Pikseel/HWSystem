/**
 * Abstract base class for displays.
 */
public abstract class Display extends Device {
    public Display(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getDevType() {
        return "Display";
    }

    public void printData(String data) {
        protocol.write(data);
        System.out.println(getName() + ": Printing \"" + data + "\".");
    }
}