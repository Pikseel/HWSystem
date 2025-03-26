import java.io.*;
import java.util.*;

/**
 * Main class to run the HWSystem, reading commands and config from environment variables.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String configFile = System.getenv("CONFIG_FILE");
        String logDir = System.getenv("LOG_DIR");
        if (configFile == null || logDir == null) {
            System.err.println("CONFIG_FILE and LOG_DIR must be set");
            return;
        }

        HWSystem system = new HWSystem(configFile, logDir);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Queue<String> commands = new LinkedList<>(); // Queue for commands
        String command;

        // Store commands in Queue until 'exit'
        while ((command = input.readLine()) != null) {
            commands.add(command);
            if (command.equals("exit")) break;
        }

        // Execute commands after 'exit'
        while (!commands.isEmpty()) {
            system.executeCommand(commands.poll());
            if (!commands.isEmpty()) System.out.println(); // Single newline between outputs
        }
        system.closePorts();
    }
}