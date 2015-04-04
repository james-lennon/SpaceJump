package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jameslennon.spacejump.util.Globals;

/**
 * Created by jameslennon on 4/2/15.
 */
public class Planet extends Attractor{

    public Planet(float x, float y, float r){
        super(20, 300);
        r /= Globals.PIXELS_PER_METER;

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
    }

}
