interface Protocol {
    String getProtocolName();
    void setOccupyingDevice(Device dev);
    Device getOccupyingDevice();
    void write(String data);
    String read();
    void close();
}