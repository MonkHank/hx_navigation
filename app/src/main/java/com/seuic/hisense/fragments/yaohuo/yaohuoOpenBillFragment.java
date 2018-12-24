package com.seuic.hisense.fragments.yaohuo;

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
 * 要货开单
 */
public class yaohuoOpenBillFragment extends BaseFragment {

    Spinner spn_Type; // 要货类型  (盘点类型)
    SpinnerAdapter<String> yaohuoTypeAdapter;
    Button btnOK;
    Button btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_yaohuo_openbill, null);
        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setTitle("要货开单");
    }

    private void initView(View myView) {
        spn_Type = (Spinner) myView.findViewById(R.id.spn_Type);// 存放地点
        String[] arrays = myView.getContext().getResources().getStringArray(R.array.pandianType);
        yaohuoTypeAdapter = new SpinnerAdapter<String>(myView.getContext(), R.layout.spinner_item, arrays);
        spn_Type.setAdapter(yaohuoTypeAdapter);
        spn_Type.setSelection(0);
        spn_Type.setEnabled(true);

        btnOK = (Button) myView.findViewById(R.id.btnOK); // 确定
        btnOK.setOnClickListener(this);

        btnCancel = (Button) myView.findViewById(R.id.btnCancel); // 取消
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                mFHelper.transcateFoward(FragmentFactory.yaohuoScan_Fragment);// 要货扫描
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}
