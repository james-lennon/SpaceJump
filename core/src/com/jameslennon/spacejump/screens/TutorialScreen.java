package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jameslennon.spacejump.grid.DemoPlayer;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;

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
        dp = new DemoPlayer(width / 2, height / 2);
        dp.show(g, stage);
        stage.addActor(g);

        Label.LabelStyle style = new Label.LabelStyle(Globals.smallFont, Color.BLACK);

        Image block = new Image(ImageManager.getImage("block"));
        block.setColor(Color.BLACK);
        block.addAction(Actions.repeat(1000, Actions.rotateBy(360, 2.f)));

        Label tap = new Label("tap and hold to jump", style);
        

        Table t = new Table();
        t.add(block);


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
