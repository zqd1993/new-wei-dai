package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil;

import com.dfjsdndsuweq.sfdkdfmsz.BaseTaoFenQiDfREvfApp;
public class BaseTaoFenQiDfREvfSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        BaseTaoFenQiDfREvfApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return BaseTaoFenQiDfREvfApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        BaseTaoFenQiDfREvfApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        BaseTaoFenQiDfREvfApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return BaseTaoFenQiDfREvfApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return BaseTaoFenQiDfREvfApp.getPreferences().getBoolean(key, false);
    }

}
