package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    public static PlayScreen instance;

    private World worldInstance;
    private GridMap map;
    private Player player;
    private ActionCam cam;
    private ComponentManager cm;
    private float score;

    private Box2DDebugRenderer debugRenderer;

    private Label.LabelStyle style;
    private Label scoreLabel;

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
        scoreLabel.setSize(2,0);
        scoreLabel.setPosition(0, 0);
        stage.addActor(scoreLabel);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Globals.inputManager = new InputManager();
        Gdx.input.setInputProcessor(Globals.inputManager);
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

        if (player.isRemoved()) {
//            setup();
            endGame();
        }else {
            score = Math.max(score, player.getX() / Component.WIDTH);
            scoreLabel.setText(String.format("%.3f", score));
            scoreLabel.setPosition(score * Component.WIDTH, 1.5f);//Globals.APP_HEIGHT / 2 / Globals.PIXELS_PER_METER);
        }

        Globals.gridMap.update();
        cm.update();
        focusOnPlayer();
    }

    private void endGame() {
        stage.addAction(Actions.fadeIn(4));
//        ImageManager.saveScreenshot(Gdx.files.external("orbyte.png"));
//        ImageManager.getScreenshot();
//        GameOverScreen.score = score;
//        Globals.game.setScreen("gameover");
    }

    private void focusOnPlayer() {
//        cam.follow(player.getBody());
//        Vector2 pos = player.getBody().getPosition();
        if (!player.isRemoved())
            cam.position.set(player.getBody().getPosition().x, .5f * Globals.APP_HEIGHT / Globals.PIXELS_PER_METER, 0);
    }

    @Override
    public void back() {

    }
}
