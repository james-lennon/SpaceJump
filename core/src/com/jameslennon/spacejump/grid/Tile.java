package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;

/**
 * Created by jameslennon on 3/21/15.
 */
public class Tile extends GridItem {

    public Tile(float x, float y) {
        img = new Image(ImageManager.getImage("tile"));
        img.setPosition(x, y);
        img.setScale(1.f / Globals.PIXELS_PER_METER);
    }

}
