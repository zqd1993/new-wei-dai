package com.dgjadsie.jkermsd.youbeihwahsndfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndactivity.YouBeiHwHsajJsumGuanYuUsActivity;
import com.dgjadsie.jkermsd.youbeihwahsndactivity.LoginYouBeiHwHsajJsumActivity;
import com.dgjadsie.jkermsd.youbeihwahsndactivity.YouBeiHwHsajJsumUserAgreementActivity;
import com.dgjadsie.jkermsd.youbeihwahsndactivity.YouBeiHwHsajJsumZhuXiaoZhangHaoActivity;
import com.dgjadsie.jkermsd.youbeihwahsndadapter.SetItemYouBeiHwHsajJsumAdapter;
import com.dgjadsie.jkermsd.youbeihwahsndentity.BaseYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.ConfigYouBeiHwHsajJsumEntity;
import com.dgjadsie.jkermsd.youbeihwahsndentity.YouBeiHwHsajJsumPersonalCenterEntity;
import com.dgjadsie.jkermsd.youbeihwahsndhttp.MainYouBeiHwHsajJsumApi;
import com.dgjadsie.jkermsd.youbeihwahsndutil.CommonYouBeiHwHsajJsumUtil;
import com.dgjadsie.jkermsd.youbeihwahsndutil.GeneralYouBeiHwHsajJsumDialog;
import com.dgjadsie.jkermsd.youbeihwahsndutil.MyYouBeiHwHsajJsumPreferences;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SetYouBeiHwHsajJsumFragment extends RxFragment {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.set_rcy)
    RecyclerView setRcv;

    public View rootView;
    protected LayoutInflater layoutInflater;
    private Bundle bundle;
    private GeneralYouBeiHwHsajJsumDialog generalYouBeiHwHsajJsumDialog;
    private String mailStr = "", phoneStr;

    private ClipboardManager clipboard;

    private ClipData clipData;
    private SetItemYouBeiHwHsajJsumAdapter setItemYouBeiHwHsajJsumAdapter;
    private List<YouBeiHwHsajJsumPersonalCenterEntity> mList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_you_bei_he_dje_yrhr_set, null);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = MyYouBeiHwHsajJsumPreferences.getString("app_mail");
        phoneStr = MyYouBeiHwHsajJsumPreferences.getString("phone");
        phoneTv.setText(phoneStr);
        mList = new ArrayList<>();
        YouBeiHwHsajJsumPersonalCenterEntity model = new YouBeiHwHsajJsumPersonalCenterEntity(R.drawable.aerty, "关于我们");
        YouBeiHwHsajJsumPersonalCenterEntity model1 = new YouBeiHwHsajJsumPersonalCenterEntity(R.drawable.xvbrt, "注册协议");
        YouBeiHwHsajJsumPersonalCenterEntity model2 = new YouBeiHwHsajJsumPersonalCenterEntity(R.drawable.urtyj, "隐私协议");
        YouBeiHwHsajJsumPersonalCenterEntity model3 = new YouBeiHwHsajJsumPersonalCenterEntity(R.drawable.etyjsd, "联系客服");
        YouBeiHwHsajJsumPersonalCenterEntity model5 = new YouBeiHwHsajJsumPersonalCenterEntity(R.drawable.szbs, "注销账号");
        YouBeiHwHsajJsumPersonalCenterEntity model6 = new YouBeiHwHsajJsumPersonalCenterEntity(R.drawable.drtuf, "退出登录");
        mList.add(model);
        mList.add(model1);
        mList.add(model2);
        mList.add(model3);
        mList.add(model5);
        mList.add(model6);
        setItemYouBeiHwHsajJsumAdapter = new SetItemYouBeiHwHsajJsumAdapter(getActivity(), mList);
        setItemYouBeiHwHsajJsumAdapter.setHasStableIds(true);
        setItemYouBeiHwHsajJsumAdapter.setOnItemClickListener(new SetItemYouBeiHwHsajJsumAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                switch (position){
                    case 0:
                        CommonYouBeiHwHsajJsumUtil.startActivity(getActivity(), YouBeiHwHsajJsumGuanYuUsActivity.class, null);
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putString("title", "注册协议");
                        bundle.putString("url", MainYouBeiHwHsajJsumApi.ZCXY);
                        CommonYouBeiHwHsajJsumUtil.startActivity(getActivity(), YouBeiHwHsajJsumUserAgreementActivity.class, bundle);
                        break;
                    case 2:
                        bundle = new Bundle();
                        bundle.putString("title", "隐私协议");
                        bundle.putString("url", MainYouBeiHwHsajJsumApi.YSXY);
                        CommonYouBeiHwHsajJsumUtil.startActivity(getActivity(), YouBeiHwHsajJsumUserAgreementActivity.class, bundle);
                        break;
                    case 3:
                        getConfigValue();
                        break;
                    case 4:
                        CommonYouBeiHwHsajJsumUtil.startActivity(getActivity(), YouBeiHwHsajJsumZhuXiaoZhangHaoActivity.class, null);
                        break;
                    case 5:
                        generalYouBeiHwHsajJsumDialog = new GeneralYouBeiHwHsajJsumDialog(getActivity(), "温馨提示", "确定退出当前登录");
                        generalYouBeiHwHsajJsumDialog.setBtnClickListener(new GeneralYouBeiHwHsajJsumDialog.BtnClickListener() {
                            @Override
                            public void leftClicked() {
                                generalYouBeiHwHsajJsumDialog.dismiss();
                            }

                            @Override
                            public void rightClicked() {
                                generalYouBeiHwHsajJsumDialog.dismiss();
                                MyYouBeiHwHsajJsumPreferences.saveString("phone", "");
                                CommonYouBeiHwHsajJsumUtil.startActivity(getActivity(), LoginYouBeiHwHsajJsumActivity.class, null);
                                getActivity().finish();
                            }
                        });
                        generalYouBeiHwHsajJsumDialog.show();
                        generalYouBeiHwHsajJsumDialog.setBtnStr("取消", "退出");
                        break;
                }
            }
        });
        setRcv.setHasFixedSize(true);
        setRcv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setRcv.setAdapter(setItemYouBeiHwHsajJsumAdapter);
    }

    private void getConfigValue(){
        MainYouBeiHwHsajJsumApi.getRetrofitManager().getApiService().getConfig().enqueue(new Callback<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>>() {
            @Override
            public void onResponse(Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> call, retrofit2.Response<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> response) {
                if (response.body() == null){
                    return;
                }
                ConfigYouBeiHwHsajJsumEntity entity = response.body().getData();
                if (entity != null) {
                    MyYouBeiHwHsajJsumPreferences.saveString("app_mail", entity.getAppMail());
                    generalYouBeiHwHsajJsumDialog = new GeneralYouBeiHwHsajJsumDialog(getActivity(), "温馨提示", entity.getAppMail());
                    generalYouBeiHwHsajJsumDialog.setBtnClickListener(new GeneralYouBeiHwHsajJsumDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            generalYouBeiHwHsajJsumDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            clipData = ClipData.newPlainText(null, entity.getAppMail());
                            clipboard.setPrimaryClip(clipData);
                            Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
                            generalYouBeiHwHsajJsumDialog.dismiss();
                        }
                    });
                    generalYouBeiHwHsajJsumDialog.show();
                    generalYouBeiHwHsajJsumDialog.setBtnStr("取消", "复制");
                }
            }

            @Override
            public void onFailure(Call<BaseYouBeiHwHsajJsumEntity<ConfigYouBeiHwHsajJsumEntity>> call, Throwable t) {

            }
        });
    }

}
