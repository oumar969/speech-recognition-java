package com.speechrecognition.ui;

import com.speechrecognition.service.SpeechRecognitionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Main GUI frame for the Speech Recognition Application
 */
public class MainFrame extends JFrame {
    private SpeechRecognitionService speechService;
    private JTextArea transcriptArea;
    private JButton startButton;
    private JButton stopButton;
    private JButton saveButton;
    private JButton clearButton;
    private JList<String> historyList;
    private JLabel statusLabel;
    private DefaultListModel<String> historyModel;

    public MainFrame() {
        speechService = new SpeechRecognitionService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Speech Recognition Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        // Create main panel with layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel - Controls
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Center panel - Transcript area and history
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel - Status
        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Controls"));

        // Start Recording Button
        startButton = new JButton("🎤 Start Recording");
        startButton.addActionListener(this::startRecording);
        panel.add(startButton);

        // Stop Recording Button
        stopButton = new JButton("⏹ Stop Recording");
        stopButton.setEnabled(false);
        stopButton.addActionListener(this::stopRecording);
        panel.add(stopButton);

        // Save Button
        saveButton = new JButton("💾 Save Transcript");
        saveButton.addActionListener(this::saveTranscript);
        panel.add(saveButton);

        // Clear Button
        clearButton = new JButton("🗑 Clear");
        clearButton.addActionListener(this::clearTranscript);
        panel.add(clearButton);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Left side - Transcript
        JPanel transcriptPanel = new JPanel(new BorderLayout());
        transcriptPanel.setBorder(BorderFactory.createTitledBorder("Transcript"));

        transcriptArea = new JTextArea();
        transcriptArea.setLineWrap(true);
        transcriptArea.setWrapStyleWord(true);
        transcriptArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(transcriptArea);
        transcriptPanel.add(scrollPane, BorderLayout.CENTER);

        // Right side - History
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("History"));

        historyModel = new DefaultListModel<>();
        historyList = new JList<>(historyModel);
        historyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = historyList.getSelectedValue();
                if (selected != null) {
                    transcriptArea.setText("Loading: " + selected);
                }
            }
        });

        JScrollPane historyScroll = new JScrollPane(historyList);
        historyPanel.add(historyScroll, BorderLayout.CENTER);

        panel.add(transcriptPanel);
        panel.add(historyPanel);

        return panel;
    }

    private void startRecording(ActionEvent e) {
        if (speechService.startRecording()) {
            statusLabel.setText("Recording in progress...");
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            transcriptArea.setText("Listening...");
        } else {
            statusLabel.setText("Error: Could not start recording");
        }
    }

    private void stopRecording(ActionEvent e) {
        speechService.stopRecording();
        statusLabel.setText("Processing audio...");
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        
        // Simulate recognition (replace with actual API call)
        String recognizedText = "Recognized: This is a sample transcription.";
        transcriptArea.setText(recognizedText);
        statusLabel.setText("Ready");
    }

    private void saveTranscript(ActionEvent e) {
        String transcript = transcriptArea.getText();
        if (transcript.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transcript to save", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (speechService.saveTranscription(transcript)) {
            updateHistoryList();
            statusLabel.setText("Transcript saved successfully");
            JOptionPane.showMessageDialog(this, "Transcript saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            statusLabel.setText("Error saving transcript");
            JOptionPane.showMessageDialog(this, "Error saving transcript", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearTranscript(ActionEvent e) {
        transcriptArea.setText("");
        statusLabel.setText("Transcript cleared");
    }

    private void updateHistoryList() {
        historyModel.clear();
        List<String> history = speechService.getTranscriptionHistory();
        for (String item : history) {
            historyModel.addElement(item);
        }
    }
}
