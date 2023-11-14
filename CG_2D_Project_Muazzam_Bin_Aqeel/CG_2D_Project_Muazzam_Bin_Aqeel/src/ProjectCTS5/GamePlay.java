package ProjectCTS5;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.prefs.Preferences;
import java.io.File;
import java.io.IOException;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    boolean play = false;
    private Toolkit toolkit;
    private int score = 0;
    private int totalbricks = 21;
    public int playerSpeed = 5;
    private boolean highScoreWindowShown = false;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;
    private Preferences preferences;
    private Clip hitPaddleSound;

    public void setPlayerSpeed(int speed) {
        this.playerSpeed = speed;
    }

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
        preferences = Preferences.userNodeForPackage(GamePlay.class);
        int savedDelay = preferences.getInt("gameSpeed", 8);
        setDelay(savedDelay);

        String wavFileName = "resources/beep.wav";

        try {
            File audioFile = new File(wavFileName);

            if (audioFile.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                AudioFormat format = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                hitPaddleSound = (Clip) AudioSystem.getLine(info);
                hitPaddleSound.open(audioInputStream);
            } else {
                System.err.println("Error: Audio file not found - " + wavFileName);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        try {
            ImageIcon backgroundImage = new ImageIcon("resources/2.jpg");
            g.drawImage(backgroundImage.getImage(), 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.draw((Graphics2D) g);
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        g.setColor(Color.lightGray);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        g.setColor(Color.white);
        g.fillRect(playerX, 550, 100, 8);
        g.setColor(Color.white);
        g.fillOval(ballposX, ballposY, 20, 20);

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
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
                hitPaddleSound.setFramePosition(0);
                hitPaddleSound.start();
            }

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

                        if (ballrect.intersects(brickrect)) {
                            map.Total_Blocks(0, i, j);
                            totalbricks--;
                            score += 5;

                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            if (ballYdir > 0 && ballrect.intersects(new Rectangle(playerX, 550, 100, 8))) {
                                toolkit.beep();
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

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
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20 * playerSpeed;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20 * playerSpeed;
    }

    public void setDelay(int newDelay) {
        this.delay = newDelay;
        Timer.setDelay(newDelay);
        preferences.putInt("gameSpeed", newDelay);
    }
}
