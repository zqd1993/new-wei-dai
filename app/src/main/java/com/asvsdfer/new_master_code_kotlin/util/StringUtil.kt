package com.asvsdfer.new_master_code_kotlin.util

import android.content.Context
import android.widget.Toast
import java.util.regex.Pattern

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.isMobile(): Boolean {
    return Pattern.matches("^1[3-9]\\d{9}$", this)
}