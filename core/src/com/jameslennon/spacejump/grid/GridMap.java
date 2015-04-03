package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.grid.Block;
import com.jameslennon.spacejump.grid.GridItem;
import com.jameslennon.spacejump.grid.Level;

import java.util.ArrayList;

/**
 * Created by jameslennon on 3/21/15.
 */
public class GridMap {

    private ArrayList<GridItem> items, itemsToRemove;
    private ArrayList<Body> bodiesToRemove;
    private ArrayList<Explosion> explosions;
    public Vector2 spawnPoint;

    public GridMap() {
        items = new ArrayList<GridItem>();
        itemsToRemove = new ArrayList<GridItem>();
        bodiesToRemove = new ArrayList<Body>();
        explosions = new ArrayList<Explosion>();
    }

    public void load(Level level) {
        for (int i = -1; i <= level.getWidth(); i++) {
            for (int j = -1; j <= level.getHeight(); j++) {
                float w = Globals.TILE_WIDTH;
                float x = (i * w)/Globals.PIXELS_PER_METER, y = (j * w)/Globals.PIXELS_PER_METER;
                addItem(new Tile(x, y));

                GridItem gi = null;
                switch (level.getValue(i, j)) {
                    case '.':
                        break;
                    case '#':
                        gi = new Block(x, y, level, i, j);
                        break;
                    case 's':
                        spawnPoint = new Vector2(x,y);
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
            items.get(i).update();

            //TODO: remove items off screen
            /*
            float offset = items.get(i).body.getPosition().y
                    - GameManager.getY();
            int distance = 6;
            if ((offset >= -distance * Tile.WIDTH && offset <= distance
                    * Tile.WIDTH)
                    || items.get(i) instanceof Ball) {
                items.get(i).setVisible(true);
                items.get(i).update();
            } else {
                items.get(i).setVisible(false);
            }
            */
        }

    }

    public void addItem(GridItem gi) {
        gi.show(Globals.stage);
        items.add(gi);
    }

    public void removeItem(GridItem g) {
        itemsToRemove.add(g);
        bodiesToRemove.add(g.body);
        g.img.remove();
        g.setRemoved(true);
    }

    public void removeBody(Body body) {
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
