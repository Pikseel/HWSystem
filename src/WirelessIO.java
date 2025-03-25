abstract class WirelessIO extends Device {
    public WirelessIO(Protocol protocol) {
        this.protocol = protocol;
    }

    public abstract String read();

    public abstract void write(String data);

    @Override
    public String getDevType() {
        return "WirelessIO";
    }
}