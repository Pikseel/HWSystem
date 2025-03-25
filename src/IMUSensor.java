abstract class IMUSensor extends Sensor {
    public IMUSensor(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getSensType() {
        return "IMUSensor";
    }

    @Override
    public String data2String() { // Implement abstract method
        return "IMU Data";
    }
}