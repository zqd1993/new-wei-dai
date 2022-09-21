package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdrfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrapi.YingJiHDSdJdgfsRetrofitManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.BaseYingJiHDSdJdgfsFragment;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsObserverManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsBaseModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsGoodsModel;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.GoodsDetailsActivityYingJiHDSdJdgfs;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdradapter.GoodsYingJiHDSdJdgfsListAdapter;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsSharePreferencesUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.StaticCYingJiHDSdJdgfsUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainYingJiHDSdJdgfsFragment extends BaseYingJiHDSdJdgfsFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsYingJiHDSdJdgfsListAdapter goodsYingJiHDSdJdgfsListAdapter;
    private Bundle bundle;
    private YingJiHDSdJdgfsGoodsModel mYingJiHDSdJdgfsGoodsModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_ying_ji_dh_jie_fuerty;
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
        rootLl.setOnClickListener(v -> productClick(mYingJiHDSdJdgfsGoodsModel));
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
        mobileType = YingJiHDSdJdgfsSharePreferencesUtil.getInt("mobileType");
        phone = YingJiHDSdJdgfsSharePreferencesUtil.getString("phone");
        mYingJiHDSdJdgfsGoodsModel = null;
        Observable<YingJiHDSdJdgfsBaseModel<List<YingJiHDSdJdgfsGoodsModel>>> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel<List<YingJiHDSdJdgfsGoodsModel>>>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel<List<YingJiHDSdJdgfsGoodsModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mYingJiHDSdJdgfsGoodsModel = model.getData().get(0);
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
                        if (goodsYingJiHDSdJdgfsListAdapter == null){
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

    private void productClick(YingJiHDSdJdgfsGoodsModel yingJiHDSdJdgfsGoodsModel) {
        phone = YingJiHDSdJdgfsSharePreferencesUtil.getString("phone");
        Observable<YingJiHDSdJdgfsBaseModel> observable = YingJiHDSdJdgfsRetrofitManager.getRetrofitManager().
                getApiService().productClick(yingJiHDSdJdgfsGoodsModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new YingJiHDSdJdgfsObserverManager<YingJiHDSdJdgfsBaseModel>() {
                    @Override
                    public void onSuccess(YingJiHDSdJdgfsBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", yingJiHDSdJdgfsGoodsModel.getProductName());
                        bundle.putString("url", yingJiHDSdJdgfsGoodsModel.getUrl());
                        StaticCYingJiHDSdJdgfsUtil.startActivity(getActivity(), GoodsDetailsActivityYingJiHDSdJdgfs.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<YingJiHDSdJdgfsGoodsModel> mData){
        if (goodsYingJiHDSdJdgfsListAdapter == null){
            goodsYingJiHDSdJdgfsListAdapter = new GoodsYingJiHDSdJdgfsListAdapter(R.layout.adapter_goods_list_layout_ying_ji_dh_jie_fuerty, mData);
            goodsYingJiHDSdJdgfsListAdapter.setHasStableIds(true);
            goodsYingJiHDSdJdgfsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsYingJiHDSdJdgfsListAdapter);
        } else {
            goodsYingJiHDSdJdgfsListAdapter.replaceData(mData);
        }
    }
}
