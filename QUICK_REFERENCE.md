# ⚡ Quick Reference Guide

Quick commands for common tasks.

## 🚀 Quick Start

### Windows
```cmd
build.bat          # Build the application
run.bat            # Run the application
```

### macOS / Linux
```bash
chmod +x build.sh run.sh  # Make scripts executable
./build.sh                # Build the application
./run.sh                  # Run the application
```

### Direct Maven Commands
```bash
mvn clean install         # Build complete project
mvn exec:java -Dexec.mainClass="com.speechrecognition.Main"  # Run app
mvn clean                 # Clean build artifacts
mvn compile               # Compile only
mvn test                  # Run tests
mvn dependency:tree       # Show dependencies
```

### Direct Java Commands
```bash
java -jar target/speech-recognition-app-1.0.0.jar  # Run JAR
java -Xmx512m -jar target/...jar                    # Run with more memory
javac -version                                       # Check Java compiler
java -version                                        # Check Java version
```

---

## 🎯 Application Controls

| Button | Function |
|--------|----------|
| 🎤 Start Recording | Begin capturing audio |
| ⏹ Stop Recording | Stop audio capture and process |
| 💾 Save | Save current transcript to file |
| 🗑 Clear | Clear transcript area |
| 📋 Copy | Copy transcript to clipboard |
| 🗑 Delete Selected | Remove selected from history |

---

## 📁 File Locations

| Item | Location |
|------|----------|
| Source Code | `src/main/java/com/speechrecognition/` |
| Compiled Classes | `target/classes/` |
| Downloaded Libraries | `target/dependency/` |
| Built JAR | `target/speech-recognition-app-1.0.0.jar` |
| Saved Transcripts | `transcriptions/` |
| Maven Config | `pom.xml` |

---

## 🔍 Useful Diagnostic Commands

```bash
# Check Java installation
java -version

# Check Maven installation
mvn -version

# Show all installed packages
mvn dependency:tree

# Clear all build artifacts
mvn clean

# Download dependencies only
mvn dependency:download-sources

# Check for updates
mvn versions:display-dependency-updates

# Full verbose build
mvn clean install -X

# Skip tests during build
mvn clean install -DskipTests
```

---

## 🛠️ Environment Variables

```bash
# Linux/macOS
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export MAVEN_HOME=/usr/local/maven
export PATH=$PATH:$MAVEN_HOME/bin

# Windows (in Command Prompt)
set JAVA_HOME=C:\Program Files\Java\jdk-17
set MAVEN_HOME=C:\apache-maven
set PATH=%PATH%;%MAVEN_HOME%\bin
```

---

## 📊 Project Statistics

- **Source Files**: 3 (.java files)
- **Dependencies**: 4 main + test libraries
- **Size**: ~100MB (with all dependencies)
- **Build Time**: ~30-60 seconds
- **JVM Version**: 11+ (17 recommended)

---

## 🐛 Common Issues - Quick Fixes

| Problem | Solution |
|---------|----------|
| `mvn: command not found` | Install Maven / Add to PATH |
| `java: command not found` | Install Java 11+ / Add to PATH |
| Build fails | Run `mvn clean install -U` |
| Audio not detected | Check microphone / Restart app |
| Out of memory | Use `java -Xmx512m -jar ...jar` |
| No speech recognized | Speak clearly, reduce noise |

---

## 📚 Key Files

```
Main.java           → Application entry point
MainFrame.java      → GUI window and controls
SpeechRecognitionService.java → Speech recognition logic
pom.xml            → Build configuration
README.md          → Main documentation
SETUP_GUIDE.md     → Detailed installation guide
```

---

## 🔗 External Resources

- [Java Documentation](https://docs.oracle.com/javase/)
- [Maven Documentation](https://maven.apache.org/)
- [CMU Sphinx4 Docs](https://cmusphinx.github.io/)
- [Swing GUI Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)

---

**For detailed information, see README.md and SETUP_GUIDE.md**
