package com.dgjadsie.jkermsd.youbeihwahsndutil;

import com.dgjadsie.jkermsd.youbeihwahsndapp.YouBeiHwHsajJsumMainApp;

public class MyYouBeiHwHsajJsumPreferences {
    public static void saveInt(String key, int value) {
        YouBeiHwHsajJsumMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return YouBeiHwHsajJsumMainApp.getPreferences().getInt(key, 0);
    }

    public static void saveString(String key, String value) {
        YouBeiHwHsajJsumMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return YouBeiHwHsajJsumMainApp.getPreferences().getString(key, "");
    }

    public static void saveBool(String key, boolean value) {
        YouBeiHwHsajJsumMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBool(String key) {
        return YouBeiHwHsajJsumMainApp.getPreferences().getBoolean(key, false);
    }
}
