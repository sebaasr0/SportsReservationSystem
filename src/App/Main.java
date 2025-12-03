package App;
// Importing the main UI window (GUI frame)
import ui.MainFrame;

import javax.swing.*;
// Main method: Program entry point
public class    Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            // If setting Look and Feel fails, the application continues
            // Default theme will be applied
            new MainFrame().setVisible(true);
        });
    }
}
