package com.jameslennon.spacejump.comps;

import com.jameslennon.spacejump.grid.Player;
import com.jameslennon.spacejump.grid.StartPlanet;
import com.jameslennon.spacejump.util.Globals;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by jameslennon on 4/6/15.
 */
public class ComponentManager {

    private ArrayDeque<Component> comps = new ArrayDeque<Component>();
    private int index;
    private float padding = 300;

    public void update(){
        if (Player.instance.getX() >= index*Component.WIDTH - padding){
            addComp();
        }
    }

    public void reset(){
        comps.clear();
        index = 1;

        Globals.gridMap.addItem(new StartPlanet(0, .5f*Globals.APP_HEIGHT/Globals.PIXELS_PER_METER));
        addComp();
        addComp();
        addComp();
    }

    private void addComp(){
        comps.push(Globals.componentLoader.addComponent(0,index++,Globals.gridMap));
    }

}
