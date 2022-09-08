package com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qingsongvyrnng.mrjgndsdg.R;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvapi.BaseQingSongShfjAFduRetrofitManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseQingSongShfjAFduFragment;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase.BaseQingSongShfjAFduObserverManager;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.BaseQingSongShfjAFduConfigModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.MineItemBaseQingSongShfjAFduModel;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.AppInfoQingSongShfjAFduActivityBase;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.CancellationQingSongShfjAFduActivityBase;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.LoginQingSongShfjAFduActivityBase;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.UserYsxyQingSongShfjAFduActivityBase;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvadapter.BaseQingSongShfjAFduMineItemAdapter;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.RemindBaseQingSongShfjAFduDialog;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.BaseQingSongShfjAFduSharePreferencesUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.StaticBaseQingSongShfjAFduUtil;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.ToastBaseQingSongShfjAFduUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineQingSongShfjAFduFragmentBase extends BaseQingSongShfjAFduFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private BaseQingSongShfjAFduMineItemAdapter baseQingSongShfjAFduMineItemAdapter;
    private List<MineItemBaseQingSongShfjAFduModel> list;
    private int[] imgRes = {R.drawable.rhfcj, R.drawable.rtyjgh, R.drawable.wtxz,
            R.drawable.kydrtu, R.drawable.kotyus, R.drawable.whxfj};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindBaseQingSongShfjAFduDialog mRemindBaseQingSongShfjAFduDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qing_song_sh_udj_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = BaseQingSongShfjAFduSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            MineItemBaseQingSongShfjAFduModel model = new MineItemBaseQingSongShfjAFduModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindBaseQingSongShfjAFduDialog = new RemindBaseQingSongShfjAFduDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindBaseQingSongShfjAFduDialog.setBtnClickListener(new RemindBaseQingSongShfjAFduDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindBaseQingSongShfjAFduDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindBaseQingSongShfjAFduDialog.dismiss();
                    BaseQingSongShfjAFduSharePreferencesUtil.saveString("phone", "");
                    StaticBaseQingSongShfjAFduUtil.startActivity(getActivity(), LoginQingSongShfjAFduActivityBase.class, null);
                    getActivity().finish();
                }
            });
            mRemindBaseQingSongShfjAFduDialog.show();
            mRemindBaseQingSongShfjAFduDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        baseQingSongShfjAFduMineItemAdapter =  new BaseQingSongShfjAFduMineItemAdapter(R.layout.adapter_mine_list_layout_qing_song_sh_udj, list);
        baseQingSongShfjAFduMineItemAdapter.setHasStableIds(true);
        baseQingSongShfjAFduMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", BaseQingSongShfjAFduRetrofitManager.ZCXY);
                    StaticBaseQingSongShfjAFduUtil.startActivity(getActivity(), UserYsxyQingSongShfjAFduActivityBase.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", BaseQingSongShfjAFduRetrofitManager.YSXY);
                    StaticBaseQingSongShfjAFduUtil.startActivity(getActivity(), UserYsxyQingSongShfjAFduActivityBase.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticBaseQingSongShfjAFduUtil.startActivity(getActivity(), AppInfoQingSongShfjAFduActivityBase.class, null);
                    break;
                case 4:
                    mRemindBaseQingSongShfjAFduDialog = new RemindBaseQingSongShfjAFduDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindBaseQingSongShfjAFduDialog.setBtnClickListener(new RemindBaseQingSongShfjAFduDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastBaseQingSongShfjAFduUtil.showShort("开启成功");
                            mRemindBaseQingSongShfjAFduDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastBaseQingSongShfjAFduUtil.showShort("关闭成功");
                            mRemindBaseQingSongShfjAFduDialog.dismiss();
                        }
                    });
                    mRemindBaseQingSongShfjAFduDialog.show();
                    mRemindBaseQingSongShfjAFduDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    StaticBaseQingSongShfjAFduUtil.startActivity(getActivity(), CancellationQingSongShfjAFduActivityBase.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(baseQingSongShfjAFduMineItemAdapter);
    }

    private void getConfig() {
        Observable<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel>> observable = BaseQingSongShfjAFduRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQingSongShfjAFduObserverManager<BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel>>() {
                    @Override
                    public void onSuccess(BaseQingSongShfjAFduModel<BaseQingSongShfjAFduConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQingSongShfjAFduConfigModel baseQingSongShfjAFduConfigModel = model.getData();
                        if (baseQingSongShfjAFduConfigModel != null) {
                            BaseQingSongShfjAFduSharePreferencesUtil.saveString("APP_MAIL", baseQingSongShfjAFduConfigModel.getAppMail());
                            mRemindBaseQingSongShfjAFduDialog = new RemindBaseQingSongShfjAFduDialog(getActivity(), "温馨提示", baseQingSongShfjAFduConfigModel.getAppMail(), false);
                            mRemindBaseQingSongShfjAFduDialog.setBtnClickListener(new RemindBaseQingSongShfjAFduDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindBaseQingSongShfjAFduDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, baseQingSongShfjAFduConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastBaseQingSongShfjAFduUtil.showShort("复制成功");
                                    mRemindBaseQingSongShfjAFduDialog.dismiss();
                                }
                            });
                            mRemindBaseQingSongShfjAFduDialog.show();
                            mRemindBaseQingSongShfjAFduDialog.setBtnStr("取消", "复制");
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
