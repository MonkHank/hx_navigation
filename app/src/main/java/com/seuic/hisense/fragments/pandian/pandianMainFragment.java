package com.seuic.hisense.fragments.pandian;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
import com.seuic.hisense.adapters.SpinnerAdapter;

import com.seuic.hisense.constant.Common;
import com.seuic.hisense.entity.t_temp;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.utils.AlertDialogHelper;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.utils.FragmentFactory;
import com.seuic.hisense.utils.LogHelper;
import com.seuic.hisense.utils.MyDialog;
import com.seuic.hisense.utils.NetWorkUtils;
import com.seuic.hisense.utils.SoundVibratorManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * 商品盘点
 */
public class pandianMainFragment extends BaseFragment {


    Spinner spn_status;//上传状态
    Button btnDelete;//删除
    Button btnDeleteAll;//删除全部
    Button btnUploadSingle;//单个上传
    Button btnUploadAll;//全部上传
    Button btnOpenBill;//开单
    Button btnModify;//修改
    TextView tv_totalCount;

    private  boolean isDownStoreIn = false;//是否已经下载库存

    MyAdapter myAdapter;
    ListView mListView;

    private TextView tv_QuerySite;

    SpinnerAdapter<String> statusAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pandian_main, null);
        initView(view, inflater);
        return view;
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).setTitle("商品盘点");

        //refreshData();

        super.onResume();
    }

    private void initView(final View myView, LayoutInflater mInflater){

        spn_status = (Spinner)myView.findViewById(R.id.spn_status);//上传状态
        String[] arrays = myView.getContext().getResources().getStringArray(R.array.uploadStatus);
        statusAdapter = new SpinnerAdapter<String>(myView.getContext(), R.layout.spinner_item, arrays);
        spn_status.setAdapter(statusAdapter);
        spn_status.setSelection(0);
        spn_status.setEnabled(true);

        btnDelete = (Button)myView.findViewById(R.id.btnDelete);//删除
        btnDelete.setOnClickListener(this);

        btnDeleteAll = (Button)myView.findViewById(R.id.btnDeleteAll);//删除全部
        btnDeleteAll.setOnClickListener(this);

        btnUploadSingle = (Button)myView.findViewById(R.id.btnUploadSingle);//单个上传
        btnUploadSingle.setOnClickListener(this);

        btnUploadAll = (Button)myView.findViewById(R.id.btnUploadAll);//全部上传
        btnUploadAll.setOnClickListener(this);

        btnOpenBill = (Button)myView.findViewById(R.id.btnOpenBill);//开单
        btnOpenBill.setOnClickListener(this);

        btnModify = (Button)myView.findViewById(R.id.btnModify);//修改
        btnModify.setOnClickListener(this);

        tv_totalCount = (TextView)myView.findViewById(R.id.tv_totalCount);

        /************************列表初始化start*********************/
        mListView =(ListView)myView.findViewById(R.id.list1);
        /*View view = mInflater.inflate(R.layout.list_pandian_main_title, null);
        mListView.addHeaderView(view);*/
        myAdapter = new MyAdapter(pandianMainFragment.this.getActivity());
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0) {
                    return;
                }

                int lastPosition = myAdapter.getSelectedPosition();//获取上次选中的行索引号
                if(lastPosition > -1){
                    //获取列表显示对应的索引号
                    int index = lastPosition - mListView.getFirstVisiblePosition();
                    if(index > -1){
                        //恢复上次选中行的默认背景色
                        View lastView = mListView.getChildAt(index);
                        if(lastView != null){//因上下滑动后，上次选中的view可能获取不到了，为空
                            myAdapter.setDefaultBackground(lastView,lastPosition);
                        }

                    }
                }

                //重新设置选中的行索引号
                myAdapter.setSelectedPosition(position);//显示黄色
                myAdapter.setSelectedBackground(view);



                //点击列表里的记录，显示相应的条码在条码输入框
                /*if (list_AdapterData.get(position).getType() == 1) {
                    editBillCode.setText(list_AdapterData.get(position).getBarcode());
                } else {
                    String subbarcode = list_AdapterData.get(position).getDesc3();
                    boolean isSpec1 = Common.IsSpec1(subbarcode);
                    if(isSpec1){//15位特殊条码的，显示原始条码就行
                        editBillCode.setText(list_AdapterData.get(position).getBarcode());
                    }else {
                        editBillCode.setText(subbarcode);
                    }
                }*/
            }
        });


        /************************列表初始化end*********************/

        //ServiceImp2.getInstance().setServiceCallback(this);

        //清除之前的库存
        //clearStoreIn();

        //加载上一次保留的数据  异步操作，加快界面显示
        mHandler.sendEmptyMessage(MSG_load);


    }

    //加载上一次保留的数据
    private void loadData(){

        refreshData();//刷新数据


        /*if(dbManager.ExistStoreInfo(BussinessType)){
            isDownStoreIn = true;
            spn_SiteCode.setEnabled(false);
            btnDelete.setEnabled(false);
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
                    AlertDialogHelper.getInstance().showMessage(pandianMainFragment.this.getActivity(), msg.getData().getString("errorMsg"));
                    clearData();//清空界面数据
                    break;
                case MSG_info:
                    if(isDownStoreIn){
                        //spn_SiteCode.setEnabled(false);//下载成功后，禁用下一站
                        btnDelete.setEnabled(false);
                    }
                    AlertDialogHelper.getInstance().showMessage(pandianMainFragment.this.getActivity(),msg.getData().getString("errorMsg"));
                    break;
                case MSG_refreshData:
                    String info = msg.getData().getString("errorMsg");
                    if(TextUtils.isEmpty(info) == false){
                        AlertDialogHelper.getInstance().showMessage(pandianMainFragment.this.getActivity(), info);
                    }
                    if(isDownStoreIn){
                        //spn_SiteCode.setEnabled(false);//下载成功后，禁用下一站
                        btnDelete.setEnabled(false);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定要返回？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (AlertDialog.BUTTON_POSITIVE == which) {
                    mFHelper.transcateBack();
                }
            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

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
            case R.id.btnDelete://删除
                deleteAlert();
                break;
            case R.id.btnDeleteAll://删除全部
                deleteAllAlert();
                break;
            case R.id.btnUploadSingle://单个上传
                //mFHelper.transcateFoward(FragmentFactory.QueryDriver_Fragment);
                break;
            case R.id.btnUploadAll://全部上传
                break;
            case R.id.btnOpenBill://开单
                mFHelper.transcateFoward(FragmentFactory.pandianOpenBill_Fragment);
                break;
            case R.id.btnModify://修改
                mFHelper.transcateFoward(FragmentFactory.pandianScan_Fragment);
                break;

        }
        super.onClick(v);
    }

    private void deleteAlert() {

        if(myAdapter.getCount() < 1){
            return;
        }

        final int selectedPosition = myAdapter.getSelectedPosition();
        if(selectedPosition < 0){
            AlertDialogHelper.getInstance().showMessage(getActivity(), "请先选中一条记录");
            return;
        }

        t_temp temp = (t_temp)myAdapter.getItem(selectedPosition);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定要删除单据？\n"+temp.getDesc2());
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (AlertDialog.BUTTON_POSITIVE == which) {
                       list_AdapterData.remove(selectedPosition);
                       notifyDataSetChanged();//刷新列表

                    int positoin = myAdapter.getSelectedPosition();
                    positoin = positoin +1;
                }
            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void deleteAllAlert() {

        if(myAdapter.getCount() < 1){
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定要删除所有单据？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (AlertDialog.BUTTON_POSITIVE == which) {
                    list_AdapterData.clear();
                    notifyDataSetChanged();//刷新列表
                }
            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void clearData(){

       /* editCPH.setText("");


        editDriverCode.setText("");
        editDriverName.setText("");

        editBillCode.setText("");

        clearStoreIn();//清除库存
        spn_SiteCode.setEnabled(true);
        btnDelete.setEnabled(true);
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

            if (list_AdapterData == null) {
                list_AdapterData = new ArrayList<t_temp>();
            }
            list_AdapterData.clear();

            String date = Common.GetSysDate();
            String billCode = "";
            Random random=new Random();//用于生成随机数
            String randomNumber = "";//
            for(int i=0;i<20;i++){
                t_temp temp = new t_temp();
                temp.setDesc1(date);

                randomNumber = ""+random.nextInt(9) + random.nextInt(9) + random.nextInt(9) ;//生成3位随机数
                billCode = "PD001"+Common.GetSysTime3() + randomNumber;
                temp.setDesc2(billCode);

                temp.setDesc3("001");

                temp.setDesc4("1000");

                list_AdapterData.add(temp);
            }

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

            tv_totalCount.setText("合计重量：" + Common.doubletoTwoDecimal(totalW));
            tv_VolumnTotal.setText("合计体积：" + Common.doubletoTwoDecimal(totalV));
*/

        } catch (Exception ex) {
            LogHelper.e(pandianMainFragment.class, ex, "refreshData");
        }

        //更新列表
        notifyDataSetChanged();
    }

    /**
     * 更新列表
     */
    private void notifyDataSetChanged() {
        if (list_AdapterData == null) {
            list_AdapterData = new ArrayList<t_temp>();
        }

        myAdapter.setSelectedPosition(-1);//取消选中
        myAdapter.notifyDataSetChanged();

        tv_totalCount.setText("共：" + list_AdapterData.size() + "单");

       /* MyAdapter myAdapter1 = (MyAdapter) mListView.getAdapter();
        myAdapter1.notifyDataSetChanged();*/

       /* //因增加了HeaderView，这里要做个转换
        HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter) mListView.getAdapter();
        MyAdapter myAdapter1 = (MyAdapter) headerAdapter.getWrappedAdapter();
        myAdapter1.notifyDataSetChanged();*/

    }


    ArrayList<t_temp> list_AdapterData = new ArrayList<t_temp>();
    class MyAdapter extends BaseAdapter {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        /**
         * 定义所包含的控件
         */
        private class ViewHolder {
            TextView tvCreateDate;//录入日期
            TextView tvBillCode;//单号
            TextView tvStaffCode;//操作员
            TextView tvTotalCount;//总数量
        }



        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return list_AdapterData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub

            return list_AdapterData.get(arg0);

        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            try {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_pandian_main, null);
                    holder = new ViewHolder();
                    holder.tvCreateDate = (TextView) convertView.findViewById(R.id.list_tvCreateDate);
                    holder.tvBillCode = (TextView) convertView.findViewById(R.id.list_tvBillCode);
                    holder.tvStaffCode = (TextView) convertView.findViewById(R.id.list_tvStaffCode);
                    holder.tvTotalCount = (TextView) convertView.findViewById(R.id.list_tvTotalCount);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                t_temp info = list_AdapterData.get(position);
                holder.tvCreateDate.setText(info.getDesc1());
                holder.tvBillCode.setText(info.getDesc2());
                holder.tvStaffCode.setText(info.getDesc3());
                holder.tvTotalCount.setText(info.getDesc4());

          /*      if(info.getCount1() == info.getCount2() ){
                    convertView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new3));
                }else {
                    convertView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new4));
                }*/


                //设置各行的默认背景色
                setDefaultBackground(convertView,position);

                /*if (position % 2 == 0) {
                    //convertView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new));
                    convertView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new));
                } else {
                    convertView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new2));
                }*/

                //选中时，重新显示背景颜色
                if (position == selectedPosition) {
                    //convertView.setBackgroundColor(Color.BLUE);
                    //convertView.setBackground(getResources().getDrawable(R.color.orange));
                    setSelectedBackground(convertView);
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return convertView;
        }

        //记录当前选中的行索引号
        private int selectedPosition =-1;

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public void setSelectedPosition(int selectedPosition) {
            this.selectedPosition = selectedPosition;
        }

        //设置选中行的背景色
        public void setSelectedBackground(View myView){
            if(myView == null){
                return;
            }
            myView.setBackground(getResources().getDrawable(R.color.orange));
            //view.setBackgroundResource(R.color.red);  // 注意是这个方法o*/
        }

        //设置默认背景色
        public void setDefaultBackground(View myView,int position){
            if(myView == null || position < 0){
                return;
            }
            if (position % 2 == 0) {
                myView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new));
            } else {
                myView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new2));
            }
        }

    }


}
