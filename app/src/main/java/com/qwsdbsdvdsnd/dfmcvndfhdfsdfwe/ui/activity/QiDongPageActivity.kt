package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.KeyEvent
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.api.NetSimple
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.SPUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StaticUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StatusBarUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.widget.QiDongPageDialog
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
                StaticUtil.startActivity(this@QiDongPageActivity, LoginActivity::class.java, null)
                finish()
            }

            override fun bottomBtnClicked() {
                finish()
            }

            override fun clickedZcxy() {
                bundle = Bundle()
                bundle!!.putInt("tag", 1)
                bundle!!.putString("url", NetSimple.ZCXY)
                StaticUtil.startActivity(this@QiDongPageActivity, UserAgreementActivity::class.java, bundle)
            }

            override fun clickedYsxy() {
                bundle = Bundle()
                bundle!!.putInt("tag", 2)
                bundle!!.putString("url", NetSimple.YSXY)
                StaticUtil.startActivity(this@QiDongPageActivity, UserAgreementActivity::class.java, bundle)
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