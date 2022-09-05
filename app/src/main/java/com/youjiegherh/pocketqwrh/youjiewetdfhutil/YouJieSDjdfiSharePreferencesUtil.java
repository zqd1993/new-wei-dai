package com.youjiegherh.pocketqwrh.youjiewetdfhutil;

import com.youjiegherh.pocketqwrh.YouJieSDjdfiApp;
public class YouJieSDjdfiSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        YouJieSDjdfiApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return YouJieSDjdfiApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        YouJieSDjdfiApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        YouJieSDjdfiApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return YouJieSDjdfiApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return YouJieSDjdfiApp.getPreferences().getBoolean(key, false);
    }

}
