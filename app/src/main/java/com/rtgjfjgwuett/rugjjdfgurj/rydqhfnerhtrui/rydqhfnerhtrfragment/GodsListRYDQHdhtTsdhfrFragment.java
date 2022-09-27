package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.rydqhfnerhtrfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rtgjfjgwuett.rugjjdfgurj.R;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrapi.RYDQHdhtTsdhfrRetrofitManager;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.BaseRYDQHdhtTsdhfrFragment;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.RYDQHdhtTsdhfrObserverManager;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.BaseRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.GoodsRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.GoodsDetailsRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.rydqhfnerhtradapter.GoodsListRYDQHdhtTsdhfrAdapter;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrSharePreferencesUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.StaticRYDQHdhtTsdhfrUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListRYDQHdhtTsdhfrFragment extends BaseRYDQHdhtTsdhfrFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsListRYDQHdhtTsdhfrAdapter goodsListRYDQHdhtTsdhfrAdapter;
    private Bundle bundle;
    private GoodsRYDQHdhtTsdhfrModel mGoodsRYDQHdhtTsdhfrModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_rydqh_fdhr_yrtehy;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsRYDQHdhtTsdhfrModel));
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
        mobileType = RYDQHdhtTsdhfrSharePreferencesUtil.getInt("mobileType");
        phone = RYDQHdhtTsdhfrSharePreferencesUtil.getString("phone");
        mGoodsRYDQHdhtTsdhfrModel = null;
        Observable<BaseRYDQHdhtTsdhfrModel<List<GoodsRYDQHdhtTsdhfrModel>>> observable = RYDQHdhtTsdhfrRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RYDQHdhtTsdhfrObserverManager<BaseRYDQHdhtTsdhfrModel<List<GoodsRYDQHdhtTsdhfrModel>>>() {
                    @Override
                    public void onSuccess(BaseRYDQHdhtTsdhfrModel<List<GoodsRYDQHdhtTsdhfrModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsRYDQHdhtTsdhfrModel = model.getData().get(0);
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
                        if (goodsListRYDQHdhtTsdhfrAdapter == null){
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

    private void productClick(GoodsRYDQHdhtTsdhfrModel goodsRYDQHdhtTsdhfrModel) {
        phone = RYDQHdhtTsdhfrSharePreferencesUtil.getString("phone");
        Observable<BaseRYDQHdhtTsdhfrModel> observable = RYDQHdhtTsdhfrRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsRYDQHdhtTsdhfrModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RYDQHdhtTsdhfrObserverManager<BaseRYDQHdhtTsdhfrModel>() {
                    @Override
                    public void onSuccess(BaseRYDQHdhtTsdhfrModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsRYDQHdhtTsdhfrModel.getProductName());
                        bundle.putString("url", goodsRYDQHdhtTsdhfrModel.getUrl());
                        StaticRYDQHdhtTsdhfrUtil.startActivity(getActivity(), GoodsDetailsRYDQHdhtTsdhfrActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsRYDQHdhtTsdhfrModel> mData){
        if (goodsListRYDQHdhtTsdhfrAdapter == null){
            goodsListRYDQHdhtTsdhfrAdapter = new GoodsListRYDQHdhtTsdhfrAdapter(R.layout.adapter_goods_list_layout_rydqh_fdhr_yrtehy, mData);
            goodsListRYDQHdhtTsdhfrAdapter.setHasStableIds(true);
            goodsListRYDQHdhtTsdhfrAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsListRYDQHdhtTsdhfrAdapter);
        } else {
            goodsListRYDQHdhtTsdhfrAdapter.replaceData(mData);
        }
    }
}
