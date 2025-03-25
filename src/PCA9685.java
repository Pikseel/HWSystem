class PCA9685 implements Device {
    private Protocol protocol;
    private State state = State.OFF;

    public PCA9685(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override public String getName() { return "PCA9685"; }
    @Override public String getDevType() { return "MotorDriver"; }
    @Override public String getSensType() { return ""; }
    @Override public State getState() { return state; }
    @Override public void turnON() { state = State.ON; protocol.write("turnON"); System.out.println("PCA9685: Turning ON."); }
    @Override public void turnOFF() { state = State.OFF; protocol.write("turnOFF"); System.out.println("PCA9685: Turning OFF."); }
    @Override public void setProtocol(Protocol protocol) { this.protocol = protocol; }
    @Override public Protocol getProtocol() { return protocol; }
    @Override public void print(String data) {}
    @Override public void write(String data) {}
    @Override public String read() { return ""; }
    @Override public void setSpeed(String speed) { protocol.write(speed); }
    @Override public String data2String() { return ""; }
}