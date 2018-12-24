package com.seuic.hisense.fragments.pifatuihuo;

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
 * 批发退货开单
 */
public class pifatuihuoOpenBillFragment extends BaseFragment {

    Button btnOK;
    Button btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_pifatuihuo_openbill, null);
        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setTitle("批发退货开单");
    }

    private void initView(View myView) {

        btnOK = (Button) myView.findViewById(R.id.btnOK); // 确定
        btnOK.setOnClickListener(this);

        btnCancel = (Button) myView.findViewById(R.id.btnCancel); // 取消
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                mFHelper.transcateFoward(FragmentFactory.pifatuihuoScan_Fragment);// 批发退货扫描
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}
