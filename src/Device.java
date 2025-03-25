interface Device {
    String getName();
    String getDevType();
    String getSensType(); // Returns "" if not a sensor
    State getState();
    void turnON();
    void turnOFF();
    void setProtocol(Protocol protocol);
    Protocol getProtocol();
    void print(String data);
    void write(String data);
    String read();
    void setSpeed(String speed);
    String data2String(); // Returns "" if not a sensor
}