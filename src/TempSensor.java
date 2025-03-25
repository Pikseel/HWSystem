abstract class TempSensor extends Sensor {
    public TempSensor(Protocol protocol) {
        super(protocol);
    }

    public Float getTemp() {
        System.out.println("getTemp() called"); // Debug
        if (state == State.ON) {
            protocol.read();
            return 24.00f;
        }
        return -1f;
    }

    @Override
    public String getSensType() {
        return "TempSensor";
    }

    @Override
    public String data2String() {
        float temp = getTemp();
        if (temp >= 0) {
            return String.format("Temp: %.2fC", temp);
        }
        return "Device is off.";
    }
}