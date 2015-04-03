package com.jameslennon.spacejump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jameslennon.spacejump.grid.GridMap;
import com.jameslennon.spacejump.util.InputManager;
import com.jameslennon.spacejump.util.ActionCam;
import com.jameslennon.spacejump.util.CollisionManager;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.LevelLoader;
import com.jameslennon.spacejump.screens.AbstractScreen;

/**
 * Created by jameslennon on 3/21/15.
 */
public class TestScreen extends AbstractScreen {

    private OrthographicCamera cam;
    private float padding = 200/Globals.PIXELS_PER_METER;
    private World worldInstance;
    private GridMap map;
//    private Player player;

    private Box2DDebugRenderer debugRenderer;

    public TestScreen(){
        super();

        //Camera stuff
        cam = new OrthographicCamera();
        stage.getViewport().setCamera(cam);
        cam.zoom = 1/Globals.PIXELS_PER_METER;

        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        super.show();
        if (worldInstance == null) {
            worldInstance = new World(new Vector2(0,-10), true);
            worldInstance.setContactListener(new CollisionManager());
        } else {
            Array<Body> bods = new Array<Body>();
            worldInstance.getBodies(bods);
            for (Body b : bods)
                worldInstance.destroyBody(b);
        }
        Globals.world = worldInstance;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Globals.stage = stage;
        Globals.inputManager = new InputManager();

        //GridMap
        if(map==null) {
            map = new GridMap();
            Globals.gridMap = map;
//            map.load(LevelLoader.parts.get(0));

//            player = new Player(map.spawnPoint.x, map.spawnPoint.y);
//            map.addItem(player);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

//        debugRenderer.render(worldInstance, cam.combined);

        //TODO: Input Management
//        im.update();
        float step = 1f / 60f;
        worldInstance.step(step, 6, 2);
//        if (delta <= step / 2) {
//            worldInstance.step(1f / 60f, 6, 2);
//        }

        Globals.gridMap.update();
        focusOnPlayer();
    }

    private void focusOnPlayer(){
//        Vector2 pos = player.getBody().getPosition();

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
