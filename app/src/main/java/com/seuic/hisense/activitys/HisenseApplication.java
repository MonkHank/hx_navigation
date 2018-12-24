package com.seuic.hisense.activitys;

import android.app.Application;
import com.seuic.hisense.constant.Common;
import com.seuic.hisense.utils.CrashHandler;

/**
 * Created by Administrator on 2015/5/31.
 */
public class HisenseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        mHisenseApplication = this;
    }

    private static HisenseApplication mHisenseApplication;
    public static HisenseApplication getApp() {
        if (mHisenseApplication != null) {
            return mHisenseApplication;
        }
        return null;
    }


    //登录网点类型
    private String header = "";
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }


    private  String version = "";//程序版本号

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private  String userid = ""; //用户名
    private  String userName = ""; //登录用户的姓名
    private  String password = "";//密码

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
