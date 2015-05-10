package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.jameslennon.spacejump.grid.DemoPlayer;

/**
 * Created by jameslennon on 5/1/15.
 */
public class TutorialScreen extends AbstractScreen {

    private DemoPlayer dp;

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Group g = new Group();
        dp = new DemoPlayer(width/2,height/2);
        dp.show(g, stage);
        stage.addActor(g);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        dp.update();
    }

    @Override
    public void back() {

    }
}
