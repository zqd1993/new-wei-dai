package com.qingsongvyrnng.mrjgndsdg.qingsojdkvapi;

import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduConfigModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.GoodsBaseQingSongShfjAFduModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseQingSongShfjAFduApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseQingSongShfjAFduModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseQingSongShfjAFduModel<List<GoodsBaseQingSongShfjAFduModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseQingSongShfjAFduModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel>> getValue(@Query("key") String phone);
}
