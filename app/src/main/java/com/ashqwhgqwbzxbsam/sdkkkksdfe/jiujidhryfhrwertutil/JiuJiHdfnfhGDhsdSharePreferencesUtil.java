package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.JiuJiHdfnfhGDhsdApp;
public class JiuJiHdfnfhGDhsdSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        JiuJiHdfnfhGDhsdApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return JiuJiHdfnfhGDhsdApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        JiuJiHdfnfhGDhsdApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        JiuJiHdfnfhGDhsdApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return JiuJiHdfnfhGDhsdApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return JiuJiHdfnfhGDhsdApp.getPreferences().getBoolean(key, false);
    }

}
