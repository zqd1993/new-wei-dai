package com.asvsdfer.new_master_code_kotlin.ui.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.asvsdfer.new_master_code_kotlin.logic.Repository
import com.asvsdfer.new_master_code_kotlin.logic.model.GoodsListReqModel
import com.asvsdfer.new_master_code_kotlin.logic.model.LoginReqModel
import com.asvsdfer.new_master_code_kotlin.logic.model.LoginsReqModel

class MainViewModel: ViewModel() {

    /**
     * 获取配置信息
     */
    private val getConfigLiveData = MutableLiveData<Any?>()

    val getConfig = Transformations.switchMap(getConfigLiveData){
        Repository.getConfig()
    }

    fun getConfig() {
        getConfigLiveData.value = getConfigLiveData.value
    }

    /**
     * 是否需要录屏
     */
    private val getValueLiveData = MutableLiveData<String>()

    val getValue = Transformations.switchMap(getValueLiveData){
        Repository.getValue(it)
    }

    fun getValue(phone: String) {
        getValueLiveData.value = phone
    }

    /**
     * 登录
     */
    private val loginLiveData = MutableLiveData<LoginReqModel>()

    val login = Transformations.switchMap(loginLiveData) {
        Repository.login(it.phone, it.code, it.device, it.ip, it.oaid)
    }

    fun login(phone: String, code: String, device: String, ip: String, oaid: String){
        loginLiveData.value = LoginReqModel(phone, code, device, ip, oaid)
    }

    /**
     * 发送验证码
     */
    private val sendVerifyCodeLiveData = MutableLiveData<String>()

    val sendVerifyCode = Transformations.switchMap(sendVerifyCodeLiveData){
        Repository.sendVerifyCode(it)
    }

    fun sendVerifyCode(phone: String) {
        sendVerifyCodeLiveData.value = phone
    }

    /**
     * 首页调用登录接口
     */
    private val loginsLiveData = MutableLiveData<LoginsReqModel>()

    val logins = Transformations.switchMap(loginsLiveData){
        Repository.logins(it.phone, it.ip)
    }

    fun logins(phone: String, ip: String) {
        loginsLiveData.value = LoginsReqModel(phone, ip)
    }

    /**
     * 获取商品列表
     */
    private val getGoodsListLiveData = MutableLiveData<GoodsListReqModel>()

    val getGoodsList = Transformations.switchMap(getGoodsListLiveData){
        Repository.getGoodsList(it.mobileType, it.phone)
    }

    fun getGoodsList(mobileType: Int, phone: String) {
        getGoodsListLiveData.value = GoodsListReqModel(mobileType, phone)
    }

}