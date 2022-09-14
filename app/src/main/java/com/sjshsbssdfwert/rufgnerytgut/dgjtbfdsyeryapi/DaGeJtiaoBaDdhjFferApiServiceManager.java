package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryapi;

import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.BaseDaGeJtiaoBaDdhjFferModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferConfigModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.GoodsDaGeJtiaoBaDdhjFferModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DaGeJtiaoBaDdhjFferApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                  @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseDaGeJtiaoBaDdhjFferModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseDaGeJtiaoBaDdhjFferModel<List<GoodsDaGeJtiaoBaDdhjFferModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseDaGeJtiaoBaDdhjFferModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseDaGeJtiaoBaDdhjFferModel<DaGeJtiaoBaDdhjFferConfigModel>> getValue(@Query("key") String phone);
}
