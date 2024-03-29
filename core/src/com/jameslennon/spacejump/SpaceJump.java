package com.jameslennon.spacejump;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.jameslennon.spacejump.screens.*;
import com.jameslennon.spacejump.util.handlers.AchievementManager;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.UserData;
import com.jameslennon.spacejump.util.handlers.AdHandler;
import com.jameslennon.spacejump.util.handlers.LeaderboardHandler;

import java.util.HashMap;

public class SpaceJump extends Game {

    private HashMap<String, Screen> screens;

    public static AchievementManager achievements = new AchievementManager();
    public static LeaderboardHandler leaderboard = new LeaderboardHandler();
    public static AdHandler ads = new AdHandler();

    @Override
    public void create() {
        Globals.game = this;
        if (Gdx.app.getType() == Application.ApplicationType.iOS) {
            Globals.APP_WIDTH = Gdx.graphics.getWidth();
            Globals.APP_HEIGHT = Gdx.graphics.getHeight();
        }

        screens = new HashMap<String, Screen>();

        //Load Stuff
        UserData.load();
        Globals.componentLoader.load();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("layout/Roboto-Thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        Globals.smallFont = generator.generateFont(parameter);
        parameter.size = 170;
        Globals.bigFont = generator.generateFont(parameter);
        parameter.size = 210;
        Globals.hugeFont = generator.generateFont(parameter);
        parameter.size = 60;
        Globals.tinyFont = generator.generateFont(parameter);
        generator.dispose();

        leaderboard.init();
        ads.init();
        achievements.init();

        //Handle screens
        setupScreens();
        setScreen("title");
//        setScreen("tutorial");
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void setupScreens() {
        screens.put("play", new PlayScreen());
        screens.put("title", new TitleScreen());
        screens.put("gameover", new GameOverScreen());
        screens.put("tutorial", new TutorialScreen());
        screens.put("info", new InfoScreen());
    }

    public void setScreen(String name) {
        setScreen(screens.get(name));
    }
}
