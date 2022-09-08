package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqieffragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dfjsdndsuweq.sfdkdfmsz.R;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefapi.BaseTaoFenQiDfREvfRetrofitManager;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseBaseTaoFenQiDfREvfFragment;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseTaoFenQiDfREvfObserverManager;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfConfigModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.MineItemBaseTaoFenQiDfREvfModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.AppInfoTaoFenQiDfREvfActivityBase;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.CancellationTaoFenQiDfREvfActivityBase;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.LoginTaoFenQiDfREvfActivityBase;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.UserYsxyTaoFenQiDfREvfActivityBase;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqiefadapter.BaseTaoFenQiDfREvfMineItemAdapter;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.RemindBaseTaoFenQiDfREvfDialog;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfSharePreferencesUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.StaticBaseTaoFenQiDfREvfUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.ToastBaseTaoFenQiDfREvfUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineTaoFenQiDfREvfFragmentBase extends BaseBaseTaoFenQiDfREvfFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private BaseTaoFenQiDfREvfMineItemAdapter baseTaoFenQiDfREvfMineItemAdapter;
    private List<MineItemBaseTaoFenQiDfREvfModel> list;
    private int[] imgRes = {R.drawable.rhfcj, R.drawable.rtyjgh, R.drawable.wtxz,
            R.drawable.kydrtu, R.drawable.kotyus, R.drawable.whxfj};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindBaseTaoFenQiDfREvfDialog mRemindBaseTaoFenQiDfREvfDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tao_fen_qi_rtgr_vbd_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = BaseTaoFenQiDfREvfSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            MineItemBaseTaoFenQiDfREvfModel model = new MineItemBaseTaoFenQiDfREvfModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindBaseTaoFenQiDfREvfDialog = new RemindBaseTaoFenQiDfREvfDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindBaseTaoFenQiDfREvfDialog.setBtnClickListener(new RemindBaseTaoFenQiDfREvfDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindBaseTaoFenQiDfREvfDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindBaseTaoFenQiDfREvfDialog.dismiss();
                    BaseTaoFenQiDfREvfSharePreferencesUtil.saveString("phone", "");
                    StaticBaseTaoFenQiDfREvfUtil.startActivity(getActivity(), LoginTaoFenQiDfREvfActivityBase.class, null);
                    getActivity().finish();
                }
            });
            mRemindBaseTaoFenQiDfREvfDialog.show();
            mRemindBaseTaoFenQiDfREvfDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        baseTaoFenQiDfREvfMineItemAdapter =  new BaseTaoFenQiDfREvfMineItemAdapter(R.layout.adapter_mine_list_layout_tao_fen_qi_rtgr_vbd, list);
        baseTaoFenQiDfREvfMineItemAdapter.setHasStableIds(true);
        baseTaoFenQiDfREvfMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", BaseTaoFenQiDfREvfRetrofitManager.ZCXY);
                    StaticBaseTaoFenQiDfREvfUtil.startActivity(getActivity(), UserYsxyTaoFenQiDfREvfActivityBase.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", BaseTaoFenQiDfREvfRetrofitManager.YSXY);
                    StaticBaseTaoFenQiDfREvfUtil.startActivity(getActivity(), UserYsxyTaoFenQiDfREvfActivityBase.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticBaseTaoFenQiDfREvfUtil.startActivity(getActivity(), AppInfoTaoFenQiDfREvfActivityBase.class, null);
                    break;
                case 4:
                    mRemindBaseTaoFenQiDfREvfDialog = new RemindBaseTaoFenQiDfREvfDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindBaseTaoFenQiDfREvfDialog.setBtnClickListener(new RemindBaseTaoFenQiDfREvfDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastBaseTaoFenQiDfREvfUtil.showShort("开启成功");
                            mRemindBaseTaoFenQiDfREvfDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastBaseTaoFenQiDfREvfUtil.showShort("关闭成功");
                            mRemindBaseTaoFenQiDfREvfDialog.dismiss();
                        }
                    });
                    mRemindBaseTaoFenQiDfREvfDialog.show();
                    mRemindBaseTaoFenQiDfREvfDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    StaticBaseTaoFenQiDfREvfUtil.startActivity(getActivity(), CancellationTaoFenQiDfREvfActivityBase.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(baseTaoFenQiDfREvfMineItemAdapter);
    }

    private void getConfig() {
        Observable<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfConfigModel>> observable = BaseTaoFenQiDfREvfRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseTaoFenQiDfREvfObserverManager<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfConfigModel>>() {
                    @Override
                    public void onSuccess(BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseTaoFenQiDfREvfConfigModel baseTaoFenQiDfREvfConfigModel = model.getData();
                        if (baseTaoFenQiDfREvfConfigModel != null) {
                            BaseTaoFenQiDfREvfSharePreferencesUtil.saveString("APP_MAIL", baseTaoFenQiDfREvfConfigModel.getAppMail());
                            mRemindBaseTaoFenQiDfREvfDialog = new RemindBaseTaoFenQiDfREvfDialog(getActivity(), "温馨提示", baseTaoFenQiDfREvfConfigModel.getAppMail(), false);
                            mRemindBaseTaoFenQiDfREvfDialog.setBtnClickListener(new RemindBaseTaoFenQiDfREvfDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindBaseTaoFenQiDfREvfDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, baseTaoFenQiDfREvfConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastBaseTaoFenQiDfREvfUtil.showShort("复制成功");
                                    mRemindBaseTaoFenQiDfREvfDialog.dismiss();
                                }
                            });
                            mRemindBaseTaoFenQiDfREvfDialog.show();
                            mRemindBaseTaoFenQiDfREvfDialog.setBtnStr("取消", "复制");
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
