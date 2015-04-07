package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;
import com.jameslennon.spacejump.util.ParticleEffectActor;

/**
 * Created by jameslennon on 4/2/15.
 */
public class Player extends GridItem {

    public static Player instance;
    public static Color col = Color.BLACK;

    private final float width = 30;
    private float speed = 10, jumpPower = 5; //10;
    private boolean isOnPlanet, isJumping;
    private Planet ground;
    private long startJump, jumpTime = 500, groundTime;
    private ParticleEffectActor pea;

    public Player(float x, float y) {
        instance = this;
        isOnPlanet = isJumping = false;

        img = new Image(ImageManager.getImage("block"));
        img.setSize(width / Globals.PIXELS_PER_METER, width / Globals.PIXELS_PER_METER);
        img.setOrigin(width / Globals.PIXELS_PER_METER / 2, width / Globals.PIXELS_PER_METER / 2);
        img.setColor(col);

        ParticleEffect effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particle/trail.particle"), Gdx.files.internal("img"));
        effect.getEmitters().get(0).getScale().setHigh(4 / Globals.PIXELS_PER_METER);
        pea = new ParticleEffectActor(effect, 0, 0);

        BodyDef c = new BodyDef();
        c.type = BodyDef.BodyType.DynamicBody;
        c.position.set(new Vector2((float) x, (float) y));
        // c.linearDamping = damp;
        c.linearDamping = 0;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / Globals.PIXELS_PER_METER / 2, width / Globals.PIXELS_PER_METER / 2);
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
    public void show(Stage s) {
        super.show(s);
        s.addActor(pea);
    }

    @Override
    public void update() {
        super.update();
        img.setPosition(getBody().getPosition().x - img.getWidth() / 2, getBody().getPosition().y - img.getHeight() / 2);
        pea.setPosition(getBody().getPosition().x, getBody().getPosition().y);
        img.setRotation(getBody().getAngle() * 180 / MathUtils.PI);

        Vector2 vel = getBody().getLinearVelocity();
        if (ground != null) {
            Vector2 offset = new Vector2(getBody().getPosition()).sub(ground.getBody().getPosition());
            if (isOnPlanet) {
                float offAngle = offset.angleRad() - MathUtils.PI / 2;
//            getBody().setLinearVelocity(new Vector2(speed,0).rotateRad(offset.angleRad() - MathUtils.PI/2));
                //Speed up
                if (vel.len() < speed) {
                    getBody().applyForceToCenter(new Vector2(speed, 0).rotateRad(vel.angleRad()), true);
                }
                //Slow down
                Vector2 modVel = new Vector2(vel).rotate(-offset.angle());
                if (Math.abs(modVel.x) >= 1.5f * speed) {
//                    System.out.println("slowing");
                    getBody().applyForceToCenter(new Vector2(-speed, 0).rotateRad(vel.angleRad()), true);
                }
            }
            if (isJumping && System.currentTimeMillis() - startJump < jumpTime) {
                long jumpingTime = System.currentTimeMillis() - startJump;
                Vector2 jump = new Vector2(0, ground.getForce(getBody().getPosition())).setAngle(offset.angle());
                body.applyForceToCenter(jump, true);
            }
        }
    }

    @Override
    public void collide(GridItem other) {
        if (other instanceof Planet) {
            isOnPlanet = true;
            ground = (Planet) other;
            groundTime = System.currentTimeMillis();
//            ground.debug(true);
        }
    }

    @Override
    public void endCollide(GridItem other) {
        if (other instanceof Planet) {
            isOnPlanet = false;
//            ground.debug(false);
//            ground = null;
        }
    }

    public void jump() {
        if (!isOnPlanet && System.currentTimeMillis() - groundTime > 100) return;
        isJumping = true;
        startJump = System.currentTimeMillis();
//        body.applyLinearImpulse(new Vector2(0,jumpPower).rotateRad(getBody().getAngle()), getBody().getPosition(), true);
        Vector2 offset = getBody().getPosition().sub(ground.getBody().getPosition());
//        Vector2 jump = new Vector2(0, jumpPower).setAngle(offset.angle());
//        body.setLinearVelocity(body.getLinearVelocity().add(jump));
        body.applyLinearImpulse(new Vector2(0, jumpPower).setAngle(offset.angle()), body.getPosition(), true);
    }

    public void endJump() {
        if (!isJumping) return;
        isJumping = false;
    }

    public float getX() {
        return getBody().getPosition().x;
    }
}
