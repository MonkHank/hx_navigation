package com.seuic.hisense.settings;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
//import com.seuic.hisense.db.DbExecutor;

import com.seuic.hisense.fragments.BaseFragment;
//import com.seuic.hisense.httpService.ServiceImp;
//import com.seuic.hisense.httpService.ServiceImp2;
import com.seuic.hisense.utils.AlertDialogHelper;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.utils.MyDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataDownloadFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener{

    private final int MSG_UPDATE_SUCCESS = 8;
    private final int MSG_UPDATE_FAIL= 9;

    private static final int MSG_START_Vendor = 0;
    private static final int MSG_START_Customer = 1;
    private static final int MSG_START_Goods = 2;
    private static final int MSG_START_StoreORG = 3;
    private static final int MSG_START_Employee = 4;

    private CheckBox cb_download_select,cb_storeORG,cb_employee,
                cb_vendor,cb_goods,cb_customer;

    private Button bt_download;
    private List<CheckBox> cbList;

    private StringBuilder buf = new StringBuilder();


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_UPDATE_SUCCESS:
                case MSG_UPDATE_FAIL:
                    MyDialog.getInstance().dismiss();
                    AlertDialogHelper.getInstance().showMessage(DataDownloadFragment.this.getActivity(),buf.toString());
                    break;
                case MSG_START_StoreORG:
                    MyDialog.getInstance().setDisplay("门店组织信息下载...");
                    break;
                case MSG_START_Employee:
                    MyDialog.getInstance().setDisplay("员工信息下载...");
                    break;
                case MSG_START_Vendor:
                    MyDialog.getInstance().setDisplay("供应商信息下载...");
                    break;
                case MSG_START_Customer:
                    MyDialog.getInstance().setDisplay("批发客户信息下载...");
                    break;
                case MSG_START_Goods:
                    MyDialog.getInstance().setDisplay("商品信息下载...");
                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_data_download, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //////ServiceImp.getInstance().setServiceCallback(this);//原接口
        //////ServiceImp2.getInstance().setServiceCallback(this);//新接口
        ((MainActivity)getActivity()).setTitle("基础数据下载");
    }

    private void initView(View view){
        cbList = new ArrayList<CheckBox>();
        //全选/不选
        cb_download_select = (CheckBox) view.findViewById(R.id.cb_download_select);
        cbList.add(cb_download_select);
        cb_download_select.setOnCheckedChangeListener(this);

        //下载
        bt_download = (Button)view.findViewById(R.id.bt_download);
        bt_download.setOnClickListener(this);

        //门店组织
        cb_storeORG = (CheckBox) view.findViewById(R.id.cb_storeORG);
        cbList.add(cb_storeORG);
        cb_storeORG.setOnCheckedChangeListener(this);

        //员工
        cb_employee = (CheckBox) view.findViewById(R.id.cb_employee);
        cbList.add(cb_employee);
        cb_employee.setOnCheckedChangeListener(this);

        //供应商
        cb_vendor = (CheckBox) view.findViewById(R.id.cb_vendor);
        cbList.add(cb_vendor);
        cb_vendor.setOnCheckedChangeListener(this);

        //批发客户
        cb_customer = (CheckBox) view.findViewById(R.id.cb_customer);
        cbList.add(cb_customer);
        cb_customer.setOnCheckedChangeListener(this);

        //批发价格
        cb_goods = (CheckBox) view.findViewById(R.id.cb_goods);
        cbList.add(cb_goods);
        cb_goods.setOnCheckedChangeListener(this);


    }

    private void initData(){

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_download_select:
                for (CheckBox cb : cbList) {
                    if (cb.getId() != R.id.cb_download_select) {
                        cb.setChecked(isChecked);
                    }
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }
        switch (v.getId()){
            case R.id.bt_download:
                download();
                break;
        }
    }

    private void download(){
        boolean b = false;//是否有选中项
        for(CheckBox cb : cbList){
            if(cb.isChecked()){
                b = true;
                break;
            }
        }
        if(!b){
            AlertDialogHelper.getInstance().showMessage(getActivity(), "请选中要下载的项！");
            return;
        }
        buf.delete(0,buf.length());

        DownLoadThread mDownLoadThread = new DownLoadThread();
        mDownLoadThread.start();
        MyDialog.getInstance().setContext(getActivity(),this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("同步数据中，请稍后...");
    }


    private class DownLoadThread extends Thread{
        @Override
        public void run() {
            for(CheckBox cb : cbList){
                if(!cb.isChecked()){
                    continue;
                }

                switch (cb.getId()){
                    case R.id.cb_storeORG://门店组织
                        downloadStoreORG();
                        break;
                    case R.id.cb_employee://员工
                        downloadEmployee();
                        break;
                    case R.id.cb_vendor://供应商
                        downloadVendor();
                        break;
                    case R.id.cb_customer://批发客户
                        downloadCustomer();
                        break;
                    case R.id.cb_goods://商品
                        downloadGoods();
                        break;
                    //case R.id.cb_download_carLine://车线资料下载
                        /*String siteName = "";
                        if(!TextUtils.isEmpty(Common.siteCode)){
                            try{
                                List<SiteInfo> list = DbExecutor.getIntance().executeQuery("select * from t_baseSite where  siteCode=?", new String[]{Common.siteCode}, SiteInfo.class);
                                if (list != null && list.size() > 0) {
                                    siteName = list.get(0).getSiteName();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        if(TextUtils.isEmpty(siteName)){//没有网点名称，无法查询车线
                            mHandler.sendEmptyMessage(MSG_START_carLine_error);
                            return;
                        }

                        mHandler.sendEmptyMessage(MSG_START_Employee);
                        sleep();
                        ServiceImp2.getInstance().SiteLine(Common.userid, Common.model.getData(), siteName);*/
                        //break;
                    default:
                        break;
                }

            }
        }

        /**
         * 为UI刷新
         */
        private void sleep() {
            try{
                Thread.sleep(300);
            }catch (Exception e){

            }
        }

        private void downloadStoreORG(){
            mHandler.sendEmptyMessage(MSG_START_StoreORG);
            sleep();

            boolean ret = existNextDownload(cb_storeORG);
            buf.append("门店组织下载成功!\n");
            if(ret == false){//当下面没有下载项了，就可以关闭下载提示、直接弹出消息提示框
                mHandler.sendEmptyMessage(MSG_UPDATE_SUCCESS);
            }
            //ServiceImp.getInstance().getDestinationInfo("");
        }


        private void downloadEmployee(){
            mHandler.sendEmptyMessage(MSG_START_Employee);
            sleep();

            boolean ret = existNextDownload(cb_employee);
            buf.append("员工信息下载成功!\n");
            if(ret == false){//当下面没有下载项了，就可以关闭下载提示、直接弹出消息提示框
                mHandler.sendEmptyMessage(MSG_UPDATE_SUCCESS);
            }
            //ServiceImp2.getInstance().DownloadSite("");
        }

        private void downloadVendor(){
            mHandler.sendEmptyMessage(MSG_START_Vendor);
            sleep();

            boolean ret = existNextDownload(cb_vendor);
            buf.append("供应商信息下载成功!\n");
            if(ret == false){//当下面没有下载项了，就可以关闭下载提示、直接弹出消息提示框
                mHandler.sendEmptyMessage(MSG_UPDATE_SUCCESS);
            }
            //ServiceImp.getInstance().getProblemCause("");
        }

        private void downloadCustomer(){
            mHandler.sendEmptyMessage(MSG_START_Customer);
            sleep();

            boolean ret = existNextDownload(cb_customer);
            buf.append("批发客户信息下载成功!\n");
            if(ret == false){//当下面没有下载项了，就可以关闭下载提示、直接弹出消息提示框
                mHandler.sendEmptyMessage(MSG_UPDATE_SUCCESS);
            }
            //ServiceImp.getInstance().getReasonsLeavingWarehouse("");
        }

        private void downloadGoods(){
            mHandler.sendEmptyMessage(MSG_START_Goods);
            sleep();

            boolean ret = existNextDownload(cb_goods);
            buf.append("商品信息下载成功!\n");
            if(ret == false){//当下面没有下载项了，就可以关闭下载提示、直接弹出消息提示框
                mHandler.sendEmptyMessage(MSG_UPDATE_SUCCESS);
            }
            //ServiceImp.getInstance().getReasonsLeavingWarehouse("");
        }

        /**
         * 判断当前下载项的后面是否还有下载项。就是说下载完本项后，是否还要继续下载其他项
         * @param currentCbx
         * @return
         */
        private boolean existNextDownload(CheckBox currentCbx ){
            boolean isCurrent = false;
            boolean hasNext = false;
            for(CheckBox cb : cbList) {

                if(isCurrent == false && currentCbx.getId() == cb.getId()){
                    isCurrent = true;
                    continue;//遇到当前下载项
                }

                if(isCurrent == false){
                    continue;//还没有遇到当前下载项
                }

                if(cb.isChecked()) {
                    hasNext = true;
                }
            }
            return hasNext;
        }
    }
}
