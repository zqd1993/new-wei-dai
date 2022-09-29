package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnfragment;

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

import com.urhdnsertjg.gjuerjfhf.R;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity.JZhuJsiQIajsdnGuanYuUsActivity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity.LoginJZhuJsiQIajsdnActivity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity.JZhuJsiQIajsdnUserAgreementActivity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity.ZhuXiaoZhangHaoActivityJZhuJsiQIajsdn;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnadapter.SetItemJZhuJsiQIajsdnAdapter;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.BaseJZhuJsiQIajsdnEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.JZhuJsiQIajsdnConfigEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.JZhuJsiQIajsdnPersonalCenterEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnhttp.JZhuJsiQIajsdnMainApi;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnCommonUtil;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnGeneralDialog;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.MyJZhuJsiQIajsdnPreferences;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SetJZhuJsiQIajsdnFragment extends RxFragment {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.set_rcy)
    RecyclerView setRcv;

    public View rootView;
    protected LayoutInflater layoutInflater;
    private Bundle bundle;
    private JZhuJsiQIajsdnGeneralDialog JZhuJsiQIajsdnGeneralDialog;
    private String mailStr = "", phoneStr;

    private ClipboardManager clipboard;

    private ClipData clipData;
    private SetItemJZhuJsiQIajsdnAdapter setItemJZhuJsiQIajsdnAdapter;
    private List<JZhuJsiQIajsdnPersonalCenterEntity> mList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_jin_zhu_jqi_djrufn_dfke_set, null);
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
        mailStr = MyJZhuJsiQIajsdnPreferences.getString("app_mail");
        phoneStr = MyJZhuJsiQIajsdnPreferences.getString("phone");
        mList = new ArrayList<>();
        JZhuJsiQIajsdnPersonalCenterEntity model = new JZhuJsiQIajsdnPersonalCenterEntity(R.drawable.uyg, "关于我们");
        JZhuJsiQIajsdnPersonalCenterEntity model1 = new JZhuJsiQIajsdnPersonalCenterEntity(R.drawable.er, "注册协议");
        JZhuJsiQIajsdnPersonalCenterEntity model2 = new JZhuJsiQIajsdnPersonalCenterEntity(R.drawable.vbwe, "隐私协议");
        JZhuJsiQIajsdnPersonalCenterEntity model3 = new JZhuJsiQIajsdnPersonalCenterEntity(R.drawable.ntyur, "投诉邮箱");
        JZhuJsiQIajsdnPersonalCenterEntity model4 = new JZhuJsiQIajsdnPersonalCenterEntity(R.drawable.gsdf, "个性化推送");
        JZhuJsiQIajsdnPersonalCenterEntity model5 = new JZhuJsiQIajsdnPersonalCenterEntity(R.drawable.wertsdf, "注销账号");
        JZhuJsiQIajsdnPersonalCenterEntity model6 = new JZhuJsiQIajsdnPersonalCenterEntity(R.drawable.qwra, "退出登录");
        mList.add(model);
        mList.add(model1);
        mList.add(model2);
        mList.add(model3);
        mList.add(model4);
        mList.add(model5);
        mList.add(model6);
        setItemJZhuJsiQIajsdnAdapter = new SetItemJZhuJsiQIajsdnAdapter(getActivity(), mList);
        setItemJZhuJsiQIajsdnAdapter.setHasStableIds(true);
        setItemJZhuJsiQIajsdnAdapter.setOnItemClickListener(new SetItemJZhuJsiQIajsdnAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                switch (position){
                    case 0:
                        JZhuJsiQIajsdnCommonUtil.startActivity(getActivity(), JZhuJsiQIajsdnGuanYuUsActivity.class, null);
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putString("title", "注册协议");
                        bundle.putString("url", JZhuJsiQIajsdnMainApi.ZCXY);
                        JZhuJsiQIajsdnCommonUtil.startActivity(getActivity(), JZhuJsiQIajsdnUserAgreementActivity.class, bundle);
                        break;
                    case 2:
                        bundle = new Bundle();
                        bundle.putString("title", "隐私协议");
                        bundle.putString("url", JZhuJsiQIajsdnMainApi.YSXY);
                        JZhuJsiQIajsdnCommonUtil.startActivity(getActivity(), JZhuJsiQIajsdnUserAgreementActivity.class, bundle);
                        break;
                    case 3:
                        getConfigValue();
                        break;
                    case 4:
                        JZhuJsiQIajsdnGeneralDialog = new JZhuJsiQIajsdnGeneralDialog(getActivity(), "温馨提示", "关闭或开启推送");
                        JZhuJsiQIajsdnGeneralDialog.setBtnClickListener(new JZhuJsiQIajsdnGeneralDialog.BtnClickListener() {
                            @Override
                            public void leftClicked() {
                                Toast.makeText(getActivity(), "开启成功", Toast.LENGTH_SHORT).show();
                                JZhuJsiQIajsdnGeneralDialog.dismiss();
                            }

                            @Override
                            public void rightClicked() {
                                Toast.makeText(getActivity(), "关闭成功", Toast.LENGTH_SHORT).show();
                                JZhuJsiQIajsdnGeneralDialog.dismiss();
                            }
                        });
                        JZhuJsiQIajsdnGeneralDialog.show();
                        JZhuJsiQIajsdnGeneralDialog.setBtnStr("开启", "关闭");
                        break;
                    case 5:
                        JZhuJsiQIajsdnCommonUtil.startActivity(getActivity(), ZhuXiaoZhangHaoActivityJZhuJsiQIajsdn.class, null);
                        break;
                    case 6:
                        JZhuJsiQIajsdnGeneralDialog = new JZhuJsiQIajsdnGeneralDialog(getActivity(), "温馨提示", "确定退出当前登录");
                        JZhuJsiQIajsdnGeneralDialog.setBtnClickListener(new JZhuJsiQIajsdnGeneralDialog.BtnClickListener() {
                            @Override
                            public void leftClicked() {
                                JZhuJsiQIajsdnGeneralDialog.dismiss();
                            }

                            @Override
                            public void rightClicked() {
                                JZhuJsiQIajsdnGeneralDialog.dismiss();
                                MyJZhuJsiQIajsdnPreferences.saveString("phone", "");
                                JZhuJsiQIajsdnCommonUtil.startActivity(getActivity(), LoginJZhuJsiQIajsdnActivity.class, null);
                                getActivity().finish();
                            }
                        });
                        JZhuJsiQIajsdnGeneralDialog.show();
                        JZhuJsiQIajsdnGeneralDialog.setBtnStr("取消", "退出");
                        break;
                }
            }
        });
        setRcv.setHasFixedSize(true);
        setRcv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setRcv.setAdapter(setItemJZhuJsiQIajsdnAdapter);
    }

    private void getConfigValue(){
        JZhuJsiQIajsdnMainApi.getRetrofitManager().getApiService().getConfig().enqueue(new Callback<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>>() {
            @Override
            public void onResponse(Call<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> call, retrofit2.Response<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> response) {
                if (response.body() == null){
                    return;
                }
                JZhuJsiQIajsdnConfigEntity entity = response.body().getData();
                if (entity != null) {
                    MyJZhuJsiQIajsdnPreferences.saveString("app_mail", entity.getAppMail());
                    JZhuJsiQIajsdnGeneralDialog = new JZhuJsiQIajsdnGeneralDialog(getActivity(), "温馨提示", entity.getAppMail());
                    JZhuJsiQIajsdnGeneralDialog.setBtnClickListener(new JZhuJsiQIajsdnGeneralDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            JZhuJsiQIajsdnGeneralDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            clipData = ClipData.newPlainText(null, entity.getAppMail());
                            clipboard.setPrimaryClip(clipData);
                            Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
                            JZhuJsiQIajsdnGeneralDialog.dismiss();
                        }
                    });
                    JZhuJsiQIajsdnGeneralDialog.show();
                    JZhuJsiQIajsdnGeneralDialog.setBtnStr("取消", "复制");
                }
            }

            @Override
            public void onFailure(Call<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> call, Throwable t) {

            }
        });
    }

}
