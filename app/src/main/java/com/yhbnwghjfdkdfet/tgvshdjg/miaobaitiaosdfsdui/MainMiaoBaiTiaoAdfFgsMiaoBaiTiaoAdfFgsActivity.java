package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.yhbnwghjfdkdfet.tgvshdjg.R;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdapi.MiaoBaiTiaoAdfFgsRetrofitManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.BaseMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.MiaoBaiTiaoAdfFgsObserverManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.MiaoBaiTiaoAdfFgsBaseModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.ConfigMiaoBaiTiaoAdfFgsModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.LoginMiaoBaiTiaoAdfFgsModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdadapter.BaseFragmentAdapterMiaoBaiTiaoAdfFgs;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdfragment.GodsListMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsFragment;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdfragment.MainMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsFragment;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdfragment.MineMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsFragment;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.SharePreferencesMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.MiaoBaiTiaoAdfFgsStatusBarUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.ToastMiaoBaiTiaoAdfFgsUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity extends BaseMiaoBaiTiaoAdfFgsActivity {

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
        return R.layout.activity_miao_bai_tiao_sdf_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        MiaoBaiTiaoAdfFgsStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsFragment());
        mFragments.add(new GodsListMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsFragment());
        mFragments.add(new MineMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsFragment());

        homeViewPager.setAdapter(new BaseFragmentAdapterMiaoBaiTiaoAdfFgs(getSupportFragmentManager(), getLifecycle(), mFragments));
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
                ToastMiaoBaiTiaoAdfFgsUtil.showShort("再按一次退出程序");
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
        phone = SharePreferencesMiaoBaiTiaoAdfFgsUtil.getString("phone");
        ip = SharePreferencesMiaoBaiTiaoAdfFgsUtil.getString("ip");
        Observable<MiaoBaiTiaoAdfFgsBaseModel<LoginMiaoBaiTiaoAdfFgsModel>> observable = MiaoBaiTiaoAdfFgsRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<MiaoBaiTiaoAdfFgsBaseModel<LoginMiaoBaiTiaoAdfFgsModel>>() {
                    @Override
                    public void onSuccess(MiaoBaiTiaoAdfFgsBaseModel<LoginMiaoBaiTiaoAdfFgsModel> model) {

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
        Observable<MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel>> observable = MiaoBaiTiaoAdfFgsRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel>>() {
                    @Override
                    public void onSuccess(MiaoBaiTiaoAdfFgsBaseModel<ConfigMiaoBaiTiaoAdfFgsModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigMiaoBaiTiaoAdfFgsModel configMiaoBaiTiaoAdfFgsModel = model.getData();
                        if (configMiaoBaiTiaoAdfFgsModel != null) {
                            SharePreferencesMiaoBaiTiaoAdfFgsUtil.saveBool("NO_RECORD", !configMiaoBaiTiaoAdfFgsModel.getVideoTape().equals("0"));
                            if (SharePreferencesMiaoBaiTiaoAdfFgsUtil.getBool("NO_RECORD")) {
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