/**
 * SparkFunMD motor driver implementation.
 */
public class SparkFunMD extends MotorDriver {
    public SparkFunMD(Protocol protocol) {
        super(protocol);
        if (!protocol.getProtocolName().equals("SPI"))
            throw new IllegalArgumentException("SparkFunMD requires SPI protocol.");
    }

    @Override
    public void turnOn() {
        System.out.println("SparkFunMD: Turning ON.");
        protocol.write("turnON");
        state = State.ON;
    }

    @Override
    public void turnOff() {
        System.out.println("SparkFunMD: Turning OFF.");
        protocol.write("turnOFF");
        state = State.OFF;
    }

    @Override
    public String getName() {
        return "SparkFunMD";
    }

    @Override
    public void setMotorSpeed(int speed) {
        protocol.write(String.valueOf(speed));
        System.out.println("SparkFunMD: Setting speed to " + speed + ".");
    }
}