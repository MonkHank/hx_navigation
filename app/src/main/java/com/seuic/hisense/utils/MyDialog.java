package com.seuic.hisense.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.seuic.hisense.constant.Common;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.fragments.ScanFragment;

/**
 * Created by Administrator on 2015/3/13.
 */
public class MyDialog {
    public static MyDialog mmyDialog;
    private Context mContext;
    ProgressDialog dialog;
    private boolean isExiting = false;
    private boolean isScanFragment = false;
    private BaseFragment mBaseFragment;
    private MyDialog(){
    }

/*    public void setContext(Context mContext){
        this.mContext = mContext;
    }*/
    public void setContext(Context mContext,final BaseFragment myFragment ){
        this.mContext = mContext;
        this.mBaseFragment = myFragment;
    }

    public static MyDialog getInstance(){
        if(mmyDialog==null){
            mmyDialog = new MyDialog();
        }
        return mmyDialog;
    }

/*    private synchronized void  initDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
        dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);

    }*/

    private synchronized void  initDialog(boolean Cancelable){
        if(dialog!=null){
            dialog.dismiss();
        }
        dialog = new ProgressDialog(mContext);
        dialog.setCancelable(Cancelable);

    }

/*    public void show(){
        initDialog();
        dialog.show();
    }*/

    public void show(){
        show(false);//禁止按返回键关闭提示框
    }

    /**
     *
     * @param Cancelable 是否可以 按返回键关闭提示框
     */
    public void show(boolean Cancelable){
        isExiting = true;
        initDialog(Cancelable);
        if(mBaseFragment!=null&&mBaseFragment instanceof ScanFragment){
            isScanFragment = true;
        }else {
            isScanFragment = false;
        }
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //Common.guidValidate1 = "";
                //YCKYApplication.getApp().setGuidValidate1("");
            }
        });
        dialog.show();
        if(isScanFragment) {
            //禁用扫描
            ScannerHelper.sendBroadcast(mContext, ScannerHelper.ACTION_SCANNER_ENABLED, ScannerHelper.KEY_ENABLED, false);
        }
    }


    public boolean isShow(){
        if(dialog!=null){
            return isExiting;
        }
        return false;
    }


    public void setDisplay(String display){
        dialog.setMessage(display);
    }

    public void dismiss(){
        if(dialog!=null){
            dialog.dismiss();
        }
        if(isScanFragment) {
            //启用扫描
            ScannerHelper.sendBroadcast(mContext, ScannerHelper.ACTION_SCANNER_ENABLED, ScannerHelper.KEY_ENABLED, true);
        }
        isExiting = false;
    }

}

