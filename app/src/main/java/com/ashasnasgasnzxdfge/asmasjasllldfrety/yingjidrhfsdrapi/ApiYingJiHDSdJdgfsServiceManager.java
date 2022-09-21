package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrapi;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsBaseModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsConfigModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsGoodsModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.LoginYingJiHDSdJdgfsModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiYingJiHDSdJdgfsServiceManager {
    @GET("/app/config/getConfig")
    Observable<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<YingJiHDSdJdgfsBaseModel<LoginYingJiHDSdJdgfsModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                          @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<YingJiHDSdJdgfsBaseModel<LoginYingJiHDSdJdgfsModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<YingJiHDSdJdgfsBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<YingJiHDSdJdgfsBaseModel<List<YingJiHDSdJdgfsGoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<YingJiHDSdJdgfsBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<YingJiHDSdJdgfsBaseModel<YingJiHDSdJdgfsConfigModel>> getValue(@Query("key") String phone);
}
