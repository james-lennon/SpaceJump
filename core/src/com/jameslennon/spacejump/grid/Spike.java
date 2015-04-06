package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;

/**
 * Created by jameslennon on 4/5/15.
 */
public class Spike extends GridItem {

    private float width = 20;

    public Spike(Planet p, float degrees) {
        img = new Image(ImageManager.getImage("spike"));
        float scale = width / img.getWidth();
        img.setSize(img.getWidth() / Globals.PIXELS_PER_METER*scale, img.getHeight() / Globals.PIXELS_PER_METER*scale);
        img.setOrigin(img.getWidth()/2, img.getHeight()/2);

        Vector2 pos = p.getBody().getPosition().add(new Vector2(0, p.getPlanetRadius()+img.getHeight()/2).rotate(degrees));

        BodyDef c = new BodyDef();
        c.type = BodyDef.BodyType.StaticBody;
        c.position.set(pos);
        c.linearDamping = 2f;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(img.getWidth() / 2, img.getHeight() / 2);
        FixtureDef fixture = new FixtureDef();
        fixture.density = .2f;
        fixture.friction = 0f;
        fixture.shape = shape;
        fixture.restitution = .0f;
        fixture.filter.categoryBits = 1;
        fixture.filter.maskBits = 3;
        body = Globals.world.createBody(c);
        body.createFixture(fixture).setUserData(this);
        body.setTransform(pos, degrees * MathUtils.PI / 180);
    }

    @Override
    public void update() {
        super.update();

        img.setPosition(getBody().getPosition().x - img.getWidth()/2, getBody().getPosition().y-img.getHeight()/2);
        img.setRotation(getBody().getAngle() * 180 / MathUtils.PI);
    }

    @Override
    public void collide(GridItem other) {
        if (other instanceof Player){
//            other.die();
        }
    }
}
