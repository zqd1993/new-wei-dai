package com.asvsdfer.new_master_code.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.asvsdfer.new_master_code.R
import com.asvsdfer.new_master_code.api.NetSimple
import com.asvsdfer.new_master_code.mode.BaseModel
import com.asvsdfer.new_master_code.mode.ConfigModel
import com.asvsdfer.new_master_code.util.StatusBarUtil
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.head_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAgreementActivity : RxAppCompatActivity() {

    private var bundle: Bundle? = null

    private var tag = 0

    private var url: String? = null

    private var webSettings: WebSettings? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        StatusBarUtil.setTransparent(this, false)
        back_image.setOnClickListener { finish() }
//        bundle = intent.extras
//        if (bundle!!.containsKey("tag")) tag = bundle!!.getInt("tag")
//        if (bundle!!.containsKey("url")) url = bundle!!.getString("url")
//        if (tag == 1) title_tv.text =
//            resources.getString(R.string.privacy_policy) else title_tv.text =
//            resources.getString(R.string.user_service_agreement)
        webSettings = web_view.settings
        webSettings?.let {
            it.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //设置缓存
            it.javaScriptEnabled = true //设置能够解析Javascript
            it.domStorageEnabled = true //设置适应Html5 重点是这个设置
            it.textZoom = 100
            web_view.webViewClient = WebViewClient()
            url?.let { it1 -> web_view.loadUrl("www.baidu.com") }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(event!!.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (web_view.canGoBack()) {
                    web_view.goBack()
                } else {
                    finish()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        if (web_view != null) web_view.onPause()
    }

    override fun onResume() {
        super.onResume()
        getConfig()
        if (web_view != null) web_view.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (web_view != null) {
            val parent: ViewGroup = web_view.parent as ViewGroup
            if (parent != null) {
                parent.removeView(web_view)
            }
            web_view.removeAllViews()
            web_view.destroy()
        }
    }

    private fun getConfig() {
        val telService = NetSimple.getRetrofitManager().apiService  //传入之前定义的接口

        //Get请求并处理结果
        telService.getValue("VIDEOTAPE").enqueue(object : Callback<BaseModel<ConfigModel>> {
            override fun onFailure(call: Call<BaseModel<ConfigModel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BaseModel<ConfigModel>>, response: Response<BaseModel<ConfigModel>>) {
                val tel = response.body()
                if (tel != null) {

                }
            }
        })
    }
}