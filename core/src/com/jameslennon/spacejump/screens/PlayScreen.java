package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
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

    private World worldInstance;
    private GridMap map;
    private Player player;
    private ActionCam cam;
    private ComponentManager cm;
    private float score;
    private int state;

    private Box2DDebugRenderer debugRenderer;

    private Label.LabelStyle style, smallStyle;
    private Label scoreLabel, playLabel;
    private Image retryImg;

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

        playLabel = new Label("tap to retry", smallStyle);
        playLabel.setColor(Color.BLACK);
        playLabel.setFontScale(1 / Globals.PIXELS_PER_METER);
        playLabel.setSize(playLabel.getWidth() / Globals.PIXELS_PER_METER, playLabel.getHeight() / Globals.PIXELS_PER_METER);
        playLabel.setPosition(0, 0);
        playLabel.addAction(Actions.fadeOut(0));
        stage.addActor(playLabel);

        retryImg = new Image(ImageManager.getImage("retry"));
        retryImg.setSize(retryImg.getWidth() / Globals.PIXELS_PER_METER, retryImg.getHeight() / Globals.PIXELS_PER_METER);
        retryImg.setColor(Color.BLACK);
        retryImg.setY(Globals.APP_HEIGHT / 2 / Globals.PIXELS_PER_METER - retryImg.getHeight() / 2);
        retryImg.addAction(Actions.fadeOut(0));
        stage.addActor(retryImg);

        state = PLAYING;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
                retryImg.setX(player.getX()-retryImg.getWidth()/2);

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
        retryImg.addAction(Actions.fadeIn(2));
        scoreLabel.addAction(Actions.moveTo(cam.position.x - scoreLabel.getWidth() / 2, 0, .5f));

        stage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                state = RESTART;
                stage.removeListener(this);
                return true;
            }
        });
//        ImageManager.saveScreenshot(Gdx.files.external("orbyte.png"));
//        ImageManager.getScreenshot();
//        GameOverScreen.score = score;
//        Globals.game.setScreen("gameover");
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

    }
}
