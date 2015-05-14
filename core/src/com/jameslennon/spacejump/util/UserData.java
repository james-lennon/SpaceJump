package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;

public class UserData {

    public static JsonValue data;
    private static FileHandle file;

    private static JsonValue createDefaultData() {
        JsonValue val = new JsonValue(ValueType.object);
        JsonValue progress = new JsonValue(0);
        progress.setName("progress");
        val.child = progress;

        JsonValue coins = new JsonValue(0);
        coins.setName("coins");
        progress.next = coins;

        JsonValue score = new JsonValue(0.f);
        score.setName("highscore");
        coins.next = score;

        JsonValue total = new JsonValue(0.f);
        total.setName("total");
        score.next = total;

        JsonValue first = new JsonValue(true);
        first.setName("first");
        total.next = first;

        return val;
    }

    public static void load() {

        if (Gdx.app.getType() == Application.ApplicationType.WebGL) {
            data = createDefaultData();
        } else {
            JsonReader reader = new JsonReader();
            file = Gdx.files.local("user.data");
            if (!file.exists()) {
                data = createDefaultData();
                file.writeString(data.toString(), false);
            }
//		System.out.println(file.file().getAbsolutePath());
            data = reader.parse(file);
        }
    }

    public static int getProgress() {
        return data.getInt("progress");
    }

    public static void setProgress(int p) {
        data.get("progress").set(p);
    }

    public static float getHighScore() {
        if (data.has("highscore"))
            return data.getFloat("highscore");
        return 0;
    }

    public static void setHighScore(float s) {
        data.get("highscore").set(s);
        save();
    }

    public static void save() {
        if (file == null) return;
        file.writeString(data.toString(), false);
    }

    public static int getCoins() {
        return data.getInt("coins");
    }

    public static void changeCoins(int amt) {
        data.get("coins").set(getCoins() + amt);
        save();
    }

    public static void addProgress() {
        data.get("progress").set(getProgress() + 1);
        save();
    }

    public static float getTotal(){
        return data.getFloat("total");
    }

    public static void addDistance(float score){
        data.get("total").set(getTotal()+score);
        save();
    }

    public static boolean isFirstRun(){
        return data.getBoolean("first");
    }

    public static void setFirst(boolean val){
        data.get("first").set(val);
        save();
    }

}