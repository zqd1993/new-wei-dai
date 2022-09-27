package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil;

import com.dfgjtruymsdf.ytjermgfjjgut.JzjqianHdhrtYhdApp;
public class JzjqianHdhrtYhdSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        JzjqianHdhrtYhdApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return JzjqianHdhrtYhdApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        JzjqianHdhrtYhdApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        JzjqianHdhrtYhdApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return JzjqianHdhrtYhdApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return JzjqianHdhrtYhdApp.getPreferences().getBoolean(key, false);
    }

}
