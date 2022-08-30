package com.asvsdfer.bjirmndf.ui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.asvsdfer.bjirmndf.R;
import com.asvsdfer.bjirmndf.api.RetrofitManager;
import com.asvsdfer.bjirmndf.base.BaseActivity;
import com.asvsdfer.bjirmndf.base.ObserverManager;
import com.asvsdfer.bjirmndf.model.BaseModel;
import com.asvsdfer.bjirmndf.model.ConfigEntity;
import com.asvsdfer.bjirmndf.ui.adapter.BaseFragmentAdapter;
import com.asvsdfer.bjirmndf.ui.fragment.MainFragment;
import com.asvsdfer.bjirmndf.util.SharePreferencesUtil;
import com.asvsdfer.bjirmndf.util.StatusBarUtil;
import com.asvsdfer.bjirmndf.util.ToastUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.shouye2, R.drawable.jingxuan2, R.drawable.wode2};
    private int[] checkedIcon = {R.drawable.shouye1, R.drawable.jingxuan1, R.drawable.wode1};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusBarUtil.setTransparent(this, false);
        if (SharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
//        getP().login();
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
        mFragments.add(new MainFragment());
        mFragments.add(new MainFragment());
        mFragments.add(new MainFragment());

        homeViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}