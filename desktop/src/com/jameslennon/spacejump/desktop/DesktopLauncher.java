package com.jameslennon.spacejump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jameslennon.spacejump.SpaceJump;
import com.jameslennon.spacejump.util.Globals;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Globals.APP_WIDTH;
        config.height = Globals.APP_HEIGHT;
		new LwjglApplication(new SpaceJump(), config);
	}
}
