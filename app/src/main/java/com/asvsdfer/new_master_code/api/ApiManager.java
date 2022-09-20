package com.asvsdfer.new_master_code.api;

import com.asvsdfer.new_master_code.mode.BaseModel;
import com.asvsdfer.new_master_code.mode.ConfigModel;
import com.asvsdfer.new_master_code.mode.GoodsModel;
import com.asvsdfer.new_master_code.mode.LoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiManager {
    @GET("/app/config/getConfig")
    Observable<BaseModel<ConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseModel<LoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                            @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseModel<LoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseModel<List<GoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Call<BaseModel<ConfigModel>> getValue(@Query("key") String phone);
}
