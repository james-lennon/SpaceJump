package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;

/**
 * Created by jameslennon on 4/7/15.
 */
public class Hole extends Attractor {

    private float imgRadius = 20 / Globals.PIXELS_PER_METER, holeRadius = 10 / Globals.PIXELS_PER_METER;

    public Hole(float x, float y) {
        super(15, 200);

        img = new Image(ImageManager.getImage("spiral"));
        img.setSize(2 * imgRadius, 2 * imgRadius);
        img.setColor(Attractor.col);
        img.setOrigin(img.getWidth() / 2, img.getHeight() / 2);
        img.setPosition(x - imgRadius, y - imgRadius);

        BodyDef c = new BodyDef();
        c.type = BodyDef.BodyType.StaticBody;
        c.position.set(new Vector2(x, y));
        CircleShape shape = new CircleShape();
        FixtureDef fixture = new FixtureDef();
        shape.setRadius(holeRadius);
        fixture.density = .2f;
        fixture.friction = 0f;
        fixture.shape = shape;
        fixture.restitution = .0f;
        fixture.filter.categoryBits = 1;
        fixture.filter.maskBits = 3;
        body = Globals.world.createBody(c);
        body.createFixture(fixture).setUserData(this);
    }

    @Override
    public void collide(GridItem other) {
        if (other instanceof Player) {
            Player p = (Player) other;
            if (p.isBoosting()) {
                p.getBody().setTransform(new Vector2(p.getBody().getPosition()).add(0, 4), p.getBody().getAngle());
            } else {
                other.die();
            }
        }
    }
}
