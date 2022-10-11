package com.asvsdfer.new_master_code_kotlin.logic.network

import com.asvsdfer.new_master_code_kotlin.logic.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/app/config/getConfig")
    fun getConfig(): Call<BaseModel<ConfigModel>>

    @GET("/app/user/login")
    fun login(@Query("phone") phone: String, @Query("code") code: String, @Query("device") device: String,
                  @Query("ip") ip: String, @Query("oaid") oaid: String): Call<BaseModel<LoginModel>>

    @GET("/app/product/productClick")
    fun productClick(@Query("productId") productId: Long, @Query("phone") phone: String): Call<BaseModel<EmptyModel>>

    @GET("/app/config/getValue")
    fun getValue(@Query("key") phone: String): Call<BaseModel<ConfigModel>>

    @GET("/app/user/login")
    fun logins(@Query("phone") phone: String, @Query("ip") ip: String): Call<BaseModel<LoginModel>>

    @GET("/app/user/sendVerifyCode")
    fun sendVerifyCode(@Query("phone") phone: String): Call<BaseModel<EmptyModel>>

    @GET("/app/product/productList")
    fun getGoodsList(@Query("mobileType") mobileType: Int, @Query("phone") phone: String): Call<BaseModel<List<ProductModel>>>
}