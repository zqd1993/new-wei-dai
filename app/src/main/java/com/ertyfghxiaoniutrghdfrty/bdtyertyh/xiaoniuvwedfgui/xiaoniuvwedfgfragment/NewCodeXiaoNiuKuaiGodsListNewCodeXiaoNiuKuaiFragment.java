package com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgui.xiaoniuvwedfgfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ertyfghxiaoniutrghdfrty.bdtyertyh.R;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgbase.BaseNewCodeXiaoNiuKuaiFragment;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgbase.NewCodeXiaoNiuKuaiObserverManager;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.BaseNewCodeXiaoNiuKuaiModel;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.GoodsNewCodeXiaoNiuKuaiModel;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgui.NewCodeXiaoNiuKuaiGoodsDetailsNewCodeXiaoNiuKuaiActivity;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgui.xiaoniuvwedfgadapter.GoodsNewCodeXiaoNiuKuaiListAdapter;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiSharePreferencesUtil;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.StaticNewCodeXiaoNiuKuaiUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewCodeXiaoNiuKuaiGodsListNewCodeXiaoNiuKuaiFragment extends BaseNewCodeXiaoNiuKuaiFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsNewCodeXiaoNiuKuaiListAdapter goodsNewCodeXiaoNiuKuaiListAdapter;
    private Bundle bundle;
    private GoodsNewCodeXiaoNiuKuaiModel mGoodsNewCodeXiaoNiuKuaiModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_xiao_niu_kuai_sdf_efbdgh;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsNewCodeXiaoNiuKuaiModel));
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
        mobileType = NewCodeXiaoNiuKuaiSharePreferencesUtil.getInt("mobileType");
        phone = NewCodeXiaoNiuKuaiSharePreferencesUtil.getString("phone");
        mGoodsNewCodeXiaoNiuKuaiModel = null;
        Observable<BaseNewCodeXiaoNiuKuaiModel<List<GoodsNewCodeXiaoNiuKuaiModel>>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseNewCodeXiaoNiuKuaiModel<List<GoodsNewCodeXiaoNiuKuaiModel>>>() {
                    @Override
                    public void onSuccess(BaseNewCodeXiaoNiuKuaiModel<List<GoodsNewCodeXiaoNiuKuaiModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsNewCodeXiaoNiuKuaiModel = model.getData().get(0);
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
                        if (goodsNewCodeXiaoNiuKuaiListAdapter == null){
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

    private void productClick(GoodsNewCodeXiaoNiuKuaiModel goodsNewCodeXiaoNiuKuaiModel) {
        phone = NewCodeXiaoNiuKuaiSharePreferencesUtil.getString("phone");
        Observable<BaseNewCodeXiaoNiuKuaiModel> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsNewCodeXiaoNiuKuaiModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseNewCodeXiaoNiuKuaiModel>() {
                    @Override
                    public void onSuccess(BaseNewCodeXiaoNiuKuaiModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", goodsNewCodeXiaoNiuKuaiModel.getUrl());
                        StaticNewCodeXiaoNiuKuaiUtil.startActivity(getActivity(), NewCodeXiaoNiuKuaiGoodsDetailsNewCodeXiaoNiuKuaiActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsNewCodeXiaoNiuKuaiModel> mData){
        if (goodsNewCodeXiaoNiuKuaiListAdapter == null){
            goodsNewCodeXiaoNiuKuaiListAdapter = new GoodsNewCodeXiaoNiuKuaiListAdapter(R.layout.adapter_goods_list_layout_xiao_niu_kuai_sdf_efbdgh, mData);
            goodsNewCodeXiaoNiuKuaiListAdapter.setHasStableIds(true);
            goodsNewCodeXiaoNiuKuaiListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsNewCodeXiaoNiuKuaiListAdapter);
        } else {
            goodsNewCodeXiaoNiuKuaiListAdapter.replaceData(mData);
        }
    }
}
