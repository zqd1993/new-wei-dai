package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteapi;

import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfBaseModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfConfigModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfGoodsModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RongjieSfFgdfApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                      @Query("ip") String ip, @Query("userIdType") String userIdType, @Query("userId") String userId);

    @GET("/app/user/login")
    Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<RongjieSfFgdfBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<RongjieSfFgdfBaseModel<List<RongjieSfFgdfGoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<RongjieSfFgdfBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>> getValue(@Query("key") String phone);
}
