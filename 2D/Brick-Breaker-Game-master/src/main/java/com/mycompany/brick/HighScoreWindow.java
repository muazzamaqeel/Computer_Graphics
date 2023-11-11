package com.mycompany.brick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class HighScoreWindow extends JFrame {
    private Preferences preferences;
    private int highestScore;

    public HighScoreWindow(int score) {
        preferences = Preferences.userNodeForPackage(GamePlay.class);
        highestScore = preferences.getInt("highestScore", 0);

        if (score > highestScore) {
            highestScore = score;
            preferences.putInt("highestScore", highestScore);
        }

        initComponents();

    }

    private void initComponents() {
        setTitle("High Score");
        setSize(300, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel scoreLabel = new JLabel("Your Score: " + highestScore);
        JButton backButton = new JButton("Back to Main");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HighScoreWindow.this.dispose();
                // You may want to modify this part based on your actual class structure
                MyApp.main(null); // Restart the application (replace with the appropriate method)
            }
        });

        panel.add(new JLabel("Congratulations! New High Score!"));
        panel.add(scoreLabel);
        panel.add(backButton);

        add(panel);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }
}
