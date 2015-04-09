package com.jameslennon.spacejump.comps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.jameslennon.spacejump.grid.GridItem;
import com.jameslennon.spacejump.grid.GridMap;

import java.util.ArrayList;

/**
 * Created by jameslennon on 4/6/15.
 */
public class ComponentLoader {

    private ArrayList<ArrayList<JsonValue>> data;

    public void load(){
        data = new ArrayList<ArrayList<JsonValue>>();

        FileHandle file = Gdx.files.internal("data/comps.data");
        JsonValue val = new JsonReader().parse(file);


        JsonValue cur = val.child;
        while (cur!=null){
            data.add(addDifficulty(cur));
            cur = cur.next;
        }
    }

    private ArrayList<JsonValue> addDifficulty(JsonValue val){
        ArrayList<JsonValue> lis = new ArrayList<JsonValue>();
        JsonValue cur = val.child;
        while (cur!=null){
            lis.add(cur);
            cur = cur.next;
        }
        return lis;
    }

    public Component addComponent(int difficulty, int off, GridMap map){
        ArrayList<JsonValue> lis = data.get(difficulty);
        return new Component(lis.get(MathUtils.random(lis.size()-1)), map, off);
    }

    public int difficulties(){
        return data.size();
    }

}
