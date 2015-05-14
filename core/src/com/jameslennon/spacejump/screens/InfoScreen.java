package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;

/**
 * Created by jameslennon on 5/13/15.
 */
public class InfoScreen extends AbstractScreen {

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Label.LabelStyle style = new Label.LabelStyle(Globals.smallFont, Color.BLACK);

        Image back = new Image(ImageManager.getImage("arrow"));
        back.setSize(back.getWidth() * .2f, back.getHeight() * .2f);
        back.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Globals.game.setScreen("title");
            }
        });

        Label james = new Label("graphics & coding by James Lennon", style);
        Label alex = new Label("music by Alex Lennon", style);
        Label icons = new Label("enjoy.", style);

        Table t = new Table();
        t.setFillParent(true);

        Table row = new Table();

        t.add(back).width(60).height(60).padBottom(30).row();
        back.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(3), Actions.fadeIn(.4f)));

        row.add(james).pad(30);
        t.add(row).row();
        row.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(0), Actions.fadeIn(.4f)));

        row = new Table();
        row.add(alex).pad(30);
        t.add(row).row();
        row.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(1), Actions.fadeIn(.4f)));

        row = new Table();
        row.add(icons).pad(30);
        t.add(row).row();
        row.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(2), Actions.fadeIn(.4f)));

        stage.addActor(t);
    }

    @Override
    public void back() {

    }
}
