package com.mycompany.brick;// Import necessary Java libraries for GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

// Define the HighScoreWindow class, extending JFrame
public class HighScoreWindow extends JFrame {
    private Preferences preferences;
    private int highestScore;

    // Constructor that takes the player's score as a parameter
    public HighScoreWindow(int score) {
        // Set up preferences to store high score information
        preferences = Preferences.userNodeForPackage(GamePlay.class);
        // Retrieve the current highest score from preferences
        highestScore = preferences.getInt("highestScore", 0);

        // If the player's score is greater than the current highest score, update and save it
        if (score > highestScore) {
            highestScore = score;
            preferences.putInt("highestScore", highestScore);
        }

        // Initialize and set up the GUI components
        initComponents();
    }

    // Method to set up and configure the GUI components
    private void initComponents() {
        // Set up JFrame properties
        setTitle("High Score");
        setSize(300, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel with a GridLayout
        JPanel panel = new JPanel(new GridLayout(3, 1));

        // Create a JLabel to display a congratulatory message or information about the high score
        JLabel scoreLabel = new JLabel("Your Score: " + highestScore);
        // Create a JButton to allow the player to go back to the main part of the application
        JButton backButton = new JButton("Back to Main");

        // Add an ActionListener to the backButton
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                HighScoreWindow.this.dispose();
                // Restart the application (you may want to modify this part based on your actual class structure)
                MyApp.main(null); // Replace with the appropriate method
            }
        });

        // Add JLabel, scoreLabel, and backButton to the panel
        panel.add(new JLabel("Congratulations! New High Score!")); // Message about the high score
        panel.add(scoreLabel); // Display the player's score
        panel.add(backButton); // Button to go back to the main part of the application

        // Add the panel to the JFrame
        add(panel);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Make the window visible
        setVisible(true);
    }
}
