@echo off
REM ============================================================================
REM Speech Recognition App Build Script for Windows
REM ============================================================================

setlocal enabledelayedexpansion

echo.
echo ============================================================================
echo   Speech Recognition Application - Build Script
echo ============================================================================
echo.

REM Check if Maven is installed
where mvn > nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven is not installed or not in PATH
    echo.
    echo To install Maven:
    echo 1. Download from: https://maven.apache.org/download.cgi
    echo 2. Extract to a location (e.g., C:\apache-maven)
    echo 3. Add to PATH: C:\apache-maven\bin
    echo 4. Verify: mvn -version
    echo.
    pause
    exit /b 1
)

REM Check if Java is installed
where java > nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher
    pause
    exit /b 1
)

echo Building Speech Recognition Application...
echo.

REM Build with Maven
mvn clean install

if errorlevel 1 (
    echo.
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo ============================================================================
echo   Build successful!
echo ============================================================================
echo.
echo You can now run the application using:
echo   java -jar target/speech-recognition-app-1.0.0.jar
echo.
echo Or use the run script:
echo   run.bat
echo.
pause
