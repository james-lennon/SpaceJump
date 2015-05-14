package com.jameslennon.spacejump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jameslennon.spacejump.SpaceJump;
import com.jameslennon.spacejump.util.Globals;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Globals.DEFAULT_WIDTH;
        config.height = Globals.DEFAULT_HEIGHT;
		config.samples = 2;
		new LwjglApplication(new SpaceJump(), config);
	}
}
