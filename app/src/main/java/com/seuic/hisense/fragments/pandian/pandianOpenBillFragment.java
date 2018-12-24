package com.seuic.hisense.fragments.pandian;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
import com.seuic.hisense.adapters.SpinnerAdapter;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.fragments.ScanFragment;
import com.seuic.hisense.utils.AlertDialogHelper;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.utils.FragmentFactory;
import com.seuic.hisense.utils.LogHelper;
import com.seuic.hisense.utils.MyDialog;
import com.seuic.hisense.utils.NetWorkUtils;
import com.seuic.hisense.utils.ScannerHelper;
import com.seuic.hisense.utils.SoundVibratorManager;

/**
 * 商品盘点
 */
public class pandianOpenBillFragment extends BaseFragment {


    Spinner spn_Type;//盘点类型
    Spinner spn_PDNO;//盘点任务号
    Button btnDownloadPD;//下载
    Button btnOK;//确定
    Button btnCancel;//取消

    private  boolean isDownStoreIn = false;//是否已经下载库存

    SpinnerAdapter<String> pandianTypeAdapter;
    SpinnerAdapter<String> pandianPDNOAdapter;//盘点任务号


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pandian_openbill, null);
        initView(view, inflater);
        return view;
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).setTitle("盘点开单");
//        this.enabelTimer = true;//开启定时器
        //refreshData();

        super.onResume();
    }

    private void initView( View myView,LayoutInflater mInflater){

        //盘点类型
        spn_Type = (Spinner)myView.findViewById(R.id.spn_Type);
        String[] arrays = myView.getContext().getResources().getStringArray(R.array.pandianType);
        pandianTypeAdapter = new SpinnerAdapter<String>(myView.getContext(), R.layout.spinner_item, arrays);
        spn_Type.setAdapter(pandianTypeAdapter);
        spn_Type.setSelection(0);
        spn_Type.setEnabled(true);

        //盘点任务号
        spn_PDNO = (Spinner)myView.findViewById(R.id.spn_PDNO);
        /*pandianPDNOAdapter = new SpinnerAdapter<String>(myView.getContext(), R.layout.spinner_item, null);
        spn_PDNO.setAdapter(pandianPDNOAdapter);*/
        spn_PDNO.setSelection(0);
        spn_PDNO.setEnabled(true);

        btnDownloadPD = (Button)myView.findViewById(R.id.btnDownloadPD);//下载
        btnDownloadPD.setOnClickListener(this);

        btnOK = (Button)myView.findViewById(R.id.btnOK);//确定
        btnOK.setOnClickListener(this);

        btnCancel = (Button)myView.findViewById(R.id.btnCancel);//取消
        btnCancel.setOnClickListener(this);

        /************************列表初始化end*********************/

        //ServiceImp2.getInstance().setServiceCallback(this);

        //清除之前的库存
        //clearStoreIn();

        //加载上一次保留的数据  异步操作，加快界面显示
        mHandler.sendEmptyMessage(MSG_load);

    }

    //加载上一次保留的数据
    private void loadData(){

        /*if(dbManager.ExistStoreInfo(BussinessType)){
            isDownStoreIn = true;
            spn_SiteCode.setEnabled(false);
            btnDownloadPD.setEnabled(false);
            refreshData();

            String SiteName = OperateProperties.getInstance().getValue("SiteName_ZCSM_DJZX");
            //恢复下一站
            if(nextSiteArray != null && nextSiteArray.length > 0 && SiteName!=null && SiteName.length()>0){
                int index = 0;
                for(String item : nextSiteArray){
                    if(item.equals(SiteName)){
                        spn_SiteCode.setSelection(index);
                        break;
                    }
                    index ++;
                }
            }

            //恢复车牌号
            String cph = OperateProperties.getInstance().getValue("CPH_ZCSM_DJZX");
            editCPH.setText(cph);

            //恢复司机
            String DriverCode = OperateProperties.getInstance().getValue("DriverCode_ZCSM_DJZX");
            editDriverCode.setText(DriverCode);
        }*/
    }



    private void deleteAlert(final String barcode) {

        SoundVibratorManager.playSound(2, 1);
        String tipMessage = String.format("重复子单号！是否要删除%s ？", barcode);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("系统提示");
        builder.setMessage(tipMessage);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (AlertDialog.BUTTON_POSITIVE == which) {

                    //删除
                    try {
                        //DbExecutor.getIntance().executeSql("delete from t_StoreInfo where SMALL_TAGNUMBER=? and BussinessType=? ", new String[]{barcode,BussinessType});

                        //刷新
                        SoundVibratorManager.playSound(1, 1);
                        ((MainActivity)getActivity()).countDecrease();//状态栏数量减1 countDecrease
                        refreshData();//删除后重新刷新
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void queryEmployee(){
        /*try {
            String employeeCode = editDriverCode.getText().toString().trim();
            if(employeeCode.length() == 0){
                editDriverName.setText("");
                return;
            }
            List<DriverBase> listTemp = DbExecutor.getIntance().executeQuery("select * from t_baseDriver where EMPLOYEE_CODE=?", new String[]{employeeCode}, DriverBase.class);
            if (listTemp != null && listTemp.size() > 0) {
                editDriverName.setText(listTemp.get(0).getEMPLOYEE_NAME());
            }else {
                editDriverName.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void callBackOnResult(Object object) {
        try {
            /*if (object != null) {
                if (object instanceof DriverBase) {
                    DriverBase info = (DriverBase) object;
                    editDriverCode.setText(info.getEMPLOYEE_CODE());
                    editDriverName.setText(info.getEMPLOYEE_NAME());
                }else if(object instanceof SiteInfo){
                    SiteInfo info = (SiteInfo) object;
                    editSiteName.setText(info.getSiteName());
                    nextSiteName = info.getSiteName();
                }else if(object.toString().equals("refresh")) {
                    refreshData();//刷新
                }else if(object.toString().startsWith("CPH")){
                    String cph = object.toString().replace("CPH","");
                    editCPH.setText(cph);
                }
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.callBackOnResult(object);
    }



    //提交结果返回
    static final  int MSG_Submit_Success = 0;
    static final  int MSG_Submit_Fail = 1;
    static final  int MSG_info = 2;
    static final  int MSG_refreshData = 3;
    static final  int MSG_Identification = 4;//验证大小票
    static final  int MSG_load = 5;//加载上一次保留的数据
    static final  int MSG_down = 6;//继续分页下载库存
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_Submit_Success:
                    AlertDialogHelper.getInstance().showMessage(pandianOpenBillFragment.this.getActivity(), msg.getData().getString("errorMsg"));
                    clearData();//清空界面数据
                    break;
                case MSG_info:
                    if(isDownStoreIn){
                        //spn_SiteCode.setEnabled(false);//下载成功后，禁用下一站
                        btnDownloadPD.setEnabled(false);
                    }
                    AlertDialogHelper.getInstance().showMessage(pandianOpenBillFragment.this.getActivity(),msg.getData().getString("errorMsg"));
                    break;
                case MSG_refreshData:
                    String info = msg.getData().getString("errorMsg");
                    if(TextUtils.isEmpty(info) == false){
                        AlertDialogHelper.getInstance().showMessage(pandianOpenBillFragment.this.getActivity(), info);
                    }
                    if(isDownStoreIn){
                        //spn_SiteCode.setEnabled(false);//下载成功后，禁用下一站
                        btnDownloadPD.setEnabled(false);
                    }
                    refreshData();
                    break;
                case MSG_load:
                    loadData();
                    break;

            }
        }
    };


    @Override
    public void finish(){

        if(isDownStoreIn ){

            //如果有未提交的数据，返回时，判断是提交还是删除
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
            builder.setMessage("确定要返回？返回后将清除界面和暂存数据\r\n是：清除数据\r\n否：保留数据");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (AlertDialog.BUTTON_POSITIVE == which) {
                        clearData();
                        mFHelper.transcateBack();
                    }
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    mFHelper.transcateBack();
                }
            });
            builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }else {
            mFHelper.transcateBack();
        }


    }

    @Override
    public void onClick(View v) {
        if (FastClick.isFastClick()){
            return;
        }
        switch (v.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.btnDownloadPD://下载
                //mFHelper.transcateFoward(FragmentFactory.QueryDestination_Fragment);
                break;
            case R.id.btnOK://确定
                mFHelper.transcateFoward(FragmentFactory.pandianScan_Fragment);
                break;
            case R.id.btnCancel://取消
                finish();
                break;
        }
        super.onClick(v);
    }

    private void clearData(){

       /* editCPH.setText("");


        editDriverCode.setText("");
        editDriverName.setText("");

        editBillCode.setText("");

        clearStoreIn();//清除库存
        spn_SiteCode.setEnabled(true);
        btnDownloadPD.setEnabled(true);
        list_AdapterData.clear();
        refreshData();*/
    }

    private void resetData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("将清除所有界面和暂存数据，确定重置吗？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (AlertDialog.BUTTON_POSITIVE == which) {
                    //返回之前清除库存数据
                    clearData();
                }
            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void downStoreInAlert() {
        if(isDownStoreIn){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("警告");
            builder.setMessage("重新下载库存，并清空扫描数据，确定吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (AlertDialog.BUTTON_POSITIVE == which) {
                       /* currentPageIndex = 0;//库存下载页索引
                        needPageCount = 0;//库存下载一共需要分多少页
                        downStoreIn();*/
                    }
                }
            });
            builder.setNegativeButton("取消", null);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }else {
            //downStoreIn();
        }

    }



    private void submitAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定要上传数据？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(AlertDialog.BUTTON_POSITIVE == which){
                    submit();
                }
            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void submit(){

/*        BaseFragment fragment = mFFactory.getFragment(FragmentFactory.ZCSM_Modify_Fragment);
        Bundle bundle = new Bundle();
        bundle.putString("listCode","");
        bundle.putString("bussinessName","修改大票件数/删除小票子单号");
        fragment.setArguments(bundle);
        mFHelper.transcateFoward(fragment);*/

        if (!NetWorkUtils.isNetworkConnected(getActivity())) {
            AlertDialogHelper.getInstance().showMessage(getActivity(),"网络不通！ ");
            return;
        }
        MyDialog.getInstance().setContext(getActivity(),this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("正在上传数据……");
        /*ServiceImp.getInstance().setServiceCallback(this);
        new Thread(){
            @Override
            public void run() {
                BackgroundThread mBackgroundThread = new BackgroundThread(ServiceImp.getInstance(),ServiceImp2.getInstance());
                *//**
                 *
                 * @param INTERFACETYPE 类型 如 "始发站点"
                 * @param carLineCode 车线
                 * @param cph 车牌号
                 * @param Wareno 库位
                 * @param driverCode 司机工号
                 * @param driverName 司机
                 * @param nextSiteName 下一站网点名称
                 *//*
                String cph = editCPH.getText().toString().trim();
                String driverCode = editDriverCode.getText().toString().trim();
                String driverName = editDriverName.getText().toString().trim();
                //
                mBackgroundThread.UpMain(BussinessType,"到件中心","",cph,"",driverCode,driverName,nextSiteName);



                super.run();
            }
        }.start();*/

    }


    /**
     * 获取数据
     */
    private void refreshData() {
        try {

            /*if (list_AdapterData == null) {
                list_AdapterData = new ArrayList<t_temp>();
            }
            list_AdapterData.clear();

            //主单数据
            //List<GetStoreInfoResponse> list = DbExecutor.getIntance().executeQuery("select * from t_StoreInfo where listCode=? and scanStatus=1 and upload=0 order by scanDate desc ", new String[]{listCode}, GetStoreInfoResponse.class);
            //List<GetStoreInfoResponse> list = DbExecutor.getIntance().executeQuery("select * from t_StoreInfo where  scanumber>0 order by scandate desc ",null, GetStoreInfoResponse.class);
            //
            //String sql = "select sum(scanumber) as scanumber,sum(COME_PIECE) as COME_PIECE,N_FLAG,BILL_CODE,SETTLEMENT_WEIGHT,CUBES,SMALL_TAGNUMBER from t_StoreInfo where  scanumber>0 and BussinessType=? group by BILL_CODE  order by scandate desc ";
            String sql = "select sum(scanumber) as scanumber,BILL_CODE ,(select sum(COME_PIECE) from t_StoreInfo a  where a.BILL_CODE=b.BILL_CODE and BussinessType=?) as COME_PIECE,N_FLAG,SETTLEMENT_WEIGHT,CUBES,SMALL_TAGNUMBER from t_StoreInfo b where  scanumber>0 and BussinessType=?  group by BILL_CODE  order by scandate desc";
            sql += " limit 0,100";
            List<GetStoreInfoResponse> list = DbExecutor.getIntance().executeQuery(sql, new String[]{BussinessType,BussinessType}, GetStoreInfoResponse.class);
            double totalW = 0;
            double totalV = 0;
            double W = 0;
            double V = 0;
            if (list != null && list.size() > 0) {
                for (GetStoreInfoResponse model : list) {
                    t_temp temp = new t_temp();
                    temp.setType(model.getN_FLAG());//1大单 0小单
                    temp.setBarcode(model.getBILL_CODE());//主单条码
                    temp.setCount1((int) model.getCOME_PIECE());//库存数量
                    temp.setCount2(model.getScanumber());//实际扫数量

                    if(model.getN_FLAG() == 1){//大票
                        W = model.getSETTLEMENT_WEIGHT();//重量
                        V = model.getCUBES();//体积
                    }else {//小票
                        W = temp.getCount2() * model.getSETTLEMENT_WEIGHT();//重量
                        V = temp.getCount2() * model.getCUBES();//体积
                    }
                    W = Common.doubletoTwoDecimal(W);
                    V = Common.doubletoTwoDecimal(V);
                    temp.setDesc1(W + "");//重量
                    temp.setDesc2(V + "");//体积
                    totalW += W;
                    totalV += V;

                    temp.setDesc3(model.getSMALL_TAGNUMBER());
                    list_AdapterData.add(temp);
                }
            }

            tv_weightTotal.setText("合计重量：" + Common.doubletoTwoDecimal(totalW));
            tv_VolumnTotal.setText("合计体积：" + Common.doubletoTwoDecimal(totalV));
*/

        } catch (Exception ex) {
            LogHelper.e(pandianOpenBillFragment.class, ex, "refreshData");
        }

        //更新列表
    }



}
