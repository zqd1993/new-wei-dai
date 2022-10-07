package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgapi;

import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyBaseModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyConfigModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyGoodsModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaoJieHauHsyaJhsdyApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<TaoJieHauHsyaJhsdyBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<TaoJieHauHsyaJhsdyBaseModel<List<TaoJieHauHsyaJhsdyGoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<TaoJieHauHsyaJhsdyBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<TaoJieHauHsyaJhsdyBaseModel<TaoJieHauHsyaJhsdyConfigModel>> getValue(@Query("key") String phone);
}
