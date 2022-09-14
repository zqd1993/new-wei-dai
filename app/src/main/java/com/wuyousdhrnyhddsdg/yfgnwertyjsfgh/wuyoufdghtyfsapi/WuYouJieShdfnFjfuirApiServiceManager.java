package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsapi;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.WuYouJieShdfnFjfuirBaseModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.ConfigWuYouJieShdfnFjfuirModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.GoodsWuYouJieShdfnFjfuirModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.LoginWuYouJieShdfnFjfuirModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WuYouJieShdfnFjfuirApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel>> getConfig();

    @GET("/app/user/login")
    Observable<WuYouJieShdfnFjfuirBaseModel<LoginWuYouJieShdfnFjfuirModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                  @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<WuYouJieShdfnFjfuirBaseModel<LoginWuYouJieShdfnFjfuirModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<WuYouJieShdfnFjfuirBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<WuYouJieShdfnFjfuirBaseModel<List<GoodsWuYouJieShdfnFjfuirModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<WuYouJieShdfnFjfuirBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel>> getValue(@Query("key") String phone);
}
