package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jameslennon on 3/21/15.
 */
public class Block extends GridItem {

    static List<String> okAdj = Arrays.asList(new String[]{"01", "02", "03", "0123", "12", "02", "23"});

    public Block(float x, float y, Level lev, int i, int j) {

        String adjString = "";
        if (lev.getValue(i, j + 1) != '#') adjString += "0";
        if (lev.getValue(i + 1, j) != '#') adjString += "1";
        if (lev.getValue(i, j - 1) != '#') adjString += "2";
        if (lev.getValue(i - 1, j) != '#') adjString += "3";

        img = new Image(selectTexture(adjString));
        img.setPosition(x, y);
        Globals.scaleImage(img);
        offsetImage(adjString);

        BodyDef c = new BodyDef();
        c.type = BodyDef.BodyType.StaticBody;
        c.position.set(new Vector2((float) x, (float) y));
        // c.linearDamping = damp;
        c.linearDamping = 2f;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(img.getWidth() / 2, img.getHeight() / 2);
        FixtureDef fixture = new FixtureDef();
        fixture.density = .2f;
        fixture.friction = .5f;
        fixture.shape = shape;
        fixture.restitution = .0f;
        fixture.filter.categoryBits = 1;
        fixture.filter.maskBits = 3;
        body = Globals.world.createBody(c);
        body.createFixture(fixture).setUserData(this);
    }

    private Texture selectTexture(String s) {
        if (s.equals("023") || s.equals("012")) s = "02";

        if (s.length() == 1 || okAdj.contains(s)) {
            return ImageManager.getImage("block" + s);
        }
        return ImageManager.getImage("block");
    }

    private void offsetImage(String s) {
        if (s.equals("02") || s.equals("0123") || s.equals("023") || s.equals("012")) {
            img.moveBy(0, img.getHeight() / 2);
        }
    }

}
