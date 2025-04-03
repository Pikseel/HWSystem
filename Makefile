JC			= javac
JFLAGS		= -d build -sourcepath src

JDOC		= javadoc
JDOCFLAGS	= -d docs -sourcepath src -subpackages .

SRC_DIR		= src
BUILD_DIR	= build
DOCS_DIR	= docs
LOG_DIR		= logs

SOURCES		=	$(SRC_DIR)/Bluetooth.java \
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

build:	$(SOURCES)
	@echo "MAKEFILE: Compiling source files..."
	@mkdir -p $(BUILD_DIR)
	@$(JC) $(JFLAGS) $(SOURCES)

javadoc:
	@echo "MAKEFILE: Generating Javadoc..."
	@mkdir -p $(DOCS_DIR)
	@$(JDOC) $(JDOCFLAGS) $(SOURCES)

run:	build
	@echo "MAKEFILE: Running the program..."
	@if [ ! -d $(LOG_DIR) ]; then mkdir -p $(LOG_DIR); fi
	@if [ ! -f $(CONFIG_FILE) ]; then echo "Error: $(CONFIG_FILE) not found"; exit 1; fi
	@java -cp $(BUILD_DIR) -DCONFIG_FILE=$(CONFIG_FILE) -DLOG_DIR=$(LOG_DIR) Main

clean:
	@echo "MAKEFILE: Cleaning up..."
	@rm -rf $(BUILD_DIR) $(DOCS_DIR) $(LOG_DIR)

re:		clean logs compile

.PHONY:	all compile javadoc logs run clean re