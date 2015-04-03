package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jameslennon.spacejump.grid.GridMap;
import com.jameslennon.spacejump.util.InputManager;
import com.jameslennon.spacejump.SpaceJump;

/**
 * Created by jameslennon on 3/21/15.
 */
public class Globals {

    public static final float PIXELS_PER_METER = 30;
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 500;
    public static final int TILE_WIDTH = 50;

    public static World world;
    public static Stage stage;
    public static GridMap gridMap;
    public static SpaceJump game;
    public static Skin skin = new Skin(Gdx.files.internal("layout/uiskin.json"));
    public static InputManager inputManager = new InputManager();

    public static void scaleImage(Image img){
        img.setSize(img.getWidth()/Globals.PIXELS_PER_METER, img.getHeight()/Globals.PIXELS_PER_METER);
    }

}
