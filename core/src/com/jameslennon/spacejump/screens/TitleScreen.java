package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jameslennon.spacejump.SpaceJump;
import com.jameslennon.spacejump.grid.DemoPlayer;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;
import com.jameslennon.spacejump.util.UserData;

/**
 * Created by jameslennon on 4/6/15.
 */
public class TitleScreen extends AbstractScreen {

    private DemoPlayer dp;
    private Label title;

    @Override
    public void show() {
        super.show();
        SpaceJump.leaderboard.connect();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.clear();

        Label.LabelStyle style = new Label.LabelStyle(Globals.hugeFont, Color.BLACK);
        Label.LabelStyle style2 = new Label.LabelStyle(Globals.smallFont, Color.BLACK);
        Label.LabelStyle style3 = new Label.LabelStyle(Globals.tinyFont, Color.BLACK);

        Table t = new Table(skin);
        title = new Label("O R B Y T E", style);
        t.add(title).center().padBottom(20).row();
        Label tap = new Label("tap to play", style2);
        t.add(tap).center().padBottom(80).row();
        t.setFillParent(true);
        stage.addActor(t);

        Label record = new Label("record: " + String.format("%.3f", UserData.getHighScore()), style3);
        record.setPosition(width - record.getWidth() - 10, 5);
        stage.addActor(record);

        Image leaderboard = new Image(ImageManager.getImage("game_center"));
        leaderboard.setPosition(10, 10);
        leaderboard.setSize(70, 70);
        leaderboard.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("showing leaderboard");
                SpaceJump.leaderboard.show();
                return true;
            }
        });
        stage.addActor(leaderboard);

        t.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Globals.game.setScreen("play");
            }
        });

//        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
//            @Override
//            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//                Globals.game.setScreen("play");
//                return true;
//            }
//        }));

        Group g = new Group();
//        dp = new DemoPlayer(width / 2 - 330, height / 2 + 25);
        dp = new DemoPlayer(title.getX(), title.getY());
        dp.show(g, stage);
        stage.addActor(g);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        dp.getImage().setPosition(title.getX() + 54, title.getY() + title.getHeight() / 2 - dp.getImage().getHeight() / 2);
        dp.update();
    }

    @Override
    public void back() {

    }
}
