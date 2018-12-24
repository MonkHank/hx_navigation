package com.seuic.hisense.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.seuic.hisense.httpService.IServiceCallback;
import com.seuic.hisense.utils.FragmentFactory;
import com.seuic.hisense.utils.FragmentHelper;

import java.util.Timer;
import java.util.TimerTask;

public class BaseFragment extends Fragment implements OnClickListener,IServiceCallback {

    public FragmentHelper mFHelper;
    public FragmentFactory mFFactory;

    //定时器更新数量
    private Timer timer;
    MyTimerTask myTimerTask;
    public  boolean enabelTimer = false;

    public BaseFragment(){
		mFHelper = FragmentHelper.getInstance();
		mFFactory = FragmentFactory.getInstance();
    }

    //扫描接受
    public  void onReceiveScanData(Context context , Intent intent){

    }

    @Override
    public void onResume() {
        if(enabelTimer){
            timer = new Timer(true);//true启用守护线程，主线程停止后，守护线程也停止
            myTimerTask = new MyTimerTask() ;//每次都要重新建一个对象，否则报错
            timer.schedule(myTimerTask,500, 5000); //延时500ms后执行，5秒执行一次
        }

        super.onResume();
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                RefreshNoUploadCount();//更新主界面数量
            }
            super.handleMessage(msg);
        };
    };

    //用来被重写
    public void  RefreshNoUploadCount()
    {

    }

    @Override
    public void onPause() {

        //销毁定时器
        try {
            if(enabelTimer && timer != null){
                if (myTimerTask != null){
                    myTimerTask.cancel();  //将原任务从队列中移除
                }
                timer.purge();
                timer.cancel();
                timer = null;
                myTimerTask = null;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
      /*  //判断是否是扫描框架
        FragmentHelper.getInstance().JudgeScanFragment();*/
        super.onHiddenChanged(hidden);
    }

    @Override
	public void onClick(View v) {
		
	}
	
	public void callBackOnResult(Object object){
		
	}
	
	public void finish(){
		mFHelper.transcateBack();
	}


    @Override
    public void addUserCallback(boolean b){

    }

    @Override
    public void loginCallback(String result){

    }

    @Override
    public void INavOperateIntfCallback(String result){

    }


}

