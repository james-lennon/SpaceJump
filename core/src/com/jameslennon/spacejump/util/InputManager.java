package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.jameslennon.spacejump.grid.Planet;
import com.jameslennon.spacejump.grid.Player;
import com.jameslennon.spacejump.screens.TestScreen;

/**
 * Created by jameslennon on 4/2/15.
 */
public class InputManager extends InputAdapter{

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE){
            Player.instance.jump();
        }else if (keycode == Input.Keys.ESCAPE){
            TestScreen.instance.setup();
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Player.instance.jump();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Player.instance.endJump();
        return true;
    }
}
