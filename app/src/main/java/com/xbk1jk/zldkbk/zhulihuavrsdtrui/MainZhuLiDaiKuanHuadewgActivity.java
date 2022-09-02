package com.xbk1jk.zldkbk.zhulihuavrsdtrui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.xbk1jk.zldkbk.R;
import com.xbk1jk.zldkbk.zhulihuavrsdtrapi.ZhuLiDaiKuanHuadewgRetrofitManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.BaseZhuLiDaiKuanHuadewgFgsActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.MiaoBaiTiaoAdfFgsObserverManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ZhuLiDaiKuanHuadewgBaseModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ConfigZhuLiDaiKuanHuadewgModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.LoginZhuLiDaiKuanHuadewgModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtradapter.BaseFragmentAdapterZhuLiDaiKuanHuadewg;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtrfragment.GodsListZhuLiDaiKuanHuadewgFragment;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtrfragment.MainZhuLiDaiKuanHuadewgFragment;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtrfragment.MineZhuLiDaiKuanHuadewgFragment;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.SharePreferencesZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ToastZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ZhuLiDaiKuanHuadewgStatusBarUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainZhuLiDaiKuanHuadewgActivity extends BaseZhuLiDaiKuanHuadewgFgsActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.lfypytjs, R.drawable.zzdfhaeru, R.drawable.fgnxtry};
    private int[] checkedIcon = {R.drawable.xfgjtdyi, R.drawable.mghksd, R.drawable.wgdfxhn};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhu_li_dai_kuan_hua_setg_sert_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ZhuLiDaiKuanHuadewgStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainZhuLiDaiKuanHuadewgFragment());
        mFragments.add(new GodsListZhuLiDaiKuanHuadewgFragment());
        mFragments.add(new MineZhuLiDaiKuanHuadewgFragment());

        homeViewPager.setAdapter(new BaseFragmentAdapterZhuLiDaiKuanHuadewg(getSupportFragmentManager(), getLifecycle(), mFragments));
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
                ToastZhuLiDaiKuanHuadewgUtil.showShort("再按一次退出程序");
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
        phone = SharePreferencesZhuLiDaiKuanHuadewgUtil.getString("phone");
        ip = SharePreferencesZhuLiDaiKuanHuadewgUtil.getString("ip");
        Observable<ZhuLiDaiKuanHuadewgBaseModel<LoginZhuLiDaiKuanHuadewgModel>> observable = ZhuLiDaiKuanHuadewgRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<ZhuLiDaiKuanHuadewgBaseModel<LoginZhuLiDaiKuanHuadewgModel>>() {
                    @Override
                    public void onSuccess(ZhuLiDaiKuanHuadewgBaseModel<LoginZhuLiDaiKuanHuadewgModel> model) {

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
        Observable<ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel>> observable = ZhuLiDaiKuanHuadewgRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel>>() {
                    @Override
                    public void onSuccess(ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigZhuLiDaiKuanHuadewgModel configZhuLiDaiKuanHuadewgModel = model.getData();
                        if (configZhuLiDaiKuanHuadewgModel != null) {
                            SharePreferencesZhuLiDaiKuanHuadewgUtil.saveBool("NO_RECORD", !configZhuLiDaiKuanHuadewgModel.getVideoTape().equals("0"));
                            if (SharePreferencesZhuLiDaiKuanHuadewgUtil.getBool("NO_RECORD")) {
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