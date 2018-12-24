package com.seuic.hisense.fragments;


import com.seuic.hisense.utils.FragmentHelper;
import com.seuic.hisense.utils.LogHelper;
import com.seuic.hisense.utils.ScannerHelper;

public abstract class ScanFragment extends BaseFragment {
	private static final String TAG = "ScanFragment";

    @Override
	public void onResume() {
		//启用扫描
		setScannerEnabled(true);
        LogHelper.i(TAG, "onResume");

        super.onResume();
	}

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogHelper.i(TAG, "onHiddenChanged" + hidden);
        if(hidden){
            //禁用扫描
            setScannerEnabled(false);
        }
        else {
            setScannerEnabled(true);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
	public void onPause() {
        LogHelper.i(TAG,"onPause");
        try {

            if (FragmentHelper.callBack == false) {//如果已经返回到前一个界面了，则不用禁用扫描
                //禁用扫描
                setScannerEnabled(false);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

		super.onPause();
	}

	
	private void setScannerEnabled(boolean enable){
		ScannerHelper.sendBroadcast(getActivity(), ScannerHelper.ACTION_SCANNER_ENABLED, ScannerHelper.KEY_ENABLED, enable);
	}
}
