package com.jameslennon.spacejump;

import com.jameslennon.spacejump.util.handlers.AchievementManager;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jameslennon on 5/10/15.
 */
public class IosAchievements extends AchievementManager {


    private TreeMap<Float, String> aMap;

    @Override
    public void init() {
        aMap = new TreeMap<Float, String>();
        aMap.put(5f, "pass_five");
        aMap.put(10f, "pass_ten");
        aMap.put(20f, "pass_twenty");
        aMap.put(30f, "pass_thirty");
        aMap.put(40f, "pass_forty");
        aMap.put(60f, "pass_60");
        aMap.put(5f, "pass_five");
    }

    @Override
    public void handleScore(float score) {
        Map.Entry<Float,String> e = aMap.ceilingEntry(score);
        SpaceJump.leaderboard.addAchievement(e.getValue());
    }
}
