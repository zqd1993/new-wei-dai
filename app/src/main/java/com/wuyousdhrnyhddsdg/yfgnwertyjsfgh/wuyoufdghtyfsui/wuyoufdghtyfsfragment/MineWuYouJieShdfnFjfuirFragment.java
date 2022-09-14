package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsapi.RetrofitWuYouJieShdfnFjfuirManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.BaseWuYouJieShdfnFjfuirFragment;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.WuYouJieShdfnFjfuirObserverManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.WuYouJieShdfnFjfuirBaseModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.ConfigWuYouJieShdfnFjfuirModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.WuYouJieShdfnFjfuirMineItemModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.AppInfoWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.CancellationWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.LoginWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.UserYsxyWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsadapter.MineItemWuYouJieShdfnFjfuirAdapter;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.RemindWuYouJieShdfnFjfuirDialog;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.ToastWuYouJieShdfnFjfuirUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirSharePreferencesUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.StaticWuYouJieShdfnFjfuirUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineWuYouJieShdfnFjfuirFragment extends BaseWuYouJieShdfnFjfuirFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn, zhuxiao_tv;

    private String mobileStr;
    private MineItemWuYouJieShdfnFjfuirAdapter mineItemWuYouJieShdfnFjfuirAdapter, mineItemWuYouJieShdfnFjfuirAdapter1;
    private List<WuYouJieShdfnFjfuirMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.rtgh, R.drawable.dfgjvbn, R.drawable.srtyhfgx,
            R.drawable.vbnsr, R.drawable.zdfhx};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐"};
    private Bundle bundle;
    private RemindWuYouJieShdfnFjfuirDialog mRemindWuYouJieShdfnFjfuirDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wu_you_jie_jdf_eryj_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        zhuxiao_tv = rootView.findViewById(R.id.zhuxiao_tv);
        mineList1 = rootView.findViewById(R.id.mine_list_1);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mobileStr = WuYouJieShdfnFjfuirSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 5; i++) {
            WuYouJieShdfnFjfuirMineItemModel model = new WuYouJieShdfnFjfuirMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            if (i < 3) {
                list.add(model);
            } else {
                list1.add(model);
            };
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindWuYouJieShdfnFjfuirDialog = new RemindWuYouJieShdfnFjfuirDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindWuYouJieShdfnFjfuirDialog.setBtnClickListener(new RemindWuYouJieShdfnFjfuirDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindWuYouJieShdfnFjfuirDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindWuYouJieShdfnFjfuirDialog.dismiss();
                    WuYouJieShdfnFjfuirSharePreferencesUtil.saveString("phone", "");
                    StaticWuYouJieShdfnFjfuirUtil.startActivity(getActivity(), LoginWuYouJieShdfnFjfuirActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindWuYouJieShdfnFjfuirDialog.show();
            mRemindWuYouJieShdfnFjfuirDialog.setBtnStr("取消", "退出");
        });
        zhuxiao_tv.setOnClickListener(v -> {
            StaticWuYouJieShdfnFjfuirUtil.startActivity(getActivity(), CancellationWuYouJieShdfnFjfuirActivity.class, null);
        });
    }

    private void setMineData(){
        mineItemWuYouJieShdfnFjfuirAdapter =  new MineItemWuYouJieShdfnFjfuirAdapter(R.layout.adapter_mine_list_layout_wu_you_jie_jdf_eryj, list);
        mineItemWuYouJieShdfnFjfuirAdapter.setHasStableIds(true);
        mineItemWuYouJieShdfnFjfuirAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RetrofitWuYouJieShdfnFjfuirManager.ZCXY);
                    StaticWuYouJieShdfnFjfuirUtil.startActivity(getActivity(), UserYsxyWuYouJieShdfnFjfuirActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RetrofitWuYouJieShdfnFjfuirManager.YSXY);
                    StaticWuYouJieShdfnFjfuirUtil.startActivity(getActivity(), UserYsxyWuYouJieShdfnFjfuirActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemWuYouJieShdfnFjfuirAdapter);
        mineItemWuYouJieShdfnFjfuirAdapter1 =  new MineItemWuYouJieShdfnFjfuirAdapter(R.layout.adapter_mine_list_layout_1_wu_you_jie_jdf_eryj, list1);
        mineItemWuYouJieShdfnFjfuirAdapter1.setHasStableIds(true);
        mineItemWuYouJieShdfnFjfuirAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticWuYouJieShdfnFjfuirUtil.startActivity(getActivity(), AppInfoWuYouJieShdfnFjfuirActivity.class, null);
                    break;
                case 1:
                    mRemindWuYouJieShdfnFjfuirDialog = new RemindWuYouJieShdfnFjfuirDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindWuYouJieShdfnFjfuirDialog.setBtnClickListener(new RemindWuYouJieShdfnFjfuirDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastWuYouJieShdfnFjfuirUtil.showShort("开启成功");
                            mRemindWuYouJieShdfnFjfuirDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastWuYouJieShdfnFjfuirUtil.showShort("关闭成功");
                            mRemindWuYouJieShdfnFjfuirDialog.dismiss();
                        }
                    });
                    mRemindWuYouJieShdfnFjfuirDialog.show();
                    mRemindWuYouJieShdfnFjfuirDialog.setBtnStr("开启", "关闭");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(mineItemWuYouJieShdfnFjfuirAdapter1);
    }

    private void getConfig() {
        Observable<WuYouJieShdfnFjfuirBaseModel<ConfigWuYouJieShdfnFjfuirModel>> observable = RetrofitWuYouJieShdfnFjfuirManager.getRetrofitManager().getApiService().getConfig();

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
                            WuYouJieShdfnFjfuirSharePreferencesUtil.saveString("APP_MAIL", configWuYouJieShdfnFjfuirModel.getAppMail());
                            mRemindWuYouJieShdfnFjfuirDialog = new RemindWuYouJieShdfnFjfuirDialog(getActivity(), "温馨提示", configWuYouJieShdfnFjfuirModel.getAppMail(), false);
                            mRemindWuYouJieShdfnFjfuirDialog.setBtnClickListener(new RemindWuYouJieShdfnFjfuirDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindWuYouJieShdfnFjfuirDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configWuYouJieShdfnFjfuirModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastWuYouJieShdfnFjfuirUtil.showShort("复制成功");
                                    mRemindWuYouJieShdfnFjfuirDialog.dismiss();
                                }
                            });
                            mRemindWuYouJieShdfnFjfuirDialog.show();
                            mRemindWuYouJieShdfnFjfuirDialog.setBtnStr("取消", "复制");
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
