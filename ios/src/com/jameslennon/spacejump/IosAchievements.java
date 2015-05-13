package com.jameslennon.spacejump;

import com.jameslennon.spacejump.util.UserData;
import com.jameslennon.spacejump.util.handlers.AchievementManager;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jameslennon on 5/10/15.
 */
public class IosAchievements extends AchievementManager {


    private TreeMap<Float, String> aMap, tMap;

    @Override
    public void init() {
        aMap = new TreeMap<Float, String>();
        tMap = new TreeMap<Float, String>();
        aMap.put(5f, "pass_five");
        aMap.put(10f, "pass_ten");
        aMap.put(20f, "pass_twenty");
        aMap.put(30f, "pass_thirty");
        aMap.put(40f, "pass_forty");
        aMap.put(60f, "pass_60");
        aMap.put(80f, "pass_eighty");
        aMap.put(99f, "pass_99");

        tMap.put(101f, "lifetime_101");
        tMap.put(213f, "lifetime_213");
        tMap.put(503f, "lifetime_503");
        tMap.put(800f, "lifetime_800");
    }

    @Override
    public void handleScore(float score) {
        Map.Entry<Float, String> e = aMap.floorEntry(score);
        if (e != null)
            SpaceJump.leaderboard.addAchievement(e.getValue());

        e = tMap.floorEntry(UserData.getTotal());
        if (e != null)
            SpaceJump.leaderboard.addAchievement(e.getValue());
    }

    @Override
    public void handleEvent(String name) {
        if (name.equals("BackWall")){
            System.out.println("back wall achievement");
            SpaceJump.leaderboard.addAchievement("back_wall");
        }else if (name.equals("EasterEgg")){
            SpaceJump.leaderboard.addAchievement("easter_egg");
        }
    }
}
