package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyapi.JzjqianHdhrtYhdRetrofitManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.BaseJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.JzjqianHdhrtYhdObserverManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.BaseJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.JzjqianHdhrtYhdConfigModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.LoginJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyadapter.JzjqianHdhrtYhdBaseFragmentAdapter;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyfragment.JzjqianHdhrtYhdGodsListFragment;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyfragment.JzjqianHdhrtYhdMainFragment;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyfragment.MineJzjqianHdhrtYhdFragment;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdSharePreferencesUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StatusJzjqianHdhrtYhdBarUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.ToastJzjqianHdhrtYhdUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainJzjqianHdhrtYhdActivity extends BaseJzjqianHdhrtYhdActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.qwetrgxf, R.drawable.ghfgn, R.drawable.zcvzbdry};
    private int[] checkedIcon = {R.drawable.rghjcfg, R.drawable.kltfyufgj, R.drawable.hjgn};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_jzjqian_sdhr_utryn_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusJzjqianHdhrtYhdBarUtil.setTransparent(this, false);
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
        mFragments.add(new JzjqianHdhrtYhdMainFragment());
        mFragments.add(new JzjqianHdhrtYhdGodsListFragment());
        mFragments.add(new MineJzjqianHdhrtYhdFragment());

        homeViewPager.setAdapter(new JzjqianHdhrtYhdBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastJzjqianHdhrtYhdUtil.showShort("再按一次退出程序");
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
        phone = JzjqianHdhrtYhdSharePreferencesUtil.getString("phone");
        ip = JzjqianHdhrtYhdSharePreferencesUtil.getString("ip");
        Observable<BaseJzjqianHdhrtYhdModel<LoginJzjqianHdhrtYhdModel>> observable = JzjqianHdhrtYhdRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JzjqianHdhrtYhdObserverManager<BaseJzjqianHdhrtYhdModel<LoginJzjqianHdhrtYhdModel>>() {
                    @Override
                    public void onSuccess(BaseJzjqianHdhrtYhdModel<LoginJzjqianHdhrtYhdModel> model) {

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
        Observable<BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel>> observable = JzjqianHdhrtYhdRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JzjqianHdhrtYhdObserverManager<BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel>>() {
                    @Override
                    public void onSuccess(BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        JzjqianHdhrtYhdConfigModel jzjqianHdhrtYhdConfigModel = model.getData();
                        if (jzjqianHdhrtYhdConfigModel != null) {
                            JzjqianHdhrtYhdSharePreferencesUtil.saveBool("NO_RECORD", !jzjqianHdhrtYhdConfigModel.getVideoTape().equals("0"));
                            if (JzjqianHdhrtYhdSharePreferencesUtil.getBool("NO_RECORD")) {
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