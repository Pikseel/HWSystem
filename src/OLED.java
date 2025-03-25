class OLED implements Device {
    private Protocol protocol;
    private State state = State.OFF;

    public OLED(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override public String getName() { return "OLED"; }
    @Override public String getDevType() { return "Display"; }
    @Override public String getSensType() { return ""; }
    @Override public State getState() { return state; }
    @Override public void turnON() { state = State.ON; protocol.write("turnON"); System.out.println("OLED: Turning ON."); }
    @Override public void turnOFF() { state = State.OFF; protocol.write("turnOFF"); System.out.println("OLED: Turning OFF."); }
    @Override public void setProtocol(Protocol protocol) { this.protocol = protocol; }
    @Override public Protocol getProtocol() { return protocol; }
    @Override public void print(String data) { protocol.write(data); System.out.println("OLED: Printing \"" + data + "\"."); }
    @Override public void write(String data) {}
    @Override public String read() { return ""; }
    @Override public void setSpeed(String speed) {}
    @Override public String data2String() { return ""; }
}