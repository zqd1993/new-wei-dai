package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyapi.JzjqianHdhrtYhdRetrofitManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.BaseJzjqianHdhrtYhdFragment;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.JzjqianHdhrtYhdObserverManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.BaseJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.GoodsJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.GoodsDetailsJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyadapter.JzjqianHdhrtYhdGoodsListAdapter;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdSharePreferencesUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StaticJzjqianHdhrtYhdUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JzjqianHdhrtYhdGodsListFragment extends BaseJzjqianHdhrtYhdFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private JzjqianHdhrtYhdGoodsListAdapter jzjqianHdhrtYhdGoodsListAdapter;
    private Bundle bundle;
    private GoodsJzjqianHdhrtYhdModel mGoodsJzjqianHdhrtYhdModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jzjqian_sdhr_utryn_goods_list;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsJzjqianHdhrtYhdModel));
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
        mobileType = JzjqianHdhrtYhdSharePreferencesUtil.getInt("mobileType");
        phone = JzjqianHdhrtYhdSharePreferencesUtil.getString("phone");
        mGoodsJzjqianHdhrtYhdModel = null;
        Observable<BaseJzjqianHdhrtYhdModel<List<GoodsJzjqianHdhrtYhdModel>>> observable = JzjqianHdhrtYhdRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JzjqianHdhrtYhdObserverManager<BaseJzjqianHdhrtYhdModel<List<GoodsJzjqianHdhrtYhdModel>>>() {
                    @Override
                    public void onSuccess(BaseJzjqianHdhrtYhdModel<List<GoodsJzjqianHdhrtYhdModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsJzjqianHdhrtYhdModel = model.getData().get(0);
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
                        if (jzjqianHdhrtYhdGoodsListAdapter == null){
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

    private void productClick(GoodsJzjqianHdhrtYhdModel goodsJzjqianHdhrtYhdModel) {
        phone = JzjqianHdhrtYhdSharePreferencesUtil.getString("phone");
        Observable<BaseJzjqianHdhrtYhdModel> observable = JzjqianHdhrtYhdRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsJzjqianHdhrtYhdModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JzjqianHdhrtYhdObserverManager<BaseJzjqianHdhrtYhdModel>() {
                    @Override
                    public void onSuccess(BaseJzjqianHdhrtYhdModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsJzjqianHdhrtYhdModel.getProductName());
                        bundle.putString("url", goodsJzjqianHdhrtYhdModel.getUrl());
                        StaticJzjqianHdhrtYhdUtil.startActivity(getActivity(), GoodsDetailsJzjqianHdhrtYhdActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsJzjqianHdhrtYhdModel> mData){
        if (jzjqianHdhrtYhdGoodsListAdapter == null){
            jzjqianHdhrtYhdGoodsListAdapter = new JzjqianHdhrtYhdGoodsListAdapter(R.layout.adapter_goods_list_layout_jzjqian_sdhr_utryn, mData);
            jzjqianHdhrtYhdGoodsListAdapter.setHasStableIds(true);
            jzjqianHdhrtYhdGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(jzjqianHdhrtYhdGoodsListAdapter);
        } else {
            jzjqianHdhrtYhdGoodsListAdapter.replaceData(mData);
        }
    }
}
