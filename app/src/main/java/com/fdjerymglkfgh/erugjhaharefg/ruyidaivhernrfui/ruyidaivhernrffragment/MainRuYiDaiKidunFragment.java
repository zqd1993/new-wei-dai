package com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui.ruyidaivhernrffragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fdjerymglkfgh.erugjhaharefg.R;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfapi.RuYiDaiKidunRetrofitManager;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfbase.BaseRuYiDaiKidunFragment;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfbase.RuYiDaiKidunObserverManager;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfmodel.BaseRuYiDaiKidunModel;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfmodel.GoodsRuYiDaiKidunModel;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui.GoodsDetailsRuYiDaiKidunActivity;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfui.ruyidaivhernrfadapter.GoodsListRuYiDaiKidunAdapter;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.RuYiDaiKidunSharePreferencesUtil;
import com.fdjerymglkfgh.erugjhaharefg.ruyidaivhernrfutil.StaticRuYiDaiKidunUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainRuYiDaiKidunFragment extends BaseRuYiDaiKidunFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsListRuYiDaiKidunAdapter goodsListRuYiDaiKidunAdapter;
    private Bundle bundle;
    private GoodsRuYiDaiKidunModel mGoodsRuYiDaiKidunModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ru_yi_dai_dfu_eng_main;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsRuYiDaiKidunModel));
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
        mobileType = RuYiDaiKidunSharePreferencesUtil.getInt("mobileType");
        phone = RuYiDaiKidunSharePreferencesUtil.getString("phone");
        mGoodsRuYiDaiKidunModel = null;
        Observable<BaseRuYiDaiKidunModel<List<GoodsRuYiDaiKidunModel>>> observable = RuYiDaiKidunRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RuYiDaiKidunObserverManager<BaseRuYiDaiKidunModel<List<GoodsRuYiDaiKidunModel>>>() {
                    @Override
                    public void onSuccess(BaseRuYiDaiKidunModel<List<GoodsRuYiDaiKidunModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsRuYiDaiKidunModel = model.getData().get(0);
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
                        if (goodsListRuYiDaiKidunAdapter == null){
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

    private void productClick(GoodsRuYiDaiKidunModel goodsRuYiDaiKidunModel) {
        phone = RuYiDaiKidunSharePreferencesUtil.getString("phone");
        Observable<BaseRuYiDaiKidunModel> observable = RuYiDaiKidunRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsRuYiDaiKidunModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RuYiDaiKidunObserverManager<BaseRuYiDaiKidunModel>() {
                    @Override
                    public void onSuccess(BaseRuYiDaiKidunModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsRuYiDaiKidunModel.getProductName());
                        bundle.putString("url", goodsRuYiDaiKidunModel.getUrl());
                        StaticRuYiDaiKidunUtil.startActivity(getActivity(), GoodsDetailsRuYiDaiKidunActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsRuYiDaiKidunModel> mData){
        if (goodsListRuYiDaiKidunAdapter == null){
            goodsListRuYiDaiKidunAdapter = new GoodsListRuYiDaiKidunAdapter(R.layout.adapter_goods_list_layout_ru_yi_dai_dfu_eng, mData);
            goodsListRuYiDaiKidunAdapter.setHasStableIds(true);
            goodsListRuYiDaiKidunAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsListRuYiDaiKidunAdapter);
        } else {
            goodsListRuYiDaiKidunAdapter.replaceData(mData);
        }
    }
}
