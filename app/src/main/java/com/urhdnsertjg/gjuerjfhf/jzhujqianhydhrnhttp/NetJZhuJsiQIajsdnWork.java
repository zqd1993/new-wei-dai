package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnhttp;

import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.BaseJZhuJsiQIajsdnEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.JZhuJsiQIajsdnConfigEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.JZhuJsiQIajsdnGoodsEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.LoginJZhuJsiQIajsdnEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetJZhuJsiQIajsdnWork {
    @GET("/app/config/getConfig")
    Call<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> getConfig();

    @GET("/app/user/login")
    Call<BaseJZhuJsiQIajsdnEntity<LoginJZhuJsiQIajsdnEntity>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                    @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Call<BaseJZhuJsiQIajsdnEntity<LoginJZhuJsiQIajsdnEntity>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Call<BaseJZhuJsiQIajsdnEntity> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Call<BaseJZhuJsiQIajsdnEntity<List<JZhuJsiQIajsdnGoodsEntity>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Call<BaseJZhuJsiQIajsdnEntity> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Call<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> getValue(@Query("key") String phone);
}
