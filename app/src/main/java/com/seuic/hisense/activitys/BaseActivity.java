package com.seuic.hisense.activitys;

import android.app.Activity;

import com.seuic.hisense.httpService.IServiceCallback;


/**
 * @author Administrator
 * @date 2015/10/22.
 */
public class BaseActivity extends Activity implements IServiceCallback {
    @Override
    public void addUserCallback(boolean b) {

    }

    @Override
    public void loginCallback(String result) {

    }

    @Override
    public void INavOperateIntfCallback(String result) {

    }

   /* @Override
    public void getDestinationInfoCallBack(int status, String msg, List<DestinationInfo> list) {

    }

    @Override
    public void getEmployeeInfoCallBack(int status, String msg, List<EmployeeInfo> list) {

    }
*/


}
