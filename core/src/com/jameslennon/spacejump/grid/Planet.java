package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;

/**
 * Created by jameslennon on 4/2/15.
 */
public class Planet extends Attractor {

    //    private Image outline;
    private float planetRadius;

    public Planet(float x, float y, float r, int spikes) {
        super(20, Math.max(2.5f * r, 130));
        r /= Globals.PIXELS_PER_METER;
        planetRadius = r;
//        mass = .5f * MathUtils.PI * r * r;
        mass = 20;

        img = new Image(ImageManager.getImage("circle"));
        img.setSize(2 * r, 2 * r);
        img.setColor(col);
        img.setPosition(x,y);

//        outline = new Image(ImageManager.getImage("outline2"));
//        outline.setSize(2 * radius, 2 * radius);

        BodyDef c = new BodyDef();
        c.type = BodyDef.BodyType.StaticBody;
        c.position.set(new Vector2((float) x, (float) y));
        // c.linearDamping = damp;
        c.linearDamping = 2f;
        CircleShape shape = new CircleShape();
//        shape.setAsBox(img.getWidth() / 2, img.getHeight() / 2);
        FixtureDef fixture = new FixtureDef();
        shape.setRadius(r);
        fixture.density = .2f;
        fixture.friction = 0f;
        fixture.shape = shape;
        fixture.restitution = .0f;
        fixture.filter.categoryBits = 1;
        fixture.filter.maskBits = 3;
        body = Globals.world.createBody(c);
        body.createFixture(fixture).setUserData(this);

        for (int i = 0; i < spikes; i++) addSpike(360.f / spikes * (i + .5f));
    }

    private void addSpike(float angle) {
//        float angle = MathUtils.random(0,360);
        Spike s = new Spike(this, angle);
        Globals.gridMap.addItem(s);
    }

    public void debug(boolean active) {
        if (active) img.setColor(Color.RED);
        else img.setColor(col);
    }

    @Override
    public void show(Stage s) {
        super.show(s);
//        s.addActor(outline);
    }

    @Override
    public void update() {
        super.update();
        img.setPosition(getBody().getPosition().x - img.getWidth() / 2,
                getBody().getPosition().y - img.getHeight() / 2);
//        outline.setPosition(getBody().getPosition().x - outline.getWidth() / 2,
//                getBody().getPosition().y - outline.getHeight() / 2);


    }

    public float getPlanetRadius() {
        return planetRadius;
    }
}
