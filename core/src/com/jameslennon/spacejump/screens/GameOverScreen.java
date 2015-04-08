package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;

/**
 * Created by jameslennon on 4/8/15.
 */
public class GameOverScreen extends AbstractScreen {

    public static float score;

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Image backg = new Image(new Texture(ImageManager.cachedScreenshot));
        backg.setFillParent(true);
        stage.addActor(backg);

        Label.LabelStyle style = new Label.LabelStyle(Globals.bigFont, Color.BLACK);

        Table table = new Table(skin);
        table.setFillParent(true);

        Label scoreLabel = new Label(String.format("%.3fm", score), style);
        table.add(scoreLabel).center().row();

        Label nextLabel = new Label("tap to play again", style);
        table.add(nextLabel);

        stage.addActor(table);
    }

    @Override
    public void back() {

    }
}
