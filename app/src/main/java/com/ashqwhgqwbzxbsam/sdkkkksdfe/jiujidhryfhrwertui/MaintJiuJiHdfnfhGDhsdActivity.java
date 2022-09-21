package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.R;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertapi.RetrofitJiuJiHdfnfhGDhsdManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.BaseJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.JiuJiHdfnfhGDhsdObserverManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.JiuJiHdfnfhGDhsdBaseModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.ConfigJiuJiHdfnfhGDhsdModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.LoginJiuJiHdfnfhGDhsdModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertadapter.JiuJiHdfnfhGDhsdBaseFragmentAdapter;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertfragment.GodsListJiuJiHdfnfhGDhsdFragment;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertfragment.MainJiuJiHdfnfhGDhsdFragment;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertfragment.MineJiuJiHdfnfhGDhsdFragment;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.ToastJiuJiHdfnfhGDhsdUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdSharePreferencesUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdStatusBarUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MaintJiuJiHdfnfhGDhsdActivity extends BaseJiuJiHdfnfhGDhsdActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.srty, R.drawable.tyvbn, R.drawable.lpyuk};
    private int[] checkedIcon = {R.drawable.zvbtdf, R.drawable.lpyuj, R.drawable.reyfgh};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_jiu_ji_fdher_reytjyh_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        JiuJiHdfnfhGDhsdStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainJiuJiHdfnfhGDhsdFragment());
        mFragments.add(new GodsListJiuJiHdfnfhGDhsdFragment());
        mFragments.add(new MineJiuJiHdfnfhGDhsdFragment());

        homeViewPager.setAdapter(new JiuJiHdfnfhGDhsdBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastJiuJiHdfnfhGDhsdUtil.showShort("再按一次退出程序");
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
        phone = JiuJiHdfnfhGDhsdSharePreferencesUtil.getString("phone");
        ip = JiuJiHdfnfhGDhsdSharePreferencesUtil.getString("ip");
        Observable<JiuJiHdfnfhGDhsdBaseModel<LoginJiuJiHdfnfhGDhsdModel>> observable = RetrofitJiuJiHdfnfhGDhsdManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JiuJiHdfnfhGDhsdObserverManager<JiuJiHdfnfhGDhsdBaseModel<LoginJiuJiHdfnfhGDhsdModel>>() {
                    @Override
                    public void onSuccess(JiuJiHdfnfhGDhsdBaseModel<LoginJiuJiHdfnfhGDhsdModel> model) {

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
        Observable<JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel>> observable = RetrofitJiuJiHdfnfhGDhsdManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JiuJiHdfnfhGDhsdObserverManager<JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel>>() {
                    @Override
                    public void onSuccess(JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigJiuJiHdfnfhGDhsdModel configJiuJiHdfnfhGDhsdModel = model.getData();
                        if (configJiuJiHdfnfhGDhsdModel != null) {
                            JiuJiHdfnfhGDhsdSharePreferencesUtil.saveBool("NO_RECORD", !configJiuJiHdfnfhGDhsdModel.getVideoTape().equals("0"));
                            if (JiuJiHdfnfhGDhsdSharePreferencesUtil.getBool("NO_RECORD")) {
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