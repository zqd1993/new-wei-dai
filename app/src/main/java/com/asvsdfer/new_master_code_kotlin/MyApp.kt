package com.asvsdfer.new_master_code_kotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MyApp: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        @SuppressLint("StaticFieldLeak")
        lateinit var sInstance: MyApp
        @SuppressLint("StaticFieldLeak")
        lateinit var preferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        sInstance = this
        preferences = getSharedPreferences("data", Context.MODE_PRIVATE)
    }
}