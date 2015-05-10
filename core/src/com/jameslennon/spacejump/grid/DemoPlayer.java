package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;
import com.jameslennon.spacejump.util.ParticleEffectActor;

/**
 * Created by jameslennon on 4/2/15.
 */
public class DemoPlayer extends GridItem {

    public static Color col = Color.BLACK;

    private final float width = 30, padding = 0.f / Globals.PIXELS_PER_METER;
    private float speed = 10, jumpPower = 5; //10;
    private boolean isOnPlanet, isJumping, didBoost;
    private Planet ground;
    private long startJump, jumpTime = 500, groundTime, orbitTime, boostTime;
    private ParticleEffectActor pea1, pea2;
    private boolean isInOrbit;
    private float orbitAngle;
    private Attractor pivot;

    public DemoPlayer(float x, float y) {

        img = new Image(ImageManager.getImage("block"));
        img.setSize(width, width);
        img.setOrigin(width / 2, width / 2);
        img.setPosition(x, y);
        img.setColor(col);

        ParticleEffect effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particle/demo_trail.particle"), Gdx.files.internal("img"));
//        effect.getEmitters().get(0).getScale().setHigh(4 / Globals.PIXELS_PER_METER);
        pea1 = new ParticleEffectActor(effect, 0, 0);
        pea2 = new ParticleEffectActor(effect, 0, 0);

        img.addAction(Actions.repeat(Integer.MAX_VALUE, Actions.rotateBy(360, 2.f)));

//        BodyDef c = new BodyDef();
//        c.type = BodyDef.BodyType.DynamicBody;
//        c.position.set(new Vector2((float) x, (float) y));
//        // c.linearDamping = damp;
//        c.linearDamping = .0f;
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(width / Globals.PIXELS_PER_METER / 2, width / Globals.PIXELS_PER_METER / 2);
//        FixtureDef fixture = new FixtureDef();
//        fixture.density = .2f;
//        fixture.friction = 0f;
//        fixture.shape = shape;
//        fixture.restitution = .0f;
//        fixture.filter.categoryBits = 1;
//        fixture.filter.maskBits = 3;
//        body = Globals.world.createBody(c);
//        body.createFixture(fixture).setUserData(this);
//        getBody().setLinearVelocity(new Vector2(speed, 0).rotateRad(getBody().getAngle()));
    }

    @Override
    public void show(Group g, Stage s) {
        super.show(g, s);
//        g.addActor(pea1);
//        g.addActor(pea2);
    }

    @Override
    public void update() {
        super.update();

        Vector2 pOff = new Vector2(0, img.getWidth() / 2f).rotate(img.getRotation() + 45);
        pea1.setPosition(img.getX() + img.getWidth() / 2 + pOff.x, img.getY() + img.getWidth() / 2 + pOff.y);
        pOff.rotate(180);
        pea2.setPosition(img.getX() + img.getWidth() / 2 + pOff.x, img.getY() + img.getWidth() / 2 + pOff.y);
    }

    @Override
    public void collide(GridItem other) {
        if (other instanceof Planet) {
            if (didBoost) {
                body.setLinearVelocity(new Vector2(0, 0));
                didBoost = false;
            }
            isOnPlanet = true;
            ground = (Planet) other;
//            System.out.println("reset "+MathUtils.random());
            groundTime = System.currentTimeMillis();
            orbitAngle = new Vector2(body.getPosition()).sub(ground.getBody().getPosition()).angle();
            orbitTime = System.currentTimeMillis();
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
        if (!isOnPlanet && System.currentTimeMillis() - groundTime > 500) return;
        isJumping = true;
        startJump = System.currentTimeMillis();
        groundTime = 0;
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
        if (getBody() == null) return 0;
        return getBody().getPosition().x;
    }

    public void enterOrbit(Attractor att) {
        if (pivot == att) return;
//        System.out.println("entering");
        isInOrbit = true;
        pivot = att;

        Vector2 offset = new Vector2(body.getPosition()).sub(pivot.getBody().getPosition());
        orbitAngle = offset.angle();
        orbitTime = System.currentTimeMillis();
    }

    public void exitOrbit(Attractor att) {
        if (att == pivot) {
//            System.out.println("leaving");
            isInOrbit = false;
            pivot = null;
        }
    }

    private void boost(float power) {
        body.setLinearVelocity(new Vector2(0, 0));
        body.applyLinearImpulse(new Vector2(power, 0), body.getPosition(), true);

        Image circle = new Image(ImageManager.getImage("outline2"));
        circle.setSize(width / Globals.PIXELS_PER_METER, width / Globals.PIXELS_PER_METER);
        circle.setColor(col);
        circle.setOrigin(circle.getWidth() / 2, circle.getHeight() / 2);
        circle.setPosition(body.getPosition().x - circle.getWidth() / 2, body.getPosition().y - circle.getHeight() / 2);
        circle.addAction(Actions.fadeOut(.5f));
        circle.addAction(Actions.scaleBy(6.0f, 6.0f, .5f));
        Globals.stage.addActor(circle);

        boostTime = System.currentTimeMillis();
        didBoost = true;
    }

    public boolean isBoosting() {
//        return System.currentTimeMillis() - boostTime < 100;
        return didBoost && System.currentTimeMillis() - boostTime < 2000;
    }

    @Override
    public void die() {
        super.die();
        pea1.getEffect().getEmitters().first().getEmission().setHigh(0);
        pea2.getEffect().getEmitters().first().getEmission().setHigh(0);

        ParticleEffect pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particle/die.particle"), Gdx.files.internal("img"));
        ParticleEmitter e = pe.getEmitters().first();
        e.getScale().setHigh(e.getScale().getHighMax() / Globals.PIXELS_PER_METER);
        e.getVelocity().setHigh(e.getVelocity().getHighMax() / Globals.PIXELS_PER_METER);
        ParticleEffectActor pea = new ParticleEffectActor(pe, body.getPosition().x, body.getPosition().y);
        Globals.stage.addActor(pea);
    }

    public boolean didBoost() {
        return didBoost;
    }
}
