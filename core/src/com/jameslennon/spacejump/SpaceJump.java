package com.jameslennon.spacejump;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jameslennon.spacejump.screens.TestScreen;
import com.jameslennon.spacejump.util.ImageManager;
import com.jameslennon.spacejump.util.UserData;

import java.util.HashMap;

public class SpaceJump extends Game {

    private HashMap<String, Screen> screens;

	@Override
	public void create () {
	    screens = new HashMap<String, Screen>();

        System.out.println(Gdx.files.internal(".").file().getAbsolutePath());
        ;

        //Load Stuff
        setupScreens();
        UserData.load();


        setScreen("test");
    }

    private void setupScreens(){
        screens.put("test", new TestScreen());
    }

    private void setScreen(String name){
        setScreen(screens.get(name));
    }
}