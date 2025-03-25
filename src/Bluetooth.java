class Bluetooth extends WirelessIO {
    public Bluetooth(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getName() {
        return "Bluetooth";
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