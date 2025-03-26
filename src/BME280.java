/**
 * BME280 temperature sensor implementation.
 */
public class BME280 extends TempSensor {
    public BME280(Protocol protocol) {
        super(protocol);
        if (!protocol.getProtocolName().equals("I2C") && !protocol.getProtocolName().equals("SPI"))
            throw new IllegalArgumentException("BME280 requires I2C or SPI protocol.");
    }

    @Override
    public void turnOn() {
        System.out.println("BME280: Turning ON.");
        protocol.write("turnON");
        state = State.ON;
    }

    @Override
    public void turnOff() {
        System.out.println("BME280: Turning OFF.");
        protocol.write("turnOFF");
        state = State.OFF;
    }

    @Override
    public String getName() {
        return "BME280";
    }

    @Override
    public float getTemp() {
        protocol.read();
        return 24.00f; // HW3 constant
    }
}