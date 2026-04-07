package com.speechrecognition.service;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling speech recognition and audio processing
 * 
 * Note: This version uses mock recognition for demonstration.
 * To integrate real speech recognition, replace processRecognition() with:
 * - Google Cloud Speech-to-Text API
 * - Microsoft Azure Speech Services
 * - CMU Sphinx4
 * - Or other speech recognition engines
 */
public class SpeechRecognitionService {
    private TargetDataLine targetDataLine;
    private boolean isRecording = false;
    private List<String> transcriptionHistory;
    private ByteArrayOutputStream audioBuffer;
    private Thread recordingThread;
    private static final String TRANSCRIPTIONS_DIR = "transcriptions";
    private RecognitionListener recognitionListener;
    private long audioRecordingLength = 0;

    public interface RecognitionListener {
        void onRecognitionResult(String result);

        void onRecognitionError(String error);
    }

    public SpeechRecognitionService() {
        this.transcriptionHistory = new ArrayList<>();
        this.audioBuffer = new ByteArrayOutputStream();
        createTranscriptionsDirectory();
    }

    private void createTranscriptionsDirectory() {
        try {
            Files.createDirectories(Paths.get(TRANSCRIPTIONS_DIR));
        } catch (IOException e) {
            System.err.println("Could not create transcriptions directory: " + e.getMessage());
        }
    }

    public void setRecognitionListener(RecognitionListener listener) {
        this.recognitionListener = listener;
    }

    /**
     * Start recording audio from the microphone
     */
    public boolean startRecording() {
        try {
            AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

            if (!AudioSystem.isLineSupported(info)) {
                notifyError("Audio line not supported");
                return false;
            }

            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            audioBuffer = new ByteArrayOutputStream();
            isRecording = true;
            audioRecordingLength = 0;

            // Start recording thread
            recordingThread = new Thread(this::recordAudio);
            recordingThread.start();

            return true;
        } catch (LineUnavailableException e) {
            notifyError("Error starting recording: " + e.getMessage());
            return false;
        }
    }

    private void recordAudio() {
        byte[] buffer = new byte[4096];
        int bytesRead;

        while (isRecording && targetDataLine != null) {
            bytesRead = targetDataLine.read(buffer, 0, buffer.length);
            if (bytesRead > 0) {
                audioBuffer.write(buffer, 0, bytesRead);
                audioRecordingLength += bytesRead;
            }
        }
    }

    /**
     * Stop recording and start speech recognition
     */
    public void stopRecording() {
        if (!isRecording || targetDataLine == null) {
            return;
        }

        isRecording = false;
        targetDataLine.stop();
        targetDataLine.close();

        // Wait for recording thread to finish
        try {
            if (recordingThread != null) {
                recordingThread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Process recognition in background thread
        new Thread(this::processRecognition).start();
    }

    /**
     * Process audio and perform speech recognition
     * 
     * Currently uses mock recognition. To implement real speech recognition:
     * 1. Replace this method with actual API calls
     * 2. Or integrate CMU Sphinx4, Google Cloud, Azure, etc.
     */
    private void processRecognition() {
        try {
            // Simulate processing time (1-3 seconds)
            Thread.sleep(1000 + (int) (Math.random() * 2000));

            // Generate mock recognized text
            String result = generateMockRecognition();
            notifyResult(result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            notifyError("Recognition interrupted");
        } catch (Exception e) {
            notifyError("Recognition error: " + e.getMessage());
        }
    }

    /**
     * Generate mock speech recognition result
     * This is for demonstration. Replace with actual speech recognition API
     */
    private String generateMockRecognition() {
        // Mock recognized texts of varying length
        String[] mockTexts = {
                "Hello, this is a test recording",
                "The weather today is beautiful",
                "I am testing the speech recognition application",
                "This is a demonstration of the speech recognition system",
                "Every good boy does fine",
                "The quick brown fox jumps over the lazy dog",
                "Good morning, how can I help you today",
                "Speech recognition is working correctly",
                "Thank you for using this application",
                "This demonstrates real-time audio processing",
                "The quick brown fox",
                "Hello world",
                "How are you doing today",
                "This is a simple test",
                "Another voice sample"
        };

        // Select random text for variety
        int randomIndex = (int) (Math.random() * mockTexts.length);
        String baseText = mockTexts[randomIndex];

        // Add confidence-like value
        int confidence = 80 + (int) (Math.random() * 20); // 80-100%
        return baseText + " [Confidence: " + confidence + "%]";
    }

    private void notifyResult(String result) {
        if (recognitionListener != null) {
            recognitionListener.onRecognitionResult(result);
        }
    }

    private void notifyError(String error) {
        if (recognitionListener != null) {
            recognitionListener.onRecognitionError(error);
        }
    }

    /**
     * Save transcription to file
     */
    public boolean saveTranscription(String transcription) {
        try {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filename = TRANSCRIPTIONS_DIR + File.separator + "transcription_" + timestamp + ".txt";

            FileWriter writer = new FileWriter(filename);
            writer.write("Timestamp: " + LocalDateTime.now() + "\n");
            writer.write("Transcription:\n");
            writer.write(transcription + "\n");
            writer.close();

            transcriptionHistory.add(filename);
            return true;
        } catch (IOException e) {
            notifyError("Error saving transcription: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get transcription history
     */
    public List<String> getTranscriptionHistory() {
        return new ArrayList<>(transcriptionHistory);
    }

    /**
     * Load transcription from file
     */
    public String loadTranscription(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            notifyError("Error loading transcription: " + e.getMessage());
            return "";
        }
    }

    /**
     * Clear transcription history
     */
    public void clearHistory() {
        transcriptionHistory.clear();
    }

    /**
     * Delete a transcription file
     */
    public boolean deleteTranscription(String filename) {
        try {
            Files.delete(Paths.get(filename));
            transcriptionHistory.remove(filename);
            return true;
        } catch (IOException e) {
            notifyError("Error deleting transcription: " + e.getMessage());
            return false;
        }
    }

    public boolean isRecording() {
        return isRecording;
    }
}
