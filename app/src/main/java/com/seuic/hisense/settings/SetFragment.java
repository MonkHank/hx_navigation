package com.seuic.hisense.settings;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.utils.AlertDialogHelper;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.utils.FragmentFactory;
import com.seuic.hisense.utils.NetWorkUtils;
import com.seuic.hisense.utils.ToastUtils;
import com.seuic.hisense.views.SubitemMenu;

public class SetFragment extends BaseFragment {
    private SubitemMenu sm_setting_wifi,sm_setting_gprs,sm_setting_bt,sm_setting_baseData,
            sm_setting_setSite,sm_setting_setUrl,sm_setting_modify_password,
            sm_setting_setSystem,sm_setting_setLog,sm_setting_setDateTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        sm_setting_wifi = (SubitemMenu)view.findViewById(R.id.sm_setting_wifi);
        sm_setting_wifi.setOnClickListener(this);

        sm_setting_gprs = (SubitemMenu)view.findViewById(R.id.sm_setting_gprs);
        sm_setting_gprs.setOnClickListener(this);

        /*sm_setting_bt = (SubitemMenu)view.findViewById(R.id.sm_setting_bt);
        sm_setting_bt.setOnClickListener(this);*/

        sm_setting_baseData = (SubitemMenu)view.findViewById(R.id.sm_setting_baseData);
        sm_setting_baseData.setOnClickListener(this);

        sm_setting_setSite = (SubitemMenu)view.findViewById(R.id.sm_setting_setSite);
        sm_setting_setSite.setOnClickListener(this);

        sm_setting_setUrl = (SubitemMenu)view.findViewById(R.id.sm_setting_setUrl);
        sm_setting_setUrl.setOnClickListener(this);

        sm_setting_modify_password = (SubitemMenu)view.findViewById(R.id.sm_setting_modify_password);
        sm_setting_modify_password.setOnClickListener(this);

        sm_setting_setSystem = (SubitemMenu)view.findViewById(R.id.sm_setting_setSystem);
        sm_setting_setSystem.setOnClickListener(this);

        sm_setting_setLog = (SubitemMenu)view.findViewById(R.id.sm_setting_setLog);
        sm_setting_setLog.setOnClickListener(this);

        sm_setting_setDateTime = (SubitemMenu)view.findViewById(R.id.sm_setting_setDateTime);
        sm_setting_setDateTime.setOnClickListener(this);

    }

    private void initData(){

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setTitle("系统设置");
    }


    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }
        switch (v.getId()){
            case R.id.sm_setting_wifi:
                getActivity().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
            case R.id.sm_setting_gprs:
                network();
                break;
            /*case R.id.sm_setting_bt:
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
                //mFHelper.transcateFoward(FragmentFactory.Bluetooth_Fragment);
                break;*/
            case R.id.sm_setting_baseData:
                mFHelper.transcateFoward(FragmentFactory.DataDownload_Fragment);
                break;
            case R.id.sm_setting_setSite:
                //mFHelper.transcateFoward(FragmentFactory.SetSite_Fragment);
                break;
            case R.id.sm_setting_setUrl:
                //mFHelper.transcateFoward(FragmentFactory.URL_Fragment);
                break;
            case R.id.sm_setting_modify_password:
                AlertDialogHelper.getInstance().showMessage(SetFragment.this.getActivity(), "该功能已禁用");
                //mFHelper.transcateFoward(FragmentFactory.ChangePassword_Fragment);
                break;
            case R.id.sm_setting_setSystem:
                updateSystem();
                break;
            case R.id.sm_setting_setLog://日志上传
                showAlert();//A_uploadLogAlert
                break;
            case R.id.sm_setting_setDateTime://日期时间
                setDateTime();
                break;



        }
    }

    private void setDateTime(){
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings","com.android.settings.DateTimeSettingsSetupWizard");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        startActivityForResult( intent , 0);

/*        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        String packageName = "com.redstone.ota.ui";
        String className = "com.redstone.ota.ui.activity.RsMainActivity";
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        startActivity(intent);*/
    }

    /*********************日志上传start*******************/

    private void showAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("系统提示");

        LinearLayout container = new LinearLayout(getActivity());
        container.setOrientation(LinearLayout.VERTICAL);
        TextView tv = getAlertMsgView(getActivity(), "请输入验证密码！");
        final EditText edt = getInputView(getActivity());
        container.addView(tv);
        container.addView(edt);

        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ("15682869".equals(edt.getText().toString())) {
                    A_uploadLog();
                } else {
                    ToastUtils.show(getActivity(), "验证密码错误！");
                }
            }
        });
        dialog.setNegativeButton("否", null);
        dialog.setView(container);
        dialog.show();
    }

    private TextView getAlertMsgView(Context context, String str) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 10, 20, 10);
        tv.setLayoutParams(lp);
        tv.setText(str);
        tv.setTextSize(20);
        return tv;
    }

    private EditText getInputView(Context context) {
        EditText edt = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 0, 20, 10);
        edt.setLayoutParams(lp);
        edt.setInputType(InputType.TYPE_CLASS_NUMBER);
        edt.setBackground(context.getResources().getDrawable(R.drawable.edittext_style));
        return edt;
    }

    /*private void A_uploadLogAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定要上传日志？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(AlertDialog.BUTTON_POSITIVE == which){
                    A_uploadLog();
                }
            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }*/

    private void A_uploadLog(){

        if (!NetWorkUtils.isNetworkConnected(getActivity())) {
            AlertDialogHelper.getInstance().showMessage(getActivity(),"网络不通！ ");
            return;
        }
       /* MyDialog.getInstance().setContext(getActivity(),this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("正在上传日志数据……");
        ServiceLog.getInstance().setServiceCallback(this);
        new Thread(){
            @Override
            public void run() {
                ServiceLog.getInstance().A_uploadLog(YCKYApplication.getApp().getUserid());

                super.run();
            }
        }.start();*/

    }

    /*@Override
    public void A_uploadLogCallBack(int status, String msg) {
        super.A_uploadLogCallBack(status, msg);

        MyDialog.getInstance().dismiss();//关闭提示框
        if(Common.SUCCESS == status){

            Message message = new Message();
            message.what = MSG_Submit_Success;
            Bundle bundle=new Bundle();
            bundle.putString("errorMsg", msg);
            message.setData(bundle);//bundle传值，耗时，效率低
            mHandler.sendMessage(message);
        }else {
            Message message = new Message();
            message.what = MSG_Submit_Fail;
            Bundle bundle=new Bundle();
            bundle.putString("errorMsg", msg);
            message.setData(bundle);//bundle传值，耗时，效率低
            mHandler.sendMessage(message);
        }

        super.A_uploadLogCallBack(status, msg);
    }*/

    //提交结果返回
    static final  int MSG_Submit_Success = 0;
    static final  int MSG_Submit_Fail = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_Submit_Success:
                    AlertDialogHelper.getInstance().showMessage(SetFragment.this.getActivity(), msg.getData().getString("errorMsg"));
                    break;
                case MSG_Submit_Fail:
                    AlertDialogHelper.getInstance().showMessage(SetFragment.this.getActivity(),msg.getData().getString("errorMsg"));
                    break;

            }
        }
    };
    /*********************日志上传 end*******************/

    /**
     * 调用云升级功能
     */
    private void updateSystem(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        String packageName = "com.redstone.ota.ui";
        String className = "com.redstone.ota.ui.activity.RsMainActivity";
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        startActivity(intent);
    }

    private void network() {
        final ComponentName cn = new ComponentName("com.android.phone","com.android.phone.MobileNetworkSettings");
        final Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        intent.addCategory(Intent.ACTION_MAIN);
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void finish() {
       /* if(YCKYApplication.getApp().isbLogin()){
            super.finish();
        }else{
            getActivity().finish();
        }*/
        getActivity().finish();
    }


}
