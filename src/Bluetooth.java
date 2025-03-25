class Bluetooth implements Device {
    private Protocol protocol;
    private State state = State.OFF;

    public Bluetooth(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override public String getName() { return "Bluetooth"; }
    @Override public String getDevType() { return "WirelessIO"; }
    @Override public String getSensType() { return ""; }
    @Override public State getState() { return state; }
    @Override public void turnON() { state = State.ON; protocol.write("turnON"); System.out.println("Bluetooth: Turning ON."); }
    @Override public void turnOFF() { state = State.OFF; protocol.write("turnOFF"); System.out.println("Bluetooth: Turning OFF."); }
    @Override public void setProtocol(Protocol protocol) { this.protocol = protocol; }
    @Override public Protocol getProtocol() { return protocol; }
    @Override public void print(String data) {}
    @Override public void write(String data) { protocol.write(data); }
    @Override public String read() { System.out.println("read() called for " + protocol.getProtocolName() + "_3"); return protocol.read(); }
    @Override public void setSpeed(String speed) {}
    @Override public String data2String() { return ""; }
}