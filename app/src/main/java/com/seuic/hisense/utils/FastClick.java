package com.seuic.hisense.utils;

/**
 * Created by Administrator on 2015/9/8.
 */
public class FastClick {
    public static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
