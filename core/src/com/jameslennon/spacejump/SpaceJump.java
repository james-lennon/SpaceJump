package com.jameslennon.spacejump;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.jameslennon.spacejump.screens.GameOverScreen;
import com.jameslennon.spacejump.screens.PlayScreen;
import com.jameslennon.spacejump.screens.TitleScreen;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.UserData;

import java.util.HashMap;

public class SpaceJump extends Game {

    private HashMap<String, Screen> screens;

	@Override
	public void create () {
        Globals.game = this;
	    screens = new HashMap<String, Screen>();

        //Load Stuff
        UserData.load();
        Globals.componentLoader.load();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("layout/Roboto-Thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        Globals.smallFont = generator.generateFont(parameter);
        parameter.size = 150;
        Globals.bigFont = generator.generateFont(parameter);
        parameter.size = 10;
        Globals.tinyFont = generator.generateFont(parameter);
        generator.dispose();

        //Handle screens
        setupScreens();
        setScreen("title");
    }

    private void setupScreens(){
        screens.put("play", new PlayScreen());
        screens.put("title", new TitleScreen());
        screens.put("gameover", new GameOverScreen());
    }

    public void setScreen(String name){
        setScreen(screens.get(name));
    }
}
