#!/bin/bash

################################################################################
# Speech Recognition App Run Script for Unix/Linux/macOS
################################################################################

echo ""
echo "============================================================================"
echo "  Speech Recognition Application - Running..."
echo "============================================================================"
echo ""

# Check if JAR file exists
if [ ! -f "target/speech-recognition-app-1.0.0.jar" ]; then
    echo "ERROR: JAR file not found!"
    echo ""
    echo "Please build the project first using:"
    echo "  ./build.sh"
    echo "  or"
    echo "  mvn clean install"
    echo ""
    read -p "Press Enter to continue..."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher"
    read -p "Press Enter to continue..."
    exit 1
fi

echo "Starting application..."
echo ""

# Run the application
java -jar target/speech-recognition-app-1.0.0.jar

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Application failed to run!"
    read -p "Press Enter to continue..."
    exit 1
fi
