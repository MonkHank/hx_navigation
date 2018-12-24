package com.seuic.hisense.utils;

import android.text.TextUtils;
import android.util.Log;

import com.seuic.hisense.constant.Common;


public class LogHelper {
	public static void e(Class<?> cls, Exception e , String method){
		if(null == cls || null == e){
			throw new NullPointerException();
		}

        LogHelper.i(cls.getName(), TextUtils.isEmpty(method) ? "" : method + ":" + e.getMessage());
	}

    public static void i(String tag,String info){
        if(Common.debug){
            Log.i(tag, info);
        }

    }
}
