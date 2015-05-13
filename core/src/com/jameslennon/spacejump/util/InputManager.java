package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.jameslennon.spacejump.SpaceJump;
import com.jameslennon.spacejump.grid.Player;
import com.jameslennon.spacejump.screens.PlayScreen;

public class InputManager extends InputAdapter {

    private int tapCount = 0;
    private long lastTap = 0;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            Player.instance.jump();
        } else if (keycode == Input.Keys.ESCAPE) {
            PlayScreen.instance.setup();
        } else if (keycode == Input.Keys.GRAVE) {
            Globals.showSpikes = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            Player.instance.endJump();
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Player.instance.jump();
        if (System.currentTimeMillis() - lastTap < 333) {
            tapCount++;
            System.out.println("tap");
        }else {
            tapCount = 0;
            System.out.println("reset");
        }
        if (tapCount>=10){
            Globals.showSpikes = true;
            SpaceJump.achievements.handleEvent("EasterEgg");
        }
        lastTap = System.currentTimeMillis();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Player.instance.endJump();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return true;
    }
}
