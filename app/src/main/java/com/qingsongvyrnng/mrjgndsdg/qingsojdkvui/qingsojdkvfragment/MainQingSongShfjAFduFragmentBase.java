package com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qingsongvyrnng.mrjgndsdg.R;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvapi.BaseQingSongShfjAFduRetrofitManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseQingSongShfjAFduFragment;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseQingSongShfjAFduObserverManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.GoodsBaseQingSongShfjAFduModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.GoodsDetailsQingSongShfjAFduActivityBase;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvadapter.GoodsListBaseQingSongShfjAFduAdapter;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduSharePreferencesUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.StaticBaseQingSongShfjAFduUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainQingSongShfjAFduFragmentBase extends BaseQingSongShfjAFduFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsListBaseQingSongShfjAFduAdapter goodsListBaseQingSongShfjAFduAdapter;
    private Bundle bundle;
    private GoodsBaseQingSongShfjAFduModel mGoodsBaseQingSongShfjAFduModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_qing_song_sh_udj;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsBaseQingSongShfjAFduModel));
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
        mobileType = BaseQingSongShfjAFduSharePreferencesUtil.getInt("mobileType");
        phone = BaseQingSongShfjAFduSharePreferencesUtil.getString("phone");
        mGoodsBaseQingSongShfjAFduModel = null;
        Observable<BaseQingSongShfjAFduModel<List<GoodsBaseQingSongShfjAFduModel>>> observable = BaseQingSongShfjAFduRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQingSongShfjAFduObserverManager<BaseQingSongShfjAFduModel<List<GoodsBaseQingSongShfjAFduModel>>>() {
                    @Override
                    public void onSuccess(BaseQingSongShfjAFduModel<List<GoodsBaseQingSongShfjAFduModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsBaseQingSongShfjAFduModel = model.getData().get(0);
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
                        if (goodsListBaseQingSongShfjAFduAdapter == null){
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

    private void productClick(GoodsBaseQingSongShfjAFduModel goodsBaseQingSongShfjAFduModel) {
        phone = BaseQingSongShfjAFduSharePreferencesUtil.getString("phone");
        Observable<BaseQingSongShfjAFduModel> observable = BaseQingSongShfjAFduRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsBaseQingSongShfjAFduModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQingSongShfjAFduObserverManager<BaseQingSongShfjAFduModel>() {
                    @Override
                    public void onSuccess(BaseQingSongShfjAFduModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsBaseQingSongShfjAFduModel.getProductName());
                        bundle.putString("url", goodsBaseQingSongShfjAFduModel.getUrl());
                        StaticBaseQingSongShfjAFduUtil.startActivity(getActivity(), GoodsDetailsQingSongShfjAFduActivityBase.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsBaseQingSongShfjAFduModel> mData){
        if (goodsListBaseQingSongShfjAFduAdapter == null){
            goodsListBaseQingSongShfjAFduAdapter = new GoodsListBaseQingSongShfjAFduAdapter(R.layout.adapter_goods_list_layout_qing_song_sh_udj, mData);
            goodsListBaseQingSongShfjAFduAdapter.setHasStableIds(true);
            goodsListBaseQingSongShfjAFduAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsListBaseQingSongShfjAFduAdapter);
        } else {
            goodsListBaseQingSongShfjAFduAdapter.replaceData(mData);
        }
    }
}
