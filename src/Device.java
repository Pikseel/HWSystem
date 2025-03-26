/**
 * Abstract base class for all devices.
 */
public abstract class Device {
    protected Protocol protocol;
    protected State state = State.OFF;

    public Device(Protocol protocol) {
        this.protocol = protocol;
    }

    public abstract void turnOn();
    public abstract void turnOff();
    public abstract String getName();
    public abstract String getDevType();

    public State getState() {
        return state;
    }
}