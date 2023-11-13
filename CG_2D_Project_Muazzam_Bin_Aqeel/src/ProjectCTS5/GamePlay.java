package ProjectCTS5;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.prefs.Preferences;
public class GamePlay extends JPanel implements KeyListener, ActionListener {
    boolean play = false;
    private Toolkit toolkit;
    private int score = 0;
    private int totalbricks = 21;
    public int playerSpeed = 5;  // Default speed
    private boolean highScoreWindowShown = false; // Flag to control high score window visibility
    private Timer Timer;
    private int delay = 8;  // Time delay between updates
    private int playerX = 310;  // Initial player position
    private int ballposX = 120;  // Initial ball position
    private int ballposY = 350;
    private int ballXdir = -1;  // Initial ball direction
    private int ballYdir = -2;
    private MapGenerator map;
    private Preferences preferences;  // Preferences for saving game settings
    private Clip hitPaddleSound;  // Sound clip for ball hitting the paddle

    // Method to set the player's speed
    public void setPlayerSpeed(int speed) {
        this.playerSpeed = speed;
    }

    // Constructor for the GamePlay class
    public GamePlay() {
        // Initialize the game map and set up key listeners
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Initialize the Timer for regular updates and start it
        Timer = new Timer(delay, this);
        Timer.start();

        // Set up preferences for saving and loading game settings
        preferences = Preferences.userNodeForPackage(GamePlay.class);
        int savedDelay = preferences.getInt("gameSpeed", 8);
        setDelay(savedDelay);

        // Load the sound file for the ball hitting the paddle
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\programming\\GIT_REPO-Computer Graphics\\Computer_Graphics\\2D\\Brick-Breaker-Game-master\\beep.wav"));
            hitPaddleSound = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
            hitPaddleSound.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void paint(Graphics g) {
        // Draw the background image
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\muazz\\OneDrive\\Desktop\\Computer Science (THU)\\CTS5\\CG\\Final_2D_Project\\BrickBreaker\\src\\2.jpg");
        g.drawImage(backgroundImage.getImage(), 0, 0, null);

        // Draw the game map
        map.draw((Graphics2D) g);

        // Draw borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Display the player's score
        g.setColor(Color.lightGray);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // Draw the player's paddle
        g.setColor(Color.white);
        g.fillRect(playerX, 550, 100, 8);

        // Draw the ball
        g.setColor(Color.white);
        g.fillOval(ballposX, ballposY, 20, 20);

        // Display game over message and prompt to restart
        if (ballposY > 570 && !highScoreWindowShown) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            highScoreWindowShown = true;
            SwingUtilities.invokeLater(() -> {
                HighScoreWindow highScoreWindow = new HighScoreWindow(score);
                highScoreWindow.setVisible(true);
            });
        }

        // Display game over message and prompt to restart if all bricks are destroyed
        if(totalbricks == 0){
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }

        // Release system resources
        g.dispose();
    }

    // Method to handle actions triggered by the Timer
    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        // Game logic
        if (play) {
            // Handle ball and paddle collision
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
                // Play the paddle-hit sound
                hitPaddleSound.setFramePosition(0);
                hitPaddleSound.start();
            }

            // Check for collision with bricks and update score
            A:
            for (int i = 0; i < map.map_game.length; i++) {
                for (int j = 0; j < map.map_game[0].length; j++) {
                    if (map.map_game[i][j] > 0) {
                        int brickX = j * map.Blocks_Width + 80;
                        int brickY = i * map.Blocks_Length + 50;
                        int bricksWidth = map.Blocks_Width;
                        int bricksHeight = map.Blocks_Length;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;

                        // Handle ball and brick collision
                        if (ballrect.intersects(brickrect)) {
                            map.Total_Blocks(0, i, j);
                            totalbricks--;
                            score += 5;

                            // Change ball direction based on collision point
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            // Play sound if the ball hits the paddle
                            if (ballYdir > 0 && ballrect.intersects(new Rectangle(playerX, 550, 100, 8))) {
                                toolkit.beep();
                            }
                            break A;
                        }
                    }
                }
            }

            // Update ball position based on direction
            ballposX += ballXdir;
            ballposY += ballYdir;

            // Reflect ball off walls
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        // Repaint the game components
        repaint();
    }

    // Method to handle key-typed events (not used in this code)
    @Override
    public void keyTyped(KeyEvent e) {
        // Not implemented
    }

    // Method to handle key-released events (not used in this code)
    @Override
    public void keyReleased(KeyEvent e) {
        // Not implemented
    }

    // Method to handle key-pressed events
    @Override
    public void keyPressed(KeyEvent e) {
        // Move the paddle right if the right arrow key is pressed
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        // Move the paddle left if the left arrow key is pressed
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        // Restart the game if the Enter key is pressed
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                // Reset game variables
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);

                // Repaint the game components
                repaint();
            }
        }
    }

    // Method to move the paddle to the right
    public void moveRight() {
        play = true;
        playerX += 20 * playerSpeed;  // Adjust speed based on playerSpeed
    }

    // Method to move the paddle to the left
    public void moveLeft() {
        play = true;
        playerX -= 20 * playerSpeed;  // Adjust speed based on playerSpeed
    }

    // Method to set the delay (time interval between game updates)
    public void setDelay(int newDelay) {
        this.delay = newDelay;
        Timer.setDelay(newDelay);

        // Save the game speed to preferences
        preferences.putInt("gameSpeed", newDelay);
    }
}
