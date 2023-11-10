package com.mycompany.brick;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyApp extends JFrame implements ActionListener {

    private JPanel menuPanel;
    private JButton playButton;
    private JButton settingsButton;
    private JButton quitButton;
    private GamePlay gamePanel;
    private JLayeredPane layeredPane;



    public MyApp() {
        // Create a JLayeredPane to manage layers
        layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

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
        settingsButton.addActionListener(this);
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
        setUndecorated(true); // Remove window decorations
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

            JFrame obj = new JFrame();
            GamePlay gameplay = new GamePlay();

            // Set the size of the new window (width, height)
            obj.setSize(700, 600); // Customize the size as needed

            // Set only the new window to be undecorated
            obj.setUndecorated(true);

            obj.add(gameplay);
            obj.setVisible(true);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (e.getSource() == settingsButton) {
            // Handle settings (you can add settings functionality)
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
            app.setSize(800, 600); // Customize the size as needed

            app.setVisible(true);
        });
    }



}

