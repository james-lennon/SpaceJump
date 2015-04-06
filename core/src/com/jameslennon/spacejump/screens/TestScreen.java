package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jameslennon.spacejump.grid.GridMap;
import com.jameslennon.spacejump.grid.Planet;
import com.jameslennon.spacejump.grid.Player;
import com.jameslennon.spacejump.grid.StartPlanet;
import com.jameslennon.spacejump.util.ActionCam;
import com.jameslennon.spacejump.util.CollisionManager;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.InputManager;

/**
 * Created by jameslennon on 3/21/15.
 */
public class TestScreen extends AbstractScreen {

    public static TestScreen instance;

//    private OrthographicCamera cam;
    private float padding = 200 / Globals.PIXELS_PER_METER;
    private World worldInstance;
    private GridMap map;
    private Player player;
    private ActionCam cam;

    private Box2DDebugRenderer debugRenderer;

    public TestScreen() {
        super();
        instance = this;

        //Camera stuff
//        cam = new OrthographicCamera();
        cam = new ActionCam();
        stage.getViewport().setCamera(cam);
        cam.zoom = 1 / Globals.PIXELS_PER_METER;

        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        super.show();
        setup();
    }

    public void setup(){
        if (worldInstance == null) {
            worldInstance = new World(new Vector2(0, 0), true);
            worldInstance.setContactListener(new CollisionManager());
        } else {
            Array<Body> bods = new Array<Body>();
            worldInstance.getBodies(bods);
            System.out.println(worldInstance.getBodyCount());
            for (Body b : bods) {
                worldInstance.destroyBody(b);
//                map.removeBody(b);
            }
        }
        Globals.world = worldInstance;
        map = new GridMap();
        Globals.gridMap = map;
        map.addItem(new StartPlanet(0, 0));
        map.addItem(new Planet(12, 0, 100, 1));
        map.addItem(new Planet(30, 0, 100, 2));
        map.addItem(new Planet(42, 0, 100, 3));
        map.addItem(new Planet(56, 0, 100, 3));
//        map.addItem(new Planet(10, -4, 50));
        map.addItem(player = new Player(-190 / Globals.PIXELS_PER_METER, -90 / Globals.PIXELS_PER_METER));

        if(Globals.stage!=null){
            stage.clear();
            map.show(stage);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Globals.stage = stage;
        Globals.inputManager = new InputManager();
        Gdx.input.setInputProcessor(Globals.inputManager);

        map.show(stage);

        //GridMap
//        if (map == null) {
//            map = new GridMap();
//            Globals.gridMap = map;
//            map.addItem(new Planet(0, 0, 75));
//            map.addItem(new Planet(10, 4, 50));
//            map.addItem(new Planet(10, -4, 50));
//            map.addItem(player = new Player(-190 / Globals.PIXELS_PER_METER, -90 / Globals.PIXELS_PER_METER));
//            map.load(LevelLoader.parts.get(0));

//            player = new Player(map.spawnPoint.x, map.spawnPoint.y);
//            map.addItem(player);
//        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        float start = System.currentTimeMillis();
//        cam.update();
//        debugRenderer.render(worldInstance, cam.combined);


        float step = 1f / 60f;
        worldInstance.step(step, 6, 2);
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
//        if (delta <= step / 2) {
//            worldInstance.step(1f / 60f, 6, 2);
//        }

        Globals.gridMap.update();
        focusOnPlayer();
    }

    private void focusOnPlayer() {
//        cam.follow(player.getBody());
//        Vector2 pos = player.getBody().getPosition();
        cam.position.set(player.getBody().getPosition().x, 0, 0);

//        float left = cam.position.x - cam.viewportWidth/2+padding, right = cam.position.x + cam.viewportWidth/2-padding;
//        float bottom = cam.position.y - cam.viewportHeight/2+padding, top = cam.position.y + cam.viewportHeight/2-padding;
//        if(pos.x < left){
//
//        }else{
//
//        }
//        cam.position.set(pos.x, pos.y, 0);
    }

    @Override
    public void back() {

    }
}
