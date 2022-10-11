package com.asvsdfer.new_master_code_kotlin.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object BaseNetWork {

    val placeService = ServiceCreator.create<ApiService>()

    suspend fun getConfig() = placeService.getConfig().await()

    suspend fun getValue(phone: String) = placeService.getValue(phone).await()

    suspend fun login(phone: String, code: String, device: String, ip: String, oaid: String) =
        placeService.login(phone, code, device, ip, oaid).await()

    suspend fun logins(phone: String, ip: String) = placeService.logins(phone, ip).await()

    suspend fun sendVerifyCode(phone: String) =
        placeService.sendVerifyCode(phone).await()

    suspend fun getGoodsList(mobileType: Int, phone: String) =
        placeService.getGoodsList(mobileType, phone).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)

                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}