package com.dgjadsie.jkermsd.youbeihwahsndactivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndadapter.YouBeiHwHsajJsumBottomTabAdapter;
import com.dgjadsie.jkermsd.youbeihwahsndadapter.HomeYouBeiHwHsajJsumFragmentAdapter;
import com.dgjadsie.jkermsd.youbeihwahsndentity.BaseYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.ConfigYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndfragment.YouBeiHwHsajJsumHomePageFragment;
import com.dgjadsie.jkermsd.youbeihwahsndfragment.SetYouBeiHwHsajJsumFragment;
import com.dgjadsie.jkermsd.youbeihwahsndhttp.MainYouBeiHwHsajJsumApi;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.dgjadsie.jkermsd.youbeihwahsndutil.StatusBarYouBeiHwHsajJsumUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class YouBeiHwHsajJsumHomePageActivity extends RxAppCompatActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.daohang_rvy)
    RecyclerView daohangRvy;

    private List<TabEntity> tabModels;

    private YouBeiHwHsajJsumBottomTabAdapter youBeiHwHsajJsumBottomTabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_you_bei_he_dje_yrhr);
        ButterKnife.bind(this);
        StatusBarYouBeiHwHsajJsumUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabEntity tabEntity = new TabEntity();
        tabEntity.setIcon(R.drawable.bnsrtu);
        tabEntity.setSelectedIcon(R.drawable.hgnty);
        tabEntity.setName("首页");
        tabEntity.setChecked(true);
        TabEntity tabEntity1 = new TabEntity();
        tabEntity1.setIcon(R.drawable.xvbtr);
        tabEntity1.setSelectedIcon(R.drawable.lpukif);
        tabEntity1.setName("产品");
        tabEntity1.setChecked(false);
        TabEntity tabEntity2 = new TabEntity();
        tabEntity2.setIcon(R.drawable.rtsd);
        tabEntity2.setSelectedIcon(R.drawable.cvhk);
        tabEntity2.setName("我的");
        tabEntity2.setChecked(false);
        tabModels.add(tabEntity);
        tabModels.add(tabEntity1);
        tabModels.add(tabEntity2);
        initAdapter(tabModels);
        fragments.add(YouBeiHwHsajJsumHomePageFragment.newInstance(1));
        fragments.add(YouBeiHwHsajJsumHomePageFragment.newInstance(2));
        fragments.add(new SetYouBeiHwHsajJsumFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new HomeYouBeiHwHsajJsumFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(YouBeiHwHsajJsumHomePageActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initAdapter(List<TabEntity> mList){
        youBeiHwHsajJsumBottomTabAdapter = new YouBeiHwHsajJsumBottomTabAdapter(this, mList);
        youBeiHwHsajJsumBottomTabAdapter.setHasStableIds(true);
        youBeiHwHsajJsumBottomTabAdapter.setOnItemClickListener(new YouBeiHwHsajJsumBottomTabAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                mainViewPager.setCurrentItem(position, false);
            }
        });
        daohangRvy.setLayoutManager(new GridLayoutManager(this, 3));
        daohangRvy.setHasFixedSize(true);
        daohangRvy.setAdapter(youBeiHwHsajJsumBottomTabAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getConfigValue();
    }

    private void getConfigValue(){
        MainYouBeiHwHsajJsumApi.getRetrofitManager().getApiService().getValue("VIDEOTAPE").enqueue(new Callback<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>>() {
            @Override
            public void onResponse(Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> call, retrofit2.Response<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> response) {
                if (response.body() == null){
                    return;
                }
                if (response.body() != null) {
                    MyYouBeiHwHsajJsumPreferences.saveBool("NO_RECORD", !response.body().getData().getVideoTape().equals("0"));
                    if (MyYouBeiHwHsajJsumPreferences.getBool("NO_RECORD")) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> call, Throwable t) {

            }
        });
    }

    public class TabEntity{
        private int icon;

        private int selectedIcon;

        private String name;

        private boolean isChecked;

        public int getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(int selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
