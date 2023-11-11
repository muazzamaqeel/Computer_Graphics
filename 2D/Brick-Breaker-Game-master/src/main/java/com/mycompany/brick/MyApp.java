package com.mycompany.brick;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyApp extends JFrame implements ActionListener {
    private int selectedPlayerSpeed = 5; // Default player speed
    private JPanel menuPanel;
    private JButton slowSpeedButton; // Add this line
    private JButton backToMainButton; // Add this line

    private JFrame settingsFrame; // Add this line
    private JButton playButton;
    private JButton settingsButton;
    private JButton quitButton;
    private GamePlay gamePanel; // Store a reference to the GamePlay instance
    private JLayeredPane layeredPane;
    private JButton normalSpeedButton;
    private JButton fastSpeedButton;



    public MyApp() {
        // Create a JLayeredPane to manage layers
        layeredPane = new JLayeredPane();
        setContentPane(layeredPane);
        gamePanel = new GamePlay();

        // Load and set the background image
        ImageIcon backgroundImage = new ImageIcon("background.jpg"); // Replace "background.jpg" with your image file
        JLabel backgroundLabel = new JLabel(backgroundImage);

        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());

        // Initialize menuPanel with a vertical BoxLayout
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Create a panel for the title and add a BoxLayout for horizontal alignment
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));

        // Create JLabel for the title
        JLabel titleLabel = getjLabel();

        // Add the title label to the titlePanel
        titlePanel.add(titleLabel);

        // Create buttons for Play, Settings, and Quit
        playButton = new JButton("Play");
        settingsButton = new JButton("Settings");
        quitButton = new JButton("Quit");

        // Add action listeners to the buttons
        playButton.addActionListener(this);
        settingsButton.addActionListener(this); // Add this line
        quitButton.addActionListener(this);

        // Set bounds and positions for components
        titlePanel.setBounds(100, 100, 500, 60); // Customize the position and size as needed
        playButton.setBounds(100, 200, 100, 30); // Customize the position and size
        settingsButton.setBounds(250, 200, 100, 30); // Customize the position and size
        quitButton.setBounds(400, 200, 100, 30); // Customize the position and size

        // Add components to the layers
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(titlePanel, JLayeredPane.PALETTE_LAYER); // Higher layer for buttons and text
        layeredPane.add(playButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(settingsButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(quitButton, JLayeredPane.PALETTE_LAYER);

        // Initialize gamePanel (the actual game)
        gamePanel = new GamePlay();

        // Set up the frame
        setTitle("BrickBreaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setUndecorated(true);



        fastSpeedButton = new JButton("Game Speed Fast");
        slowSpeedButton = new JButton("Game Speed Slow");
        backToMainButton = new JButton("Back to Main");

        // Add action listeners to the new buttons
        fastSpeedButton.addActionListener(this);
        slowSpeedButton.addActionListener(this);
        backToMainButton.addActionListener(this);

        // Set bounds and positions for new components
        fastSpeedButton.setBounds(100, 300, 150, 30);
        slowSpeedButton.setBounds(250, 300, 150, 30);
        backToMainButton.setBounds(400, 300, 150, 30);

        // Initialize the settings frame
        settingsFrame = new JFrame("Game Settings");
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setSize(700, 400); // Customize the size as needed
        settingsFrame.setUndecorated(true);
        settingsFrame.setLayout(null); // Use absolute layout for simplicity

        // Add components to the settings frame
        settingsFrame.add(fastSpeedButton);
        settingsFrame.add(slowSpeedButton);
        settingsFrame.add(backToMainButton);

    }



    private static JLabel getjLabel() {
        JLabel titleLabel = new JLabel("BrickBreaker Remastered");
        titleLabel.setForeground(Color.BLACK);

        // Set a larger font for the title label
        Font titleFont = new Font("Arial", Font.BOLD, 40); // Customize the font as needed
        titleLabel.setFont(titleFont);
        return titleLabel;
    }



    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            // Start the game when the "Play" button is clicked
            this.remove(menuPanel);
            this.add(gamePanel);
            gamePanel.requestFocus();
            gamePanel.setFocusable(true);
            gamePanel.setFocusTraversalKeysEnabled(false);
            gamePanel.play = true;

            // The Game starts

            this.dispose(); // Close the current window
            JFrame obj = new JFrame();
            GamePlay gameplay = new GamePlay();
            // Set the size of the new window (width, height)
            obj.setSize(700, 600); // Customize the size as needed
            obj.setResizable(false); // Make the window not resizable

            // Set only the new window to be undecorated
            //obj.setUndecorated(true);

            obj.add(gameplay);
            obj.setVisible(true);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (e.getSource() == settingsButton) {
            // Display the settings frame when the "Settings" button is clicked
            settingsFrame.setVisible(true);
        } else if (e.getSource() == fastSpeedButton) {
            // Set the game speed to fast
            gamePanel.setDelay(1);
            selectedPlayerSpeed = 10; // Adjust as needed
            gamePanel.setPlayerSpeed(selectedPlayerSpeed);
            gamePanel.playerSpeed = selectedPlayerSpeed; // Update playerSpeed variable

        } else if (e.getSource() == slowSpeedButton) {
            // Set the game speed to slow
            gamePanel.setDelay(15); // Change the delay value as needed
            selectedPlayerSpeed = 1; // Adjust as needed
            gamePanel.setPlayerSpeed(selectedPlayerSpeed);
            gamePanel.playerSpeed = selectedPlayerSpeed;

        } else if (e.getSource() == backToMainButton) {
            // Close the settings frame and go back to the main frame
            settingsFrame.dispose();
        } else if (e.getSource() == quitButton) {
            System.exit(0); // Exit the game when the "Quit" button is clicked
        }


        this.repaint(); // Repaint the frame
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyApp app = new MyApp();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set the size of the window (width, height)
            app.setSize(700, 600); // Customize the size as needed

            app.setVisible(true);
            app.setResizable(false); // Make the window not resizable

        });
    }



}

