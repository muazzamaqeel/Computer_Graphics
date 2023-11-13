package ProjectCTS5;
import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class HighScoreWindow extends JFrame {
    private Preferences preferences_obj;
    private int highestScore;

    public HighScoreWindow(int currentScore) {
        preferences_obj = Preferences.userNodeForPackage(GamePlay.class);
        highestScore = preferences_obj.getInt("Highest Score", 0);

        if (currentScore > highestScore) {
            highestScore = currentScore;
            preferences_obj.putInt("Highest Score", highestScore);
        }

        initComponents(currentScore);
    }

    private void initComponents(int currentScore) {
        setTitle("High Score");
        setSize(300, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        JLabel congratsLabel = new JLabel("Congratulations!");
        JLabel scoreLabel = new JLabel("Your Score: " + currentScore);
        JLabel highestScoreLabel = new JLabel("Highest Score: " + highestScore);
        JButton backButton = new JButton("Quit");

        backButton.addActionListener(e -> System.exit(0));

        panel.add(congratsLabel);
        panel.add(scoreLabel);
        panel.add(highestScoreLabel);
        panel.add(backButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
