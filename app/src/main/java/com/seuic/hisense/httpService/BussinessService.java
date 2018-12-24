package com.seuic.hisense.httpService;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2016/5/11.
 */
public class BussinessService {
    private static BussinessService mBussinessService;
    private IServiceCallback mServiceCallback;

    public static BussinessService getInstance(){
        if(mBussinessService ==null){
            synchronized (BussinessService.class) {
                if(mBussinessService ==null) {
                    mBussinessService = new BussinessService();
                }
            }
        }
        return mBussinessService;
    }

    public void setServiceCallback(IServiceCallback mServiceCallback){
        this.mServiceCallback = mServiceCallback;
    }

    /**
     * ע��
     * //@param mUser
     */
    /*public void addUser(User mUser){
        ThreadPoolUtils.execute(new AddUserRunnable(mUser));
    }

    private class AddUserRunnable implements Runnable {
        User mUser;
        AddUserRunnable(User mUser){
            this.mUser = mUser;
        }
        @Override
        public void run() {
            final boolean b = mServiceImp.addUser(mUser);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.addUserCallback(b);
                }
            });
        }
    }*/

    public void login(String grid,String pass){
        ThreadPoolUtils.execute(new loginRunnable(grid,pass));
    }

    private class loginRunnable implements Runnable {
        String grid,pass;
        loginRunnable(String grid,String pass){
            this.grid = grid;
            this.pass = pass;
        }

        @Override
        public void run() {
            final String result = ServiceImp.getInstance().login(this.grid,this.pass);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.loginCallback(result);
                }
            });
        }
    }


    public void INavOperateIntf(int psOperateTypeCode, String psDataType, String psXMLData){
        ThreadPoolUtils.execute(new INavOperateIntfRunnable(psOperateTypeCode,psDataType,psXMLData));
    }

    private class INavOperateIntfRunnable implements Runnable {
        int psOperateTypeCode;
        String psDataType,psXMLData;
        INavOperateIntfRunnable(int psOperateTypeCode, String psDataType, String psXMLData){
            this.psOperateTypeCode = psOperateTypeCode;
            this.psDataType = psDataType;
            this.psXMLData = psXMLData;
        }
        @Override
        public void run() {
            final String result = ServiceImp.getInstance().INavOperateIntf(this.psOperateTypeCode,this.psDataType,this.psXMLData);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.INavOperateIntfCallback(result);
                }
            });
        }
    }

}
