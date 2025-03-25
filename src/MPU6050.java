class MPU6050 implements Device {
    private Protocol protocol;
    private State state = State.OFF;

    public MPU6050(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override public String getName() { return "MPU6050"; }
    @Override public String getDevType() { return "Sensor"; }
    @Override public String getSensType() { return "IMUSensor"; }
    @Override public State getState() { return state; }
    @Override public void turnON() { state = State.ON; protocol.write("turnON"); System.out.println("MPU6050: Turning ON."); }
    @Override public void turnOFF() { state = State.OFF; protocol.write("turnOFF"); System.out.println("MPU6050: Turning OFF."); }
    @Override public void setProtocol(Protocol protocol) { this.protocol = protocol; }
    @Override public Protocol getProtocol() { return protocol; }
    @Override public void print(String data) {}
    @Override public void write(String data) {}
    @Override public String read() { return protocol.read(); }
    @Override public void setSpeed(String speed) {}
    @Override public String data2String() { return "Accel: 1.0g"; }
}