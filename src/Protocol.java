interface Protocol {
    String getProtocolName();
    String read();
    void write(String data);
    void writeLog(String logDirectory);
    Device getOccupyingDevice();
    void setOccupyingDevice(Device device);
}