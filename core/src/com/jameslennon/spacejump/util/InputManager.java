package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.jameslennon.spacejump.grid.Planet;
import com.jameslennon.spacejump.grid.Player;

/**
 * Created by jameslennon on 4/2/15.
 */
public class InputManager extends InputAdapter{

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE){
            Player.instance.jump();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE){
            Player.instance.endJump();
        }
        return true;
    }
}
