package com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.BaseNewCodeXiaoNiuKuaiFragment;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.NewCodeXiaoNiuKuaiObserverManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.BaseYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.GoodsYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.YouJieSDjdfiGoodsDetailsActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhadapter.GoodsYouJieSDjdfiListAdapter;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiSharePreferencesUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.StaticYouJieSDjdfiUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class YouJieSDjdfiGodsListFragment extends BaseNewCodeXiaoNiuKuaiFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsYouJieSDjdfiListAdapter goodsYouJieSDjdfiListAdapter;
    private Bundle bundle;
    private GoodsYouJieSDjdfiModel mGoodsYouJieSDjdfiModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_you_jie_iejbvr;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsYouJieSDjdfiModel));
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
        mobileType = YouJieSDjdfiSharePreferencesUtil.getInt("mobileType");
        phone = YouJieSDjdfiSharePreferencesUtil.getString("phone");
        mGoodsYouJieSDjdfiModel = null;
        Observable<BaseYouJieSDjdfiModel<List<GoodsYouJieSDjdfiModel>>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseYouJieSDjdfiModel<List<GoodsYouJieSDjdfiModel>>>() {
                    @Override
                    public void onSuccess(BaseYouJieSDjdfiModel<List<GoodsYouJieSDjdfiModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsYouJieSDjdfiModel = model.getData().get(0);
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
                        if (goodsYouJieSDjdfiListAdapter == null){
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

    private void productClick(GoodsYouJieSDjdfiModel goodsYouJieSDjdfiModel) {
        phone = YouJieSDjdfiSharePreferencesUtil.getString("phone");
        Observable<BaseYouJieSDjdfiModel> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsYouJieSDjdfiModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseYouJieSDjdfiModel>() {
                    @Override
                    public void onSuccess(BaseYouJieSDjdfiModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", goodsYouJieSDjdfiModel.getUrl());
                        StaticYouJieSDjdfiUtil.startActivity(getActivity(), YouJieSDjdfiGoodsDetailsActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsYouJieSDjdfiModel> mData){
        if (goodsYouJieSDjdfiListAdapter == null){
            goodsYouJieSDjdfiListAdapter = new GoodsYouJieSDjdfiListAdapter(R.layout.adapter_goods_list_layout_you_jie_iejbvr, mData);
            goodsYouJieSDjdfiListAdapter.setHasStableIds(true);
            goodsYouJieSDjdfiListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsYouJieSDjdfiListAdapter);
        } else {
            goodsYouJieSDjdfiListAdapter.replaceData(mData);
        }
    }
}
