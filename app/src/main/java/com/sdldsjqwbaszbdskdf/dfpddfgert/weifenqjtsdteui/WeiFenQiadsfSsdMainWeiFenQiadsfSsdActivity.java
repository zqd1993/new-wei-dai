package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.sdldsjqwbaszbdskdf.dfpddfgert.R;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteapi.RongjieSfFgdfRetrofitManager;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.BaseRongjieSfFgdfActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.RongjieSfFgdfObserverManager;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfBaseModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfConfigModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfLoginModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.weifenqjtsdteadapter.WeiFenQiadsfSsdBaseFragmentAdapter;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.weifenqjtsdtefragment.GodsListWeiFenQiadsfSsdFragment;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.weifenqjtsdtefragment.MainWeiFenQiadsfSsdFragment;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.weifenqjtsdtefragment.MineWeiFenQiadsfSsdFragment;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.SharePreferencesUtilWeiFenQiadsfSsd;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.ToastWeiFenQiadsfSsdUtil;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.WeiFenQiadsfSsdStatusBarUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeiFenQiadsfSsdMainWeiFenQiadsfSsdActivity extends BaseRongjieSfFgdfActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.nxfdyzery, R.drawable.nnsrtiraes, R.drawable.xfbrtdy};
    private int[] checkedIcon = {R.drawable.kyfofyjdfg, R.drawable.ergdfhsruy, R.drawable.xdfgdfy};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wei_fen_qi_dfger_vjkrt_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        WeiFenQiadsfSsdStatusBarUtil.setTransparent(this, false);
        WeiFenQiadsfSsdStatusBarUtil.setLightMode(this);
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
        mFragments.add(new MainWeiFenQiadsfSsdFragment());
        mFragments.add(new GodsListWeiFenQiadsfSsdFragment());
        mFragments.add(new MineWeiFenQiadsfSsdFragment());

        homeViewPager.setAdapter(new WeiFenQiadsfSsdBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    public void changePage(){
        tabLayout.setCurrentTab(1);
        homeViewPager.setCurrentItem(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastWeiFenQiadsfSsdUtil.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void logins() {
        phone = SharePreferencesUtilWeiFenQiadsfSsd.getString("phone");
        ip = SharePreferencesUtilWeiFenQiadsfSsd.getString("ip");
        Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<RongjieSfFgdfLoginModel> model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getConfig();
    }

    private void getConfig() {
        Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        RongjieSfFgdfConfigModel rongjieSfFgdfConfigModel = model.getData();
                        if (rongjieSfFgdfConfigModel != null) {
                            SharePreferencesUtilWeiFenQiadsfSsd.saveBool("NO_RECORD", !rongjieSfFgdfConfigModel.getVideoTape().equals("0"));
                            if (SharePreferencesUtilWeiFenQiadsfSsd.getBool("NO_RECORD")) {
                                getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                            }
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }
}