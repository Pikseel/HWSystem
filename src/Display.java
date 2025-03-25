abstract class Display extends Device {
    public Display() { // Default constructor
    }

    public Display(Protocol protocol) { // Constructor with Protocol
        this.protocol = protocol;
    }

    public void print(String data) {
        if (state == State.ON) {
            protocol.write(data);
            System.out.println(getName() + ": Printing \"" + data + "\".");
        } else {
            System.err.println("Device is not active.");
        }
    }

    @Override
    public String getDevType() {
        return "Display";
    }
}