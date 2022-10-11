package com.asvsdfer.new_master_code_kotlin.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.logic.network.ServiceCreator
import com.asvsdfer.new_master_code_kotlin.ui.view.WelcomePageDialog
import com.asvsdfer.new_master_code_kotlin.util.PreferencesOpenUtil
import com.asvsdfer.new_master_code_kotlin.util.StatusBarUtil
import com.asvsdfer.new_master_code_kotlin.util.jumpActivity

class WelcomeActivity : AppCompatActivity(){

    private var started: Boolean = false

    private  var isResume = false

    private var phone = ""

    private var welcomePageDialog: WelcomePageDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_welcome)
        started = PreferencesOpenUtil.getBool("started")!!
        phone = PreferencesOpenUtil.getString("phone").toString()
        Handler().postDelayed({ startPage() }, 500)
    }

    override fun onResume() {
        isResume = true
        super.onResume()
        Handler().postDelayed({ isResume = false }, 500)
    }

    private fun showDialog() {
        welcomePageDialog = WelcomePageDialog(this)
        welcomePageDialog?.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                finish()
                return@OnKeyListener false
            }
            true
        })
        welcomePageDialog?.setOnListener(object : WelcomePageDialog.OnListener {
            override fun topBtnClicked() {
                PreferencesOpenUtil.saveBool("started", true)
                welcomePageDialog?.dismiss()
                jumpActivity<LoginActivity>(this@WelcomeActivity)
                finish()
            }

            override fun bottomBtnClicked() {
                finish()
            }

            override fun clickedZcxy() {
                jumpActivity<WebViewActivity>(this@WelcomeActivity){
                    putExtra("tag", 1)
                    putExtra("url", ServiceCreator.ZCXY)
                }
            }

            override fun clickedYsxy() {
                jumpActivity<WebViewActivity>(this@WelcomeActivity){
                    putExtra("tag", 2)
                    putExtra("url", ServiceCreator.YSXY)
                }
            }
        })
        welcomePageDialog?.show()
    }

    private fun startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                jumpActivity<MainActivity>(this@WelcomeActivity)
            } else {
                jumpActivity<LoginActivity>(this@WelcomeActivity)
            }
            finish()
        } else {
            showDialog()
        }
    }

}