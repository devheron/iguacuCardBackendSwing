package org.example;

import org.example.swing.LoginFrame;
import org.example.swing.ui.UITheme;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        UITheme.install();
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
