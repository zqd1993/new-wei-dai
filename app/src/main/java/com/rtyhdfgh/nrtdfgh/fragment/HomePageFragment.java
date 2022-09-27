package com.rtyhdfgh.nrtdfgh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rtyhdfgh.nrtdfgh.R;
import com.rtyhdfgh.nrtdfgh.activity.ShowGoogsInfoActivity;
import com.rtyhdfgh.nrtdfgh.adapter.GoodsInfoItemAdapter;
import com.rtyhdfgh.nrtdfgh.entity.BaseEntity;
import com.rtyhdfgh.nrtdfgh.entity.GoodsEntity;
import com.rtyhdfgh.nrtdfgh.http.MainApi;
import com.rtyhdfgh.nrtdfgh.util.CommonUtil;
import com.rtyhdfgh.nrtdfgh.util.MyPreferences;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class HomePageFragment extends RxFragment {

    @BindView(R.id.goods_list)
    RecyclerView goodsList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.no_data_ll)
    View noDataLl;
    @BindView(R.id.parent_cl)
    View parentCl;
    @BindView(R.id.tishi_fl)
    View tishiFl;

    public View rootView;
    protected LayoutInflater layoutInflater;
    private Bundle bundle;
    private GoodsEntity goodsEntity;
    private int mobileType, type;
    private String phone;
    private GoodsInfoItemAdapter goodsInfoItemAdapter;

    public static HomePageFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_page, null);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt("type");
        }
        if (type == 2){
            tishiFl.setVisibility(View.GONE);
        } else {
            tishiFl.setVisibility(View.VISIBLE);
        }
        refreshLayout.setOnRefreshListener(() -> getGoodsList());
        parentCl.setOnClickListener(v -> productClick(goodsEntity));
        noDataLl.setOnClickListener(v -> getGoodsList());
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = MyPreferences.getInt("mobileType");
        phone = MyPreferences.getString("phone");
        goodsEntity = null;
        MainApi.getRetrofitManager().getApiService().getGoodsList(mobileType, phone).enqueue(new Callback<BaseEntity<List<GoodsEntity>>>() {
            @Override
            public void onResponse(Call<BaseEntity<List<GoodsEntity>>> call, retrofit2.Response<BaseEntity<List<GoodsEntity>>> response) {
                if (response.body() == null){
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                    return;
                }
                List<GoodsEntity> entity = response.body().getData();
                if (entity != null && entity.size() > 0){
                    noDataLl.setVisibility(View.GONE);
                    goodsList.setVisibility(View.VISIBLE);
                    goodsEntity = entity.get(0);
                    goodsInfoItemAdapter = new GoodsInfoItemAdapter(getActivity(), entity);
                    goodsInfoItemAdapter.setHasStableIds(true);
                    goodsInfoItemAdapter.setOnItemClickListener(new GoodsInfoItemAdapter.OnItemClickListener() {
                        @Override
                        public void itemClicked(GoodsEntity goodsEntity) {
                            productClick(goodsEntity);
                        }
                    });
                    goodsList.setHasFixedSize(true);
                    goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    goodsList.setAdapter(goodsInfoItemAdapter);
                } else {
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<List<GoodsEntity>>> call, Throwable t) {

            }
        });
    }

    private void productClick(GoodsEntity goodsEntity){
        phone = MyPreferences.getString("phone");
        MainApi.getRetrofitManager().getApiService().productClick(goodsEntity.getId(), phone).enqueue(new Callback<BaseEntity>() {
            @Override
            public void onResponse(Call<BaseEntity> call, retrofit2.Response<BaseEntity> response) {
                bundle = new Bundle();
                bundle.putString("name", goodsEntity.getProductName());
                bundle.putString("url", goodsEntity.getUrl());
                CommonUtil.startActivity(getActivity(), ShowGoogsInfoActivity.class, bundle);
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                bundle = new Bundle();
                bundle.putString("name", goodsEntity.getProductName());
                bundle.putString("url", goodsEntity.getUrl());
                CommonUtil.startActivity(getActivity(), ShowGoogsInfoActivity.class, bundle);
            }
        });
    }

}
