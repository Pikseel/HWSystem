# Makefile for CSE222 Homework 3 Java project
# Compatible with macOS, assumes JDK 11 and source files in src/

# Directories
SRC_DIR = src
BUILD_DIR = build
DOCS_DIR = docs

# Java compiler and runtime
JAVAC = javac
JAVA = java
JAVADOC = javadoc

# Source files
SOURCES = $(wildcard $(SRC_DIR)/*.java)

# Default target
all: $(BUILD_DIR) compile

re: clean all

# Create build directory
$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

# Compile all Java files together
compile: $(SOURCES)
	$(JAVAC) -d $(BUILD_DIR) -sourcepath $(SRC_DIR) $(SOURCES)

# Run the program (requires config file and log directory as arguments)
run: all
	@if [ -z "$(CONFIG_FILE)" ] || [ -z "$(LOG_DIR)" ]; then \
		echo "Usage: make run CONFIG_FILE=<path_to_config> LOG_DIR=<path_to_log_dir>"; \
		exit 1; \
	else \
		$(JAVA) -cp $(BUILD_DIR) Main $(CONFIG_FILE) $(LOG_DIR); \
	fi

# Generate JavaDoc documentation
javadoc: $(SOURCES)
	mkdir -p $(DOCS_DIR)
	$(JAVADOC) -d $(DOCS_DIR) -sourcepath $(SRC_DIR) $(SOURCES)

# Clean build and docs directories
clean:
	rm -rf $(BUILD_DIR) $(DOCS_DIR)

# Phony targets
.PHONY: all compile run javadoc clean