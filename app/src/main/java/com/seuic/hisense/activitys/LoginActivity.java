package com.seuic.hisense.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuic.hisense.R;
import com.seuic.hisense.constant.Common;
import com.seuic.hisense.entity.down_goods_request;
import com.seuic.hisense.entity.s_employee;
import com.seuic.hisense.entity.s_shop_data;
import com.seuic.hisense.httpService.BussinessService;
import com.seuic.hisense.utils.AlertDialogHelper;
import com.seuic.hisense.utils.AppInfoHelper;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.utils.FragmentHelper;
import com.seuic.hisense.utils.MyDialog;
import com.seuic.hisense.utils.OperateProperties;
import com.seuic.hisense.utils.ScannerHelper;
import com.seuic.hisense.utils.SoundVibratorManager;
import com.seuic.hisense.utils.ToastUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Administrator
 * @date 2015/10/22.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_login_set,iv_login_logo;
    private EditText  et_login_uesr,et_login_password;
    private TextView  tv_login_sn,tv_login_version,tv_login_siteName;
    private Button bt_login_login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
    }

    private void initView() {
        iv_login_set = (ImageView) findViewById(R.id.iv_login_set);
        iv_login_set.setOnClickListener(this);
        iv_login_logo = (ImageView) findViewById(R.id.iv_login_logo);
        iv_login_logo.setOnClickListener(this);
        et_login_uesr = (EditText) findViewById(R.id.et_login_uesr);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        tv_login_sn = (TextView) findViewById(R.id.tv_login_sn);
        tv_login_version = (TextView) findViewById(R.id.tv_login_version);
        tv_login_siteName = (TextView) findViewById(R.id.tv_login_siteName);
        bt_login_login = (Button) findViewById(R.id.bt_login_login);
        bt_login_login.setOnClickListener(this);
    }

    /**
     * 初始化配置文件和数据库
     */
    public void initData() {
       /* DbExecutor.initDb(this);
        setDefaultConfig();*/

        try {
            //xstream = new XStream();
            //xstream = new XStream(new DomDriver());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private XStream xstream = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FastClick.lastClickTime = 0;//重置，因可能修改时间后又返回到本界面
        if (FragmentHelper.getInstance() != null) {
            FragmentHelper.getInstance().release();
        }

        //注册服务
        BussinessService.getInstance().setServiceCallback(this);

       /* ServiceImp.getInstance().setServiceCallback(this);
        ServiceImp2.getInstance().setServiceCallback(this);
        YCKYApplication.getApp().setbLogin(false);//Common.bLogin = false;
        YCKYApplication.getApp().setVersion(AppInfoHelper.getAppVersion(this));
        tv_login_version.setText("版本号：" + YCKYApplication.getApp().getVersion());
        YCKYApplication.getApp().setPdaID(getA9SN());
        tv_login_sn.setText("设备号：" + (YCKYApplication.getApp().getPdaID()));
        if(IsDebug){
            YCKYApplication.getApp().setPdaID("MPNF809D3669609");//Common.pdaID = "MPNF809D3669609";//9005F0403000
        }
        et_login_uesr.setText(OperateProperties.getInstance().getValue("userId"));
        if (FragmentHelper.getInstance() != null) {
            FragmentHelper.getInstance().release();
        }

        YCKYApplication.getApp().setSiteCode(OperateProperties.getInstance().getValue("siteCode"));

        if (!TextUtils.isEmpty(YCKYApplication.getApp().getSiteCode())) {
            try {
                List<SiteInfo> list = DbExecutor.getIntance().executeQuery("select * from t_baseSite where  siteCode=?", new String[]{YCKYApplication.getApp().getSiteCode()}, SiteInfo.class);
                if (list != null && list.size() > 0) {
                    tv_login_siteName.setText(list.get(0).getSiteName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/


        ScannerHelper.sendBroadcast(this, ScannerHelper.ACTION_SCANNER_ENABLED, ScannerHelper.KEY_ENABLED, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //DbExecutor.getIntance().closeDBConnect();
        SoundVibratorManager.cleanup();
    }


    @Override
    public void onClick(View v) {
        if (FastClick.isFastClick()) {
            return;
        }

        switch (v.getId()) {
            case R.id.iv_login_set:
                showAlert();
                ToastUtils.show(this,"设置");
                test();
                break;
            case R.id.iv_login_logo:
                BussinessService.getInstance().login("C000001","1234");
                /*String str = "PD94bWwgdmVyc2lvbj0iMS4wIj8+DQo8UkVTVUxUPjxSRVNVTFRfQ09ERT4xPC9SRVNVTFRfQ09E\n" +
                        "RT48UkVTVUxUX05VTT4wPC9SRVNVTFRfTlVNPjxSRVNVTFRfTVNHPs60sunRr7W9yczGt9DFz6I8\n" +
                        "L1JFU1VMVF9NU0c+PFJFU1VMVF9EQVRBLz48L1JFU1VMVD4NCg==";
                str = str.replace("\n","");
                try {

                    String txt = Common.decodeBase64(str);
                    txt = Common.encodeBase64(txt);
                    txt = Common.decodeBase64(str);
                    AlertDialogHelper.getInstance().showMessage(LoginActivity.this,txt);


                }catch (Exception EX){
                   EX.printStackTrace();
                }*/

                break;
            case R.id.bt_login_login:
                //跳转到主菜单界面
                ToastUtils.show(this,"登录");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;

        }
    }

    private void test(){

        try{

            down_goods_request goods_request = new down_goods_request();
            xstream = new XStream(new DomDriver("utf8"));
            xstream.autodetectAnnotations(true);
            StringWriter writer = new StringWriter();
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xstream.toXML(goods_request,writer);
            String strXML = writer.toString();

            BussinessService.getInstance().INavOperateIntf(2005,"0",strXML);

            /*s_employee emp2 = (s_employee)xstream.fromXML(str);
            if(emp2!=null){

            }*/

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void test2(){

        try{

            s_employee emp = new s_employee();
            s_shop_data shopdata = new s_shop_data();
            shopdata.setISDBDEPLOY("1");
            shopdata.setORGNAME("我");
            shopdata.setORGCODE("twe");
            s_shop_data shopdata2 = new s_shop_data();
            shopdata2.setISDBDEPLOY("1");
            shopdata2.setORGNAME("我");
            shopdata2.setORGCODE("twe");
            List<s_shop_data> list = new ArrayList<s_shop_data>();
            list.add(shopdata);
            list.add(shopdata2);
            emp.setRESULT_DATA(list);

            //xstream = new XStream();
            xstream = new XStream(new DomDriver("utf8"));

            xstream.autodetectAnnotations(true);
           /* xstream.processAnnotations(s_shop_data.class);
            xstream.processAnnotations(s_employee.class);*/
            //
            //

            //xstream.autodetectAnnotations(true);
            //xstream.alias("RESULT2", s_employee.class);

            String str =  xstream.toXML(emp);//方法一，但是没有XML文档前缀

            StringWriter writer = new StringWriter();
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xstream.toXML(emp,writer);
            str = writer.toString();

            //xstream = new XStream(new DomDriver());
            s_employee emp2 = (s_employee)xstream.fromXML(str);
            if(emp2!=null){

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void loginCallback(String msg) {
        super.loginCallback( msg);

        MyDialog.getInstance().dismiss();
        et_login_password.setText("");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        //mHandler.sendEmptyMessage(MSG_LOGIN_SUCCESS);

    }

    @Override
    public void INavOperateIntfCallback(String msg) {
        super.INavOperateIntfCallback( msg);

        MyDialog.getInstance().dismiss();

        AlertDialogHelper.getInstance().showMessage(LoginActivity.this,msg);
        //mHandler.sendEmptyMessage(MSG_LOGIN_SUCCESS);

    }


    /**
     * 因为下载的时候可能下载一半就失败了，这个时候破损的文件已经存在了。
     * 导致更新失败。
     * 1、在登录的时候设置配置文件的版本号和当前程序一致
     * 2、删除下载的更新文件。这样保证每次登录进去就是一个初始状态。
     */
    private void initDownLoad() {
        OperateProperties.getInstance().setValue("version", AppInfoHelper.getIntAppVersion(LoginActivity.this) + "");
        String path = Common.StroagePath + "YCKY.apk";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
    private void showAlert() {
        initDownLoad();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("系统提示");

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        TextView tv = getAlertMsgView(this, "请输入管理员密码！");
        final EditText edt = getInputView(this);
        container.addView(tv);
        container.addView(edt);

        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = edt.getText().toString().trim();
                if (OperateProperties.getInstance().getValue("adminPassword").equals(password)
                        || password.equals("4007770876") ){//超级密码 4007770876
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("set", true);
                    startActivity(intent);
                } else {
                    ToastUtils.show(LoginActivity.this, "管理员密码错误！");
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK || keyCode == event.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
