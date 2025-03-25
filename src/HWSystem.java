import java.util.*;

class HWSystem {
    private List<Protocol> ports;
    private Map<Integer, Device> devices = new HashMap<>(); // Unified map for all devices
    private Map<Integer, Integer> deviceToPort = new HashMap<>(); // Maps devID to portID
    private int maxSensors, maxDisplays, maxWirelessIOs, maxMotorDrivers;

    public HWSystem(List<Protocol> ports, Map<String, Integer> maxDevices) {
        this.ports = ports;
        this.maxSensors = maxDevices.getOrDefault("sensors", 0);
        this.maxDisplays = maxDevices.getOrDefault("displays", 0);
        this.maxWirelessIOs = maxDevices.getOrDefault("wireless adapters", 0);
        this.maxMotorDrivers = maxDevices.getOrDefault("motor drivers", 0);
    }

    private Device createDevice(String devName, Protocol port) {
        switch (devName) {
            case "DHT11": return new DHT11(port);
            case "BME280": return new BME280(port);
            case "MPU6050": return new MPU6050(port);
            case "GY951": return new GY951(port);
            case "LCD": return new LCD(port);
            case "OLED": return new OLED(port);
            case "Bluetooth": return new Bluetooth(port);
            case "Wifi": return new Wifi(port);
            case "PCA9685": return new PCA9685(port);
            case "SparkFunMD": return new SparkFunMD(port);
            default: return null;
        }
    }

    private void addDevice(String devName, int portID, int devID) {
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID: " + portID);
            return;
        }
        Protocol port = ports.get(portID);
        Device device = createDevice(devName, port);
        if (device == null) {
            System.err.println("Unknown device: " + devName);
            return;
        }
        String protocolName = port.getProtocolName();
        String devType = device.getDevType();
        if (devType.contains("Sensor") && !protocolName.equals("OneWire")) {
            System.err.println(devName + " only supports OneWire protocol");
            return;
        } else if (devType.equals("Display") && !protocolName.equals("I2C")) {
            System.err.println(devName + " only supports I2C protocol");
            return;
        } else if (devType.equals("WirelessIO") && !protocolName.equals("UART")) {
            System.err.println(devName + " only supports UART protocol");
            return;
        } else if (devType.equals("MotorDriver") && !(protocolName.equals("I2C") || protocolName.equals("SPI"))) {
            System.err.println(devName + " only supports I2C or SPI protocol");
            return;
        }
        if (port.getOccupyingDevice() != null) {
            System.err.println("Port " + portID + " is already occupied");
            return;
        }
        int count = 0;
        Iterator<Map.Entry<Integer, Device>> it = devices.entrySet().iterator();
        while (it.hasNext()) {
            Device d = it.next().getValue();
            if (d.getDevType().equals(devType)) count++;
        }
        if (devType.contains("Sensor") && count >= maxSensors) {
            System.err.println("Maximum number of sensors reached");
            return;
        } else if (devType.equals("Display") && count >= maxDisplays) {
            System.err.println("Maximum number of displays reached");
            return;
        } else if (devType.equals("WirelessIO") && count >= maxWirelessIOs) {
            System.err.println("Maximum number of wireless adapters reached");
            return;
        } else if (devType.equals("MotorDriver") && count >= maxMotorDrivers) {
            System.err.println("Maximum number of motor drivers reached");
            return;
        }
        if (devices.containsKey(devID)) {
            System.err.println(devType + " devID " + devID + " already used");
            return;
        }
        device.setProtocol(port);
        port.setOccupyingDevice(device);
        devices.put(devID, device);
        deviceToPort.put(devID, portID);
        System.out.println("Device added.");
    }

    public void executeCommand(String commandLine) {
        String[] tokens = commandLine.split("\\s+");
        String command = tokens[0];
        switch (command) {
            case "list":
                if (tokens.length == 2) {
                    if (tokens[1].equals("ports")) { // O(n)
                        System.out.println("list of ports:");
                        for (int i = 0; i < ports.size(); i++) {
                            Protocol port = ports.get(i);
                            Device dev = port.getOccupyingDevice();
                            String devInfo = dev == null ? "empty" : "occupied " + dev.getName() + " " + dev.getDevType();
                            if (dev != null && dev.getDevType().contains("Sensor")) {
                                devInfo += " " + dev.getSensType();
                                Iterator<Map.Entry<Integer, Device>> it = devices.entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry<Integer, Device> entry = it.next();
                                    if (entry.getValue() == dev) {
                                        devInfo += " " + entry.getKey();
                                        break;
                                    }
                                }
                            }
                            devInfo += dev != null ? " " + dev.getState() : "";
                            System.out.println(i + " " + port.getProtocolName() + " " + devInfo);
                        }
                    } else if (tokens[1].equals("Sensor")) { // O(n)
                        System.out.println("list of Sensors:");
                        Iterator<Map.Entry<Integer, Device>> it = devices.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, Device> entry = it.next();
                            Device dev = entry.getValue();
                            if (dev.getDevType().contains("Sensor")) {
                                int portID = deviceToPort.get(entry.getKey());
                                System.out.println(dev.getName() + " " + entry.getKey() + " " + portID + " " + dev.getProtocol().getProtocolName());
                            }
                        }
                    } else if (tokens[1].equals("Display")) { // O(n)
                        System.out.println("list of Displays:");
                        Iterator<Map.Entry<Integer, Device>> it = devices.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, Device> entry = it.next();
                            Device dev = entry.getValue();
                            if (dev.getDevType().equals("Display")) {
                                int portID = deviceToPort.get(entry.getKey());
                                System.out.println(dev.getName() + " " + entry.getKey() + " " + portID + " " + dev.getProtocol().getProtocolName());
                            }
                        }
                    } else if (tokens[1].equals("MotorDriver")) { // O(n)
                        System.out.println("list of MotorDrivers:");
                        Iterator<Map.Entry<Integer, Device>> it = devices.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, Device> entry = it.next();
                            Device dev = entry.getValue();
                            if (dev.getDevType().equals("MotorDriver")) {
                                int portID = deviceToPort.get(entry.getKey());
                                System.out.println(dev.getName() + " " + entry.getKey() + " " + portID + " " + dev.getProtocol().getProtocolName());
                            }
                        }
                    } else if (tokens[1].equals("WirelessIO")) { // O(n)
                        System.out.println("list of WirelessIOs:");
                        Iterator<Map.Entry<Integer, Device>> it = devices.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, Device> entry = it.next();
                            Device dev = entry.getValue();
                            if (dev.getDevType().equals("WirelessIO")) {
                                int portID = deviceToPort.get(entry.getKey());
                                System.out.println(dev.getName() + " " + entry.getKey() + " " + portID + " " + dev.getProtocol().getProtocolName());
                            }
                        }
                    } else {
                        System.err.println("Unknown list command: " + commandLine);
                    }
                } else {
                    System.err.println("Unknown list command: " + commandLine);
                }
                break;
            case "addDev": // O(1) except for counting, which is O(n) once
                addDevice(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                break;
            case "rmDev": // O(1)
                int portID = Integer.parseInt(tokens[1]);
                if (portID < 0 || portID >= ports.size()) {
                    System.err.println("Invalid port ID: " + portID);
                    return;
                }
                Protocol port = ports.get(portID);
                Device dev = port.getOccupyingDevice();
                if (dev == null) {
                    System.err.println("Port " + portID + " is empty");
                    return;
                }
                if (dev.getState() == State.ON) {
                    System.err.println("Device is active. Command failed.");
                    return;
                }
                Iterator<Map.Entry<Integer, Device>> it = devices.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Device> entry = it.next();
                    if (entry.getValue() == dev) {
                        devices.remove(entry.getKey());
                        deviceToPort.remove(entry.getKey());
                        break;
                    }
                }
                port.setOccupyingDevice(null);
                System.out.println("Device removed.");
                break;
            case "turnON": // O(1)
                portID = Integer.parseInt(tokens[1]);
                if (portID < 0 || portID >= ports.size()) {
                    System.err.println("Invalid port ID: " + portID);
                    return;
                }
                port = ports.get(portID);
                dev = port.getOccupyingDevice();
                if (dev == null) {
                    System.err.println("No device on port " + portID);
                    return;
                }
                dev.turnON();
                break;
            case "turnOFF": // O(1)
                portID = Integer.parseInt(tokens[1]);
                if (portID < 0 || portID >= ports.size()) {
                    System.err.println("Invalid port ID: " + portID);
                    return;
                }
                port = ports.get(portID);
                dev = port.getOccupyingDevice();
                if (dev == null) {
                    System.err.println("No device on port " + portID);
                    return;
                }
                dev.turnOFF();
                break;
            case "readSensor": // O(1)
                int devID = Integer.parseInt(tokens[1]);
                dev = devices.get(devID);
                if (dev == null || !dev.getDevType().contains("Sensor")) {
                    System.err.println("Sensor with devID " + devID + " not found");
                    return;
                }
                if (dev.getState() != State.ON) {
                    System.err.println("Device is not active.");
                    return;
                }
                System.out.printf("%s %s Sensor: %s%n", dev.getName(), dev.getSensType(), dev.data2String());
                break;
            case "printDisplay": // O(1)
                devID = Integer.parseInt(tokens[1]);
                dev = devices.get(devID);
                if (dev == null || !dev.getDevType().equals("Display")) {
                    System.err.println("Display with devID " + devID + " not found");
                    return;
                }
                String data = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));
                dev.print(data);
                break;
            case "writeWireless": // O(1)
                devID = Integer.parseInt(tokens[1]);
                dev = devices.get(devID);
                if (dev == null || !dev.getDevType().equals("WirelessIO")) {
                    System.err.println("WirelessIO with devID " + devID + " not found");
                    return;
                }
                if (dev.getState() != State.ON) {
                    System.err.println("Device is not active.");
                    return;
                }
                data = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));
                dev.write(data);
                System.out.println(dev.getName() + ": Sending \"" + data + "\".");
                break;
            case "readWireless": // O(1)
                devID = Integer.parseInt(tokens[1]);
                dev = devices.get(devID);
                if (dev == null || !dev.getDevType().equals("WirelessIO")) {
                    System.err.println("WirelessIO with devID " + devID + " not found");
                    return;
                }
                if (dev.getState() != State.ON) {
                    System.err.println("Device is not active.");
                    return;
                }
                System.out.println(dev.getName() + ": Received \"" + dev.read() + "\".");
                break;
            case "setMotorSpeed": // O(1)
                devID = Integer.parseInt(tokens[1]);
                dev = devices.get(devID);
                if (dev == null || !dev.getDevType().equals("MotorDriver")) {
                    System.err.println("MotorDriver with devID " + devID + " not found");
                    return;
                }
                if (dev.getState() != State.ON) {
                    System.err.println("Device is not active.");
                    return;
                }
                String speed = tokens[2];
                dev.setSpeed(speed);
                System.out.println(dev.getName() + ": Setting speed to " + speed + ".");
                break;
            default:
                System.err.println("Unknown command: " + command);
        }
    }

    public void close() {
        for (int i = 0; i < ports.size(); i++) {
            ports.get(i).close();
        }
    }
}