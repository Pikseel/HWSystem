/**
 * Bluetooth wireless IO implementation.
 */
public class Bluetooth extends WirelessIO {
    public Bluetooth(Protocol protocol) {
        super(protocol);
        if (!protocol.getProtocolName().equals("UART"))
            throw new IllegalArgumentException("Bluetooth requires UART protocol.");
    }

    @Override
    public void turnOn() {
        System.out.println("Bluetooth: Turning ON.");
        protocol.write("turnON");
        state = State.ON;
    }

    @Override
    public void turnOff() {
        System.out.println("Bluetooth: Turning OFF.");
        protocol.write("turnOFF");
        state = State.OFF;
    }

    @Override
    public String getName() {
        return "Bluetooth";
    }
}