@echo off
REM ============================================================================
REM Speech Recognition App Run Script for Windows
REM ============================================================================

setlocal enabledelayedexpansion

echo.
echo ============================================================================
echo   Speech Recognition Application - Running...
echo ============================================================================
echo.

REM Check if JAR file exists
if not exist "target\speech-recognition-app-1.0.0.jar" (
    echo ERROR: JAR file not found!
    echo.
    echo Please build the project first using:
    echo   build.bat
    echo   or
    echo   mvn clean install
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

echo Starting application...
echo.

REM Run the application
java -jar target/speech-recognition-app-1.0.0.jar

if errorlevel 1 (
    echo.
    echo ERROR: Application failed to run!
    pause
    exit /b 1
)
