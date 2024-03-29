package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jameslennon.spacejump.comps.Component;
import com.jameslennon.spacejump.util.Globals;

import java.util.ArrayList;

/**
 * Created by jameslennon on 3/21/15.
 */
public class GridMap {

    public Vector2 spawnPoint;
    private ArrayList<GridItem> items, itemsToRemove;
    private ArrayList<Body> bodiesToRemove;
    private ArrayList<Explosion> explosions;

    public Group group;

    public GridMap() {
        items = new ArrayList<GridItem>();
        itemsToRemove = new ArrayList<GridItem>();
        bodiesToRemove = new ArrayList<Body>();
        explosions = new ArrayList<Explosion>();
        group = new Group();
    }

    public void load(Level level) {
        for (int i = -1; i <= level.getWidth(); i++) {
            for (int j = -1; j <= level.getHeight(); j++) {
                float w = Globals.TILE_WIDTH;
                float x = (i * w) / Globals.PIXELS_PER_METER, y = (j * w) / Globals.PIXELS_PER_METER;
                addItem(new Tile(x, y));

                GridItem gi = null;
                switch (level.getValue(i, j)) {
                    case '.':
                        break;
                    case '#':
                        gi = new Block(x, y, level, i, j);
                        break;
                    case 's':
                        spawnPoint = new Vector2(x, y);
                        break;

                    default:
                        System.err.println("unrecognized GridItem");
                        break;
                }

                if (gi != null) addItem(gi);
            }
        }

    }

    public void update() {
        for (Explosion e : explosions) {
            doExplosion(e);
        }
        for (int i = 0; i < itemsToRemove.size(); i++) {
            items.remove(itemsToRemove.get(i));
        }
        itemsToRemove.clear();
        for (int i = 0; i < bodiesToRemove.size(); i++) {
            Globals.world.destroyBody(bodiesToRemove.get(i));
        }
        bodiesToRemove.clear();
        for (int i = 0; i < items.size(); i++) {
//            if (!items.get(i).isRemoved())
//                items.get(i).update();

            float offset = items.get(i).getBody().getPosition().x
                    - Player.instance.getX();
            int distance = 2;
            if ((offset >= -distance * Component.WIDTH && offset <= distance
                    * Component.WIDTH)
                    || items.get(i) instanceof Player) {
                if (!items.get(i).isRemoved()) {
                    items.get(i).setVisible(true);
                    items.get(i).update();
                }
            } else {
                items.get(i).setVisible(false);
            }

        }

    }

    public void addItem(GridItem gi) {
        gi.show(group, Globals.stage);
        items.add(gi);
    }

    public void show(Stage s) {
//        for (GridItem gi : items) {
//            gi.show(s);
//        }
        s.addActor(group);
    }

    public void remove() {

    }

    public void removeItem(GridItem g) {
        itemsToRemove.add(g);
        removeBody(g.body);
//        g.body = null;
        g.img.remove();
        g.setRemoved(true);
    }

    private void removeBody(Body body) {
        if (body != null)
            bodiesToRemove.add(body);
    }

    public ArrayList<GridItem> getItems() {
        return items;
    }

    private void doExplosion(Explosion e) {
        float maxdist = e.rad * Globals.TILE_WIDTH, maxforce = e.force;
        // Array<Body> bodies = new Array<Body>();
        // Globals.getWorld().getBodies(bodies);
        float maxdamage = 20;
        for (GridItem gi : items) {
            Body b = gi.getBody();
            if (b == null)
                continue;
            float dx = b.getPosition().x - e.point.x, dy = b.getPosition().y
                    - e.point.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist >= maxdist)
                continue;
            float force = (maxdist - dist) / maxdist;
            if (e.suck) {
                force = -force;
            }
            float damage = force * maxdamage;
            // if (e.damage) {
            // gi.changeHealth((int) -damage, e.id);
            // }
            force *= maxforce;
            float ang;
            // ang = MathUtils.atan2(b.getPosition().y - e.point.y,
            // b.getPosition().x - e.point.x);
            ang = MathUtils.PI * 1.5f;

            // if (e.suck)
            // ang += MathUtils.PI;
            if (e.suck)
                b.applyForceToCenter(new Vector2(MathUtils.cos(ang) * force,
                        MathUtils.sin(ang) * force), true);
            else {
                b.applyLinearImpulse(new Vector2(MathUtils.cos(ang) * force,
                        MathUtils.sin(ang) * force), b.getPosition(), true);
            }
        }
    }

    private class Explosion {
        public Vector2 point;
        public float force;
        public int rad;
        public boolean suck, damage;

        public Explosion(float x, float y, float f, int r, boolean s, boolean d) {
            point = new Vector2(x, y);
            force = f;
            rad = r;
            suck = s;
            damage = d;
        }
    }


}
