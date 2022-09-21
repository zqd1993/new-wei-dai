package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.SPUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StatusBarUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.widget.UserDialog
import kotlinx.android.synthetic.main.activity_zhuxiao_page.commit_btn
import kotlinx.android.synthetic.main.head_layout.*
import kotlinx.android.synthetic.main.loading_layout.*

class ZhuXiaoActivity : AppCompatActivity(){

    private var mUserDialog: UserDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_zhuxiao_page)
        if (SPUtil.getBool("NO_RECORD")) {
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
        title_tv.text = "注销账号"
        back_image.setOnClickListener(View.OnClickListener { v: View? -> finish() })
        commit_btn.setOnClickListener(View.OnClickListener { v: View? ->
            mUserDialog = UserDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false)
            mUserDialog?.setBtnClickListener(object : UserDialog.BtnClickListener {
                override fun leftClicked() {
                    mUserDialog?.dismiss()
                }

                override fun rightClicked() {
                    rotate_loading.start()
                    loading_fl.visibility = View.VISIBLE
                    Handler().postDelayed({
                        loading_fl.visibility = View.GONE
                        rotate_loading.stop()
                        finish()
                    }, 2000)
                    mUserDialog?.dismiss()
                }
            })
            mUserDialog?.show()
            mUserDialog?.setBtnStr("取消", "注销账号")
        })
    }

}