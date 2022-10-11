package com.asvsdfer.new_master_code_kotlin.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.ui.view.UserDialog
import com.asvsdfer.new_master_code_kotlin.util.PreferencesOpenUtil
import com.asvsdfer.new_master_code_kotlin.util.StatusBarUtil
import com.asvsdfer.new_master_code_kotlin.util.showToast
import kotlinx.android.synthetic.main.activity_zhuxiao.*
import kotlinx.android.synthetic.main.top_layout.*

class ZhuXiaoActivity : AppCompatActivity() {

    private var mUserDialog: UserDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_zhuxiao)
        if (PreferencesOpenUtil.getBool("NO_RECORD") == true) {
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
        title_tv.text = "注销账号"
        back_image.setOnClickListener { finish() }
        commit_btn.setOnClickListener {
            mUserDialog = UserDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", "取消", "注销账号")
            mUserDialog?.setBtnClickListener(object : UserDialog.BtnClickListener {
                override fun leftClicked() {
                    mUserDialog?.dismiss()
                }

                override fun rightClicked() {
                    "注销已提交，请耐心等待..".showToast(this@ZhuXiaoActivity)
                    mUserDialog?.dismiss()
                    finish()
                }
            })
            mUserDialog?.show()
        }
    }
}