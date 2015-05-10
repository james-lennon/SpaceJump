package com.jameslennon.spacejump;

import com.jameslennon.spacejump.util.Globals;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.glkit.GLKViewDrawableMultisample;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.jameslennon.spacejump.SpaceJump;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;

public class IOSLauncher extends IOSApplication.Delegate {

//    private GameCenterHandler gch;

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.multisample = GLKViewDrawableMultisample._4X;
        SpaceJump.leaderboard = new GameCenterHandler();
        return new IOSApplication(new SpaceJump(), config);
    }

//    @Override
//    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
//        return super.didFinishLaunching(application, launchOptions);
//    }
//
//    @Override
//    public void didBecomeActive(UIApplication application) {
//        Globals.setLeaderboard(gch);
//        super.didBecomeActive(application);
//    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}