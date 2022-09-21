package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.api;

import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.BaseModel;
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.ConfigModel;
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.EmptyModel;
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.GoodsModel;
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.LoginModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiManager {
    @GET("/app/config/getConfig")
    Call<BaseModel<ConfigModel>> getConfig();

    @GET("/app/user/login")
    Call<BaseModel<LoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                            @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Call<BaseModel<LoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Call<BaseModel<EmptyModel>> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Call<BaseModel<List<GoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Call<BaseModel<EmptyModel>> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Call<BaseModel<ConfigModel>> getValue(@Query("key") String phone);
}
