package com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegui.zhaocaimaowegfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhaocaimaowerdfgf.nheradfert.R;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegapi.RetrofitZhaoCaiCatKfrtManager;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegbase.BaseZhaoCaiCatKfrtFragment;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegbase.ZhaoCaiCatKfrtObserverManager;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegmodel.ZhaoCaiCatKfrtBaseModel;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegmodel.GoodsZhaoCaiCatKfrtModel;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegui.ZhaoCaiCatKfrtGoodsDetailsZhaoCaiCatKfrtActivity;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegui.zhaocaimaowegadapter.ZhaoCaiCatKfrtGoodsListAdapter;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtSharePreferencesUtil;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil.StaticZhaoCaiCatKfrtUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainZhaoCaiCatKfrtZhaoCaiCatKfrtFragment extends BaseZhaoCaiCatKfrtFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private ZhaoCaiCatKfrtGoodsListAdapter zhaoCaiCatKfrtGoodsListAdapter;
    private Bundle bundle;
    private GoodsZhaoCaiCatKfrtModel mGoodsZhaoCaiCatKfrtModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhao_cai_mao_dfget_main;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsZhaoCaiCatKfrtModel));
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
        mobileType = ZhaoCaiCatKfrtSharePreferencesUtil.getInt("mobileType");
        phone = ZhaoCaiCatKfrtSharePreferencesUtil.getString("phone");
        mGoodsZhaoCaiCatKfrtModel = null;
        Observable<ZhaoCaiCatKfrtBaseModel<List<GoodsZhaoCaiCatKfrtModel>>> observable = RetrofitZhaoCaiCatKfrtManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZhaoCaiCatKfrtObserverManager<ZhaoCaiCatKfrtBaseModel<List<GoodsZhaoCaiCatKfrtModel>>>() {
                    @Override
                    public void onSuccess(ZhaoCaiCatKfrtBaseModel<List<GoodsZhaoCaiCatKfrtModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsZhaoCaiCatKfrtModel = model.getData().get(0);
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
                        if (zhaoCaiCatKfrtGoodsListAdapter == null){
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

    private void productClick(GoodsZhaoCaiCatKfrtModel goodsZhaoCaiCatKfrtModel) {
        phone = ZhaoCaiCatKfrtSharePreferencesUtil.getString("phone");
        Observable<ZhaoCaiCatKfrtBaseModel> observable = RetrofitZhaoCaiCatKfrtManager.getRetrofitManager().
                getApiService().productClick(goodsZhaoCaiCatKfrtModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZhaoCaiCatKfrtObserverManager<ZhaoCaiCatKfrtBaseModel>() {
                    @Override
                    public void onSuccess(ZhaoCaiCatKfrtBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsZhaoCaiCatKfrtModel.getProductName());
                        bundle.putString("url", goodsZhaoCaiCatKfrtModel.getUrl());
                        StaticZhaoCaiCatKfrtUtil.startActivity(getActivity(), ZhaoCaiCatKfrtGoodsDetailsZhaoCaiCatKfrtActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsZhaoCaiCatKfrtModel> mData){
        if (zhaoCaiCatKfrtGoodsListAdapter == null){
            zhaoCaiCatKfrtGoodsListAdapter = new ZhaoCaiCatKfrtGoodsListAdapter(R.layout.adapter_goods_list_layout_zhao_cai_mao_dfget, mData);
            zhaoCaiCatKfrtGoodsListAdapter.setHasStableIds(true);
            zhaoCaiCatKfrtGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(zhaoCaiCatKfrtGoodsListAdapter);
        } else {
            zhaoCaiCatKfrtGoodsListAdapter.replaceData(mData);
        }
    }
}
