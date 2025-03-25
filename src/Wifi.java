class Wifi extends WirelessIO {
    public Wifi(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getName() {
        return "Wifi";
    }

    @Override
    public String read() {
        return protocol.read() + "Some Data";
    }

    @Override
    public void write(String data) {
        protocol.write(data);
    }
}