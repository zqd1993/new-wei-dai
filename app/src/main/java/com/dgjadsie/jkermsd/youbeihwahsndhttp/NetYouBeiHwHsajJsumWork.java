package com.dgjadsie.jkermsd.youbeihwahsndhttp;

import com.dgjadsie.jkermsd.youbeihwahsndentity.BaseYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.ConfigYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.YouBeiHwHsajJsumGoodsEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.YouBeiHwHsajJsumLoginEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetYouBeiHwHsajJsumWork {
    @GET("/app/config/getConfig")
    Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> getConfig();

    @GET("/app/user/login")
    Call<BaseYouBeiHwHsajJsumEntity<YouBeiHwHsajJsumLoginEntity>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                        @Query("ip") String ip);

    @GET("/app/user/login")
    Call<BaseYouBeiHwHsajJsumEntity<YouBeiHwHsajJsumLoginEntity>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Call<BaseYouBeiHwHsajJsumEntity> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Call<BaseYouBeiHwHsajJsumEntity<List<YouBeiHwHsajJsumGoodsEntity>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Call<BaseYouBeiHwHsajJsumEntity> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> getValue(@Query("key") String phone);
}
