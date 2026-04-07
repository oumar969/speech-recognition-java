#!/bin/bash

################################################################################
# Speech Recognition App Build Script for Unix/Linux/macOS
################################################################################

echo ""
echo "============================================================================"
echo "  Speech Recognition Application - Build Script"
echo "============================================================================"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo ""
    echo "To install Maven:"
    echo "  macOS: brew install maven"
    echo "  Ubuntu/Debian: sudo apt-get install maven"
    echo "  Or download from: https://maven.apache.org/download.cgi"
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

echo "Building Speech Recognition Application..."
echo ""

# Build with Maven
mvn clean install

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Build failed!"
    read -p "Press Enter to continue..."
    exit 1
fi

echo ""
echo "============================================================================"
echo "  Build successful!"
echo "============================================================================"
echo ""
echo "You can now run the application using:"
echo "  java -jar target/speech-recognition-app-1.0.0.jar"
echo ""
echo "Or use the run script:"
echo "  ./run.sh"
echo ""
read -p "Press Enter to continue..."
