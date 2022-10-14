package com.rtyhdfgh.nrtdfgh.http;

import com.rtyhdfgh.nrtdfgh.entity.BaseEntity;
import com.rtyhdfgh.nrtdfgh.entity.ConfigEntity;
import com.rtyhdfgh.nrtdfgh.entity.GoodsEntity;
import com.rtyhdfgh.nrtdfgh.entity.LoginEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetWork {
    @GET("/app/config/getConfig")
    Call<BaseEntity<ConfigEntity>> getConfig();

    @GET("/app/user/login")
    Call<BaseEntity<LoginEntity>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                        @Query("ip") String ip);

    @GET("/app/user/login")
    Call<BaseEntity<LoginEntity>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Call<BaseEntity> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Call<BaseEntity<List<GoodsEntity>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Call<BaseEntity> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Call<BaseEntity<ConfigEntity>> getValue(@Query("key") String phone);
}
