class GY951 implements Device {
    private Protocol protocol;
    private State state = State.OFF;

    public GY951(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override public String getName() { return "GY951"; }
    @Override public String getDevType() { return "Sensor"; }
    @Override public String getSensType() { return "IMUSensor"; }
    @Override public State getState() { return state; }
    @Override public void turnON() { state = State.ON; protocol.write("turnON"); System.out.println("GY951: Turning ON."); }
    @Override public void turnOFF() { state = State.OFF; protocol.write("turnOFF"); System.out.println("GY951: Turning OFF."); }
    @Override public void setProtocol(Protocol protocol) { this.protocol = protocol; }
    @Override public Protocol getProtocol() { return protocol; }
    @Override public void print(String data) {}
    @Override public void write(String data) {}
    @Override public String read() { return protocol.read(); }
    @Override public void setSpeed(String speed) {}
    @Override public String data2String() { return "Gyro: 100deg/s"; }
}