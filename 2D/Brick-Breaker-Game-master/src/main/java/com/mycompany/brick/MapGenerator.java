package com.mycompany.brick;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    // 2D array to represent the game map
    public int map[][];
    // Width and height of individual bricks
    public int bricksWidth;
    public int bricksHeight;

    // Constructor to initialize the map based on the given number of rows and columns
    public MapGenerator(int row, int col) {
        // Initialize the map with all bricks initially present
        map = new int[row][col];
        for (int[] map1 : map) {
            for (int j = 0; j < map[0].length; j++) {
                map1[j] = 1;
            }
        }
        // Calculate the width and height of individual bricks based on the given dimensions
        bricksWidth = 540 / col;
        bricksHeight = 150 / row;
    }

    // Method to draw the game map using Graphics2D
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    // Draw the filled rectangle representing a brick
                    g.setColor(Color.white);
                    g.fillRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);

                    // Draw the border of the brick
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);
                }
            }
        }
    }

    // Method to set the value of a specific brick in the map
    public void setBricksValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
