/**
 * Wifi wireless IO implementation.
 */
public class Wifi extends WirelessIO {
    public Wifi(Protocol protocol) {
        super(protocol);
        if (!protocol.getProtocolName().equals("SPI") && !protocol.getProtocolName().equals("UART"))
            throw new IllegalArgumentException("Wifi requires SPI or UART protocol.");
    }

    @Override
    public void turnOn() {
        System.out.println("Wifi: Turning ON.");
        protocol.write("turnON");
        state = State.ON;
    }

    @Override
    public void turnOff() {
        System.out.println("Wifi: Turning OFF.");
        protocol.write("turnOFF");
        state = State.OFF;
    }

    @Override
    public String getName() {
        return "Wifi";
    }
}