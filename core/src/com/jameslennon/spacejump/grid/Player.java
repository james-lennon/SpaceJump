package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jameslennon.spacejump.util.Globals;

/**
 * Created by jameslennon on 4/2/15.
 */
public class Player extends GridItem {

    public static Player instance;

    private final float width = 30;
    private float speed = 10, jumpPower = 20;
    private boolean isOnPlanet;
    private Planet ground;

    public Player(float x, float y) {
        instance = this;
        isOnPlanet = false;

        BodyDef c = new BodyDef();
        c.type = BodyDef.BodyType.DynamicBody;
        c.position.set(new Vector2((float) x, (float) y));
        // c.linearDamping = damp;
        c.linearDamping = 0;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / Globals.PIXELS_PER_METER /2, width / Globals.PIXELS_PER_METER /2);
        FixtureDef fixture = new FixtureDef();
        fixture.density = .2f;
        fixture.friction = 0f;
        fixture.shape = shape;
        fixture.restitution = .0f;
        fixture.filter.categoryBits = 1;
        fixture.filter.maskBits = 3;
        body = Globals.world.createBody(c);
        body.createFixture(fixture).setUserData(this);
        getBody().setLinearVelocity(new Vector2(speed, 0).rotateRad(getBody().getAngle()));
    }

    @Override
    public void update() {
        super.update();
        if (isOnPlanet){
            Vector2 offset = getBody().getPosition().sub(ground.getBody().getPosition());
//            getBody().setLinearVelocity(new Vector2(speed,0).rotateRad(offset.angleRad() - MathUtils.PI/2));
        }
    }

    @Override
    public void collide(GridItem other) {
        if (other instanceof Planet){
            isOnPlanet = true;
            ground = (Planet) other;
        }
    }

    @Override
    public void endCollide(GridItem other) {
        if (other instanceof Planet){
            isOnPlanet = false;
//            ground = null;
        }
    }

    public void jump(){
        if (!isOnPlanet)return;
//        body.applyLinearImpulse(new Vector2(0,jumpPower).rotateRad(getBody().getAngle()), getBody().getPosition(), true);
        Vector2 offset = getBody().getPosition().sub(ground.getBody().getPosition());
        Vector2 jump = new Vector2(0,jumpPower).setAngle(offset.angle());
        body.setLinearVelocity(body.getLinearVelocity().add(jump));
    }

    public void endJump(){

    }
}
