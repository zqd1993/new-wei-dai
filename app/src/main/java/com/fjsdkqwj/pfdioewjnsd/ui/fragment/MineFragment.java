package com.fjsdkqwj.pfdioewjnsd.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.api.RetrofitManager;
import com.fjsdkqwj.pfdioewjnsd.base.BaseFragment;
import com.fjsdkqwj.pfdioewjnsd.base.ObserverManager;
import com.fjsdkqwj.pfdioewjnsd.model.BaseModel;
import com.fjsdkqwj.pfdioewjnsd.model.ConfigModel;
import com.fjsdkqwj.pfdioewjnsd.model.MineItemModel;
import com.fjsdkqwj.pfdioewjnsd.ui.AppInfoActivity;
import com.fjsdkqwj.pfdioewjnsd.ui.CancellationActivity;
import com.fjsdkqwj.pfdioewjnsd.ui.LoginActivity;
import com.fjsdkqwj.pfdioewjnsd.ui.UserYsxyActivity;
import com.fjsdkqwj.pfdioewjnsd.ui.adapter.MineItemAdapter;
import com.fjsdkqwj.pfdioewjnsd.util.RemindDialog;
import com.fjsdkqwj.pfdioewjnsd.util.SharePreferencesUtil;
import com.fjsdkqwj.pfdioewjnsd.util.StaticUtil;
import com.fjsdkqwj.pfdioewjnsd.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineFragment extends BaseFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private MineItemAdapter mineItemAdapter;
    private List<MineItemModel> list;
    private int[] imgRes = {R.drawable.dfgeryxch, R.drawable.fnsrtu, R.drawable.kfghx,
            R.drawable.bdtfujs, R.drawable.fdyurtyu, R.drawable.tyjghj};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindDialog mRemindDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = SharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            MineItemModel model = new MineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindDialog = new RemindDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindDialog.setBtnClickListener(new RemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindDialog.dismiss();
                    SharePreferencesUtil.saveString("phone", "");
                    StaticUtil.startActivity(getActivity(), LoginActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindDialog.show();
            mRemindDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        mineItemAdapter =  new MineItemAdapter(R.layout.adapter_mine_list_layout, list);
        mineItemAdapter.setHasStableIds(true);
        mineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RetrofitManager.ZCXY);
                    StaticUtil.startActivity(getActivity(), UserYsxyActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RetrofitManager.YSXY);
                    StaticUtil.startActivity(getActivity(), UserYsxyActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticUtil.startActivity(getActivity(), AppInfoActivity.class, null);
                    break;
                case 4:
                    mRemindDialog = new RemindDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindDialog.setBtnClickListener(new RemindDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastUtil.showShort("开启成功");
                            mRemindDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastUtil.showShort("关闭成功");
                            mRemindDialog.dismiss();
                        }
                    });
                    mRemindDialog.show();
                    mRemindDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    StaticUtil.startActivity(getActivity(), CancellationActivity.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemAdapter);
    }

    private void getConfig() {
        Observable<BaseModel<ConfigModel>> observable = RetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseModel<ConfigModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigModel configModel = model.getData();
                        if (configModel != null) {
                            SharePreferencesUtil.saveString("APP_MAIL", configModel.getAppMail());
                            mRemindDialog = new RemindDialog(getActivity(), "温馨提示", configModel.getAppMail(), false);
                            mRemindDialog.setBtnClickListener(new RemindDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastUtil.showShort("复制成功");
                                    mRemindDialog.dismiss();
                                }
                            });
                            mRemindDialog.show();
                            mRemindDialog.setBtnStr("取消", "复制");
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
