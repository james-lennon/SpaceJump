package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jameslennon.spacejump.util.Globals;

/**
 * Created by jameslennon on 4/3/15.
 */
public class Attractor extends GridItem {

    private final static float G = 10;
    public static Color col = Color.BLACK;

    protected float mass;
    protected float radius;
    protected ShapeRenderer sr;

    public Attractor(float mass, float radius) {
        this.mass = mass;
        this.radius = radius / Globals.PIXELS_PER_METER;
        sr = new ShapeRenderer();
    }

    @Override
    public void update() {
        super.update();
        sr.setProjectionMatrix(Globals.stage.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(col);
        sr.circle(getBody().getPosition().x,
                getBody().getPosition().y,
                radius);
        sr.end();

        Body a = getBody(), b = Player.instance.getBody();
        if (b==null)return;
        float dist = a.getPosition().dst(b.getPosition());
        if (dist <= radius && !Player.instance.isBoosting()){
            Player.instance.enterOrbit(this);
            Vector2 planetDistance = b.getPosition().sub(a.getPosition());
            float vecSum = Math.abs(planetDistance.x) + Math.abs(planetDistance.y);

            float magnitude = (mass * G)/(dist);
            Vector2 r = a.getPosition().sub(b.getPosition()).nor();
            b.applyForceToCenter(r.scl(magnitude), true);
        }else {
            Player.instance.exitOrbit(this);
        }
    }

    public float getForce(Vector2 pos){
        float dist = pos.dst(getBody().getPosition());
        if (dist <= radius) {
            return (mass * G) / (dist);
        }
        return 0;
    }
}
