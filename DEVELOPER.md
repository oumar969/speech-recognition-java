# 👨‍💻 Developer Documentation

This document is for developers who want to understand, modify, or extend the application.

## 📐 Architecture Overview

```
┌─────────────────────────────────────────┐
│         MainFrame (GUI Layer)           │
│  - JFrame: Main application window      │
│  - UI Components: Buttons, text areas   │
│  - Event Handlers: Click listeners      │
└────────────────┬────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────┐
│   SpeechRecognitionService (Logic)      │
│  - Audio recording from microphone      │
│  - Sphinx4 speech recognition           │
│  - File management & history            │
│  - Event notifications                  │
└────────────────┬────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────┐
│    External Libraries & Services        │
│  - Sphinx4: Speech recognition engine   │
│  - Java Sound: Audio input/output       │
│  - File I/O: Transcription storage      │
└─────────────────────────────────────────┘
```

## 🔧 Class Structure

### Main.java
```java
public class Main {
    public static void main(String[] args)
        // Entry point
        // Launches MainFrame in EDT
}
```

### MainFrame.java
**Extends**: `JFrame`  
**Implements**: `SpeechRecognitionService.RecognitionListener`

**Key Methods**:
- `initializeUI()` - Build GUI components
- `createControlPanel()` - Top control buttons
- `createCenterPanel()` - Transcript and history
- `startRecording()` - Begin audio capture
- `stopRecording()` - Process audio
- `saveTranscript()` - Save to file
- `onRecognitionResult()` - Handle recognition callback
- `onRecognitionError()` - Handle error callback

**Key Components**:
- `JTextArea transcriptArea` - Display recognized text
- `JList<String> historyList` - Show previous transcriptions
- `JButton` - Various action buttons
- `JLabel statusLabel` - Status updates
- `JProgressBar recordingProgress` - Recording indicator

### SpeechRecognitionService.java

**Key Methods**:
- `startRecording()` - Initialize microphone input
- `stopRecording()` - Stop recording & start recognition
- `processRecognition()` - Run speech recognition
- `saveTranscription()` - Write to file
- `loadTranscription()` - Read from file
- `deleteTranscription()` - Remove file
- `getTranscriptionHistory()` - List all saved transcriptions

**Key Components**:
- `LiveSpeechRecognizer recognizer` - Sphinx4 engine
- `TargetDataLine targetDataLine` - Audio input device
- `ByteArrayOutputStream audioBuffer` - Audio data storage
- `RecognitionListener recognitionListener` - Callback interface

**Interface**: `RecognitionListener`
```java
interface RecognitionListener {
    void onRecognitionResult(String result);
    void onRecognitionError(String error);
}
```

---

## 🔄 Program Flow

### Recording & Recognition Flow

```
User clicks "Start Recording"
    ↓
startRecording() checks for microphone
    ↓
Opens audio input line at 16kHz, 16-bit mono
    ↓
Starts recording thread (recordAudio())
    ↓
User speaks...
    ↓
User clicks "Stop Recording"
    ↓
stopRecording() closes audio line
    ↓
processRecognition() runs Sphinx4
    ↓
Result sent via onRecognitionResult()
    ↓
Text appears in transcript area
```

### File Save Flow

```
User clicks "Save"
    ↓
saveTranscription(text) called
    ↓
Generate timestamp filename
    ↓
Write to "transcriptions/" directory
    ↓
Add path to history list
    ↓
Update history UI
```

---

## 🛠️ How to Extend the Application

### Add a New Feature

#### Example: Record audio to file

1. **Add method to `SpeechRecognitionService.java`**:
```java
public boolean saveAudioFile(String filename) {
    try {
        AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
        File file = new File(filename);
        AudioInputStream ais = new AudioInputStream(audioInputStream, 
            audioFormat, AudioSystem.NOT_SPECIFIED);
        AudioSystem.write(ais, fileType, file);
        return true;
    } catch (IOException e) {
        notifyError("Error saving audio: " + e.getMessage());
        return false;
    }
}
```

2. **Add UI button in `MainFrame.java`**:
```java
JButton recordAudioButton = new JButton("🔊 Save Audio");
recordAudioButton.addActionListener(e -> {
    String filename = "audio_" + System.currentTimeMillis() + ".wav";
    if (speechService.saveAudioFile(filename)) {
        statusLabel.setText("Audio saved: " + filename);
    }
});
```

### Add Language Support

1. **Create language configuration in `SpeechRecognitionService.java`**:
```java
private String language = "en-US";

public void setLanguage(String lang) {
    this.language = lang;
    updateRecognizer();
}

private void updateRecognizer() {
    Configuration config = new Configuration();
    switch(language) {
        case "en-US":
            config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            break;
        case "es-ES":
            config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/es-es/es-es");
            break;
    }
    this.recognizer = new LiveSpeechRecognizer(config);
}
```

2. **Add language dropdown to UI**:
```java
JComboBox<String> languageCombo = new JComboBox<>(
    new String[]{"English (US)", "Spanish", "French"}
);
languageCombo.addActionListener(e -> {
    String selected = (String) languageCombo.getSelectedItem();
    speechService.setLanguage(mapLanguage(selected));
});
```

### Add Real-time Display

1. **Modify `processRecognition()`** to emit partial results:
```java
private void processRecognition() {
    recognizer.startRecognition(true);
    
    while (isRecording) {
        SpeechResult result = recognizer.getResult();
        if (result != null) {
            String hypothesis = result.getHypothesis();
            notifyResult(hypothesis);
            updateUI(hypothesis);
        }
    }
}
```

### Add Export Formats

1. **Add export method**:
```java
public boolean exportAsJSON(String filename, String transcript) {
    try {
        JsonObject json = Json.createObjectBuilder()
            .add("timestamp", LocalDateTime.now().toString())
            .add("transcript", transcript)
            .build();
        
        Files.write(Paths.get(filename), 
            json.toString().getBytes());
        return true;
    } catch (IOException e) {
        notifyError("Export failed: " + e.getMessage());
        return false;
    }
}

public boolean exportAsPDF(String filename, String transcript) {
    // Implement PDF export using iText or Apache PDFBox
}
```

---

## 📦 Adding Dependencies

### Adding a New Library

1. **Add to `pom.xml`** in `<dependencies>` section:
```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. **Update Maven**:
```bash
mvn dependency:resolve
```

3. **Use in code**:
```java
import com.example.MyLibrary;

MyLibrary lib = new MyLibrary();
```

### Recommended Libraries for Extensions

- **PDF Export**: `com.itextpdf:itextpdf:5.5.13`
- **JSON**: `com.google.code.gson:gson:2.8.9`
- **HTTP Client**: `org.apache.httpcomponents:httpclient:4.5.13`
- **Database**: `org.hibernate:hibernate-core:5.6.0`
- **Log4j**: `org.slf4j:slf4j-api:1.7.32`

---

## 🧪 Testing

### Unit Testing

Create `src/test/java/com/speechrecognition/service/SpeechRecognitionServiceTest.java`:

```java
import org.junit.Test;
import static org.junit.Assert.*;

public class SpeechRecognitionServiceTest {
    
    @Test
    public void testServiceInitialization() {
        SpeechRecognitionService service = new SpeechRecognitionService();
        assertNotNull(service);
    }
    
    @Test
    public void testStartRecording() {
        SpeechRecognitionService service = new SpeechRecognitionService();
        boolean result = service.startRecording();
        assertTrue(result);
        service.stopRecording();
    }
}
```

Run tests:
```bash
mvn test
```

---

## 🔐 Performance Considerations

### Memory Optimization
- **Audio Buffer**: Increase buffer size for better quality (currently 4096 bytes)
- **Thread Pool**: Use ExecutorService for multiple concurrent recordings
- **Garbage Collection**: Monitor heap usage, increase with `-Xmx` flag

### Audio Quality
- Current sample rate: 16,000 Hz (trade-off: quality vs performance)
- Current bit depth: 16-bit (standard for speech recognition)
- Use higher sample rates (44,100 Hz) for music, lower (8,000 Hz) for fast speech

### Recognition Performance
- Use context-specific language models for better accuracy
- Implement caching for frequently recognized phrases
- Consider offline vs. online recognition engines

---

## 🐛 Debugging Tips

### Enable Debug Logging

Add to `SpeechRecognitionService.java`:
```java
private static final boolean DEBUG = true;

private void debug(String message) {
    if (DEBUG) {
        System.out.println("[DEBUG] " + message);
    }
}
```

### Check Audio Input

```java
private void diagnosticAudioInfo() {
    Mixer.Info[] infos = AudioSystem.getMixerInfo();
    for (Mixer.Info info : infos) {
        System.out.println("Mixer: " + info.getName());
        Mixer mixer = AudioSystem.getMixer(info);
        Line.Info[] lineInfos = mixer.getSourceLineInfo();
        System.out.println("  Lines: " + lineInfos.length);
    }
}
```

### Monitor Memory

```bash
java -verbose:gc -Xmx256m -jar target/speech-recognition-app-1.0.0.jar
```

---

## 📝 Code Style Guidelines

### Naming Conventions
- **Classes**: PascalCase (`MainFrame`, `SpeechRecognitionService`)
- **Methods**: camelCase (`startRecording`, `saveTranscription`)
- **Constants**: UPPER_CASE (`SAMPLE_RATE`, `TRANSCRIPTIONS_DIR`)
- **Variables**: camelCase (`audioBuffer`, `recordingTime`)

### Documentation
- Add JavaDoc comments for all public methods
- Include usage examples for complex methods
- Document algorithm choices and trade-offs

### Error Handling
- Use custom exceptions for application-specific errors
- Log errors with context information
- Provide user-friendly error messages

---

## 📚 References

- [Sphinx4 API](https://cmusphinx.github.io/wiki/tutoriallm/)
- [Java Sound API](https://docs.oracle.com/javase/8/docs/technotes/guides/sound/)
- [Swing GUI Toolkit](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Java File I/O](https://docs.oracle.com/javase/tutorial/i18n/resbundle/concept.html)

---

**Happy coding! 🚀**
