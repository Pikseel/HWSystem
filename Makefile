# Compiler and flags
JC = javac
JFLAGS = -d build -sourcepath src
JDOC = javadoc
JDOCFLAGS = -d docs -sourcepath src -subpackages .

# Directories
SRC_DIR = src
BUILD_DIR = build
DOCS_DIR = docs
LOG_DIR = logs

# Configurable variables (can be overridden via command line, e.g., make CONFIG_FILE=custom.txt)
CONFIG_FILE ?= test/config1.txt
LOG_DIR ?= logs

# List of source files
SOURCES = \
    $(SRC_DIR)/Bluetooth.java \
    $(SRC_DIR)/BME280.java \
    $(SRC_DIR)/Device.java \
    $(SRC_DIR)/DHT11.java \
    $(SRC_DIR)/Display.java \
    $(SRC_DIR)/GY951.java \
    $(SRC_DIR)/HWSystem.java \
    $(SRC_DIR)/I2C.java \
    $(SRC_DIR)/IMUSensor.java \
    $(SRC_DIR)/LCD.java \
    $(SRC_DIR)/Main.java \
    $(SRC_DIR)/MotorDriver.java \
    $(SRC_DIR)/MPU6050.java \
    $(SRC_DIR)/OLED.java \
    $(SRC_DIR)/OneWire.java \
    $(SRC_DIR)/PCA9685.java \
    $(SRC_DIR)/Protocol.java \
    $(SRC_DIR)/Sensor.java \
    $(SRC_DIR)/SparkFunMD.java \
    $(SRC_DIR)/SPI.java \
    $(SRC_DIR)/State.java \
    $(SRC_DIR)/TempSensor.java \
    $(SRC_DIR)/UART.java \
    $(SRC_DIR)/Wifi.java \
    $(SRC_DIR)/WirelessIO.java

# Default target
all: compile

# Compile all Java files
compile: $(SOURCES)
	@mkdir -p $(BUILD_DIR)
	$(JC) $(JFLAGS) $(SOURCES)

# Generate Javadoc
javadoc:
	@mkdir -p $(DOCS_DIR)
	$(JDOC) $(JDOCFLAGS) $(SOURCES)

# Create or recreate logs directory
logs:
	@rm -rf $(LOG_DIR)
	@mkdir -p $(LOG_DIR)

# Run the program with CONFIG_FILE and LOG_DIR
run: compile
	@if [ ! -d $(LOG_DIR) ]; then mkdir -p $(LOG_DIR); fi
	@if [ ! -f $(CONFIG_FILE) ]; then echo "Error: $(CONFIG_FILE) not found"; exit 1; fi
	java -cp $(BUILD_DIR) -DCONFIG_FILE=$(CONFIG_FILE) -DLOG_DIR=$(LOG_DIR) Main

# Clean build and docs directories
clean:
	rm -rf $(BUILD_DIR) $(DOCS_DIR) $(LOG_DIR)

# Rebuild: clean, recreate logs, then compile
re: clean logs compile

.PHONY: all compile javadoc logs run clean re