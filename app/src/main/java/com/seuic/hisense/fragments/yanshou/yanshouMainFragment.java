package com.seuic.hisense.fragments.yanshou;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by someone on 2017/4/18.
 */

/**
 * 商品验收
 */
public class yanshouMainFragment extends BaseFragment {
    Spinner spn_status;//上传状态
    Button btnDelete;//删除
    Button btnDeleteAll;//删除全部
    Button btnUploadSingle;//单个上传
    Button btnUploadAll;//全部上传
    Button btnOpenBill;//开单
    Button btnModify;//修改
    TextView tv_totalCount; // 总单数量

    SpinnerAdapter<String> statusAdapter;
    ListView mListView;
    private MyAdapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yanshou_main, null);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setTitle("商品验收");
    }

    private void initView(View myView) {
        spn_status = (Spinner) myView.findViewById(R.id.spn_status);
        String[] arrays = myView.getResources().getStringArray(R.array.uploadStatus);
        statusAdapter = new SpinnerAdapter<>(myView.getContext(), R.layout.spinner_item, arrays);
        spn_status.setAdapter(statusAdapter);
        spn_status.setSelection(0);
        spn_status.setEnabled(true);

        tv_totalCount = (TextView) myView.findViewById(R.id.tv_totalCount); // 单据数量

        btnDelete = (Button) myView.findViewById(R.id.btnDelete); // 删除
        btnDelete.setOnClickListener(this);

        btnDeleteAll = (Button) myView.findViewById(R.id.btnDeleteAll); // 删除全部
        btnDeleteAll.setOnClickListener(this);

        btnUploadSingle = (Button) myView.findViewById(R.id.btnUploadSingle); // 单个上传
        btnUploadSingle.setOnClickListener(this);

        btnUploadAll = (Button) myView.findViewById(R.id.btnUploadAll); // 全部上传
        btnUploadAll.setOnClickListener(this);

        btnOpenBill = (Button) myView.findViewById(R.id.btnOpenBill); // 开单
        btnOpenBill.setOnClickListener(this);

        btnModify = (Button) myView.findViewById(R.id.btnModify); // 修改
        btnModify.setOnClickListener(this);

        /************************列表初始化start*********************/
        mListView = (ListView) myView.findViewById(R.id.list1);
        myAdapter = new MyAdapter(yanshouMainFragment.this.getActivity());
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0) {
                    return;
                }

                int lastPosition = myAdapter.getSelectedPosition();//获取上次选中的行索引号
                if (lastPosition > -1) {
                    //获取列表显示对应的索引号
                    int index = lastPosition - mListView.getFirstVisiblePosition();
                    if (index > -1) {
                        //恢复上次选中行的默认背景色
                        View lastView = mListView.getChildAt(index);
                        if (lastView != null) {//因上下滑动后，上次选中的view可能获取不到了，为空
                            myAdapter.setDefaultBackground(lastView, lastPosition);
                        }
                    }
                }
                //重新设置选中的行索引号
                myAdapter.setSelectedPosition(position);
                //显示黄色
                myAdapter.setSelectedBackground(view);
            }

        });
        /************************列表初始化end*********************/

        // 数据初始化
        refreshData();
    }

    @Override
    public void onClick(View v) {
        if (FastClick.isFastClick())
            return ;
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
                break;
            case R.id.btnUploadAll://全部上传
                break;
            case R.id.btnOpenBill://开单
                mFHelper.transcateFoward(FragmentFactory.yanshouOpenBill_Fragment);
                break;
            case R.id.btnModify://修改
                mFHelper.transcateFoward(FragmentFactory.yanshouScan_Fragment);
                break;

        }
    }

    private void deleteAlert() {
        if(myAdapter.getCount() < 1)
            return;

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
                }
            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void deleteAllAlert() {
        if(myAdapter.getCount() < 1)
            return;

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

    ArrayList<t_temp> list_AdapterData = new ArrayList<t_temp>();
    class MyAdapter extends BaseAdapter {
        private Context context;// 此处是用来通过布局获取view对象用的；

        public MyAdapter(Context context) {
            this.context = context;
        }

        private class ViewHolder {
            TextView tvCreateDate;//录入日期
            TextView tvBillCode;//单号
            TextView tvStaffCode;//操作员
            TextView tvProviderName; // 供应商名称
        }

        @Override
        public int getCount() {
            return list_AdapterData.size();
        }

        @Override
        public Object getItem(int position) {
            return list_AdapterData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                // getContext()会崩溃
//                convertView = View.inflate(getContext(), R.layout.list_caigou_main, null);
                convertView = View.inflate(context, R.layout.list_caigou_main, null);

                holder = new ViewHolder();
                holder.tvCreateDate = (TextView) convertView.findViewById(R.id.list_tvCreateDate);// 录入日期
                holder.tvBillCode = (TextView) convertView.findViewById(R.id.list_tvBillCode);// 单据号
                holder.tvStaffCode = (TextView) convertView.findViewById(R.id.list_tvStaffCode);// 操作员
                holder.tvProviderName = (TextView) convertView.findViewById(R.id.list_tvProviderName);//供应商名称
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            t_temp info = (t_temp) getItem(position);
            holder.tvCreateDate.setText(info.getDesc1());
            holder.tvBillCode.setText(info.getDesc2());
            holder.tvStaffCode.setText(info.getDesc3());
            holder.tvProviderName.setText(info.getDesc4());

            //设置各行的默认背景色
            setDefaultBackground(convertView, position);

            //选中时，重新显示背景颜色
            if (position == selectedPosition) {
                setSelectedBackground(convertView);
            }
            return convertView;
        }

        //记录当前选中的行索引号
        private int selectedPosition = -1;

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public void setSelectedPosition(int selectedPosition) {
            this.selectedPosition = selectedPosition;
        }

        //设置选中行的背景色
        public void setSelectedBackground(View myView) {
            if (myView == null) {
                return;
            }
            myView.setBackground(getResources().getDrawable(R.color.orange));
            //view.setBackgroundResource(R.color.red);  // 注意是这个方法o*/
        }

        //设置默认背景色
        public void setDefaultBackground(View myView, int position) {
            if (myView == null || position < 0) {
                return;
            }
            if (position % 2 == 0) {
                myView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new));
            } else {
                myView.setBackground(getResources().getDrawable(R.drawable.clickstyle_new2));
            }
        }
    }

    /**
     * 获取数据
     */
    private void refreshData() {
        if (list_AdapterData == null) {
            list_AdapterData = new ArrayList<t_temp>();
        }
        list_AdapterData.clear();

        String date = Common.GetSysDate();
        String billCode = "";
        Random random = new Random();//用于生成随机数
        String randomNumber = "";//
        for (int i = 0; i < 20; i++) {
            t_temp temp = new t_temp();
            temp.setDesc1(date);

            randomNumber = "" + random.nextInt(9) + random.nextInt(9) + random.nextInt(9);//生成3位随机数
            billCode = "PD001" + Common.GetSysTime3() + randomNumber;
            temp.setDesc2(billCode);

            temp.setDesc3("001");

            temp.setDesc4("1000");

            list_AdapterData.add(temp);
        }
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

    }
}
