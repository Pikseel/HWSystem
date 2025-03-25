abstract class Sensor extends Device {
    public Sensor(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getDevType() {
        return "Sensor";
    }

    public abstract String getSensType();

    public abstract String data2String();
}