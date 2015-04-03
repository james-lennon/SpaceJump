package com.jameslennon.spacejump.util;

import com.badlogic.gdx.physics.box2d.*;
import com.jameslennon.spacejump.grid.GridItem;

/**
 * Created by jameslennon on 3/21/15.
 */
public class CollisionManager implements ContactListener {

    public CollisionManager() {

    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA(), b = contact.getFixtureB();

        if (a.getUserData() instanceof GridItem
                && b.getUserData() instanceof GridItem) {
            GridItem ga = (GridItem) a.getUserData(), gb = (GridItem) b
                    .getUserData();
            // if(!ga.isDead() && !gb.isDead()){
            ga.collide(gb);
            gb.collide(ga);
            // }
            // .collide((GridItem) b.getUserData());
            // ((GridItem) b.getUserData()).collide((GridItem) a.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA(), b = contact.getFixtureB();

        if (a.getUserData() instanceof GridItem
                && b.getUserData() instanceof GridItem) {
            GridItem ga = (GridItem) a.getUserData(), gb = (GridItem) b
                    .getUserData();
            ga.endCollide(gb);
            gb.endCollide(ga);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

}
