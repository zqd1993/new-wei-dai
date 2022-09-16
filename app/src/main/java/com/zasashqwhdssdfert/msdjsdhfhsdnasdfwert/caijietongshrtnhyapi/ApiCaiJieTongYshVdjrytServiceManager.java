package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyapi;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.CaiJieTongYshVdjrytBaseModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.ConfigCaiJieTongYshVdjrytModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.CaiJieTongYshVdjrytGoodsModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.LoginCaiJieTongYshVdjrytModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCaiJieTongYshVdjrytServiceManager {
    @GET("/app/config/getConfig")
    Observable<CaiJieTongYshVdjrytBaseModel<ConfigCaiJieTongYshVdjrytModel>> getConfig();

    @GET("/app/user/login")
    Observable<CaiJieTongYshVdjrytBaseModel<LoginCaiJieTongYshVdjrytModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                  @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<CaiJieTongYshVdjrytBaseModel<LoginCaiJieTongYshVdjrytModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<CaiJieTongYshVdjrytBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<CaiJieTongYshVdjrytBaseModel<List<CaiJieTongYshVdjrytGoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<CaiJieTongYshVdjrytBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<CaiJieTongYshVdjrytBaseModel<ConfigCaiJieTongYshVdjrytModel>> getValue(@Query("key") String phone);
}
