package com.seuic.hisense.fragments.tiaobo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
import com.seuic.hisense.adapters.SpinnerAdapter;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.utils.FragmentFactory;

/**
 * Created by someone on 2017/4/18.
 */

/**
 * 调拨开单
 */
public class tiaoboOpenBillFragment extends BaseFragment {

    Spinner spn_Tiaobo; // 调拨标记
    SpinnerAdapter<String> tiaoboAdapter;
    Button btnOK;
    Button btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_tiaobo_openbill, null);
        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setTitle("店间调拨开单");
    }

    private void initView(View myView) {
        spn_Tiaobo = (Spinner) myView.findViewById(R.id.spn_Flag);// 调拨标记
        String[] arrays = myView.getContext().getResources().getStringArray(R.array.tiaoboFlag);
        tiaoboAdapter = new SpinnerAdapter<String>(myView.getContext(), R.layout.spinner_item, arrays);
        spn_Tiaobo.setAdapter(tiaoboAdapter);
        spn_Tiaobo.setSelection(0);
        spn_Tiaobo.setEnabled(true);

        btnOK = (Button) myView.findViewById(R.id.btnOK); // 确定
        btnOK.setOnClickListener(this);

        btnCancel = (Button) myView.findViewById(R.id.btnCancel); // 取消
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                mFHelper.transcateFoward(FragmentFactory.tiaoboScan_Fragment);// 店间调拨扫描
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}
