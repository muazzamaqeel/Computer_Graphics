package ProjectCTS5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    // 2D array Used for creating the map
    public int map_game[][];
    public int Blocks_Width;
    public int Blocks_Length;

    // Constructor to initialize
    public MapGenerator(int row, int col) {
        map_game = new int[row][col];
        for (int[] map1 : map_game) {
            for (int j = 0; j < map_game[0].length; j++) {
                map1[j] = 1;
            }
        }
        Blocks_Width = 540 / col;
        Blocks_Length = 150 / row;
    }

    // Method to draw the game map using Graphics2D
    public void draw(Graphics2D g) {
        for (int i = 0; i < map_game.length; i++) {
            for (int j = 0; j < map_game[0].length; j++) {
                if (map_game[i][j] > 0) {
                    // Draw the filled rectangle representing a brick
                    g.setColor(Color.white);
                    g.fillRect(j * Blocks_Width + 80, i * Blocks_Length + 50, Blocks_Width, Blocks_Length);

                    // Draw the border of the brick
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * Blocks_Width + 80, i * Blocks_Length + 50, Blocks_Width, Blocks_Length);
                }
            }
        }
    }

    // Method to set the value of a specific brick in the map
    public void Total_Blocks(int v, int r, int c) {
        map_game[r][c] = v;
    }
}

