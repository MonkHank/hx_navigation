package com.seuic.hisense.fragments.tuihuo;

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
 * 退货开单
 */
public class tuihuoOpenBillFragment extends BaseFragment {

    Spinner spn_Address; // 存放地点
    SpinnerAdapter<String> caigouAddressAdapter;
    Button btnOK;
    Button btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_tuihuo_openbill, null);
        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setTitle("退货开单");
    }

    private void initView(View myView) {
        spn_Address = (Spinner) myView.findViewById(R.id.spn_Address);// 存放地点
        String[] arrays = myView.getContext().getResources().getStringArray(R.array.caigouAddress);
        caigouAddressAdapter = new SpinnerAdapter<String>(myView.getContext(), R.layout.spinner_item, arrays);
        spn_Address.setAdapter(caigouAddressAdapter);
        spn_Address.setSelection(0);
        spn_Address.setEnabled(true);

        btnOK = (Button) myView.findViewById(R.id.btnOK); // 确定
        btnOK.setOnClickListener(this);

        btnCancel = (Button) myView.findViewById(R.id.btnCancel); // 取消
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                mFHelper.transcateFoward(FragmentFactory.tuihuoScan_Fragment);// 验收扫描
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}
