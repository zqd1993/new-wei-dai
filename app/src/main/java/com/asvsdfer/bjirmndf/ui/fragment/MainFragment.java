package com.asvsdfer.bjirmndf.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.asvsdfer.bjirmndf.R;
import com.asvsdfer.bjirmndf.api.RetrofitManager;
import com.asvsdfer.bjirmndf.base.BaseFragment;
import com.asvsdfer.bjirmndf.base.ObserverManager;
import com.asvsdfer.bjirmndf.model.BaseModel;
import com.asvsdfer.bjirmndf.model.GoodsModel;
import com.asvsdfer.bjirmndf.model.LoginModel;
import com.asvsdfer.bjirmndf.ui.GoodsDetailsActivity;
import com.asvsdfer.bjirmndf.ui.LoginActivity;
import com.asvsdfer.bjirmndf.ui.UserYsxyActivity;
import com.asvsdfer.bjirmndf.ui.adapter.GoodsListAdapter;
import com.asvsdfer.bjirmndf.util.SharePreferencesUtil;
import com.asvsdfer.bjirmndf.util.StaticUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends BaseFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsListAdapter goodsListAdapter;
    private Bundle bundle;
    private GoodsModel mGoodsModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsModel));
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
        mobileType = SharePreferencesUtil.getInt("mobileType");
        phone = SharePreferencesUtil.getString("phone");
        mGoodsModel = null;
        Observable<BaseModel<List<GoodsModel>>> observable = RetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseModel<List<GoodsModel>>>() {
                    @Override
                    public void onSuccess(BaseModel<List<GoodsModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsModel = model.getData().get(0);
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
                        if (goodsListAdapter == null){
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

    private void productClick(GoodsModel goodsModel) {
        phone = SharePreferencesUtil.getString("phone");
        Observable<BaseModel> observable = RetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", goodsModel.getUrl());
                        StaticUtil.startActivity(getActivity(), GoodsDetailsActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsModel> mData){
        if (goodsListAdapter == null){
            goodsListAdapter = new GoodsListAdapter(R.layout.adapter_goods_list_layout, mData);
            goodsListAdapter.setHasStableIds(true);
            goodsListAdapter.setOnItemClickListener((adapter, view, position) -> {
                productClick(mData.get(position));
            });
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            goodsList.setAdapter(goodsListAdapter);
        } else {
            goodsListAdapter.replaceData(mData);
        }
    }
}
