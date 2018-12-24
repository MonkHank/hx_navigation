package com.seuic.hisense.fragments;


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
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.views.QueryEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class QueryScanFragment extends BaseFragment implements QueryEditText.OnClickQueryEditText {
    private QueryEditText et_query_data;
    private ListView lv_destination;
    // 双击事件记录最近一次点击的ID
    private int lastClickId=-100;

    // 双击事件记录最近一次点击的时间
    private long lastClickTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_query_scan, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).setTitle("盘点开单查询");

        super.onResume();
    }

    private void initView(View view){
        et_query_data = (QueryEditText)view.findViewById(R.id.et_query_data);
        et_query_data.setOnClickQueryEditText(this);

        lv_destination = (ListView)view.findViewById(R.id.lv_destination);
        lv_destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 如果是双击,1秒内连续点击判断为双击
                if (lastClickId == position
                        && (Math.abs(lastClickTime - System.currentTimeMillis()) < 1000)) {
                    lastClickId = -100;
                    lastClickTime = 0;
                    //////mFHelper.transcateBackOnResult(listTemp.get(position - 1));

                } else {
                    lastClickId = position;
                    lastClickTime = System.currentTimeMillis();
                }
            }
        });

        View view1 = View.inflate(getActivity(),R.layout.list_query_scan_title, null);
        lv_destination.addHeaderView(view1);
    }

    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    //////List<SiteLineDetail> listTemp;
    ListViewAdapter adapter;
    private void initData(){
        try{
  /*          listTemp = DbExecutor.getIntance().executeQuery("select C_LINECODE,C_LINENAME,C_CARNO,BL_WORKOVER from t_siteLine limit 100",null,SiteLineDetail.class);
            if(listTemp!=null&&listTemp.size()>0){
                for(SiteLineDetail item:listTemp){
                    HashMap<String,String> hashmap = new HashMap<String,String>();
                    hashmap.put("C_LINECODE",item.getC_LINECODE());
                    hashmap.put("C_LINENAME",item.getC_LINENAME());
                    hashmap.put("C_CARNO",item.getC_CARNO());
                    list.add(hashmap);
                }
            }*/
            adapter = new ListViewAdapter(getActivity(),list);
            lv_destination.setAdapter(adapter);

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

    /**
     * 查询数据
     */
    private void refreshData(){
        String key = et_query_data.getQueryEditText().trim();
        if(!TextUtils.isEmpty(key)){
            try{
                list.clear();
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

                adapter.notifyDataSetChanged();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }
        switch (v.getId()){


        }
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context context;                        //运行上下文
        private ArrayList<HashMap<String,String>> listItems;    //商品信息集合
        private LayoutInflater listContainer;           //视图容器
        public final class ListItemView{                //自定义控件集合
            public TextView tvPLUCODE;
            public TextView tvPLUNAME;
            public TextView tvBARCODE;
            public TextView tvCOUNT;
        }


        public ListViewAdapter(Context context, ArrayList<HashMap<String,String>> listItems) {
            this.context = context;
            listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
            this.listItems = listItems;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return listItems.size();
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return listItems.get(arg0);
        }

        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }


        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int selectID = position;
            //自定义视图
            ListItemView  listItemView = null;
            if (convertView == null) { //C_LINECODE,C_LINENAME,C_CARNO
                listItemView = new ListItemView();
                //获取list_item布局文件的视图
                convertView = listContainer.inflate(R.layout.list_query_scan, null);
                listItemView.tvPLUCODE = ((TextView)convertView.findViewById(R.id.list_tvPLUCODE));
                listItemView.tvPLUNAME = ((TextView)convertView.findViewById(R.id.list_tvPLUNAME));
                listItemView.tvBARCODE = ((TextView)convertView.findViewById(R.id.list_tvBARCODE));
                listItemView.tvCOUNT = ((TextView)convertView.findViewById(R.id.list_tvCOUNT));

                //设置控件集到convertView
                convertView.setTag(listItemView);
            }else {
                listItemView = (ListItemView)convertView.getTag();
            }
            listItemView.tvPLUCODE.setText((String) listItems.get(
                    position).get("PLUCODE"));
            listItemView.tvPLUNAME.setText((String) listItems.get(position)
                    .get("PLUNAME"));
            listItemView.tvBARCODE.setText((String) listItems.get(position)
                    .get("BARCODE"));
            listItemView.tvCOUNT.setText((String) listItems.get(position)
                    .get("COUNT"));

            return convertView;
        }
    }


}
