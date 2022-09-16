package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.R;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyapi.MeiJFenQiDdfgjRfdfgRetrofitManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.MeiJFenQiDdfgjRfdfgBaseFragment;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.MeiJFenQiDdfgjRfdfgObserverManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.BaseMeiJFenQiDdfgjRfdfgModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgConfigModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgMineItemModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity.AppInfoMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity.CancellationMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity.LoginMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity.UserYsxyMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyadapter.MeiJFenQiDdfgjRfdfgMineItemAdapter;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.RemindMeiJFenQiDdfgjRfdfgDialog;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgSharePreferencesUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.StaticMeiJFenQiDdfgjRfdfgUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.ToastMeiJFenQiDdfgjRfdfgUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineFragmentMeiJFenQiDdfgjRfdfg extends MeiJFenQiDdfgjRfdfgBaseFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private MeiJFenQiDdfgjRfdfgMineItemAdapter meiJFenQiDdfgjRfdfgMineItemAdapter;
    private List<MeiJFenQiDdfgjRfdfgMineItemModel> list;
    private int[] imgRes = {R.drawable.tubiao1, R.drawable.tubiao2, R.drawable.tubiao3,
            R.drawable.tubiao4, R.drawable.tubiao5, R.drawable.tubiao6};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindMeiJFenQiDdfgjRfdfgDialog mRemindMeiJFenQiDdfgjRfdfgDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mei_jie_sfgh_ewyfhg_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            MeiJFenQiDdfgjRfdfgMineItemModel model = new MeiJFenQiDdfgjRfdfgMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindMeiJFenQiDdfgjRfdfgDialog = new RemindMeiJFenQiDdfgjRfdfgDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindMeiJFenQiDdfgjRfdfgDialog.setBtnClickListener(new RemindMeiJFenQiDdfgjRfdfgDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindMeiJFenQiDdfgjRfdfgDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindMeiJFenQiDdfgjRfdfgDialog.dismiss();
                    MeiJFenQiDdfgjRfdfgSharePreferencesUtil.saveString("phone", "");
                    StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(getActivity(), LoginMeiJFenQiDdfgjRfdfgActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindMeiJFenQiDdfgjRfdfgDialog.show();
            mRemindMeiJFenQiDdfgjRfdfgDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        meiJFenQiDdfgjRfdfgMineItemAdapter =  new MeiJFenQiDdfgjRfdfgMineItemAdapter(R.layout.adapter_mine_list_layout_mei_jie_sfgh_ewyfhg, list);
        meiJFenQiDdfgjRfdfgMineItemAdapter.setHasStableIds(true);
        meiJFenQiDdfgjRfdfgMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", MeiJFenQiDdfgjRfdfgRetrofitManager.ZCXY);
                    StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(getActivity(), UserYsxyMeiJFenQiDdfgjRfdfgActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", MeiJFenQiDdfgjRfdfgRetrofitManager.YSXY);
                    StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(getActivity(), UserYsxyMeiJFenQiDdfgjRfdfgActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(getActivity(), AppInfoMeiJFenQiDdfgjRfdfgActivity.class, null);
                    break;
                case 4:
                    mRemindMeiJFenQiDdfgjRfdfgDialog = new RemindMeiJFenQiDdfgjRfdfgDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindMeiJFenQiDdfgjRfdfgDialog.setBtnClickListener(new RemindMeiJFenQiDdfgjRfdfgDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastMeiJFenQiDdfgjRfdfgUtil.showShort("开启成功");
                            mRemindMeiJFenQiDdfgjRfdfgDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastMeiJFenQiDdfgjRfdfgUtil.showShort("关闭成功");
                            mRemindMeiJFenQiDdfgjRfdfgDialog.dismiss();
                        }
                    });
                    mRemindMeiJFenQiDdfgjRfdfgDialog.show();
                    mRemindMeiJFenQiDdfgjRfdfgDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(getActivity(), CancellationMeiJFenQiDdfgjRfdfgActivity.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(meiJFenQiDdfgjRfdfgMineItemAdapter);
    }

    private void getConfig() {
        Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel>> observable = MeiJFenQiDdfgjRfdfgRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MeiJFenQiDdfgjRfdfgObserverManager<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel>>() {
                    @Override
                    public void onSuccess(BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        MeiJFenQiDdfgjRfdfgConfigModel meiJFenQiDdfgjRfdfgConfigModel = model.getData();
                        if (meiJFenQiDdfgjRfdfgConfigModel != null) {
                            MeiJFenQiDdfgjRfdfgSharePreferencesUtil.saveString("APP_MAIL", meiJFenQiDdfgjRfdfgConfigModel.getAppMail());
                            mRemindMeiJFenQiDdfgjRfdfgDialog = new RemindMeiJFenQiDdfgjRfdfgDialog(getActivity(), "温馨提示", meiJFenQiDdfgjRfdfgConfigModel.getAppMail(), false);
                            mRemindMeiJFenQiDdfgjRfdfgDialog.setBtnClickListener(new RemindMeiJFenQiDdfgjRfdfgDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindMeiJFenQiDdfgjRfdfgDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, meiJFenQiDdfgjRfdfgConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastMeiJFenQiDdfgjRfdfgUtil.showShort("复制成功");
                                    mRemindMeiJFenQiDdfgjRfdfgDialog.dismiss();
                                }
                            });
                            mRemindMeiJFenQiDdfgjRfdfgDialog.show();
                            mRemindMeiJFenQiDdfgjRfdfgDialog.setBtnStr("取消", "复制");
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
