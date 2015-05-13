package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by jameslennon on 5/12/15.
 */
public class MusicManager {

    private Music menu, play;

    public MusicManager(){
        play = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));
        menu = Gdx.audio.newMusic(Gdx.files.internal("audio/menu.wav"));
    }

    public void playMenu(){
        play.stop();
        menu.setLooping(true);
        menu.play();
    }

    public void playGame(){
        menu.stop();
        play.setLooping(true);
        play.play();
    }

}
