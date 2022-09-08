package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.R;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvapi.BaseQuHuaDjdfuVdhrRetrofitManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseQuHuaDjdfuVdhrFragment;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseQuHuaDjdfuVdhrObserverManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrConfigModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.MineItemBaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.AppInfoQuHuaDjdfuVdhrActivityBase;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.CancellationQuHuaDjdfuVdhrActivityBase;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.LoginQuHuaDjdfuVdhrActivityBase;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.UserYsxyQuHuaDjdfuVdhrActivityBase;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvadapter.BaseQuHuaDjdfuVdhrMineItemAdapter;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.RemindBaseQuHuaDjdfuVdhrDialog;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrSharePreferencesUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.StaticBaseQuHuaDjdfuVdhrUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.ToastBaseQuHuaDjdfuVdhrUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineQuHuaDjdfuVdhrFragmentBase extends BaseQuHuaDjdfuVdhrFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private BaseQuHuaDjdfuVdhrMineItemAdapter baseQuHuaDjdfuVdhrMineItemAdapter;
    private List<MineItemBaseQuHuaDjdfuVdhrModel> list;
    private int[] imgRes = {R.drawable.rhfcj, R.drawable.rtyjgh, R.drawable.wtxz,
            R.drawable.kydrtu, R.drawable.whxfj};
    private String[] tvRes = {"注册协议", "隐私协议", "联系客服", "关于我们", "注销账户"};
    private Bundle bundle;
    private RemindBaseQuHuaDjdfuVdhrDialog mRemindBaseQuHuaDjdfuVdhrDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qu_hua_hua_erf_engh_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = BaseQuHuaDjdfuVdhrSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 5; i++) {
            MineItemBaseQuHuaDjdfuVdhrModel model = new MineItemBaseQuHuaDjdfuVdhrModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindBaseQuHuaDjdfuVdhrDialog = new RemindBaseQuHuaDjdfuVdhrDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindBaseQuHuaDjdfuVdhrDialog.setBtnClickListener(new RemindBaseQuHuaDjdfuVdhrDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindBaseQuHuaDjdfuVdhrDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindBaseQuHuaDjdfuVdhrDialog.dismiss();
                    BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveString("phone", "");
                    StaticBaseQuHuaDjdfuVdhrUtil.startActivity(getActivity(), LoginQuHuaDjdfuVdhrActivityBase.class, null);
                    getActivity().finish();
                }
            });
            mRemindBaseQuHuaDjdfuVdhrDialog.show();
            mRemindBaseQuHuaDjdfuVdhrDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        baseQuHuaDjdfuVdhrMineItemAdapter =  new BaseQuHuaDjdfuVdhrMineItemAdapter(R.layout.adapter_mine_list_layout_qu_hua_hua_erf_engh, list);
        baseQuHuaDjdfuVdhrMineItemAdapter.setHasStableIds(true);
        baseQuHuaDjdfuVdhrMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", BaseQuHuaDjdfuVdhrRetrofitManager.ZCXY);
                    StaticBaseQuHuaDjdfuVdhrUtil.startActivity(getActivity(), UserYsxyQuHuaDjdfuVdhrActivityBase.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", BaseQuHuaDjdfuVdhrRetrofitManager.YSXY);
                    StaticBaseQuHuaDjdfuVdhrUtil.startActivity(getActivity(), UserYsxyQuHuaDjdfuVdhrActivityBase.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticBaseQuHuaDjdfuVdhrUtil.startActivity(getActivity(), AppInfoQuHuaDjdfuVdhrActivityBase.class, null);
                    break;
                case 4:
                    StaticBaseQuHuaDjdfuVdhrUtil.startActivity(getActivity(), CancellationQuHuaDjdfuVdhrActivityBase.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(baseQuHuaDjdfuVdhrMineItemAdapter);
    }

    private void getConfig() {
        Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQuHuaDjdfuVdhrConfigModel baseQuHuaDjdfuVdhrConfigModel = model.getData();
                        if (baseQuHuaDjdfuVdhrConfigModel != null) {
                            BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveString("APP_MAIL", baseQuHuaDjdfuVdhrConfigModel.getAppMail());
                            mRemindBaseQuHuaDjdfuVdhrDialog = new RemindBaseQuHuaDjdfuVdhrDialog(getActivity(), "温馨提示", baseQuHuaDjdfuVdhrConfigModel.getAppMail(), false);
                            mRemindBaseQuHuaDjdfuVdhrDialog.setBtnClickListener(new RemindBaseQuHuaDjdfuVdhrDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindBaseQuHuaDjdfuVdhrDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, baseQuHuaDjdfuVdhrConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastBaseQuHuaDjdfuVdhrUtil.showShort("复制成功");
                                    mRemindBaseQuHuaDjdfuVdhrDialog.dismiss();
                                }
                            });
                            mRemindBaseQuHuaDjdfuVdhrDialog.show();
                            mRemindBaseQuHuaDjdfuVdhrDialog.setBtnStr("取消", "复制");
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
