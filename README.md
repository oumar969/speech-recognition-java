# Speech Recognition Java Application

A Java Swing application for speech-to-text recognition with real-time audio processing.

## Features

- 🎤 Record audio from microphone
- 📝 Real-time speech-to-text conversion
- 📊 Display recognized text
- ▶️ Playback recorded audio
- 💾 Save transcriptions to file
- 🗂️ View transcription history
- 🌍 Multiple language support
- ✏️ Edit and copy text
- 🎯 Confidence score display

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

## Setup

1. Clone the repository
```bash
git clone https://github.com/oumar969/speech-recognition-java.git
cd speech-recognition-java
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn exec:java -Dexec.mainClass="com.speechrecognition.Main"
```

Or run the JAR file:
```bash
java -jar target/speech-recognition-app-1.0.0.jar
```

## Project Structure

```
speech-recognition-java/
├── src/
│   ├── main/
│   │   ├── java/com/speechrecognition/
│   │   │   ├── Main.java
│   │   │   ├── ui/
│   │   │   │   └── MainFrame.java
│   │   │   └── service/
│   │   │       └── SpeechRecognitionService.java
│   │   └── resources/
│   └── test/java/
├── pom.xml
└── README.md
```

## Dependencies

- **Sphinx4** - Speech recognition engine
- **Java Swing** - GUI framework
- **Maven** - Build tool

## Usage

1. Click "Start Recording" to begin capturing audio
2. Speak clearly into your microphone
3. Click "Stop Recording" when done
4. The recognized text will appear in the transcript area
5. Save your transcription or edit it as needed

## Contributing

Contributions are welcome! Feel free to fork and submit pull requests.

## License

MIT License
