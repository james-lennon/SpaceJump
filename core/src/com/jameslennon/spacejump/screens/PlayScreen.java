package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jameslennon.spacejump.SpaceJump;
import com.jameslennon.spacejump.comps.Component;
import com.jameslennon.spacejump.comps.ComponentManager;
import com.jameslennon.spacejump.grid.GridMap;
import com.jameslennon.spacejump.grid.Player;
import com.jameslennon.spacejump.util.*;

/**
 * Created by jameslennon on 3/21/15.
 */
public class PlayScreen extends AbstractScreen {
    private final static int PLAYING = 0, ENDING = 1, OVER = 2, RESTART = 3;
    public static PlayScreen instance;
    private int adCount;
    private World worldInstance;
    private GridMap map;
    private Player player;
    private ActionCam cam;
    private ComponentManager cm;
    private float score, width;
    private int state;

    private Box2DDebugRenderer debugRenderer;

    private Label.LabelStyle style, smallStyle;
    private Label scoreLabel, playLabel, highScoreLabel;
    private Image retryImg, backImg;
    private Group buttons;

    public PlayScreen() {
        super();
        instance = this;

        //Camera stuff
        cam = new ActionCam();
        cm = new ComponentManager();
        Globals.compManager = cm;
        stage.getViewport().setCamera(cam);
        cam.zoom = 1 / Globals.PIXELS_PER_METER;

        debugRenderer = new Box2DDebugRenderer();

        adCount = 0;
    }

    @Override
    public void show() {
        super.show();
        Globals.stage = stage;
        style = new Label.LabelStyle(Globals.bigFont, Color.BLACK);
        smallStyle = new Label.LabelStyle(Globals.smallFont, Color.BLACK);
        setup();
    }

    public void setup() {
        if (worldInstance == null) {
            worldInstance = new World(new Vector2(0, 0), true);
            worldInstance.setContactListener(new CollisionManager());
        } else {
            Array<Body> bods = new Array<Body>();
            worldInstance.getBodies(bods);
            for (Body b : bods) {
                worldInstance.destroyBody(b);
            }
        }
        Globals.world = worldInstance;
        map = new GridMap();
        Globals.gridMap = map;
        cm.reset();
        map.addItem(player = new Player(0, .2f * Globals.APP_HEIGHT / Globals.PIXELS_PER_METER));

        if (Globals.stage != null) {
            stage.clear();
            map.show(stage);
        }
        stage.getRoot().addAction(Actions.fadeOut(0));
        stage.getRoot().addAction(Actions.fadeIn(2));
        score = 0;
        scoreLabel = new Label("0", style);
        scoreLabel.setColor(Color.BLACK);
        scoreLabel.setFontScale(1 / Globals.PIXELS_PER_METER);
        scoreLabel.setSize(scoreLabel.getWidth() / Globals.PIXELS_PER_METER, scoreLabel.getHeight() / Globals.PIXELS_PER_METER);
        scoreLabel.setPosition(0, 0);
        stage.addActor(scoreLabel);

        highScoreLabel = new Label("R E C O R D", style);
        highScoreLabel.setColor(Color.BLACK);
        highScoreLabel.setFontScale(1 / Globals.PIXELS_PER_METER);
        highScoreLabel.setSize(highScoreLabel.getWidth() / Globals.PIXELS_PER_METER, highScoreLabel.getHeight() / Globals.PIXELS_PER_METER);
        highScoreLabel.addAction(Actions.fadeOut(0.f));
        highScoreLabel.setX(0);
        highScoreLabel.setY(Globals.APP_HEIGHT / Globals.PIXELS_PER_METER - highScoreLabel.getHeight());
        stage.addActor(highScoreLabel);

        playLabel = new Label("tap to retry", smallStyle);
        playLabel.setColor(Color.BLACK);
        playLabel.setFontScale(1 / Globals.PIXELS_PER_METER);
        playLabel.setSize(playLabel.getWidth() / Globals.PIXELS_PER_METER, playLabel.getHeight() / Globals.PIXELS_PER_METER);
        playLabel.setPosition(0, 0);
        playLabel.addAction(Actions.fadeOut(0));
        stage.addActor(playLabel);

        buttons = new Table();

        backImg = new Image(ImageManager.getImage("arrow"));
        backImg.setColor(Color.BLACK);
        backImg.setSize(backImg.getWidth() / Globals.PIXELS_PER_METER, backImg.getHeight() / Globals.PIXELS_PER_METER);
//        backImg.setX(0);
        backImg.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back();
                return true;
            }
        });
        buttons.addActor(backImg);

        retryImg = new Image(ImageManager.getImage("retry"));
        retryImg.setSize(retryImg.getWidth() / Globals.PIXELS_PER_METER, retryImg.getHeight() / Globals.PIXELS_PER_METER);
        retryImg.setColor(Color.BLACK);
        retryImg.setX(backImg.getWidth() + .2f);
        buttons.addActor(retryImg);
        buttons.setWidth(2 * retryImg.getWidth() + .2f);
        buttons.setHeight(retryImg.getHeight());

        buttons.setY(Globals.APP_HEIGHT / 2 / Globals.PIXELS_PER_METER - buttons.getHeight() / 2);
        stage.addActor(buttons);

        state = PLAYING;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.width = width;
        Globals.inputManager = new InputManager();
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, Globals.inputManager));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

//        cam.update();
//        debugRenderer.render(worldInstance, cam.combined);

        float step = 1f / 60f;
        worldInstance.step(step, 6, 2);
//        if (delta <= step / 2) {
//            worldInstance.step(1f / 60f, 6, 2);
//        }

        if (state == RESTART) {
            setup();
        }

        if (state == PLAYING) {
            if (player.isRemoved()) {
//            setup();
                endGame();
            } else {
                score = Math.max(score, player.getX() / Component.WIDTH);
                scoreLabel.setText(String.format("%.3f", score));
                scoreLabel.setPosition(score * Component.WIDTH, 0);//Globals.APP_HEIGHT / 2 / Globals.PIXELS_PER_METER);

                playLabel.setPosition(player.getX() - playLabel.getWidth(), Globals.APP_HEIGHT / 2 / Globals.PIXELS_PER_METER);
                highScoreLabel.setX(player.getX() - highScoreLabel.getWidth() / 2);
                float w = width / Globals.PIXELS_PER_METER;
                buttons.setX(player.getX() - w);
//                backImg.setX(player.getX() - w);
//                retryImg.setX(player.getX() - w);

//                Globals.gridMap.update();
            }
        }

        Globals.gridMap.update();
        cm.update();
        focusOnPlayer();
    }

    private void endGame() {
        state = OVER;
        map.group.addAction(Actions.fadeOut(2));
        scoreLabel.addAction(Actions.moveTo(cam.position.x - scoreLabel.getWidth() * 2, 0, .5f));
        buttons.addAction(Actions.sequence(Actions.delay(.5f),
                Actions.moveTo(cam.position.x - buttons.getWidth() / 2, buttons.getY(), .5f)));

        stage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                state = RESTART;
                stage.removeListener(this);
                return true;
            }
        });

        float highScore = UserData.getHighScore();
        if (score > highScore) {
            highScoreLabel.addAction(Actions.fadeIn(.5f));
            UserData.setHighScore(score);
            SpaceJump.leaderboard.addScore(score);
        } else {
            if (adCount % 6 == 2) {
                SpaceJump.ads.show();
            }
            adCount++;
        }
        UserData.addDistance(score);
        SpaceJump.achievements.handleScore(score);

//        ImageManager.saveScreenshot(Gdx.files.external("orbyte.png"));
//        ImageManager.getScreenshot();
    }

    private void focusOnPlayer() {
//        Vector2 pos = player.getBody().getPosition();
        if (!player.isRemoved()) {
            cam.position.set(player.getBody().getPosition().x, .5f * Globals.APP_HEIGHT / Globals.PIXELS_PER_METER, 0);
//            cam.follow(player.getBody());
        }
    }

    @Override
    public void back() {
        Globals.game.setScreen("title");
    }
}
