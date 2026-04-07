# 📋 Complete Setup Guide

This guide will help you set up and run the Speech Recognition Application on your system.

## 🖥️ Table of Contents

- [Windows Setup](#windows-setup)
- [macOS Setup](#macos-setup)
- [Linux Setup](#linux-setup)
- [Running the Application](#running-the-application)
- [Troubleshooting](#troubleshooting)

---

## 🪟 Windows Setup

### Step 1: Install Java 11+

1. Download Java from: https://www.oracle.com/java/technologies/downloads/
   - OR use OpenJDK: https://adoptopenjdk.net/
   - Select **Windows x64** version
   
2. Run the installer and follow the prompts
   
3. Verify installation by opening Command Prompt and typing:
   ```cmd
   java -version
   ```
   You should see output like: `java version "17.0.x"`

### Step 2: Install Maven

1. Download from: https://maven.apache.org/download.cgi
   - Download: "apache-maven-3.8.x-bin.zip"

2. Extract to a location (recommended: `C:\apache-maven`)

3. Add Maven to PATH:
   - Open Environment Variables:
     - Press `Win + X` → Select "System"
     - Click "Advanced system settings"
     - Click "Environment Variables" button
   - Under "System variables", click "New"
     - Variable name: `MAVEN_HOME`
     - Variable value: `C:\apache-maven` (your install path)
   - Find and edit "Path" variable:
     - Click "Edit"
     - Click "New"
     - Add: `%MAVEN_HOME%\bin`
     - Click OK, OK, OK

4. Verify installation - open a NEW Command Prompt:
   ```cmd
   mvn -version
   ```
   You should see Maven version info

### Step 3: Build the Application

1. Open Command Prompt
2. Navigate to project folder:
   ```cmd
   cd C:\path\to\speech-recognition-java
   ```
3. Build using either method:
   - **Using script** (easiest):
     ```cmd
     build.bat
     ```
   - **Using Maven directly**:
     ```cmd
     mvn clean install
     ```

Wait for build to complete - it will download ~100MB of dependencies.

### Step 4: Run the Application

Use either method:

- **Using script** (easiest):
  ```cmd
  run.bat
  ```

- **Using Maven**:
  ```cmd
  mvn exec:java -Dexec.mainClass="com.speechrecognition.Main"
  ```

- **Using Java directly**:
  ```cmd
  java -jar target/speech-recognition-app-1.0.0.jar
  ```

---

## 🍎 macOS Setup

### Step 1: Install Java 11+

#### Option A: Using Homebrew (Recommended)
```bash
brew install openjdk@17
```

#### Option B: Manual Download
1. Download from: https://www.oracle.com/java/technologies/downloads/
2. Select macOS version
3. Run installer

Verify installation:
```bash
java -version
```

### Step 2: Install Maven

#### Option A: Using Homebrew (Recommended)
```bash
brew install maven
```

#### Option B: Manual Download
1. Download: https://maven.apache.org/download.cgi
2. Extract: `tar xzf apache-maven-3.8.x-bin.tar.gz`
3. Move: `mv apache-maven-3.8.x ~/apache-maven`
4. Add to PATH in `~/.zshrc` or `~/.bash_profile`:
   ```bash
   export PATH="$PATH:$HOME/apache-maven/bin"
   ```
5. Reload: `source ~/.zshrc`

Verify installation:
```bash
mvn -version
```

### Step 3: Build the Application

1. Open Terminal
2. Navigate to project:
   ```bash
   cd /path/to/speech-recognition-java
   ```
3. Make scripts executable:
   ```bash
   chmod +x build.sh run.sh
   ```
4. Build:
   ```bash
   ./build.sh
   ```
   Or:
   ```bash
   mvn clean install
   ```

### Step 4: Run the Application

```bash
./run.sh
```

Or:
```bash
java -jar target/speech-recognition-app-1.0.0.jar
```

**Note**: You may need to allow microphone access:
- System Preferences → Security & Privacy → Microphone
- Add Java/your terminal to allowed apps

---

## 🐧 Linux Setup

### Step 1: Install Java 11+

#### Ubuntu/Debian
```bash
sudo apt-get update
sudo apt-get install openjdk-17-jdk
```

#### Fedora/RHEL
```bash
sudo dnf install java-17-openjdk java-17-openjdk-devel
```

#### Arch
```bash
sudo pacman -S jdk-openjdk
```

Verify:
```bash
java -version
```

### Step 2: Install Maven

#### Ubuntu/Debian
```bash
sudo apt-get install maven
```

#### Fedora/RHEL
```bash
sudo dnf install maven
```

#### Arch
```bash
sudo pacman -S maven
```

#### Manual Download
```bash
wget https://archive.apache.org/dist/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz
tar xzf apache-maven-3.8.1-bin.tar.gz
sudo mv apache-maven-3.8.1 /opt/maven
```

Add to PATH in `~/.bashrc`:
```bash
export PATH="/opt/maven/bin:$PATH"
```

Reload:
```bash
source ~/.bashrc
```

Verify:
```bash
mvn -version
```

### Step 3: Install Audio Support

For better audio recognition:
```bash
sudo apt-get install pulseaudio alsa-utils
```

### Step 4: Build the Application

1. Open Terminal
2. Navigate to project:
   ```bash
   cd ~/path/to/speech-recognition-java
   ```
3. Make scripts executable:
   ```bash
   chmod +x build.sh run.sh
   ```
4. Build:
   ```bash
   ./build.sh
   ```
   Or:
   ```bash
   mvn clean install
   ```

### Step 5: Run the Application

```bash
./run.sh
```

Or:
```bash
java -jar target/speech-recognition-app-1.0.0.jar
```

---

## 🚀 Running the Application

After successful build, the application will open in a GUI window.

### First Time Usage

1. Click **"🎤 Start Recording"**
2. Speak clearly into your microphone
   - Ensure your microphone is connected and enabled
   - Minimize background noise
3. Click **"⏹ Stop Recording"** when done
4. Wait for speech recognition to complete
5. Your transcription appears in the main area
6. Click **"💾 Save"** to save, or **"📋 Copy"** to copy text

### Tips for Best Results

- Speak at a normal pace
- Pronounce words clearly
- Use English language (default)
- Minimize background noise
- Ensure microphone is working properly

---

## 🐛 Troubleshooting

### Java Not Found
```
Error: 'java' is not recognized as an internal or external command
```
**Solution:**
- Reinstall Java
- Add Java to PATH (see installation steps)
- Use full path: `C:\Program Files\Java\jdk-17\bin\java.exe`

### Maven Not Found
```
Error: 'mvn' is not recognized as an internal or external command
```
**Solution:**
- Reinstall Maven
- Add Maven to PATH (see installation steps)
- Use full path: `C:\apache-maven\bin\mvn.bat`

### Build Fails
```
[ERROR] Could not transfer artifact edu.cmu.sphinx:sphinx4-core:jar:5.2
```
**Solution:**
- Check internet connection
- Try: `mvn clean install -U` (update all dependencies)
- Clear Maven cache: `mvn clean -DskipTests`

### Microphone Not Detected
```
Error: Audio line not supported
```
**Solution:**
- Ensure microphone is connected and enabled
- Check Windows/Mac/Linux audio settings
- Try plugging in microphone via USB
- Restart the application

### Application Won't Start
```
Exception: NoClassDefFoundError
```
**Solution:**
- Ensure build completed successfully
- Try: `mvn clean install` again
- Check Java version: `java -version`
- Increase heap: `java -Xmx512m -jar target/speech-recognition-app-1.0.0.jar`

### Low Recognition Accuracy
- Speak more slowly and clearly
- Reduce background noise
- Move closer to microphone
- Use higher quality microphone
- Ensure good microphone positioning

### Out of Memory
```
Exception: java.lang.OutOfMemoryError
```
**Solution:**
- Increase JVM memory:
  ```bash
  java -Xmx512m -jar target/speech-recognition-app-1.0.0.jar
  ```
- Or increase MAVEN_OPTS:
  ```bash
  export MAVEN_OPTS="-Xmx512m"
  ```

---

## 📞 Getting Help

If you encounter issues not listed above:

1. Check the main README.md
2. Ensure all prerequisites are installed correctly
3. Try rebuilding: `mvn clean install -U`
4. Check Java/Maven versions are correct
5. Open an issue on GitHub with error details

---

**Happy speech recognizing! 🎤**
