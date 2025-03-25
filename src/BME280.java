class BME280 implements Device {
    private Protocol protocol;
    private State state = State.OFF;

    public BME280(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override public String getName() { return "BME280"; }
    @Override public String getDevType() { return "Sensor"; }
    @Override public String getSensType() { return "TempSensor"; }
    @Override public State getState() { return state; }
    @Override public void turnON() { state = State.ON; protocol.write("turnON"); System.out.println("BME280: Turning ON."); }
    @Override public void turnOFF() { state = State.OFF; protocol.write("turnOFF"); System.out.println("BME280: Turning OFF."); }
    @Override public void setProtocol(Protocol protocol) { this.protocol = protocol; }
    @Override public Protocol getProtocol() { return protocol; }
    @Override public void print(String data) {}
    @Override public void write(String data) {}
    @Override public String read() { return protocol.read(); }
    @Override public void setSpeed(String speed) {}
    @Override public String data2String() { return "Temp: 25.00C"; }
}