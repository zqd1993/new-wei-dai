package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.SPUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StaticUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_about_mine.*
import kotlinx.android.synthetic.main.head_layout.*

class AppVersionActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_about_mine)
        if (SPUtil.getBool("NO_RECORD")) {
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
        title_tv.text = "关于我们"
        app_version_info_tv.text = "当前版本号：v" + StaticUtil.getAppVersion(this)
        back_image.setOnClickListener { finish() }
    }

}