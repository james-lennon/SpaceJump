package com.jameslennon.spacejump.comps;

import com.badlogic.gdx.math.MathUtils;
import com.jameslennon.spacejump.grid.Player;
import com.jameslennon.spacejump.grid.StartPlanet;
import com.jameslennon.spacejump.util.Globals;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class ComponentManager {

    private ArrayDeque<Component> comps = new ArrayDeque<Component>();
    private int index;
    private float padding = 900/Globals.PIXELS_PER_METER;

    public void update(){
        if (Player.instance.isRemoved())return;
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
        int d = getDifficulty();
//        System.out.println(d);
        comps.add(Globals.componentLoader.addComponent(d, index++, Globals.gridMap));
    }

    private int getDifficulty(){
        int d = Globals.componentLoader.difficulties();
        int maxWeight = (int) Math.pow(3, d);
        int sum = 0;
        int weight = maxWeight;
        for (int i=0; i<d; i++){
            sum += weight;
            weight /= 2;
        }
        int rnd = MathUtils.random(sum-1);
        weight = maxWeight;
        for (int i=0; i<d; i++){
            if (rnd < weight)
                return i;
            rnd -= weight;
            weight /= 2;
        }
        System.err.println("Random difficulty error");
        return 0;
    }

    public void onBoost() {
        float x = Player.instance.getX();
        Component comp;
        while (true){
            comp = comps.peekLast();
            if (comp !=null && comp.offset>=x && comp.offset < x+padding){
//                comp.remove();
                comps.removeLast();
            }else {
                break;
            }
        }
    }
}
