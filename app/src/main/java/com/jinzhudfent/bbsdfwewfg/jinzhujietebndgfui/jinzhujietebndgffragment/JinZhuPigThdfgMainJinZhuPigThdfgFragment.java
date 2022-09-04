package com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfui.jinzhujietebndgffragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jinzhudfent.bbsdfwewfg.R;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfapi.JinZhuPigThdfgRetrofitManager;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfbase.BaseJinZhuPigThdfgFragment;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfbase.JinZhuPigThdfgObserverManager;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfmodel.BaseJinZhuPigThdfgModel;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfmodel.GoodsJinZhuPigThdfgModel;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfui.GoodsDetailsJinZhuPigThdfgActivity;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfui.jinzhujietebndgfadapter.JinZhuPigThdfgGoodsListAdapter;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgSharePreferencesUtil;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfutil.StaticJinZhuPigThdfgUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JinZhuPigThdfgMainJinZhuPigThdfgFragment extends BaseJinZhuPigThdfgFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private JinZhuPigThdfgGoodsListAdapter jinZhuPigThdfgGoodsListAdapter;
    private Bundle bundle;
    private GoodsJinZhuPigThdfgModel mGoodsJinZhuPigThdfgModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jin_zhu_asf_pig_main;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsJinZhuPigThdfgModel));
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
        mobileType = JinZhuPigThdfgSharePreferencesUtil.getInt("mobileType");
        phone = JinZhuPigThdfgSharePreferencesUtil.getString("phone");
        mGoodsJinZhuPigThdfgModel = null;
        Observable<BaseJinZhuPigThdfgModel<List<GoodsJinZhuPigThdfgModel>>> observable = JinZhuPigThdfgRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JinZhuPigThdfgObserverManager<BaseJinZhuPigThdfgModel<List<GoodsJinZhuPigThdfgModel>>>() {
                    @Override
                    public void onSuccess(BaseJinZhuPigThdfgModel<List<GoodsJinZhuPigThdfgModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsJinZhuPigThdfgModel = model.getData().get(0);
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
                        if (jinZhuPigThdfgGoodsListAdapter == null){
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

    private void productClick(GoodsJinZhuPigThdfgModel goodsJinZhuPigThdfgModel) {
        phone = JinZhuPigThdfgSharePreferencesUtil.getString("phone");
        Observable<BaseJinZhuPigThdfgModel> observable = JinZhuPigThdfgRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsJinZhuPigThdfgModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JinZhuPigThdfgObserverManager<BaseJinZhuPigThdfgModel>() {
                    @Override
                    public void onSuccess(BaseJinZhuPigThdfgModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsJinZhuPigThdfgModel.getProductName());
                        bundle.putString("url", goodsJinZhuPigThdfgModel.getUrl());
                        StaticJinZhuPigThdfgUtil.startActivity(getActivity(), GoodsDetailsJinZhuPigThdfgActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsJinZhuPigThdfgModel> mData){
        if (jinZhuPigThdfgGoodsListAdapter == null){
            jinZhuPigThdfgGoodsListAdapter = new JinZhuPigThdfgGoodsListAdapter(R.layout.adapter_goods_list_layout_jin_zhu_asf_pig, mData);
            jinZhuPigThdfgGoodsListAdapter.setHasStableIds(true);
            jinZhuPigThdfgGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(jinZhuPigThdfgGoodsListAdapter);
        } else {
            jinZhuPigThdfgGoodsListAdapter.replaceData(mData);
        }
    }
}
