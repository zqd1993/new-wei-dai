package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqieffragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dfjsdndsuweq.sfdkdfmsz.R;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefapi.BaseTaoFenQiDfREvfRetrofitManager;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseBaseTaoFenQiDfREvfFragment;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseTaoFenQiDfREvfObserverManager;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.GoodsBaseTaoFenQiDfREvfModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.GoodsDetailsTaoFenQiDfREvfActivityBase;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqiefadapter.GoodsListBaseTaoFenQiDfREvfAdapter;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfSharePreferencesUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.StaticBaseTaoFenQiDfREvfUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainTaoFenQiDfREvfFragmentBase extends BaseBaseTaoFenQiDfREvfFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsListBaseTaoFenQiDfREvfAdapter goodsListBaseTaoFenQiDfREvfAdapter;
    private Bundle bundle;
    private GoodsBaseTaoFenQiDfREvfModel mGoodsBaseTaoFenQiDfREvfModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_tao_fen_qi_rtgr_vbd;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsBaseTaoFenQiDfREvfModel));
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
        mobileType = BaseTaoFenQiDfREvfSharePreferencesUtil.getInt("mobileType");
        phone = BaseTaoFenQiDfREvfSharePreferencesUtil.getString("phone");
        mGoodsBaseTaoFenQiDfREvfModel = null;
        Observable<BaseTaoFenQiDfREvfModel<List<GoodsBaseTaoFenQiDfREvfModel>>> observable = BaseTaoFenQiDfREvfRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseTaoFenQiDfREvfObserverManager<BaseTaoFenQiDfREvfModel<List<GoodsBaseTaoFenQiDfREvfModel>>>() {
                    @Override
                    public void onSuccess(BaseTaoFenQiDfREvfModel<List<GoodsBaseTaoFenQiDfREvfModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsBaseTaoFenQiDfREvfModel = model.getData().get(0);
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
                        if (goodsListBaseTaoFenQiDfREvfAdapter == null){
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

    private void productClick(GoodsBaseTaoFenQiDfREvfModel goodsBaseTaoFenQiDfREvfModel) {
        phone = BaseTaoFenQiDfREvfSharePreferencesUtil.getString("phone");
        Observable<BaseTaoFenQiDfREvfModel> observable = BaseTaoFenQiDfREvfRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsBaseTaoFenQiDfREvfModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseTaoFenQiDfREvfObserverManager<BaseTaoFenQiDfREvfModel>() {
                    @Override
                    public void onSuccess(BaseTaoFenQiDfREvfModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsBaseTaoFenQiDfREvfModel.getProductName());
                        bundle.putString("url", goodsBaseTaoFenQiDfREvfModel.getUrl());
                        StaticBaseTaoFenQiDfREvfUtil.startActivity(getActivity(), GoodsDetailsTaoFenQiDfREvfActivityBase.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsBaseTaoFenQiDfREvfModel> mData){
        if (goodsListBaseTaoFenQiDfREvfAdapter == null){
            goodsListBaseTaoFenQiDfREvfAdapter = new GoodsListBaseTaoFenQiDfREvfAdapter(R.layout.adapter_goods_list_layout_tao_fen_qi_rtgr_vbd, mData);
            goodsListBaseTaoFenQiDfREvfAdapter.setHasStableIds(true);
            goodsListBaseTaoFenQiDfREvfAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsListBaseTaoFenQiDfREvfAdapter);
        } else {
            goodsListBaseTaoFenQiDfREvfAdapter.replaceData(mData);
        }
    }
}
