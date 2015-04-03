package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jameslennon.spacejump.grid.Level;

import java.util.HashMap;

/**
 * Created by jameslennon on 3/21/15.
 */
public class LevelLoader {

    public static HashMap<Integer, Level> parts;

    public static void load() {
        parts = new HashMap<Integer, Level>();

        FileHandle levelsDir = Gdx.files.internal("levels");
        FileHandle[] levelFiles = levelsDir.list();


        for (FileHandle h : levelFiles) {
            if (h.name().endsWith(".level")) {
                int index = Integer.parseInt(h.nameWithoutExtension());
                parts.put(index, new Level(h));
            }
        }
    }

}
