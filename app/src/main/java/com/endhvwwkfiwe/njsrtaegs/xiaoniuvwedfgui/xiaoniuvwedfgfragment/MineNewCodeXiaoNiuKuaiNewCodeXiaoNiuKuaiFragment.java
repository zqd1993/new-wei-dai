package com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.xiaoniuvwedfgfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endhvwwkfiwe.njsrtaegs.R;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgbase.BaseNewCodeXiaoNiuKuaiFragment;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgbase.NewCodeXiaoNiuKuaiObserverManager;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgmodel.BaseNewCodeXiaoNiuKuaiModel;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgmodel.ConfigNewCodeXiaoNiuKuaiModel;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgmodel.NewCodeXiaoNiuKuaiMineItemModel;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.NewCodeXiaoNiuKuaiAppInfoNewCodeXiaoNiuKuaiActivity;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.CancellationNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.LoginNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.NewCodeXiaoNiuKuaiUserYsxyNewCodeXiaoNiuKuaiActivity;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.xiaoniuvwedfgadapter.MineNewCodeXiaoNiuKuaiItemAdapter;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.RemindNewCodeXiaoNiuKuaiDialog;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiSharePreferencesUtil;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.StaticNewCodeXiaoNiuKuaiUtil;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.ToastNewCodeXiaoNiuKuaiUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiFragment extends BaseNewCodeXiaoNiuKuaiFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private MineNewCodeXiaoNiuKuaiItemAdapter mineNewCodeXiaoNiuKuaiItemAdapter;
    private List<NewCodeXiaoNiuKuaiMineItemModel> list;
    private int[] imgRes = {R.drawable.dfgeryxch, R.drawable.fnsrtu, R.drawable.kfghx,
            R.drawable.bdtfujs, R.drawable.fdyurtyu, R.drawable.tyjghj};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindNewCodeXiaoNiuKuaiDialog mRemindNewCodeXiaoNiuKuaiDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xiao_niu_kuai_sdf_efbdgh_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = NewCodeXiaoNiuKuaiSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            NewCodeXiaoNiuKuaiMineItemModel model = new NewCodeXiaoNiuKuaiMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindNewCodeXiaoNiuKuaiDialog = new RemindNewCodeXiaoNiuKuaiDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindNewCodeXiaoNiuKuaiDialog.setBtnClickListener(new RemindNewCodeXiaoNiuKuaiDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindNewCodeXiaoNiuKuaiDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindNewCodeXiaoNiuKuaiDialog.dismiss();
                    NewCodeXiaoNiuKuaiSharePreferencesUtil.saveString("phone", "");
                    StaticNewCodeXiaoNiuKuaiUtil.startActivity(getActivity(), LoginNewCodeXiaoNiuKuaiNewCodeXiaoNiuKuaiActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindNewCodeXiaoNiuKuaiDialog.show();
            mRemindNewCodeXiaoNiuKuaiDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        mineNewCodeXiaoNiuKuaiItemAdapter =  new MineNewCodeXiaoNiuKuaiItemAdapter(R.layout.adapter_mine_list_layout_xiao_niu_kuai_sdf_efbdgh, list);
        mineNewCodeXiaoNiuKuaiItemAdapter.setHasStableIds(true);
        mineNewCodeXiaoNiuKuaiItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.ZCXY);
                    StaticNewCodeXiaoNiuKuaiUtil.startActivity(getActivity(), NewCodeXiaoNiuKuaiUserYsxyNewCodeXiaoNiuKuaiActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", NewCodeXiaoNiuKuaiRetrofitManager.YSXY);
                    StaticNewCodeXiaoNiuKuaiUtil.startActivity(getActivity(), NewCodeXiaoNiuKuaiUserYsxyNewCodeXiaoNiuKuaiActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticNewCodeXiaoNiuKuaiUtil.startActivity(getActivity(), NewCodeXiaoNiuKuaiAppInfoNewCodeXiaoNiuKuaiActivity.class, null);
                    break;
                case 4:
                    mRemindNewCodeXiaoNiuKuaiDialog = new RemindNewCodeXiaoNiuKuaiDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindNewCodeXiaoNiuKuaiDialog.setBtnClickListener(new RemindNewCodeXiaoNiuKuaiDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastNewCodeXiaoNiuKuaiUtil.showShort("开启成功");
                            mRemindNewCodeXiaoNiuKuaiDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastNewCodeXiaoNiuKuaiUtil.showShort("关闭成功");
                            mRemindNewCodeXiaoNiuKuaiDialog.dismiss();
                        }
                    });
                    mRemindNewCodeXiaoNiuKuaiDialog.show();
                    mRemindNewCodeXiaoNiuKuaiDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    StaticNewCodeXiaoNiuKuaiUtil.startActivity(getActivity(), CancellationNewCodeXiaoNiuKuaiActivityNewCodeXiaoNiuKuai.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineNewCodeXiaoNiuKuaiItemAdapter);
    }

    private void getConfig() {
        Observable<BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel>>() {
                    @Override
                    public void onSuccess(BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigNewCodeXiaoNiuKuaiModel configNewCodeXiaoNiuKuaiModel = model.getData();
                        if (configNewCodeXiaoNiuKuaiModel != null) {
                            NewCodeXiaoNiuKuaiSharePreferencesUtil.saveString("APP_MAIL", configNewCodeXiaoNiuKuaiModel.getAppMail());
                            mRemindNewCodeXiaoNiuKuaiDialog = new RemindNewCodeXiaoNiuKuaiDialog(getActivity(), "温馨提示", configNewCodeXiaoNiuKuaiModel.getAppMail(), false);
                            mRemindNewCodeXiaoNiuKuaiDialog.setBtnClickListener(new RemindNewCodeXiaoNiuKuaiDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindNewCodeXiaoNiuKuaiDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configNewCodeXiaoNiuKuaiModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastNewCodeXiaoNiuKuaiUtil.showShort("复制成功");
                                    mRemindNewCodeXiaoNiuKuaiDialog.dismiss();
                                }
                            });
                            mRemindNewCodeXiaoNiuKuaiDialog.show();
                            mRemindNewCodeXiaoNiuKuaiDialog.setBtnStr("取消", "复制");
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
