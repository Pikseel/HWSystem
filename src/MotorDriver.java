abstract class MotorDriver extends Device {
    public MotorDriver() { // Default constructor
    }

    public MotorDriver(Protocol protocol) { // Constructor with Protocol
        this.protocol = protocol;
    }

    public void setSpeed(String speed) {
        protocol.write(speed);
    }

    @Override
    public String getDevType() {
        return "MotorDriver";
    }
}