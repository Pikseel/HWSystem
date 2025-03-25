import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Main <config_file> <log_directory>");
            return;
        }
        String configFile = args[0];
        String logDirectory = args[1];

        Map<String, Integer> maxDevices = new HashMap<>();
        List<Protocol> ports = parseConfig(configFile, maxDevices);
        if (ports == null) {
            return;
        }

        HWSystem system = new HWSystem(ports, maxDevices);
        List<String> commands = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            if (command.equals("exit")) {
                break;
            }
            commands.add(command);
        }
        scanner.close();

        Iterator<String> cmdIterator = commands.iterator();
        while (cmdIterator.hasNext()) {
            system.executeCommand(cmdIterator.next());
        }
        System.out.println("Exiting...");

        Iterator<Protocol> portIterator = ports.iterator();
        while (portIterator.hasNext()) {
            portIterator.next().writeLog(logDirectory);
        }
    }

    private static List<Protocol> parseConfig(String configFile, Map<String, Integer> maxDevices) {
        try (Scanner scanner = new Scanner(new File(configFile))) {
            List<Protocol> ports = new ArrayList<>();
            String[] protocolLine = scanner.nextLine().split(":")[1].split(",");
            for (int i = 0; i < protocolLine.length; i++) {
                String proto = protocolLine[i].trim();
                switch (proto) {
                    case "I2C": ports.add(new I2C(i)); break;
                    case "SPI": ports.add(new SPI(i)); break;
                    case "OneWire": ports.add(new OneWire(i)); break;
                    case "UART": ports.add(new UART(i)); break;
                    default:
                        System.err.println("Unknown protocol in config: " + proto);
                        return null;
                }
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split(":");
                String key = parts[0].replace("# of ", "").trim();
                maxDevices.put(key, Integer.parseInt(parts[1].trim()));
            }
            return ports;
        } catch (FileNotFoundException e) {
            System.err.println("Configuration file not found: " + configFile);
            return null;
        } catch (Exception e) {
            System.err.println("Error parsing config file: " + e.getMessage());
            return null;
        }
    }
}