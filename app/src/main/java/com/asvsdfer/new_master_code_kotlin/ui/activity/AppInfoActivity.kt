package com.asvsdfer.new_master_code_kotlin.ui.activity

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.util.PreferencesOpenUtil
import com.asvsdfer.new_master_code_kotlin.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_app_info.*
import kotlinx.android.synthetic.main.top_layout.*

class AppInfoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_app_info)
        if (PreferencesOpenUtil.getBool("NO_RECORD") == true) {
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
        title_tv.text = "关于我们"
        app_version_info_tv.text = "当前版本号：v" + getAppVersion(this)
        back_image.setOnClickListener { finish() }
    }

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