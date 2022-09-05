package com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil;

import com.geihuawefvjelkfu.qwersdbn.NcOpGeiNiHuaApp;
public class NcOpGeiNiHuaSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        NcOpGeiNiHuaApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return NcOpGeiNiHuaApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        NcOpGeiNiHuaApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        NcOpGeiNiHuaApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return NcOpGeiNiHuaApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return NcOpGeiNiHuaApp.getPreferences().getBoolean(key, false);
    }

}
