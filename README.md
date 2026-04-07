# 🎤 Speech Recognition Java Application

A modern Java Swing application for speech-to-text recognition with real-time audio processing using CMU Sphinx4.

## ✨ Features

- 🎤 **Real-time audio recording** from microphone
- 🗣️ **Automatic speech-to-text conversion** using Sphinx4
- 📝 **Live transcription display** with word-by-word updates
- 💾 **Save transcriptions** to timestamped files
- 📂 **Transcription history** with quick access
- 🗑️ **Delete old transcriptions**
- 📋 **Copy to clipboard** functionality
- ⏱️ **Recording timer** showing duration
- 🔄 **Real-time status updates**
- 🌐 **Cross-platform support** (Windows, Mac, Linux)

## 🛠️ Requirements

- **Java 11 or higher** (17+ recommended)
- **Maven 3.6 or higher** (for building)

## 📦 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/oumar969/speech-recognition-java.git
cd speech-recognition-java
```

### 2. Install Maven (if not already installed)

#### Windows
Download from: https://maven.apache.org/download.cgi
- Extract to a location (e.g., `C:\apache-maven`)
- Add to PATH: `C:\apache-maven\bin`
- Verify: `mvn -version`

#### macOS
```bash
brew install maven
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt-get install maven
```

### 3. Build the Project
```bash
mvn clean install
```

This will:
- Download all dependencies (including Sphinx4)
- Compile the source code
- Create the executable JAR file in `target/`

## 🚀 Running the Application

### Option 1: Using Maven
```bash
mvn exec:java -Dexec.mainClass="com.speechrecognition.Main"
```

### Option 2: Using JAR file
```bash
java -jar target/speech-recognition-app-1.0.0.jar
```

### Option 3: Direct Java execution
```bash
java -cp target/classes:target/dependency/* com.speechrecognition.Main
```

## 📖 Usage Guide

1. **Start Recording**: Click "🎤 Start Recording" button
   - The app will begin listening to your microphone
   - Status will show "Recording in progress..."
   - Recording timer will display elapsed time

2. **Speak Clearly**: Speak into your microphone
   - The app processes audio in real-time
   - Ensure clear pronunciation for better accuracy
   - Allow 1-2 seconds of silence to end recording

3. **Stop Recording**: Click "⏹ Stop Recording"
   - Audio processing begins automatically
   - Recognized text appears in transcript area
   - Status shows when ready

4. **Manage Transcript**:
   - **Copy** (📋): Copy text to clipboard
   - **Save** (💾): Save transcript with timestamp
   - **Clear** (🗑️): Clear current transcript
   - **Delete** (🗑️): Remove from history

5. **View History**:
   - Select any previous transcription from the history list
   - Click "Delete Selected" to remove
   - Timestamps show when each was created

## 📁 Project Structure

```
speech-recognition-java/
├── src/
│   ├── main/
│   │   ├── java/com/speechrecognition/
│   │   │   ├── Main.java                    # Application entry point
│   │   │   ├── ui/
│   │   │   │   └── MainFrame.java           # GUI frame (Swing)
│   │   │   └── service/
│   │   │       └── SpeechRecognitionService.java  # Speech recognition logic
│   │   └── resources/
│   └── test/java/
├── target/
│   ├── classes/                             # Compiled classes
│   ├── dependency/                          # Downloaded libraries
│   └── speech-recognition-app-1.0.0.jar   # Executable JAR
├── pom.xml                                  # Maven configuration
├── README.md                                # This file
└── transcriptions/                          # Saved transcriptions
```

## 🔧 Dependencies

- **Sphinx4 Core (5.2)** - Open-source speech recognition engine
  - Lightweight and offline capable
  - English language model included
  - Free and open-source (BSD license)

- **Java Swing** - Built-in Java GUI framework
  - No additional downloads needed

- **JUnit 4.13.2** - Testing framework

## 🎯 Technical Details

### Audio Processing
- **Sample Rate**: 16,000 Hz (16 kHz)
- **Bit Depth**: 16-bit PCM
- **Channels**: Mono (1 channel)
- **Format**: WAV (uncompressed)

### Recognition Engine
- **Engine**: CMU Sphinx4
- **Model**: English US dictionary
- **Mode**: Live speech recognition
- **Accuracy**: Varies based on audio quality and speaker

### File Management
- Transcriptions saved to: `transcriptions/` directory
- File naming: `transcription_YYYY-MM-DD_HH-mm-ss.txt`
- Each file contains timestamp and transcribed text

## 🐛 Troubleshooting

### Microphone not detected
- Ensure microphone is connected and enabled
- Check system audio settings
- Try running as administrator

### Speech not recognized
- Speak more clearly and distinctly
- Adjust microphone volume
- Ensure minimal background noise
- Try speaking common English words

### Build fails with "Sphinx not found"
- Download pom.xml dependencies: `mvn dependency:resolve`
- Check internet connection for Maven repository access
- Try: `mvn clean install -U` (update dependencies)

### Out of Memory error
- Increase JVM heap: `java -Xmx512m -jar target/speech-recognition-app-1.0.0.jar`
- Or: `export MAVEN_OPTS="-Xmx512m"`

### Application crashes on start
- Ensure Java 11+ is installed: `java -version`
- Check that all dependencies are downloaded
- Try rebuilding: `mvn clean install`

## 🔐 System Permissions

The application may request permission to access your microphone:
- **Windows 10/11**: Grant permission when prompted
- **macOS**: Allow in System Preferences > Security & Privacy
- **Linux**: No special permissions usually needed

## 📝 Building a Distributable

Create a standalone executable JAR with all dependencies:
```bash
mvn clean package
```

This creates: `target/speech-recognition-app-1.0.0.jar` with all dependencies included.

To distribute:
```bash
java -jar speech-recognition-app-1.0.0.jar
```

## 🚀 Advanced Configuration

### Custom Language Models
To add language support beyond English:
1. Download language models from CMU Sphinx
2. Update paths in `SpeechRecognitionService.java`
3. Recompile and rebuild

### Performance Optimization
- Use `-Xmx256m` or higher for faster processing
- Disable background applications for better recognition
- Use wired microphone for stable input

## 📄 License

MIT License - See LICENSE file for details

## 🤝 Contributing

Contributions welcome! To contribute:
1. Fork the repository
2. Create a feature branch
3. Make your improvements
4. Submit a pull request

## 📧 Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Check existing documentation
- Review troubleshooting section

## 🎓 Learning Resources

- [CMU Sphinx Documentation](https://cmusphinx.github.io/)
- [Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Maven Documentation](https://maven.apache.org/)

---

**Enjoy your speech recognition application! 🎤**
