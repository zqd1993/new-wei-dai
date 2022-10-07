package com.fghjtuytjuj.drtysghjertyh.page;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.fghjtuytjuj.drtysghjertyh.bean.BaseModel;
import com.fghjtuytjuj.drtysghjertyh.bean.ConfigBean;
import com.fghjtuytjuj.drtysghjertyh.bean.LoginBean;
import com.fghjtuytjuj.drtysghjertyh.common.SharePreferencesUtil;
import com.fghjtuytjuj.drtysghjertyh.common.StaticCommon;
import com.fghjtuytjuj.drtysghjertyh.common.StatusBarUtil;
import com.fghjtuytjuj.drtysghjertyh.net.NetApi;
import com.fjsdkqwj.pfdioewjnsd.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class WorkActivity extends RxAppCompatActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.xvbery, R.drawable.lpuyg, R.drawable.tyudd};
    private int[] checkedIcon = {R.drawable.utrfghdf, R.drawable.sfgwert, R.drawable.xcxfh};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        logins();
        homeViewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        customTabEntities = new ArrayList<>();
        homeViewPager.setUserInputEnabled(false);
        for (int i = 0; i < 3; i++) {
            int position = i;
            CustomTabEntity customTabEntity = new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return mTitles[position];
                }

                @Override
                public int getTabSelectedIcon() {
                    return checkedIcon[position];
                }

                @Override
                public int getTabUnselectedIcon() {
                    return uncheckedIcon[position];
                }
            };
            customTabEntities.add(customTabEntity);
        }
        tabLayout.setTabData(customTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                homeViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mFragments.add(WorkFragment.newInstance(1));
        mFragments.add(WorkFragment.newInstance(2));
        mFragments.add(new PersonalCenterFragment());

        homeViewPager.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getConfigValue();
    }

    private void logins(){
        phone = SharePreferencesUtil.getString("phone");
        ip = SharePreferencesUtil.getString("ip");
        NetApi.getNetApi().getNetInterface().logins(phone, ip).enqueue(new Callback<BaseModel<LoginBean>>() {
            @Override
            public void onResponse(Call<BaseModel<LoginBean>> call, retrofit2.Response<BaseModel<LoginBean>> response) {

            }

            @Override
            public void onFailure(Call<BaseModel<LoginBean>> call, Throwable t) {

            }
        });
    }

    private void getConfigValue(){
        NetApi.getNetApi().getNetInterface().getValue("VIDEOTAPE").enqueue(new Callback<BaseModel<ConfigBean>>() {
            @Override
            public void onResponse(Call<BaseModel<ConfigBean>> call, retrofit2.Response<BaseModel<ConfigBean>> response) {
                if (response.body() == null){
                    return;
                }
                if (response.body() != null) {
                    SharePreferencesUtil.saveBool("NO_RECORD", !response.body().getData().getVideoTape().equals("0"));
                    if (SharePreferencesUtil.getBool("NO_RECORD")) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ConfigBean>> call, Throwable t) {

            }
        });
    }
}
