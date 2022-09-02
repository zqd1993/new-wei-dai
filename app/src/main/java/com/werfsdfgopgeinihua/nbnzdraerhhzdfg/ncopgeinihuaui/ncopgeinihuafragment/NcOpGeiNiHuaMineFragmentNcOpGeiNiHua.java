package com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.ncopgeinihuafragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.R;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaapi.NcOpGeiNiHuaRetrofitManager;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuabase.NcOpGeiNiHuaBaseFragment;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuabase.ObserverManagerNcOpGeiNiHua;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.BaseNcOpGeiNiHuaModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.NcOpGeiNiHuaConfigModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.MineItemNcOpGeiNiHuaModel;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.NcOpGeiNiHuaAppInfoNcOpGeiNiHuaActivity;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.NcOpGeiNiHuaCancellationNcOpGeiNiHuaActivity;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.NcOpGeiNiHuaUserYsxyNcOpGeiNiHuaActivity;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.ncopgeinihuaadapter.NcOpGeiNiHuaMineItemAdapter;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaRemindDialog;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaSharePreferencesUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaStaticUtil;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuautil.NcOpGeiNiHuaToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NcOpGeiNiHuaMineFragmentNcOpGeiNiHua extends NcOpGeiNiHuaBaseFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private NcOpGeiNiHuaMineItemAdapter ncOpGeiNiHuaMineItemAdapter;
    private List<MineItemNcOpGeiNiHuaModel> list;
    private int[] imgRes = {R.drawable.tubiao1, R.drawable.tubiao2, R.drawable.tubiao3,
            R.drawable.tubiao4, R.drawable.tubiao5, R.drawable.tubiao6};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private NcOpGeiNiHuaRemindDialog mNcOpGeiNiHuaRemindDialog;
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
        mobileStr = NcOpGeiNiHuaSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            MineItemNcOpGeiNiHuaModel model = new MineItemNcOpGeiNiHuaModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mNcOpGeiNiHuaRemindDialog = new NcOpGeiNiHuaRemindDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mNcOpGeiNiHuaRemindDialog.setBtnClickListener(new NcOpGeiNiHuaRemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mNcOpGeiNiHuaRemindDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mNcOpGeiNiHuaRemindDialog.dismiss();
                    NcOpGeiNiHuaSharePreferencesUtil.saveString("phone", "");
                    NcOpGeiNiHuaStaticUtil.startActivity(getActivity(), NcOpGeiNiHuaLoginNcOpGeiNiHuaActivity.class, null);
                    getActivity().finish();
                }
            });
            mNcOpGeiNiHuaRemindDialog.show();
            mNcOpGeiNiHuaRemindDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        ncOpGeiNiHuaMineItemAdapter =  new NcOpGeiNiHuaMineItemAdapter(R.layout.adapter_mine_list_layout, list);
        ncOpGeiNiHuaMineItemAdapter.setHasStableIds(true);
        ncOpGeiNiHuaMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", NcOpGeiNiHuaRetrofitManager.ZCXY);
                    NcOpGeiNiHuaStaticUtil.startActivity(getActivity(), NcOpGeiNiHuaUserYsxyNcOpGeiNiHuaActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", NcOpGeiNiHuaRetrofitManager.YSXY);
                    NcOpGeiNiHuaStaticUtil.startActivity(getActivity(), NcOpGeiNiHuaUserYsxyNcOpGeiNiHuaActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    NcOpGeiNiHuaStaticUtil.startActivity(getActivity(), NcOpGeiNiHuaAppInfoNcOpGeiNiHuaActivity.class, null);
                    break;
                case 4:
                    mNcOpGeiNiHuaRemindDialog = new NcOpGeiNiHuaRemindDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mNcOpGeiNiHuaRemindDialog.setBtnClickListener(new NcOpGeiNiHuaRemindDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            NcOpGeiNiHuaToastUtil.showShort("开启成功");
                            mNcOpGeiNiHuaRemindDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            NcOpGeiNiHuaToastUtil.showShort("关闭成功");
                            mNcOpGeiNiHuaRemindDialog.dismiss();
                        }
                    });
                    mNcOpGeiNiHuaRemindDialog.show();
                    mNcOpGeiNiHuaRemindDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    NcOpGeiNiHuaStaticUtil.startActivity(getActivity(), NcOpGeiNiHuaCancellationNcOpGeiNiHuaActivity.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(ncOpGeiNiHuaMineItemAdapter);
    }

    private void getConfig() {
        Observable<BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel>> observable = NcOpGeiNiHuaRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManagerNcOpGeiNiHua<BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel>>() {
                    @Override
                    public void onSuccess(BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        NcOpGeiNiHuaConfigModel ncOpGeiNiHuaConfigModel = model.getData();
                        if (ncOpGeiNiHuaConfigModel != null) {
                            NcOpGeiNiHuaSharePreferencesUtil.saveString("APP_MAIL", ncOpGeiNiHuaConfigModel.getAppMail());
                            mNcOpGeiNiHuaRemindDialog = new NcOpGeiNiHuaRemindDialog(getActivity(), "温馨提示", ncOpGeiNiHuaConfigModel.getAppMail(), false);
                            mNcOpGeiNiHuaRemindDialog.setBtnClickListener(new NcOpGeiNiHuaRemindDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mNcOpGeiNiHuaRemindDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, ncOpGeiNiHuaConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    NcOpGeiNiHuaToastUtil.showShort("复制成功");
                                    mNcOpGeiNiHuaRemindDialog.dismiss();
                                }
                            });
                            mNcOpGeiNiHuaRemindDialog.show();
                            mNcOpGeiNiHuaRemindDialog.setBtnStr("取消", "复制");
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
