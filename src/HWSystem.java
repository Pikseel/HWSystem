import java.util.*;

class HWSystem {
    private List<Protocol> ports;
    private Map<Integer, Sensor> sensors = new HashMap<>();
    private Map<Integer, Display> displays = new HashMap<>();
    private Map<Integer, WirelessIO> wirelessIOs = new HashMap<>();
    private Map<Integer, MotorDriver> motorDrivers = new HashMap<>();
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
        if (devType.contains("Sensor") && sensors.size() >= maxSensors) {
            System.err.println("Maximum number of sensors reached");
            return;
        } else if (devType.equals("Display") && displays.size() >= maxDisplays) {
            System.err.println("Maximum number of displays reached");
            return;
        } else if (devType.equals("WirelessIO") && wirelessIOs.size() >= maxWirelessIOs) {
            System.err.println("Maximum number of wireless adapters reached");
            return;
        } else if (devType.equals("MotorDriver") && motorDrivers.size() >= maxMotorDrivers) {
            System.err.println("Maximum number of motor drivers reached");
            return;
        }
        device.setProtocol(port);
        port.setOccupyingDevice(device);
        if (devType.contains("Sensor")) {
            if (sensors.containsKey(devID)) {
                System.err.println("Sensor devID " + devID + " already used");
                return;
            }
            sensors.put(devID, (Sensor) device);
        } else if (devType.equals("Display")) {
            if (displays.containsKey(devID)) {
                System.err.println("Display devID " + devID + " already used");
                return;
            }
            displays.put(devID, (Display) device);
        } else if (devType.equals("WirelessIO")) {
            if (wirelessIOs.containsKey(devID)) {
                System.err.println("WirelessIO devID " + devID + " already used");
                return;
            }
            wirelessIOs.put(devID, (WirelessIO) device);
        } else if (devType.equals("MotorDriver")) {
            if (motorDrivers.containsKey(devID)) {
                System.err.println("MotorDriver devID " + devID + " already used");
                return;
            }
            motorDrivers.put(devID, (MotorDriver) device);
        }
        System.out.println("Device added.");
    }

    public void executeCommand(String commandLine) {
        String[] tokens = commandLine.split("\\s+");
        String command = tokens[0];
        switch (command) {
            case "list":
                if (tokens.length == 2) {
                    if (tokens[1].equals("ports")) {
                        System.out.println("list of ports:");
                        for (int i = 0; i < ports.size(); i++) {
                            Protocol port = ports.get(i);
                            Device dev = port.getOccupyingDevice();
                            String devInfo = dev == null ? "empty" : "occupied " + dev.getName() + " " + dev.getDevType();
                            if (dev instanceof Sensor) {
                                Sensor sensor = (Sensor) dev;
                                devInfo += " " + sensor.getSensType();
                                for (Map.Entry<Integer, Sensor> entry : sensors.entrySet()) {
                                    if (entry.getValue() == sensor) {
                                        devInfo += " " + entry.getKey();
                                        break;
                                    }
                                }
                            }
                            devInfo += dev != null ? " " + dev.getState() : "";
                            System.out.println(i + " " + port.getProtocolName() + " " + devInfo);
                        }
                    } else if (tokens[1].equals("Sensor")) {
                        System.out.println("list of Sensors:");
                        Iterator<Map.Entry<Integer, Sensor>> it = sensors.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, Sensor> entry = it.next();
                            Sensor sensor = entry.getValue();
                            int portID = ports.indexOf(sensor.protocol);
                            System.out.println(sensor.getName() + " " + entry.getKey() + " " + portID + " " + sensor.protocol.getProtocolName());
                        }
                    } else if (tokens[1].equals("Display")) {
                        System.out.println("list of Displays:");
                        Iterator<Map.Entry<Integer, Display>> it = displays.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, Display> entry = it.next();
                            Display display = entry.getValue();
                            int portID = ports.indexOf(display.protocol);
                            System.out.println(display.getName() + " " + entry.getKey() + " " + portID + " " + display.protocol.getProtocolName());
                        }
                    } else if (tokens[1].equals("MotorDriver")) {
                        System.out.println("list of MotorDrivers:");
                        Iterator<Map.Entry<Integer, MotorDriver>> it = motorDrivers.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, MotorDriver> entry = it.next();
                            MotorDriver motor = entry.getValue();
                            int portID = ports.indexOf(motor.protocol);
                            System.out.println(motor.getName() + " " + entry.getKey() + " " + portID + " " + motor.protocol.getProtocolName());
                        }
                    } else if (tokens[1].equals("WirelessIO")) {
                        System.out.println("list of WirelessIOs:");
                        Iterator<Map.Entry<Integer, WirelessIO>> it = wirelessIOs.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, WirelessIO> entry = it.next();
                            WirelessIO wireless = entry.getValue();
                            int portID = ports.indexOf(wireless.protocol);
                            System.out.println(wireless.getName() + " " + entry.getKey() + " " + portID + " " + wireless.protocol.getProtocolName());
                        }
                    } else {
                        System.err.println("Unknown list command: " + commandLine);
                    }
                } else {
                    System.err.println("Unknown list command: " + commandLine);
                }
                break;
            case "addDev":
                addDevice(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                break;
            case "rmDev":
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
                String devType = dev.getDevType();
                if (devType.contains("Sensor")) {
                    sensors.values().remove(dev);
                } else if (devType.equals("Display")) {
                    displays.values().remove(dev);
                } else if (devType.equals("WirelessIO")) {
                    wirelessIOs.values().remove(dev);
                } else if (devType.equals("MotorDriver")) {
                    motorDrivers.values().remove(dev);
                }
                port.setOccupyingDevice(null);
                System.out.println("Device removed.");
                break;
            case "turnON":
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
            case "turnOFF":
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
            case "readSensor":
                readSensor(Integer.parseInt(tokens[1]));
                break;
            case "printDisplay":
                int devID = Integer.parseInt(tokens[1]);
                Display display = displays.get(devID);
                if (display == null) {
                    System.err.println("Display with devID " + devID + " not found");
                    return;
                }
                String data = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));
                display.print(data);
                break;
            case "writeWireless":
                devID = Integer.parseInt(tokens[1]);
                WirelessIO wireless = wirelessIOs.get(devID);
                if (wireless == null) {
                    System.err.println("WirelessIO with devID " + devID + " not found");
                    return;
                }
                data = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));
                if (wireless.getState() != State.ON) {
                    System.err.println("Device is not active.");
                    return;
                }
                wireless.write(data);
                System.out.println(wireless.getName() + ": Sending \"" + data + "\".");
                break;
            case "readWireless":
                devID = Integer.parseInt(tokens[1]);
                wireless = wirelessIOs.get(devID);
                if (wireless == null) {
                    System.err.println("WirelessIO with devID " + devID + " not found");
                    return;
                }
                if (wireless.getState() != State.ON) {
                    System.err.println("Device is not active.");
                    return;
                }
                System.out.println(wireless.getName() + ": Received \"" + wireless.read() + "\".");
                break;
            case "setMotorSpeed":
                devID = Integer.parseInt(tokens[1]);
                MotorDriver motor = motorDrivers.get(devID);
                if (motor == null) {
                    System.err.println("MotorDriver with devID " + devID + " not found");
                    return;
                }
                String speed = tokens[2];
                if (motor.getState() != State.ON) {
                    System.err.println("Device is not active.");
                    return;
                }
                motor.setSpeed(speed);
                System.out.println(motor.getName() + ": Setting speed to " + speed + ".");
                break;
            default:
                System.err.println("Unknown command: " + command);
        }
    }

    private void readSensor(int devID) {
        Sensor sensor = sensors.get(devID);
        if (sensor == null) {
            System.err.println("Sensor with devID " + devID + " not found");
            return;
        }
        if (sensor.getState() != State.ON) {
            System.err.println("Device is not active.");
            return;
        }
        System.out.printf("%s %s Sensor: %s%n", sensor.getName(), sensor.getSensType(), sensor.data2String());
    }
}