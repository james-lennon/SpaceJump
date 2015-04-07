package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jameslennon.spacejump.SpaceJump;
import com.jameslennon.spacejump.util.Globals;

/**
 * Created by jameslennon on 4/6/15.
 */
public class TitleScreen extends AbstractScreen {

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Label.LabelStyle style = new Label.LabelStyle(Globals.bigFont, Color.BLACK);
        Label.LabelStyle style2 = new Label.LabelStyle(Globals.smallFont, Color.BLACK);

        Table t = new Table(skin);
        Label title = new Label("O R B Y T E", style);
        t.add(title).center().padBottom(20).row();
        Label tap = new Label("tap to play", style2);
        t.add(tap).center();
        t.setFillParent(true);

        stage.addActor(t);

        stage.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Globals.game.setScreen("play");
            }
        });
    }

    @Override
    public void back() {

    }
}
