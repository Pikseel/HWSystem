/**
 * LCD display implementation.
 */
public class LCD extends Display {
    public LCD(Protocol protocol) {
        super(protocol);
        if (!protocol.getProtocolName().equals("I2C"))
            throw new IllegalArgumentException("LCD requires I2C protocol.");
    }

    @Override
    public void turnOn() {
        System.out.println("LCD: Turning ON.");
        protocol.write("turnON");
        state = State.ON;
    }

    @Override
    public void turnOff() {
        System.out.println("LCD: Turning OFF.");
        protocol.write("turnOFF");
        state = State.OFF;
    }

    @Override
    public String getName() {
        return "LCD";
    }
}