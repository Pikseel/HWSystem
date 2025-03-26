/**
 * Interface for communication protocols.
 */
public interface Protocol {
    String getProtocolName();
    String read();
    void write(String input);
    void close();
}