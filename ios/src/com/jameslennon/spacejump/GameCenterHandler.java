package com.jameslennon.spacejump;

import com.jameslennon.spacejump.util.handlers.LeaderboardHandler;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.gamekit.GKAchievement;
import org.robovm.apple.gamekit.GKLeaderboard;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.bindings.gamecenter.GameCenterListener;
import org.robovm.bindings.gamecenter.GameCenterManager;

import java.util.ArrayList;

/**
 * Created by jameslennon on 5/7/15.
 */
public class GameCenterHandler extends LeaderboardHandler {

    private GameCenterManager gcManager;

    public void init(){
        gcManager = new GameCenterManager(UIApplication.getSharedApplication().getKeyWindow(), new GameCenterListener() {
            @Override
            public void playerLoginCompleted() {
                System.out.println("playerLoginCompleted");
                gcManager.loadLeaderboards();
                gcManager.loadAchievements();
            }

            @Override
            public void playerLoginFailed(NSError error) {
                System.out.println("playerLoginFailed: "+error);

            }

            @Override
            public void achievementReportCompleted() {
                System.out.println("achievementReportCompleted");
            }

            @Override
            public void achievementReportFailed(NSError error) {
                System.out.println("playerLoginFailed:"+error);
            }

            @Override
            public void achievementsLoadCompleted(ArrayList<GKAchievement> achievements) {

            }

            @Override
            public void achievementsLoadFailed(NSError error) {

            }

            @Override
            public void achievementsResetCompleted() {

            }

            @Override
            public void achievementsResetFailed(NSError error) {

            }

            @Override
            public void scoreReportCompleted() {
                System.out.println("scoreReportCompleted");
            }

            @Override
            public void scoreReportFailed(NSError error) {
                System.out.println("scoreReportFailed: "+error);
            }

            @Override
            public void leaderboardsLoadCompleted(ArrayList<GKLeaderboard> scores) {
                System.out.println("leaderboardsLoadCompleted: "+scores);

            }

            @Override
            public void leaderboardsLoadFailed(NSError error) {
                System.out.println("leaderboardsLoadFailed: "+error);

            }

            @Override
            public void leaderboardViewDismissed() {

            }

            @Override
            public void achievementViewDismissed() {

            }
        });
    }

    @Override
    public void connect() {
        super.connect();
        gcManager.login();
    }

    @Override
    public void show() {
        super.show();
        gcManager.showLeaderboardView("orbyte_highscore");
//        gcManager.showLeaderboardsView();
    }

    @Override
    public void addScore(float score) {
        super.addScore(score);
        gcManager.reportScore("orbyte_highscore",(long)(score*1000));
    }

    @Override
    public void addAchievement(String name) {
        gcManager.reportAchievement(name);
    }
}
