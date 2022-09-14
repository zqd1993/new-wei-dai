package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsapi.RetrofitWuYouJieShdfnFjfuirManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.BaseWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.WuYouJieShdfnFjfuirObserverManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.WuYouJieShdfnFjfuirBaseModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.ConfigWuYouJieShdfnFjfuirModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.LoginWuYouJieShdfnFjfuirModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsadapter.WuYouJieShdfnFjfuirBaseFragmentAdapter;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsfragment.GodsListWuYouJieShdfnFjfuirFragment;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsfragment.MainWuYouJieShdfnFjfuirFragment;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsfragment.MineWuYouJieShdfnFjfuirFragment;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.ToastWuYouJieShdfnFjfuirUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirSharePreferencesUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirStatusBarUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MaintWuYouJieShdfnFjfuirActivity extends BaseWuYouJieShdfnFjfuirActivity {

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
        return R.layout.activity_wu_you_jie_jdf_eryj_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        WuYouJieShdfnFjfuirStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainWuYouJieShdfnFjfuirFragment());
        mFragments.add(new GodsListWuYouJieShdfnFjfuirFragment());
        mFragments.add(new MineWuYouJieShdfnFjfuirFragment());

        homeViewPager.setAdapter(new WuYouJieShdfnFjfuirBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastWuYouJieShdfnFjfuirUtil.showShort("再按一次退出程序");
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
        phone = WuYouJieShdfnFjfuirSharePreferencesUtil.getString("phone");
        ip = WuYouJieShdfnFjfuirSharePreferencesUtil.getString("ip");
        Observable<WuYouJieShdfnFjfuirBaseModel<LoginWuYouJieShdfnFjfuirModel>> observable = RetrofitWuYouJieShdfnFjfuirManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYouJieShdfnFjfuirObserverManager<WuYouJieShdfnFjfuirBaseModel<LoginWuYouJieShdfnFjfuirModel>>() {
                    @Override
                    public void onSuccess(WuYouJieShdfnFjfuirBaseModel<LoginWuYouJieShdfnFjfuirModel> model) {

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
        Observable<WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel>> observable = RetrofitWuYouJieShdfnFjfuirManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYouJieShdfnFjfuirObserverManager<WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel>>() {
                    @Override
                    public void onSuccess(WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigWuYouJieShdfnFjfuirModel configWuYouJieShdfnFjfuirModel = model.getData();
                        if (configWuYouJieShdfnFjfuirModel != null) {
                            WuYouJieShdfnFjfuirSharePreferencesUtil.saveBool("NO_RECORD", !configWuYouJieShdfnFjfuirModel.getVideoTape().equals("0"));
                            if (WuYouJieShdfnFjfuirSharePreferencesUtil.getBool("NO_RECORD")) {
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