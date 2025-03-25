import java.io.*;
import java.util.*;

abstract class AbstractProtocol implements Protocol {
    private Stack<String> log = new Stack<>();
    private String protocolName;
    private int portID;
    private Device occupyingDevice = null;

    public AbstractProtocol(String protocolName, int portID) {
        this.protocolName = protocolName;
        this.portID = portID;
        log.push("Port Opened.");
    }

    @Override
    public String getProtocolName() {
        return protocolName;
    }

    @Override
    public String read() {
        System.out.println("read() called for " + protocolName + "_" + portID); // Debug
        log.push("Reading.");
        return "";
    }

    @Override
    public void write(String data) {
        log.push("Writing \"" + data + "\".");
    }

    @Override
    public void writeLog(String logDirectory) {
        String fileName = protocolName + "_" + portID + ".log";
        try (PrintWriter writer = new PrintWriter(new File(logDirectory, fileName))) {
            while (!log.isEmpty()) {
                writer.println(log.pop());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error writing log file " + fileName + ": " + e.getMessage());
        }
    }

    @Override
    public Device getOccupyingDevice() {
        return occupyingDevice;
    }

    @Override
    public void setOccupyingDevice(Device device) {
        this.occupyingDevice = device;
    }
}