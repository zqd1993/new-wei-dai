package com.asvsdfer.new_master_code_kotlin.util

import com.asvsdfer.new_master_code_kotlin.MyApp

object PreferencesOpenUtil {

    fun saveInt(key: String?, value: Int) {
        MyApp.preferences?.let { it.edit().putInt(key, value).commit() }
    }

    fun getInt(key: String?): Int? {
        return MyApp.preferences?.getInt(key, 0)
    }

    fun saveBool(key: String?, value: Boolean) {
        MyApp.preferences?.let { it.edit().putBoolean(key, value).commit() }
    }

    fun saveString(key: String?, value: String?) {
        MyApp.preferences?.let { it.edit().putString(key, value).commit() }
    }

    fun getString(key: String?): String? {
        return MyApp.preferences?.getString(key, "")
    }

    fun getBool(key: String?): Boolean? {
        return MyApp.preferences?.getBoolean(key, false)
    }
}