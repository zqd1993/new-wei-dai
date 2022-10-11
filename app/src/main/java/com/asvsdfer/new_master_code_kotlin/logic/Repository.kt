package com.asvsdfer.new_master_code_kotlin.logic

import androidx.lifecycle.liveData
import com.asvsdfer.new_master_code_kotlin.MyApp
import com.asvsdfer.new_master_code_kotlin.logic.model.*
import com.asvsdfer.new_master_code_kotlin.logic.network.BaseNetWork
import com.asvsdfer.new_master_code_kotlin.util.showToast
import kotlinx.coroutines.Dispatchers

object Repository {
    fun getConfig() = liveData(Dispatchers.IO) {
        val result = try {
            val responseModel = BaseNetWork.getConfig()
            if (responseModel.code == 200) {
                val places = responseModel.data
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is${responseModel.msg}"))
            }
        } catch (e: Exception) {
            Result.failure<ConfigModel>(e)
        }
        emit(result)
    }

    fun getValue(phone: String) = liveData(Dispatchers.IO) {
        val result = try {
            val responseModel = BaseNetWork.getValue(phone)
            if (responseModel.code == 200) {
                val places = responseModel.data
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is${responseModel.msg}"))
            }
        } catch (e: Exception) {
            Result.failure<ConfigModel>(e)
        }
        emit(result)
    }

    fun login(phone: String, code: String, device: String, ip: String, oaid: String) = liveData(Dispatchers.IO) {
        val result = try {
            val responseModel = BaseNetWork.login(phone, code, device, ip, oaid)
            if (responseModel.code == 200) {
                val places = responseModel.data
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is${responseModel.msg}"))
            }
        } catch (e: Exception) {
            Result.failure<LoginModel>(e)
        }
        emit(result)
    }

    fun sendVerifyCode(phone: String) = liveData(Dispatchers.IO) {
        val result = try {
            val responseModel = BaseNetWork.sendVerifyCode(phone)
            if (responseModel.code == 200) {
                val places = responseModel.data
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is${responseModel.msg}"))
            }
        } catch (e: Exception) {
            Result.failure<EmptyModel>(e)
        }
        emit(result)
    }

    fun logins(phone: String, ip: String) = liveData(Dispatchers.IO) {
        val result = try {
            val responseModel = BaseNetWork.logins(phone, ip)
            if (responseModel.code == 200) {
                val places = responseModel.data
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is${responseModel.msg}"))
            }
        } catch (e: Exception) {
            Result.failure<LoginModel>(e)
        }
        emit(result)
    }

    fun getGoodsList(mobileType: Int, phone: String) = liveData(Dispatchers.IO) {
        val result = try {
            val responseModel = BaseNetWork.getGoodsList(mobileType, phone)
            if (responseModel.code == 200) {
                val places = responseModel.data
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is${responseModel.msg}"))
            }
        } catch (e: Exception) {
            Result.failure<List<ProductModel>>(e)
        }
        emit(result)
    }
}