package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil;

import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgapp.MainHaojjQianShsjHajduApp;

public class HaojjQianShsjHajduMyPreferences {
    public static void saveInt(String key, int value) {
        MainHaojjQianShsjHajduApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainHaojjQianShsjHajduApp.getPreferences().getInt(key, 0);
    }

    public static void saveString(String key, String value) {
        MainHaojjQianShsjHajduApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainHaojjQianShsjHajduApp.getPreferences().getString(key, "");
    }

    public static void saveBool(String key, boolean value) {
        MainHaojjQianShsjHajduApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBool(String key) {
        return MainHaojjQianShsjHajduApp.getPreferences().getBoolean(key, false);
    }
}
