package com.asvsdfer.new_master_code_kotlin.logic.network

import android.util.Log
import com.asvsdfer.new_master_code_kotlin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

object ServiceCreator {

    const val BASE_URL = "http://43.249.30.103:7751/"
    const val ZCXY = "https://www.baidu.com"
    const val YSXY = "https://www.baidu.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getBuilder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getBuilder(): OkHttpClient.Builder {
        val interceptor = HttpLoggingInterceptor { message ->
            var message = message
            try {
                message = message.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25")
                val text = URLDecoder.decode(message, "utf-8")
                Log.d("OKHttp-----", text)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                Log.d("OKHttp-----", message)
            }
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }
        return builder
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}