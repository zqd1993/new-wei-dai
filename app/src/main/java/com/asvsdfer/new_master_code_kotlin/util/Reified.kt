package com.asvsdfer.new_master_code_kotlin.util

import android.content.Context
import android.content.Intent

inline fun <reified T> jumpActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> jumpActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

