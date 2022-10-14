package com.asvsdfer.new_master_code_kotlin.ui.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.asvsdfer.new_master_code_kotlin.MyApp
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.logic.network.ServiceCreator
import com.asvsdfer.new_master_code_kotlin.ui.livedata.MainViewModel
import com.asvsdfer.new_master_code_kotlin.ui.view.CountDownTimerUtils
import com.asvsdfer.new_master_code_kotlin.util.*
import com.github.gzuliyujiang.oaid.DeviceID
import com.github.gzuliyujiang.oaid.DeviceIdentifier
import com.github.gzuliyujiang.oaid.IGetter
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var mobileStr = ""
    private var verificationStr = ""
    private var ip = ""
    private var oaidStr = ""
    private var isNeedVerification = false
    private var isOaid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_login)
        sendRequestWithOkHttp()
        viewModel.getConfig()
        val text = arrayOf(
            "我已阅读并同意",
            "《注册服务协议》",
            "《用户隐私协议》"
        )
        val stringBuffer = StringBuffer()
        for (i in text.indices) {
            stringBuffer.append(text[i])
        }
        login_remind_tv.text = stringBuffer.toString()
        val spannableString = SpannableString(login_remind_tv.text.toString().trim())
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                jumpActivity<WebViewActivity>(this@LoginActivity) {
                    putExtra("tag", 1)
                    putExtra("url", ServiceCreator.ZCXY)
                }
            }

        }
        if (text.size > 1) {
            spannableString.setSpan(
                clickableSpan,
                text[0].length,
                text[0].length + text[1].length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#C99F7A")),
                text[0].length,
                text[0].length + text[1].length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        if (text.size > 1) {
            val clickableSpan1: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    jumpActivity<WebViewActivity>(this@LoginActivity) {
                        putExtra("tag", 2)
                        putExtra("url", ServiceCreator.YSXY)
                    }
                }

            }
            val startNum = spannableString.length - text[2].length
            val endNum = spannableString.length
            spannableString.setSpan(
                clickableSpan1,
                startNum,
                endNum,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#C99F7A")),
                startNum,
                endNum,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        login_remind_tv.text = spannableString
        login_remind_tv.movementMethod = LinkMovementMethod.getInstance()
        initListener()
        observeLiveData()
    }

    private fun initListener() {
        login_btn.setOnClickListener(View.OnClickListener { v: View? ->
            mobileStr = mobile_et.text.toString().trim { it <= ' ' }
            verificationStr = verification_et.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(mobileStr)) {
                "请输入手机号".showToast(this@LoginActivity)
                return@OnClickListener
            }
            if (!mobileStr.isMobile()) {
                "请输入正确的手机号".showToast(this@LoginActivity)
                return@OnClickListener
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                "验证码不能为空".showToast(this@LoginActivity)
                return@OnClickListener
            }
            if (!remind_cb.isChecked) {
                "请阅读用户协议及隐私政策".showToast(this@LoginActivity)
                return@OnClickListener
            }
            if (!isOaid) {
                DeviceIdentifier.register(MyApp.sInstance)
                isOaid = true
            }
            DeviceID.getOAID(this, object : IGetter {
                override fun onOAIDGetComplete(result: String) {
                    var result = result
                    if (TextUtils.isEmpty(result)) {
                        oaidStr = ""
                    } else {
                        val length = result.length
                        if (length < 64) {
                            for (i in 0 until 64 - length) {
                                result += "0"
                            }
                        }
                        oaidStr = result
                    }
                    viewModel.login(mobileStr, verificationStr, "", ip, oaidStr)
                }

                override fun onOAIDGetError(error: Exception) {
                    viewModel.login(mobileStr, verificationStr, "", ip, oaidStr)
                }
            })
        })
        get_verification_tv.setOnClickListener(View.OnClickListener { v: View? ->
            mobileStr = mobile_et.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(mobileStr)) {
                "请输入手机号".showToast(this@LoginActivity)
                return@OnClickListener
            }
            if (!mobileStr.isMobile()) {
                "请输入正确的手机号".showToast(this@LoginActivity)
                return@OnClickListener
            }
            viewModel.sendVerifyCode(mobileStr)
        })
    }

    private fun observeLiveData() {
        viewModel.login.observe(this) {
            val model = it.getOrNull()
            if (model != null) {
                PreferencesOpenUtil.saveInt("mobileType", model.mobileType)
                PreferencesOpenUtil.saveString("phone", mobileStr)
                PreferencesOpenUtil.saveString("ip", ip)
                jumpActivity<MainActivity>(this@LoginActivity)
                finish()
            } else {
                "登录失败".showToast(MyApp.context)
            }
        }
        viewModel.sendVerifyCode.observe(this) {
            val model = it.getOrNull()
            if (model != null) {
                "发送成功".showToast(this@LoginActivity)
                val mCountDownTimerUtils = CountDownTimerUtils(get_verification_tv, 60000, 1000)
                mCountDownTimerUtils.start()
            } else{
                "验证码发送失败".showToast(this@LoginActivity)
            }
        }
        viewModel.getConfig.observe(this, { result ->
            val model = result.getOrNull()
            if (model != null) {
                PreferencesOpenUtil.saveString("APP_MAIL", model.appMail)
                isNeedVerification = "1" == model.isCodeLogin
                verification_ll.visibility = if (isNeedVerification) View.VISIBLE else View.GONE
                remind_cb.isChecked = "1" == model.isSelectLogin
            }
        })
    }

    private fun sendRequestWithOkHttp() {
        Thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://pv.sohu.com/cityjson")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body().string()
                parseJSONWithJSONObject(responseData) //调用json解析的方法
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun parseJSONWithJSONObject(jsonData: String) {
        var jsonStr = ""
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1)
            }
            val jsonObject = JSONObject(jsonStr)
            ip = jsonObject.getString("cip")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}