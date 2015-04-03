package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by jameslennon on 3/21/15.
 */
public class SoundManager {

    private static Music gameMusic, battleMusic;

    public static void load() {
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/GameMusic.mp3"));
        gameMusic.setLooping(true);
        battleMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/BattleMusic.ogg"));
        battleMusic.setLooping(true);
    }

    public static void playGameMusic() {
        gameMusic.play();
    }

}
