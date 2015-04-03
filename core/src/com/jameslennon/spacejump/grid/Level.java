package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by jameslennon on 3/21/15.
 */
public class Level {

    private int w, h;
    private char[][] grid;

    public Level(FileHandle file) {
//        Scanner in = new Scanner(file.read());
        BufferedReader reader = file.reader(1024);
        try {
            String line = reader.readLine();
            String[] parts = line.split(" ");
            w = Integer.parseInt(parts[0]);
            h = Integer.parseInt(parts[1]);
//            in.nextLine();
            grid = new char[w][h];
            for (int j = h - 1; j >= 0; j--) {
                line = reader.readLine();
                for (int i = 0; i < w; i++) {
                    char c = line.charAt(i);
                    grid[i][j] = c;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public char getValue(int x, int y) {
        if (x < 0 || x >= w || y < 0 || y >= h) {
            System.err.println("Level coordinates out of bounds: x:" + x + ", y:" + y + ", w:" + w + ", h:" + h);
            return '#';
        }
        return grid[x][y];
    }

}
