package com.example.leo.diarynew.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by leo on 2017/9/25.
 */

public class SharedPreferenceUtils {
    public static final String SHARED_PREFERENCE_NAME = "diray";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharedPreferenceUtils(Context context){
        sp = context.getSharedPreferences(SHARED_PREFERENCE_NAME , Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
    }

    public void putString(String key,String value){
        editor.putString(key,value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public long getLong(String key) {
        return getLong(key, -1L);
    }

    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public void remove(String key) {
        editor.remove(key).apply();
    }

    public boolean contains(String key) {
        return sp.contains(key);
    }

    public void clear() {
        editor.clear().apply();
    }
}
