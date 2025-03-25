import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

abstract class AbstractProtocol implements Protocol {
    protected Device occupyingDevice;
    private String protocolName;
    private String logFile;
    private Stack<String> logEntries = new Stack<>();

    public AbstractProtocol(String protocolName, String logDir, int portID) {
        this.protocolName = protocolName;
        this.logFile = logDir + "/" + protocolName + "_" + portID + ".log";
        logEntries.push("Port Opened.");
    }

    @Override
    public String getProtocolName() {
        return protocolName;
    }

    @Override
    public void setOccupyingDevice(Device dev) {
        occupyingDevice = dev;
    }

    @Override
    public Device getOccupyingDevice() {
        return occupyingDevice;
    }

    @Override
    public void write(String data) {
        logEntries.push("Writing \"" + data + "\".");
    }

    @Override
    public String read() {
        logEntries.push("Reading.");
        return "Some Data";
    }

    @Override
    public void close() {
        try (FileWriter writer = new FileWriter(logFile)) {
            while (!logEntries.isEmpty()) {
                writer.write(logEntries.pop() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }
}