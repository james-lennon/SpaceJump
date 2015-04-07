package com.jameslennon.spacejump;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.jameslennon.spacejump.screens.TestScreen;
import com.jameslennon.spacejump.screens.TitleScreen;
import com.jameslennon.spacejump.util.Globals;
import com.jameslennon.spacejump.util.ImageManager;
import com.jameslennon.spacejump.util.UserData;

import java.util.HashMap;

public class SpaceJump extends Game {

    private HashMap<String, Screen> screens;

	@Override
	public void create () {
        Globals.game = this;
	    screens = new HashMap<String, Screen>();

        //Load Stuff
        setupScreens();
        UserData.load();
        Globals.componentLoader.load();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("layout/Roboto-Thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font25 = generator.generateFont(parameter);
        Globals.smallFont = font25;
        parameter.size = 150;
        Globals.bigFont = generator.generateFont(parameter);
        generator.dispose();


        setScreen("title");
    }

    private void setupScreens(){
        screens.put("play", new TestScreen());
        screens.put("title", new TitleScreen());
    }

    public void setScreen(String name){
        setScreen(screens.get(name));
    }
}
