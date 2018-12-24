package com.seuic.hisense.fragments.pandian;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
import com.seuic.hisense.entity.t_temp;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.utils.LogHelper;
import com.seuic.hisense.views.QueryEditText;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class pandianQueryScanFragment extends BaseFragment implements QueryEditText.OnClickQueryEditText {
    private QueryEditText et_query_data;
    MyAdapter myAdapter;
    ListView mListView;
    TextView tv_totalCount;

    // 双击事件记录最近一次点击的ID
    private int lastClickId=-100;

    // 双击事件记录最近一次点击的时间
    private long lastClickTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_pandian_query_scan, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).setTitle("盘点开单查询");

        super.onResume();
    }

    private void initView(View myView){

        tv_totalCount = (TextView) myView.findViewById(R.id.tv_totalCount);

        et_query_data = (QueryEditText)myView.findViewById(R.id.et_query_data);
        et_query_data.setOnClickQueryEditText(this);

        /************************列表初始化start*********************/
        mListView =(ListView)myView.findViewById(R.id.list1);
        myAdapter = new MyAdapter(this.getActivity());
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

            }
        });
        /************************列表初始化end*********************/
    }

    private void initData(){
        try{

            et_query_data.setQueryEditText("00100147");
            refreshData();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void ClickQueryEditText() {
        refreshData();//查询数据
    }

    @Override
    public void afterTextChanged(Editable s) {
        refreshData();//查询数据
    }

    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }
        switch (v.getId()){


        }
    }

    /**
     * 查询数据
     */
    private void refreshData(){
        String key = et_query_data.getQueryEditText().trim();
        if(!TextUtils.isEmpty(key)){
            try{
                /*listTemp = DbExecutor.getIntance().executeQuery("select C_LINECODE,C_LINENAME,C_CARNO,BL_WORKOVER from t_siteLine where C_LINECODE like ? or C_LINENAME like ?  or C_CARNO like ? limit 100",new String[]{"%"+key+"%","%"+key+"%","%"+key+"%"},SiteLineDetail.class);
                if(listTemp!=null&&listTemp.size()>0){
                    for(SiteLineDetail item:listTemp){
                        HashMap<String,String> hashmap = new HashMap<String,String>();
                        hashmap.put("C_LINECODE",item.getC_LINECODE());
                        hashmap.put("C_LINENAME",item.getC_LINENAME());
                        hashmap.put("C_CARNO",item.getC_CARNO());
                        list.add(hashmap);
                    }

                }*/

                if (list_AdapterData == null) {
                    list_AdapterData = new ArrayList<t_temp>();
                }
                list_AdapterData.clear();

                t_temp temp = new t_temp();
                temp.setDesc1("001001476");
                temp.setDesc2("鲁花一级压榨花生油1");
                temp.setDesc3("6");
                temp.setDesc4("12");
                temp.setDesc5("72");
                list_AdapterData.add(temp);

                t_temp temp1 = new t_temp();
                temp1.setDesc1("001001477");
                temp1.setDesc2("鲁花一级压榨花生油2");
                temp1.setDesc3("6");
                temp1.setDesc4("12");
                temp1.setDesc5("72");
                list_AdapterData.add(temp1);

                //更新列表
                notifyDataSetChanged();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
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

        tv_totalCount.setText("共" + list_AdapterData.size() + "行");
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
            TextView tvXH;//序号
            TextView tvPLUCODE;//商品编码
            TextView tvPLUNAME;//品名
            TextView tvBZ;//包装list_tvZS
            TextView tvDJ;//单件
            TextView tvZS;//总数
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
             /*   TextView tvXH;//序号
                TextView tvPLUCODE;//商品编码
                TextView tvPLUNAME;//品名
                TextView tvBZ;//包装
                TextView tvDJ;//单件
                TextView tvZS;//总数*/
                MyAdapter.ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_pandian_scan, null);
                    holder = new MyAdapter.ViewHolder();
                    holder.tvXH = (TextView) convertView.findViewById(R.id.list_tvXH);
                    holder.tvPLUCODE = (TextView) convertView.findViewById(R.id.list_tvPLUCODE);
                    holder.tvPLUNAME = (TextView) convertView.findViewById(R.id.list_tvPLUNAME);
                    holder.tvBZ = (TextView) convertView.findViewById(R.id.list_tvBZ);
                    holder.tvDJ = (TextView) convertView.findViewById(R.id.list_tvDJ);
                    holder.tvZS = (TextView) convertView.findViewById(R.id.list_tvZS);
                    convertView.setTag(holder);
                } else {
                    holder = (MyAdapter.ViewHolder) convertView.getTag();
                }

                t_temp info = list_AdapterData.get(position);
                holder.tvXH.setText(""+(position+1));
                holder.tvPLUCODE.setText(info.getDesc1());
                holder.tvPLUNAME.setText(info.getDesc2());
                holder.tvBZ.setText(info.getDesc3());
                holder.tvDJ.setText(info.getDesc4());
                holder.tvZS.setText(info.getDesc5());

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
