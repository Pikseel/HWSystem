import java.util.*;
import java.io.*;
// Got a little help figuring out how to use the BufferedReader and FileReader classes and 
// why would using them could benefit me. Also got help with the ArrayList and List classes
// because i was confused about maintaining complexity as O(c.n) (O(1) for lists) but i failed.
// That's why i used hashmap which recommended by AI. Got help abpout exceptions
// was challenging. So i got help because i didn't know what to throw in some cases and how to thro
public class HWSystem
{
	private List<Protocol>				ports;			// ArrayList for O(1) access
	private Map<Integer, Sensor>		sensors;		// HashMap for O(1) lookup
	private Map<Integer, Display>		displays;
	private Map<Integer, WirelessIO>	wireless;
	private Map<Integer, MotorDriver>	motorDrivers;
	private Map<Integer, Device>		portToDevice;	// Maps portID to Device

	public	HWSystem(String configFile, String logDir)
	{
		ports = new ArrayList<>();
		sensors = new HashMap<>();
		displays = new HashMap<>();
		wireless = new HashMap<>();
		motorDrivers = new HashMap<>();
		portToDevice = new HashMap<>();
		loadConfig(configFile, logDir);
	}

	private void	loadConfig(String filename, String logDir)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			String[]	protocols = br.readLine().split(": ")[1].split(",");
			for (int i = 0; i < protocols.length; i++)
			{
				switch (protocols[i].trim())
				{
					case ("I2C"):
						ports.add(new I2C(logDir, i));
						break;
					case ("SPI"):
						ports.add(new SPI(logDir, i));
						break;
					case ("OneWire"):
						ports.add(new OneWire(logDir, i));
						break;
					case ("UART"):
						ports.add(new UART(logDir, i));
						break;
				}
			}
		}
		catch (IOException e)
		{
			System.err.println("Error reading config: " + e.getMessage());
		}
	}

	private Device	createDevice(String devName, Protocol protocol)
	{
		try
		{
			switch (devName)
			{
				case ("DHT11"):
					return (new DHT11(protocol));
				case ("BME280"):
					return (new BME280(protocol));
				case ("MPU6050"):
					return (new MPU6050(protocol));
				case ("GY951"):
					return (new GY951(protocol));
				case ("LCD"):
					return (new LCD(protocol));
				case ("OLED"):
					return (new OLED(protocol));
				case ("Bluetooth"):
					return (new Bluetooth(protocol));
				case ("Wifi"):
					return (new Wifi(protocol));
				case ("PCA9685"):
					return (new PCA9685(protocol));
				case ("SparkFunMD"):
					return (new SparkFunMD(protocol));
				default: System.err.println("Unknown device: " + devName);
					return (null);
			}
		}
		catch (IllegalArgumentException e)
		{
			System.err.println(e.getMessage()); return null;
		}
	}

	public void	executeCommand(String commandLine)
	{
		String[]	tokens = commandLine.split("\\s+");
		String		command = tokens[0];

		switch (command)
		{
			case ("list"):
				if (tokens[1].equals("ports"))
					listPorts();
				else
					listDevices(tokens[1]);
				break;
			case ("addDev"):
				addDevice(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
				break;
			case ("rmDev"):
				removeDevice(Integer.parseInt(tokens[1]));
				break;
			case ("turnON"):
				turnON(Integer.parseInt(tokens[1]));
				break;
			case ("turnOFF"):
				turnOFF(Integer.parseInt(tokens[1]));
				break;
			case ("readSensor"):
				readSensor(Integer.parseInt(tokens[1]));
				break;
			case ("printDisplay"):
				printDisplay(Integer.parseInt(tokens[1]), String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length)));
				break;
			case ("writeWireless"):
				writeWireless(Integer.parseInt(tokens[1]), String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length)));
				break;
			case ("readWireless"):
				readWireless(Integer.parseInt(tokens[1]));
				break;
			case ("setMotorSpeed"):
				setMotorSpeed(Integer.parseInt(tokens[1]), tokens[2]);
				break;
			case ("exit"):
				System.out.println("Exiting...");
				break;
			default:
				System.err.println("Unknown command: " + command);
		}
	}

	private void	addDevice(String devName, int portID, int devID)
	{
		if (portID < 0 || portID >= ports.size())
		{
			System.err.println("Invalid port ID: " + portID);
			return ;
		}
		if (portToDevice.containsKey(portID))
		{
			System.err.println("Port " + portID + " is already occupied");
			return ;
		}
		Device	device = createDevice(devName, ports.get(portID));
		if (device == null)
			return ;

		String	devType = device.getDevType();
		if (devType.contains("Sensor"))
		{
			if (sensors.containsKey(devID))
			{
				System.err.println("Sensor devID " + devID + " already used");
				return ;
			}
			sensors.put(devID, (Sensor) device);
		}
		else if (devType.equals("Display"))
		{
			if (displays.containsKey(devID))
			{
				System.err.println("Display devID " + devID + " already used");
				return ;
			}
			displays.put(devID, (Display) device);
		}
		else if (devType.equals("WirelessIO"))
		{
			if (wireless.containsKey(devID))
			{
				System.err.println("WirelessIO devID " + devID + " already used");
				return ;
			}
			wireless.put(devID, (WirelessIO) device);
		}
		else if (devType.equals("MotorDriver"))
		{
			if (motorDrivers.containsKey(devID))
			{
				System.err.println("MotorDriver devID " + devID + " already used");
				return ;
			}
			motorDrivers.put(devID, (MotorDriver) device);
		}
		portToDevice.put(portID, device);
		System.out.println("Device added.");
	}

	private void	removeDevice(int portID)
	{
		if (!portToDevice.containsKey(portID))
		{
			System.err.println("Port " + portID + " is empty or invalid");
			return ;
		}
		Device	device = portToDevice.get(portID);
		if (device.getState() == State.ON)
		{
			System.err.println("Device is active. Command failed.");
			return ;
		}
		String	devType = device.getDevType();
		if (devType.contains("Sensor"))
			sensors.values().remove(device);
		else if (devType.equals("Display"))
			displays.values().remove(device);
		else if (devType.equals("WirelessIO"))
			wireless.values().remove(device);
		else if (devType.equals("MotorDriver"))
			motorDrivers.values().remove(device);
		portToDevice.remove(portID);
		System.out.println("Device removed.");
	}

	private void	turnON(int portID)
	{
		Device	device = portToDevice.get(portID);
		if (device == null)
		{
			System.err.println("No device on port " + portID);
			return ;
		}
		if (device.getState() == State.ON)
		{
			System.err.println("Device on port " + portID + " already ON");
			return ;
		}
		device.turnOn();
	}

	private void	turnOFF(int portID)
	{
		Device	device = portToDevice.get(portID);
		if (device == null)
		{
			System.err.println("No device on port " + portID);
			return ;
		}
		if (device.getState() == State.OFF)
		{
			System.err.println("Device on port " + portID + " already OFF");
			return ;
		}
		device.turnOff();
	}

	private void	listPorts()
	{
		System.out.println("list of ports:");
		Iterator<Protocol>	portIt = ports.iterator();
		int	portID = 0;
		while (portIt.hasNext())
		{
			Protocol	protocol = portIt.next();
			Device		device = portToDevice.get(portID);
			if (device == null)
				System.out.println(portID + " " + protocol.getProtocolName() + " empty");
			else
			{
				String	devType = device.getDevType();
				int		devID = findDevID(device, devType);
				System.out.println(portID + " " + protocol.getProtocolName() + " occupied " +
					device.getName() + " " + devType + " " + devID + " " + device.getState());
			}
			portID++;
		}
	}

	private int	findDevID(Device device, String devType)
	{
		if (devType.contains("Sensor"))
		{
			Iterator<Map.Entry<Integer, Sensor>>	it = sensors.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Integer, Sensor> entry = it.next();
				if (entry.getValue() == device)
					return (entry.getKey());
			}
		}
		else if (devType.equals("Display"))
		{
			Iterator<Map.Entry<Integer, Display>>	it = displays.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Integer, Display> entry = it.next();
				if (entry.getValue() == device)
					return (entry.getKey());
			}
		}
		else if (devType.equals("WirelessIO"))
		{
			Iterator<Map.Entry<Integer, WirelessIO>>	it = wireless.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Integer, WirelessIO> entry = it.next();
				if (entry.getValue() == device)
					return (entry.getKey());
			}
		}
		else if (devType.equals("MotorDriver"))
		{
			Iterator<Map.Entry<Integer, MotorDriver>>	it = motorDrivers.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Integer, MotorDriver> entry = it.next();
				if (entry.getValue() == device)
					return (entry.getKey());
			}
		}
		return (-1);
	}

	private void	listDevices(String devType)
	{
		System.out.println("list of " + devType + "s:");
		if (devType.contains("Sensor"))
		{
			Iterator<Map.Entry<Integer, Sensor>>	it = sensors.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Integer, Sensor>	entry = it.next();
				Sensor	device = entry.getValue();
				int		portID = findPortID(device);
				System.out.println(device.getName() + " " + entry.getKey() + " " + portID + " " +
					ports.get(portID).getProtocolName());
			}
		}
		else if (devType.equals("Display"))
		{
			Iterator<Map.Entry<Integer, Display>>	it = displays.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Integer, Display>	entry = it.next();
				Display	device = entry.getValue();
				int		portID = findPortID(device);
				System.out.println(device.getName() + " " + entry.getKey() + " " + portID + " " +
					ports.get(portID).getProtocolName());
			}
		}
		else if (devType.equals("WirelessIO"))
		{
			Iterator<Map.Entry<Integer, WirelessIO>>	it = wireless.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Integer, WirelessIO>	entry = it.next();
				WirelessIO	device = entry.getValue();
				int			portID = findPortID(device);
				System.out.println(device.getName() + " " + entry.getKey() + " " + portID + " " +
					ports.get(portID).getProtocolName());
			}
		}
		else if (devType.equals("MotorDriver"))
		{
			Iterator<Map.Entry<Integer, MotorDriver>>	it = motorDrivers.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Integer, MotorDriver>	entry = it.next();
				MotorDriver	device = entry.getValue();
				int			portID = findPortID(device);
				System.out.println(device.getName() + " " + entry.getKey() + " " + portID + " " +
					ports.get(portID).getProtocolName());
			}
		}
		else
			System.err.println("Unknown device type: " + devType);
	}

	private int	findPortID(Device device)
	{
		Iterator<Map.Entry<Integer, Device>>	it = portToDevice.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<Integer, Device>	entry = it.next();
			if (entry.getValue() == device)
				return (entry.getKey());
		}
		return (-1);
	}

	private void	readSensor(int devID)
	{
		Sensor	sensor = sensors.get(devID);
		if (sensor == null)
		{
			System.err.println("Sensor with devID " + devID + " not found");
			return ;
		}
		if (sensor.getState() != State.ON)
		{
			System.err.println("Device is not active. Command failed.");
			return ;
		}
		System.out.println(sensor.getName() + " " + sensor.getSensType() + " Sensor: " + sensor.data2String());
	}

	private void	printDisplay(int devID, String data)
	{
		Display	display = displays.get(devID);
		if (display == null)
		{
			System.err.println("Display with devID " + devID + " not found");
			return ;
		}
		if (display.getState() != State.ON)
		{
			System.err.println("Device is not active. Command failed.");
			return ;
		}
		display.printData(data);
	}

	private void	writeWireless(int devID, String data)
	{
		WirelessIO	wirelessIO = wireless.get(devID);
		if (wirelessIO == null)
		{
			System.err.println("WirelessIO with devID " + devID + " not found");
			return ;
		}
		if (wirelessIO.getState() != State.ON)
		{
			System.err.println("Device is not active. Command failed.");
			return ;
		}
		wirelessIO.sendData(data);
		System.out.println(wirelessIO.getName() + ": Sending \"" + data + "\".");
	}

	private void	readWireless(int devID)
	{
		WirelessIO	wirelessIO = wireless.get(devID);
		if (wirelessIO == null)
		{
			System.err.println("WirelessIO with devID " + devID + " not found");
			return ;
		}
		if (wirelessIO.getState() != State.ON)
		{
			System.err.println("Device is not active. Command failed.");
			return ;
		}
		System.out.println(wirelessIO.getName() + ": Received \"" + wirelessIO.recvData() + "\".");
	}

	private void	setMotorSpeed(int devID, String speed)
	{
		MotorDriver	motor = motorDrivers.get(devID);
		if (motor == null)
		{
			System.err.println("MotorDriver with devID " + devID + " not found");
			return ;
		}
		if (motor.getState() != State.ON)
		{
			System.err.println("Device is not active. Command failed.");
			return ;
		}
		motor.setMotorSpeed(Integer.parseInt(speed));
	}

	public void	closePorts()
	{
		Iterator<Protocol>	it = ports.iterator();
		while (it.hasNext())
			it.next().close();
	}
}
