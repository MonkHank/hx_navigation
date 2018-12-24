package com.seuic.hisense.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	private static Toast mToast;
	
	public static void show(Context context , String msg){
		if(mToast == null){
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		else{
			mToast.setText(msg);
		}
		mToast.show();
	}

    public static void show(Context context , String msg, int duration){
        if(mToast == null){
            mToast = Toast.makeText(context, msg, duration);
        }
        else{
            mToast.setText(msg);
        }
        mToast.show();
    }
	
	public static void show(Context context , int resid){
		if(mToast == null){
			mToast = Toast.makeText(context, resid, Toast.LENGTH_SHORT);
		}
		else{
			mToast.setText(resid);
		}
		mToast.show();
	}
}
