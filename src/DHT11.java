class DHT11 implements Device {
    private Protocol protocol;
    private State state = State.OFF;

    public DHT11(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override public String getName() { return "DHT11"; }
    @Override public String getDevType() { return "Sensor"; }
    @Override public String getSensType() { return "TempSensor"; }
    @Override public State getState() { return state; }
    @Override public void turnON() { state = State.ON; protocol.write("turnON"); System.out.println("DHT11: Turning ON."); }
    @Override public void turnOFF() { state = State.OFF; protocol.write("turnOFF"); System.out.println("DHT11: Turning OFF."); }
    @Override public void setProtocol(Protocol protocol) { this.protocol = protocol; }
    @Override public Protocol getProtocol() { return protocol; }
    @Override public void print(String data) {}
    @Override public void write(String data) {}
    @Override public String read() { System.out.println("read() called for " + protocol.getProtocolName() + "_2"); return protocol.read(); }
    @Override public void setSpeed(String speed) {}
    @Override public String data2String() { System.out.println("getTemp() called"); return "Temp: 24.00C"; }
}