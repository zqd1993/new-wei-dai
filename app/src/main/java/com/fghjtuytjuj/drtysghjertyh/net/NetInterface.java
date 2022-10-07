package com.fghjtuytjuj.drtysghjertyh.net;

import com.fghjtuytjuj.drtysghjertyh.bean.BaseModel;
import com.fghjtuytjuj.drtysghjertyh.bean.ConfigBean;
import com.fghjtuytjuj.drtysghjertyh.bean.LoginBean;
import com.fghjtuytjuj.drtysghjertyh.bean.ProductBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetInterface {
    @GET("/app/config/getConfig")
    Call<BaseModel<ConfigBean>> getConfig();

    @GET("/app/user/login")
    Call<BaseModel<LoginBean>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                           @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/product/productClick")
    Call<BaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Call<BaseModel<ConfigBean>> getValue(@Query("key") String phone);

    @GET("/app/user/login")
    Call<BaseModel<LoginBean>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Call<BaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Call<BaseModel<List<ProductBean>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

}
