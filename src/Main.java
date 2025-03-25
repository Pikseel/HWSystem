import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String configFile = System.getProperty("CONFIG_FILE");
        String logDir = System.getProperty("LOG_DIR");
        if (configFile == null || logDir == null) {
            System.err.println("CONFIG_FILE and LOG_DIR must be specified");
            return;
        }

        List<Protocol> ports = new ArrayList<>();
        Map<String, Integer> maxDevices = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line = br.readLine();
            String[] portNames = line.split(":")[1].split(",");
            for (int i = 0; i < portNames.length; i++) {
                String name = portNames[i].trim();
                switch (name) {
                    case "I2C": ports.add(new I2C(logDir, i)); break;
                    case "SPI": ports.add(new SPI(logDir, i)); break;
                    case "OneWire": ports.add(new OneWire(logDir, i)); break;
                    case "UART": ports.add(new UART(logDir, i)); break;
                }
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                maxDevices.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            }
        }

        HWSystem system = new HWSystem(ports, maxDevices);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String command;
        while ((command = input.readLine()) != null && !command.equals("exit")) {
            system.executeCommand(command);
        }
        system.close();
    }
}