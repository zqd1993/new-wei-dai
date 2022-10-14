package com.dgjadsie.jkermsd.youbeihwahsndfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndactivity.YouBeiHwHsajJsumShowGoogsInfoActivity;
import com.dgjadsie.jkermsd.youbeihwahsndadapter.GoodsInfoItemYouBeiHwHsajJsumAdapter;
import com.dgjadsie.jkermsd.youbeihwahsndentity.BaseYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.YouBeiHwHsajJsumGoodsEntity;
import com.dgjadsie.jkermsd.youbeihwahsndhttp.MainYouBeiHwHsajJsumApi;
import com.dgjadsie.jkermsd.youbeihwahsndutil.CommonYouBeiHwHsajJsumUtil;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class YouBeiHwHsajJsumHomePageFragment extends RxFragment {

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
    private YouBeiHwHsajJsumGoodsEntity youBeiHwHsajJsumGoodsEntity;
    private int mobileType, type;
    private String phone;
    private GoodsInfoItemYouBeiHwHsajJsumAdapter goodsInfoItemYouBeiHwHsajJsumAdapter;

    public static YouBeiHwHsajJsumHomePageFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        YouBeiHwHsajJsumHomePageFragment fragment = new YouBeiHwHsajJsumHomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_page_you_bei_he_dje_yrhr, null);
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
        if (type == 2) {
            tishiFl.setVisibility(View.GONE);
        } else {
            tishiFl.setVisibility(View.VISIBLE);
        }
        refreshLayout.setOnRefreshListener(() -> getGoodsList());
        parentCl.setOnClickListener(v -> productClick(youBeiHwHsajJsumGoodsEntity));
        noDataLl.setOnClickListener(v -> getGoodsList());
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = MyYouBeiHwHsajJsumPreferences.getInt("mobileType");
        phone = MyYouBeiHwHsajJsumPreferences.getString("phone");
        youBeiHwHsajJsumGoodsEntity = null;
        MainYouBeiHwHsajJsumApi.getRetrofitManager().getApiService().getGoodsList(mobileType, phone).enqueue(new Callback<BaseYouBeiHwHsajJsumEntity<List<YouBeiHwHsajJsumGoodsEntity>>>() {
            @Override
            public void onResponse(Call<BaseYouBeiHwHsajJsumEntity<List<YouBeiHwHsajJsumGoodsEntity>>> call, retrofit2.Response<BaseYouBeiHwHsajJsumEntity<List<YouBeiHwHsajJsumGoodsEntity>>> response) {
                if (response.body() == null) {
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                    return;
                }
                List<YouBeiHwHsajJsumGoodsEntity> entity = response.body().getData();
                if (entity != null && entity.size() > 0) {
                    noDataLl.setVisibility(View.GONE);
                    goodsList.setVisibility(View.VISIBLE);
                    youBeiHwHsajJsumGoodsEntity = entity.get(0);
                    goodsInfoItemYouBeiHwHsajJsumAdapter = new GoodsInfoItemYouBeiHwHsajJsumAdapter(getActivity(), entity);
                    goodsInfoItemYouBeiHwHsajJsumAdapter.setHasStableIds(true);
                    goodsInfoItemYouBeiHwHsajJsumAdapter.setOnItemClickListener(new GoodsInfoItemYouBeiHwHsajJsumAdapter.OnItemClickListener() {
                        @Override
                        public void itemClicked(YouBeiHwHsajJsumGoodsEntity youBeiHwHsajJsumGoodsEntity) {
                            productClick(youBeiHwHsajJsumGoodsEntity);
                        }
                    });
                    goodsList.setHasFixedSize(true);
                    goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    goodsList.setAdapter(goodsInfoItemYouBeiHwHsajJsumAdapter);
                } else {
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BaseYouBeiHwHsajJsumEntity<List<YouBeiHwHsajJsumGoodsEntity>>> call, Throwable t) {

            }
        });
    }

    private void productClick(YouBeiHwHsajJsumGoodsEntity youBeiHwHsajJsumGoodsEntity) {
        if (youBeiHwHsajJsumGoodsEntity == null) {
            return;
        }
        phone = MyYouBeiHwHsajJsumPreferences.getString("phone");
        MainYouBeiHwHsajJsumApi.getRetrofitManager().getApiService().productClick(youBeiHwHsajJsumGoodsEntity.getId(), phone).enqueue(new Callback<BaseYouBeiHwHsajJsumEntity>() {
            @Override
            public void onResponse(Call<BaseYouBeiHwHsajJsumEntity> call, retrofit2.Response<BaseYouBeiHwHsajJsumEntity> response) {
                bundle = new Bundle();
                bundle.putString("name", youBeiHwHsajJsumGoodsEntity.getProductName());
                bundle.putString("url", youBeiHwHsajJsumGoodsEntity.getUrl());
                CommonYouBeiHwHsajJsumUtil.startActivity(getActivity(), YouBeiHwHsajJsumShowGoogsInfoActivity.class, bundle);
            }

            @Override
            public void onFailure(Call<BaseYouBeiHwHsajJsumEntity> call, Throwable t) {
                bundle = new Bundle();
                bundle.putString("name", youBeiHwHsajJsumGoodsEntity.getProductName());
                bundle.putString("url", youBeiHwHsajJsumGoodsEntity.getUrl());
                CommonYouBeiHwHsajJsumUtil.startActivity(getActivity(), YouBeiHwHsajJsumShowGoogsInfoActivity.class, bundle);
            }
        });
    }

}
