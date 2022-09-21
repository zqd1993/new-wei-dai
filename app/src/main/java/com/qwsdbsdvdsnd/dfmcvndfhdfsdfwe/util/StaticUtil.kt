package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import java.util.regex.Pattern

class StaticUtil {
    companion object {

        @JvmStatic
        fun isMobile(number: String?): Boolean {
            return if (number != null && !number.isEmpty()) {
                Pattern.matches("^1[3-9]\\d{9}$", number)
            } else false
        }

        @JvmStatic
        fun startActivity(context: Context, clazz: Class<*>?, bundle: Bundle?) {
            val intent = Intent(context, clazz)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }

        @JvmStatic
        fun getAppVersion(context: Context): String? {
            var version = ""
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                version = packageInfo.versionName
                if (TextUtils.isEmpty(version) || version.length <= 0) {
                    return ""
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return version
        }
    }
}