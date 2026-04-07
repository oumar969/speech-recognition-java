package com.speechrecognition;

import com.speechrecognition.ui.MainFrame;

import javax.swing.*;

/**
 * Main entry point for the Speech Recognition Application
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
