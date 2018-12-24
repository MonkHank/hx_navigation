package com.seuic.hisense.fragments.caigou;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.views.QueryEditText;

/**
 * Created by someone on 2017/4/18.
 */

/**
 * 采购查询
 */
public class caigouQueryScanFragment extends BaseFragment implements QueryEditText.OnClickQueryEditText{

     QueryEditText et_query_data;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_caigou_query_scan, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        et_query_data = (QueryEditText) view.findViewById(R.id.et_query_data);
        et_query_data.setOnClickQueryEditText(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setTitle("采购查询");
    }

    @Override
    public void ClickQueryEditText() {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
