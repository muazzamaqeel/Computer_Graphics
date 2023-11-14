package ProjectCTS5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyApp extends JFrame implements ActionListener {
    private final JPanel menuPanel;
    private final JButton playButton;
    private final JButton settingsButton;
    private final JButton quitButton;
    private final JFrame settingsFrame;
    private final JButton fastSpeedButton;
    private final JButton slowSpeedButton;
    private final JButton backToMainButton;
    private GamePlay gamePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyApp app = new MyApp();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setSize(700, 700);
            app.setVisible(true);
            app.setResizable(false);
        });
    }
    public MyApp() {
        JLayeredPane LayerObject = new JLayeredPane();
        setContentPane(LayerObject);
        gamePanel = new GamePlay();
        String bg ="resources/2.jpg";
        ImageIcon backgroundImage = new ImageIcon(bg);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        JLabel titleLabel = WindowDetails();
        titlePanel.add(titleLabel);

        playButton = new JButton("Play");
        settingsButton = new JButton("Settings");
        quitButton = new JButton("Quit");
        playButton.addActionListener(this);
        settingsButton.addActionListener(this);
        quitButton.addActionListener(this);

        titlePanel.setBounds(100, 100, 500, 60);
        playButton.setBounds(100, 200, 100, 30);
        settingsButton.setBounds(250, 200, 100, 30);
        quitButton.setBounds(400, 200, 100, 30);

        LayerObject.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        LayerObject.add(titlePanel, JLayeredPane.PALETTE_LAYER);
        LayerObject.add(playButton, JLayeredPane.PALETTE_LAYER);
        LayerObject.add(settingsButton, JLayeredPane.PALETTE_LAYER);
        LayerObject.add(quitButton, JLayeredPane.PALETTE_LAYER);

        gamePanel = new GamePlay();
        setTitle("BrickBreaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fastSpeedButton = new JButton("Game Speed Fast");
        slowSpeedButton = new JButton("Game Speed Slow");
        backToMainButton = new JButton("Back to Main");
        fastSpeedButton.addActionListener(this);
        slowSpeedButton.addActionListener(this);
        backToMainButton.addActionListener(this);
        fastSpeedButton.setBounds(100, 300, 150, 30);
        slowSpeedButton.setBounds(250, 300, 150, 30);
        backToMainButton.setBounds(400, 300, 150, 30);

        settingsFrame = new JFrame("Game Settings");
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setSize(700, 700);
        settingsFrame.setLayout(null);
        settingsFrame.add(fastSpeedButton);
        settingsFrame.add(slowSpeedButton);
        settingsFrame.add(backToMainButton);
    }

    private static JLabel WindowDetails() {
        JLabel titleLabel = new JLabel("BrickBreaker Remastered");
        titleLabel.setForeground(Color.BLACK);
        Font titleFont = new Font("Arial", Font.BOLD, 40);
        titleLabel.setFont(titleFont);
        return titleLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedPlayerSpeed;
        GamePlay GameObj;
        if (e.getSource() == playButton) {
            this.remove(menuPanel);
            this.add(gamePanel);
            gamePanel.requestFocus();
            gamePanel.setFocusable(true);
            gamePanel.setFocusTraversalKeysEnabled(false);
            gamePanel.play = true;
            this.dispose();
            JFrame instance1 = new JFrame();
            GameObj = new GamePlay();
            instance1.setSize(700, 700);
            instance1.setResizable(false);
            instance1.add(GameObj);
            instance1.setVisible(true);
            instance1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } else if (e.getSource() == settingsButton) {
            settingsFrame.setVisible(true);
        } else if (e.getSource() == fastSpeedButton) {
            gamePanel.setDelay(3);
            selectedPlayerSpeed = 10;
            gamePanel.setPlayerSpeed(selectedPlayerSpeed);
            gamePanel.playerSpeed = selectedPlayerSpeed;
        } else if (e.getSource() == slowSpeedButton) {
            gamePanel.setDelay(10);
            selectedPlayerSpeed = 1;
            gamePanel.setPlayerSpeed(selectedPlayerSpeed);
            gamePanel.playerSpeed = selectedPlayerSpeed;
        } else if (e.getSource() == backToMainButton) {
            settingsFrame.setVisible(false);
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }

        this.repaint();
    }

}
