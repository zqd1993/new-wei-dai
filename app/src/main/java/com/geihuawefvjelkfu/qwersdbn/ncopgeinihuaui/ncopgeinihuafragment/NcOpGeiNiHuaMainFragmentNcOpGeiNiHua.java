package com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaui.ncopgeinihuafragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.geihuawefvjelkfu.qwersdbn.R;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaapi.NcOpGeiNiHuaRetrofitManager;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuabase.NcOpGeiNiHuaBaseFragment;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuabase.ObserverManagerNcOpGeiNiHua;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.BaseNcOpGeiNiHuaModel;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.NcOpGeiNiHuaGoodsModel;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaui.NcOpGeiNiHuaGoodsDetailsNcOpGeiNiHuaActivity;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaui.ncopgeinihuaadapter.NcOpGeiNiHuaGoodsListAdapter;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaSharePreferencesUtil;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaStaticUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NcOpGeiNiHuaMainFragmentNcOpGeiNiHua extends NcOpGeiNiHuaBaseFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private NcOpGeiNiHuaGoodsListAdapter ncOpGeiNiHuaGoodsListAdapter;
    private Bundle bundle;
    private NcOpGeiNiHuaGoodsModel mNcOpGeiNiHuaGoodsModel;

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
        rootLl.setOnClickListener(v -> productClick(mNcOpGeiNiHuaGoodsModel));
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
        mobileType = NcOpGeiNiHuaSharePreferencesUtil.getInt("mobileType");
        phone = NcOpGeiNiHuaSharePreferencesUtil.getString("phone");
        mNcOpGeiNiHuaGoodsModel = null;
        Observable<BaseNcOpGeiNiHuaModel<List<NcOpGeiNiHuaGoodsModel>>> observable = NcOpGeiNiHuaRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManagerNcOpGeiNiHua<BaseNcOpGeiNiHuaModel<List<NcOpGeiNiHuaGoodsModel>>>() {
                    @Override
                    public void onSuccess(BaseNcOpGeiNiHuaModel<List<NcOpGeiNiHuaGoodsModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mNcOpGeiNiHuaGoodsModel = model.getData().get(0);
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
                        if (ncOpGeiNiHuaGoodsListAdapter == null){
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

    private void productClick(NcOpGeiNiHuaGoodsModel ncOpGeiNiHuaGoodsModel) {
        phone = NcOpGeiNiHuaSharePreferencesUtil.getString("phone");
        Observable<BaseNcOpGeiNiHuaModel> observable = NcOpGeiNiHuaRetrofitManager.getRetrofitManager().
                getApiService().productClick(ncOpGeiNiHuaGoodsModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManagerNcOpGeiNiHua<BaseNcOpGeiNiHuaModel>() {
                    @Override
                    public void onSuccess(BaseNcOpGeiNiHuaModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", ncOpGeiNiHuaGoodsModel.getUrl());
                        NcOpGeiNiHuaStaticUtil.startActivity(getActivity(), NcOpGeiNiHuaGoodsDetailsNcOpGeiNiHuaActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<NcOpGeiNiHuaGoodsModel> mData){
        if (ncOpGeiNiHuaGoodsListAdapter == null){
            ncOpGeiNiHuaGoodsListAdapter = new NcOpGeiNiHuaGoodsListAdapter(R.layout.adapter_goods_list_layout, mData);
            ncOpGeiNiHuaGoodsListAdapter.setHasStableIds(true);
            ncOpGeiNiHuaGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            goodsList.setAdapter(ncOpGeiNiHuaGoodsListAdapter);
        } else {
            ncOpGeiNiHuaGoodsListAdapter.replaceData(mData);
        }
    }
}
