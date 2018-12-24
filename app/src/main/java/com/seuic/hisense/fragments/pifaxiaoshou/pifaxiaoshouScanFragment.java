package com.seuic.hisense.fragments.pifaxiaoshou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.utils.FragmentFactory;

/**
 * Created by someone on 2017/4/18.
 */

/**
 * 批发扫描
 */
public class pifaxiaoshouScanFragment extends BaseFragment {

    Button btnQuery; // 查询

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_pifaxiaoshou_scan, null);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        getActivity().setTitle("采购扫描");
        ((MainActivity)getActivity()).setTitle("批发扫描");
    }

    private void initView(View view) {
        btnQuery = (Button)view.findViewById(R.id.btnQuery); // 查询
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnQuery: // 查询
                mFHelper.transcateFoward(FragmentFactory.pifaxiaoshouQuery_Fragment);
                break;
        }
    }
}
