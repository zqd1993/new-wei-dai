package com.asvsdfer.new_master_code.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.Toast
import com.asvsdfer.new_master_code.R
import com.asvsdfer.new_master_code.util.SPUtil
import com.asvsdfer.new_master_code.util.StaticUtil
import com.asvsdfer.new_master_code.util.StatusBarUtil
import com.asvsdfer.new_master_code.widget.QiDongPageDialog
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

class QiDongPageActivity : RxAppCompatActivity(){

    private var bundle: Bundle? = null

    private var started = false

    private  var isResume = false

    private var phone = ""

    private var qiDongPageDialog: QiDongPageDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_qi_dong_page)
        started = SPUtil.getBool("started")
        phone = SPUtil.getString("phone")
        Handler().postDelayed({ startPage() }, 500)
    }

    override fun onResume() {
        isResume = true
        super.onResume()
        Handler().postDelayed({ isResume = false }, 500)
    }

    private fun showDialog() {
        qiDongPageDialog = QiDongPageDialog(this)
        qiDongPageDialog?.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                finish()
                return@OnKeyListener false
            }
            true
        })
        qiDongPageDialog?.setOnListener(object : QiDongPageDialog.OnListener {
            override fun topBtnClicked() {
                SPUtil.saveBool("started", true)
                qiDongPageDialog?.dismiss()
                finish()
            }

            override fun bottomBtnClicked() {
                finish()
            }

            override fun clickedZcxy() {
                Toast.makeText(this@QiDongPageActivity, "点击第一个", Toast.LENGTH_SHORT).show()
            }

            override fun clickedYsxy() {
                Toast.makeText(this@QiDongPageActivity, "点击第二个", Toast.LENGTH_SHORT).show()
            }
        })
        qiDongPageDialog?.show()
    }

    private fun startPage() {
        if (started) {
            if (!TextUtils.isEmpty(phone)) {
                StaticUtil.startActivity(this@QiDongPageActivity, MainActivity::class.java, null)
            } else {
                StaticUtil.startActivity(this@QiDongPageActivity, LoginActivity::class.java, null)
            }
            finish()
        } else {
            showDialog()
        }
    }

}