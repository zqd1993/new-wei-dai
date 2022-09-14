package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil;

import com.sjshsbssdfwert.rufgnerytgut.DaGeJtiaoBaDdhjFferApp;
public class DaGeJtiaoBaDdhjFferSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        DaGeJtiaoBaDdhjFferApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return DaGeJtiaoBaDdhjFferApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        DaGeJtiaoBaDdhjFferApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        DaGeJtiaoBaDdhjFferApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return DaGeJtiaoBaDdhjFferApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return DaGeJtiaoBaDdhjFferApp.getPreferences().getBoolean(key, false);
    }

}
