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
 * Created by jameslennon on 5/1/15.
 */
public class TutorialScreen extends AbstractScreen {

    public static boolean showPlay = false;

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
        Image block = new Image(ImageManager.getImage("tap"));
        block.setColor(Color.BLACK);
        block.setSize(50, 50);
        block.setOrigin(Align.center);
        block.addAction(Actions.fadeOut(0));
        block.addAction(Actions.repeat(Integer.MAX_VALUE, Actions.sequence(Actions.fadeIn(.3f), Actions.delay(.4f), Actions.fadeOut(.3f))));
        Image hole = new Image(ImageManager.getImage("spiral"));
        hole.setSize(hole.getWidth() * .2f, hole.getHeight() * .2f);

        Label tap = new Label("tap and hold to jump", style);
        Label avoid = new Label("try not to touch black holes", style);
        Label boost = new Label("get into orbit to get a boost!", style);
        Label play = new Label("tap to play", style);

        Table t = new Table();
        t.setFillParent(true);

        Table row = new Table();

        if (!showPlay) {
            t.add(back).width(60).height(60).padBottom(30).row();
            back.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(3), Actions.fadeIn(.4f)));
        }

        row.add(block).height(50).width(50).pad(30);
        row.add(tap);
        t.add(row).row();
        row.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(0), Actions.fadeIn(.4f)));

        row = new Table();
        row.add(avoid);
        row.add(hole).height(50).width(50).pad(30);
        t.add(row).row();
        row.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(1), Actions.fadeIn(.4f)));

        row = new Table();
        row.add(boost);
        t.add(row).row();
        row.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(2), Actions.fadeIn(.4f)));

        if (showPlay){
            t.add(play).padTop(30);
            play.addAction(Actions.sequence(Actions.fadeOut(0), Actions.delay(3.5f), Actions.fadeIn(.4f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            stage.addListener(new ClickListener(){
                                @Override
                                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                    Globals.game.setScreen("play");
                                }
                            });
                        }
                    })
            ));
            play.addListener(new ClickListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Globals.game.setScreen("play");
                }
            });
        }

        stage.addActor(t);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        dp.update();
    }

    @Override
    public void back() {

    }
}
