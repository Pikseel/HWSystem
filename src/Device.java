abstract class Device {
    protected Protocol protocol;
    protected State state = State.OFF;

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public State getState() {
        return state;
    }

    public void turnON() {
        state = State.ON;
        protocol.write("turnON");
        System.out.println(getName() + ": Turning ON.");
    }

    public void turnOFF() {
        state = State.OFF;
        protocol.write("turnOFF");
        System.out.println(getName() + ": Turning OFF.");
    }

    public abstract String getName();

    public abstract String getDevType();
}