package com.asvsdfer.new_master_code_kotlin.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.ui.livedata.MainViewModel
import com.asvsdfer.new_master_code_kotlin.util.PreferencesOpenUtil
import com.asvsdfer.new_master_code_kotlin.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.top_layout.*

class WebViewActivity: AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var bundle: Bundle? = null

    private var tag = 0

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        StatusBarUtil.setTransparent(this, false)
        back_image.setOnClickListener { finish() }
        bundle = intent.extras
        if (bundle!!.containsKey("tag")) tag = bundle!!.getInt("tag")
        if (bundle!!.containsKey("url")) url = bundle!!.getString("url")
        if (tag == 1) title_tv.text =
            resources.getString(R.string.privacy_policy) else title_tv.text =
            resources.getString(R.string.user_service_agreement)
        val webSettings: WebSettings = web_view.settings
        viewModel.getValue.observe(this, { result ->
            val model = result.getOrNull()
            if (model != null) {
                PreferencesOpenUtil.saveBool(
                    "NO_RECORD",
                    model.videoTape != "0"
                )
                if (PreferencesOpenUtil.getBool("NO_RECORD") == true) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
                }
            }
        })
        webSettings?.let {
            it.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //设置缓存
            it.javaScriptEnabled = true //设置能够解析Javascript
            it.domStorageEnabled = true //设置适应Html5 重点是这个设置
            it.textZoom = 100
            web_view.webViewClient = WebViewClient()
            url?.let { it1 -> web_view.loadUrl(it1) }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN) {
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
        viewModel.getValue("VIDEOTAPE")
        if (web_view != null) web_view.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (web_view != null) {
            val parent: ViewGroup = web_view.parent as ViewGroup
            web_view.removeAllViews()
            web_view.destroy()
            if (parent != null) {
                parent.removeView(web_view)
            }
        }
    }

}