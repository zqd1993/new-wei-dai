package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyufragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tyfdgeruyfgh.ernhfygwsert.R;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuapi.RetrofitZCMdhdTshdfManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.BaseZCMdhdTshdfFragment;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.ZCMdhdTshdfObserverManager;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ZCMdhdTshdfBaseModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.GoodsZCMdhdTshdfModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.GoodsDetailsZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyuadapter.ZCMdhdTshdfGoodsListAdapter;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfSharePreferencesUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.StaticZCMdhdTshdfUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListZCMdhdTshdfFragment extends BaseZCMdhdTshdfFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private ZCMdhdTshdfGoodsListAdapter ZCMdhdTshdfGoodsListAdapter;
    private Bundle bundle;
    private GoodsZCMdhdTshdfModel mGoodsZCMdhdTshdfModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_zcm_fhgetr_tqttry;
    }

    @Override
    public void initData() {
        goodsList = rootView.findViewById(R.id.goods_list);
        noDataLl = rootView.findViewById(R.id.no_data_ll);
        rootLl = rootView.findViewById(R.id.root_ll);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> getGoodsList());
        rootLl.setOnClickListener(v -> productClick(mGoodsZCMdhdTshdfModel));
        noDataLl.setOnClickListener(v -> {
            getGoodsList();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = ZCMdhdTshdfSharePreferencesUtil.getInt("mobileType");
        phone = ZCMdhdTshdfSharePreferencesUtil.getString("phone");
        mGoodsZCMdhdTshdfModel = null;
        Observable<ZCMdhdTshdfBaseModel<List<GoodsZCMdhdTshdfModel>>> observable = RetrofitZCMdhdTshdfManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZCMdhdTshdfObserverManager<ZCMdhdTshdfBaseModel<List<GoodsZCMdhdTshdfModel>>>() {
                    @Override
                    public void onSuccess(ZCMdhdTshdfBaseModel<List<GoodsZCMdhdTshdfModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsZCMdhdTshdfModel = model.getData().get(0);
                                setListData(model.getData());
                            } else {
                                noDataLl.setVisibility(View.VISIBLE);
                                goodsList.setVisibility(View.GONE);
                            }
                        } else {
                            noDataLl.setVisibility(View.VISIBLE);
                            goodsList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                        if (ZCMdhdTshdfGoodsListAdapter == null){
                            noDataLl.setVisibility(View.VISIBLE);
                            goodsList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void productClick(GoodsZCMdhdTshdfModel goodsZCMdhdTshdfModel) {
        phone = ZCMdhdTshdfSharePreferencesUtil.getString("phone");
        Observable<ZCMdhdTshdfBaseModel> observable = RetrofitZCMdhdTshdfManager.getRetrofitManager().
                getApiService().productClick(goodsZCMdhdTshdfModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZCMdhdTshdfObserverManager<ZCMdhdTshdfBaseModel>() {
                    @Override
                    public void onSuccess(ZCMdhdTshdfBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsZCMdhdTshdfModel.getProductName());
                        bundle.putString("url", goodsZCMdhdTshdfModel.getUrl());
                        StaticZCMdhdTshdfUtil.startActivity(getActivity(), GoodsDetailsZCMdhdTshdfActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsZCMdhdTshdfModel> mData){
        if (ZCMdhdTshdfGoodsListAdapter == null){
            ZCMdhdTshdfGoodsListAdapter = new ZCMdhdTshdfGoodsListAdapter(R.layout.adapter_goods_list_layout_zcm_fhgetr_tqttry, mData);
            ZCMdhdTshdfGoodsListAdapter.setHasStableIds(true);
            ZCMdhdTshdfGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(ZCMdhdTshdfGoodsListAdapter);
        } else {
            ZCMdhdTshdfGoodsListAdapter.replaceData(mData);
        }
    }
}
