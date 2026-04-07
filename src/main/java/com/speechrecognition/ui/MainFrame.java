package com.speechrecognition.ui;

import com.speechrecognition.service.SpeechRecognitionService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main GUI frame for the Speech Recognition Application
 */
public class MainFrame extends JFrame implements SpeechRecognitionService.RecognitionListener {
    private SpeechRecognitionService speechService;
    private JTextArea transcriptArea;
    private JButton startButton;
    private JButton stopButton;
    private JButton saveButton;
    private JButton clearButton;
    private JButton deleteButton;
    private JButton copyButton;
    private JList<String> historyList;
    private JLabel statusLabel;
    private JLabel recordingTimeLabel;
    private JProgressBar recordingProgress;
    private DefaultListModel<String> historyModel;
    private long recordingStartTime;
    private Timer recordingTimer;

    public MainFrame() {
        speechService = new SpeechRecognitionService();
        speechService.setRecognitionListener(this);
        loadTranscriptionHistory();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("🎤 Speech Recognition Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // Create main panel with layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top panel - Controls
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Center panel - Transcript area and history
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel - Status
        JPanel statusPanel = createStatusPanel();
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Add window listener to load history on startup
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                updateHistoryList();
            }
        });
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Recording Controls"));

        // Left side - Main buttons
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        startButton = createButton("🎤 Start Recording", this::startRecording);
        leftPanel.add(startButton);

        stopButton = createButton("⏹ Stop Recording", this::stopRecording);
        stopButton.setEnabled(false);
        leftPanel.add(stopButton);

        saveButton = createButton("💾 Save", this::saveTranscript);
        leftPanel.add(saveButton);

        clearButton = createButton("🗑 Clear", this::clearTranscript);
        leftPanel.add(clearButton);

        copyButton = createButton("📋 Copy", this::copyToClipboard);
        leftPanel.add(copyButton);

        // Right side - Recording time and progress
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        recordingTimeLabel = new JLabel("Time: 00:00");
        recordingTimeLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        rightPanel.add(recordingTimeLabel);

        recordingProgress = new JProgressBar();
        recordingProgress.setIndeterminate(true);
        recordingProgress.setVisible(false);
        recordingProgress.setPreferredSize(new Dimension(150, 20));
        rightPanel.add(recordingProgress);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JButton createButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.addActionListener(listener);
        return button;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));

        // Left side - Transcript
        JPanel transcriptPanel = new JPanel(new BorderLayout());
        transcriptPanel.setBorder(new TitledBorder("Transcript"));

        transcriptArea = new JTextArea();
        transcriptArea.setLineWrap(true);
        transcriptArea.setWrapStyleWord(true);
        transcriptArea.setFont(new Font("Arial", Font.PLAIN, 13));
        transcriptArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(transcriptArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        transcriptPanel.add(scrollPane, BorderLayout.CENTER);

        // Right side - History
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(new TitledBorder("Transcription History"));

        historyModel = new DefaultListModel<>();
        historyList = new JList<>(historyModel);
        historyList.setFont(new Font("Arial", Font.PLAIN, 12));
        historyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedTranscription();
            }
        });

        JScrollPane historyScroll = new JScrollPane(historyList);
        historyScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        historyPanel.add(historyScroll, BorderLayout.CENTER);

        // History bottom panel with delete button
        JPanel historyButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        deleteButton = createButton("🗑 Delete Selected", this::deleteTranscription);
        historyButtonPanel.add(deleteButton);
        historyPanel.add(historyButtonPanel, BorderLayout.SOUTH);

        panel.add(transcriptPanel);
        panel.add(historyPanel);

        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(statusLabel, BorderLayout.WEST);

        return panel;
    }

    private void startRecording(ActionEvent e) {
        if (speechService.startRecording()) {
            statusLabel.setText("Recording in progress... Speak clearly into the microphone");
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            saveButton.setEnabled(false);
            copyButton.setEnabled(false);
            deleteButton.setEnabled(false);
            transcriptArea.setText("Listening...\n");
            
            recordingStartTime = System.currentTimeMillis();
            recordingProgress.setVisible(true);

            // Start timer to update recording time
            recordingTimer = new Timer(1000, e1 -> updateRecordingTime());
            recordingTimer.start();
        } else {
            statusLabel.setText("Error: Could not start recording. Make sure microphone is available.");
            JOptionPane.showMessageDialog(this, "Error starting recording", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRecordingTime() {
        long elapsed = System.currentTimeMillis() - recordingStartTime;
        int seconds = (int) (elapsed / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        recordingTimeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    private void stopRecording(ActionEvent e) {
        if (recordingTimer != null) {
            recordingTimer.stop();
        }
        
        statusLabel.setText("Processing audio... Please wait");
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        recordingProgress.setVisible(false);
        recordingTimeLabel.setText("Time: 00:00");
        
        speechService.stopRecording();
    }

    private void saveTranscript(ActionEvent e) {
        String transcript = transcriptArea.getText().trim();
        if (transcript.isEmpty() || transcript.equals("Listening...")) {
            JOptionPane.showMessageDialog(this, "No transcript to save", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (speechService.saveTranscription(transcript)) {
            updateHistoryList();
            statusLabel.setText("Transcript saved successfully");
            JOptionPane.showMessageDialog(this, "Transcript saved to file!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            statusLabel.setText("Error saving transcript");
            JOptionPane.showMessageDialog(this, "Error saving transcript", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearTranscript(ActionEvent e) {
        transcriptArea.setText("");
        statusLabel.setText("Transcript cleared");
    }

    private void copyToClipboard(ActionEvent e) {
        String text = transcriptArea.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to copy", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        statusLabel.setText("Text copied to clipboard");
    }

    private void deleteTranscription(ActionEvent e) {
        int selectedIndex = historyList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transcription to delete", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String selected = historyModel.getElementAt(selectedIndex);
        int result = JOptionPane.showConfirmDialog(this, "Delete: " + selected + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION && speechService.deleteTranscription(selected)) {
            updateHistoryList();
            transcriptArea.setText("");
            statusLabel.setText("Transcription deleted");
        }
    }

    private void loadSelectedTranscription() {
        String selected = historyList.getSelectedValue();
        if (selected != null) {
            String content = speechService.loadTranscription(selected);
            transcriptArea.setText(content);
            statusLabel.setText("Loaded: " + new File(selected).getName());
        }
    }

    private void updateHistoryList() {
        historyModel.clear();
        List<String> history = speechService.getTranscriptionHistory();
        for (String item : history) {
            String displayName = new File(item).getName();
            historyModel.addElement(item);
        }
    }

    private void loadTranscriptionHistory() {
        File transcriptionsDir = new File("transcriptions");
        if (transcriptionsDir.exists() && transcriptionsDir.isDirectory()) {
            File[] files = transcriptionsDir.listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    try {
                        speechService.getTranscriptionHistory().add(file.getAbsolutePath());
                    } catch (Exception e) {
                        System.err.println("Error loading history: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void onRecognitionResult(String result) {
        SwingUtilities.invokeLater(() -> {
            transcriptArea.setText(result + "\n");
            statusLabel.setText("Recognition complete - Ready");
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            saveButton.setEnabled(true);
            copyButton.setEnabled(true);
            deleteButton.setEnabled(true);
        });
    }

    @Override
    public void onRecognitionError(String error) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Error: " + error);
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            saveButton.setEnabled(true);
            copyButton.setEnabled(true);
            deleteButton.setEnabled(true);
        });
    }
}

