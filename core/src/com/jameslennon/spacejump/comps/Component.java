package com.jameslennon.spacejump.comps;

import com.badlogic.gdx.utils.JsonValue;
import com.jameslennon.spacejump.grid.GridItem;
import com.jameslennon.spacejump.grid.GridMap;
import com.jameslennon.spacejump.grid.Hole;
import com.jameslennon.spacejump.grid.Planet;
import com.jameslennon.spacejump.util.Globals;

import java.util.ArrayList;

/**
 * Created by jameslennon on 4/6/15.
 */
public class Component {

    public final static float WIDTH = 500f / Globals.PIXELS_PER_METER;

    private ArrayList<GridItem> items;
    private GridMap map;
    public float offset;

    public Component(JsonValue data, GridMap gm, int off) {
        items = new ArrayList<GridItem>();
        map = gm;
        offset = off * WIDTH;
        JsonValue cur = data.child;
        while (cur != null) {
            GridItem gi = inflate(cur);
            items.add(gi);
            map.addItem(gi);

            cur = cur.next;
        }
    }

    private GridItem inflate(JsonValue value) {
        String type = value.getString("type");
        float x = offset + (float) value.getDouble("x") * WIDTH,
                y = (float) value.getDouble("y") * Globals.APP_HEIGHT / Globals.PIXELS_PER_METER;
        if (type.equals("p")) {
            return new Planet(x, y, (float) value.getDouble("r"), 0);
        } else if (type.equals("h")) {
            return new Hole(x,y);
        }
        return null;
    }

    public void remove() {
        for (GridItem gi : items){
            gi.die();
        }
        items.clear();
    }
}
