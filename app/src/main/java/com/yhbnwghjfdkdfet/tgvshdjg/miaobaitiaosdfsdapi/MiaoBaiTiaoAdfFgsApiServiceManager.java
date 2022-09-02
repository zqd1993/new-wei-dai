package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdapi;

import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.MiaoBaiTiaoAdfFgsBaseModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.ConfigMiaoBaiTiaoAdfFgsModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.GoodsMiaoBaiTiaoAdfFgsModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.LoginMiaoBaiTiaoAdfFgsModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MiaoBaiTiaoAdfFgsApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel>> getConfig();

    @GET("/app/user/login")
    Observable<MiaoBaiTiaoAdfFgsBaseModel<LoginMiaoBaiTiaoAdfFgsModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                              @Query("ip") String ip, @Query("userIdType") String userIdType, @Query("userId") String userId);

    @GET("/app/user/login")
    Observable<MiaoBaiTiaoAdfFgsBaseModel<LoginMiaoBaiTiaoAdfFgsModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<MiaoBaiTiaoAdfFgsBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<MiaoBaiTiaoAdfFgsBaseModel<List<GoodsMiaoBaiTiaoAdfFgsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<MiaoBaiTiaoAdfFgsBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel>> getValue(@Query("key") String phone);
}
