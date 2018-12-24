package com.seuic.hisense.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.seuic.hisense.R;
import com.seuic.hisense.constant.Common;
import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.fragments.ScanFragment;
import com.seuic.hisense.utils.AlertDialogHelper;
import com.seuic.hisense.utils.AppInfoHelper;
import com.seuic.hisense.utils.AssetsHelper;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.utils.FragmentFactory;
import com.seuic.hisense.utils.FragmentHelper;
import com.seuic.hisense.utils.LogHelper;
import com.seuic.hisense.utils.LoggUtils;
import com.seuic.hisense.utils.MyDialog;
import com.seuic.hisense.utils.OperateProperties;
import com.seuic.hisense.utils.ScannerHelper;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/*import com.seuic.hisense.Service.UpLoadService;
import com.seuic.hisense.ServiceInterface.ICameraCallback;*/
//////import com.seuic.hisense.db.DbExecutor;
//////import com.seuic.hisense.db.dbManager;
//////import com.seuic.hisense.fragments.MainFragment;


public class MainActivity extends Activity implements View.OnClickListener{
    private static final int MSG_UPDATE = 1;
    private static final int MSG_DOWNLOAD =2;
    private static final int MSG_REFRESH =3;

    private FragmentHelper mFHelper;
    private FragmentFactory mFFactory;
    private LinearLayout ll_back;
    private TextView tv_title,tv_userName,tv_unUpload_count;

    private ScannerReceiver mReceiver;
    private ScreenOnOffReceiver mScreenOnOffReceiver;

    private int version;
    private boolean isDownloading = false;//是否下载中状态标示
    private long lastisDownload =0;//上一次下载的时间
    private boolean bShowDialog = false;//对话框是否已经显示
    public  static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 5;
    //定时器更新数量
    private Timer timer;
    private MyTimerTask myTimerTask;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_DOWNLOAD:
                    //开始下载更新文件
                    HttpUtils http = new HttpUtils();
                    HttpHandler handler = http.download(Common.updateServerUrl,
                            Common.StroagePath+"YCKY.apk",
                            false, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                            false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                            new RequestCallBack<File>() {
                                @Override
                                public void onStart() {
                                    builder = new Dialog(MainActivity.this);
                                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View layout = inflater.inflate(R.layout.dialog_download,null);
                                    tv_pb = (TextView)layout.findViewById(R.id.tv_pb);
                                    tv_content = (TextView)layout.findViewById(R.id.tv_content);
                                    pb = (ProgressBar)layout.findViewById(R.id.progressBar);
                                    builder.setTitle("程序更新");
                                    builder.setContentView(layout);
                                    builder.setCancelable(false);
                                    builder.setCanceledOnTouchOutside(false);
                                    builder.show();
                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                    tv_content.setText("程序下载百分比："+current*100 /total+"%");
                                    pb.setMax((int) total);
                                    pb.setProgress((int)current);
                                }

                                @Override
                                public void onSuccess(ResponseInfo<File> responseInfo) {
                                    OperateProperties.getInstance().setValue("version",version+"");
                                    OperateProperties.getInstance().setValue("updatePath",responseInfo.result.getPath());
                                    builder.dismiss();
                                    update(MainActivity.this,OperateProperties.getInstance().getValue("updatePath"));
                                    isDownloading = false;
                                }


                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    builder.dismiss();
                                    AlertDialogHelper.getInstance().showMessage(MainActivity.this,msg);
                                    isDownloading = false;
                                }
                            });
                    break;
                case MSG_UPDATE:
                    update(MainActivity.this, OperateProperties.getInstance().getValue("updatePath"));
                    isDownloading = false;
                    break;
                case MSG_REFRESH:
                    countStatistics();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();


    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {

        FastClick.lastClickTime = 0;//重置，因可能修改时间后又返回到本界面
/*
        upgrade();

        //安装第三方电话APP
        installApp();*/

        super.onResume();
    }

    /*****************安装第三方电话APP start*********************/
    private void installApp(){

        try {
            boolean isExist = AppInfoHelper.checkApkExist(this,"com.haigang.callcard");
            if(isExist){
                return;//已经安装过了，则返回
            }
            //拷贝第三方电话APP
            AssetsHelper mDBCopy = new AssetsHelper(this,"CallCard_signed.apk");
            String appFilePath = mDBCopy.Checkfile();
            //安装第三方电话APP
            if(appFilePath!= null && appFilePath.length() > 0) {
                updateAPP(this,appFilePath);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    boolean bshowDialogAPP1 = false;
    public void updateAPP(final Context context,final String appPath){

        if(bshowDialogAPP1){
            return;
        }
        bshowDialogAPP1 = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setTitle("系统提示");
        builder.setMessage("安装新的电话APP！\r\n如果提示“禁止安装”，选择“设置”进入“安全”，勾选“未知来源”输入4007770876 确定后返回，重新安装。");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (AlertDialog.BUTTON_POSITIVE == which) {
                    bshowDialogAPP1 = false;
                    File file = new File(appPath);
                    if (appPath.contains("apk") && file.exists()) {
                        LogHelper.i("install CallCard_signed", appPath);
                        Uri uri = Uri.fromFile(new File(appPath));
                        installAPK(uri);
                    }

                    return;
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*****************安装第三方电话APP end*********************/

    private Dialog builder = null;
    private TextView tv_content = null;
    private ProgressBar pb = null;
    private TextView tv_pb;

    private void upgrade() {
        if (isDownloading == false || (isDownloading && (System.currentTimeMillis() - lastisDownload) > 20 * 60 * 60 * 1000)) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        android.os.Process.setThreadPriority (android.os.Process.THREAD_PRIORITY_BACKGROUND);
                        isDownloading = true;
                        //////DbExecutor.deletePastData();
                        int i = version = Common.getServerVersion();
                        int j = AppInfoHelper.getIntAppVersion(MainActivity.this);
                        LogHelper.i("downloadAneApp", "i:"+i+"j:"+j);
                        //如果版本已经下载下来，但是用户没有点击更新的时候就直接更新不下载。
                        if (OperateProperties.getInstance().getValue("version") != null && Integer.parseInt(OperateProperties.getInstance().getValue("version")) == i && j < i) {
                            mHandler.sendEmptyMessage(MSG_UPDATE);
                            return;
                        }
                        LogHelper.i("downloadAneApp", "1");
                        if (i > j) {
                            if (Common.updateServerUrl == null || Common.updateServerUrl.trim().length() == 0) {
                                LogHelper.i("downloadAneApp", "请先到设置里录入软件更新地址");
                                LoggUtils.error("请先到设置里录入软件更新地址");
                                return;
                            }
                            lastisDownload = System.currentTimeMillis();
                            //删除已存在数据
                            String path = Common.StroagePath+"YCKY.apk";
                            File file = new File(path);
                            if(file.exists()){
                                file.delete();
                            }
                            mHandler.sendEmptyMessage(MSG_DOWNLOAD);
                        }else{
                            isDownloading = false;
                        }
                    } catch (Exception ex) {
                        LogHelper.i("downloadAneApp", ex.getMessage());
                        LoggUtils.error("downloadAneApp" + ex.getMessage());
                        isDownloading = false;
                    }

                    super.run();
                }
            }.start();
        }
    }

    public void update(final Context context,final String updatePath){
        LogHelper.i("downloadAneApp", "2");
        if(bShowDialog){
            return;
        }
        LogHelper.i("downloadAneApp", "3");
        bShowDialog = true;
        OperateProperties.getInstance().setValue("updatePath",updatePath);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setTitle("系统提示");
        builder.setMessage("程序有新版本，是否更新？");
/*        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bShowDialog = false;

            }
        });*/

/*        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {
                bShowDialog = false;
            }
        });*/

        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(AlertDialog.BUTTON_POSITIVE == which){
                    bShowDialog = false;
                    File file = new File(updatePath);
                    LogHelper.i("prepare", updatePath);
                    if (updatePath.contains("apk") && file.exists()) {
                        Uri uri=  Uri.fromFile(new File(updatePath));
                        installAPK(uri);
                    }

                    return ;
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

    /**
     * 安装apk文件
     */
    private void installAPK(Uri apk) {

        // 通过Intent安装APK文件
        if (apk == null) {

            Toast.makeText(MainActivity.this, "下载更新失败，请重新尝试", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intents = new Intent();
        intents.setAction("android.intent.action.VIEW");
        intents.addCategory("android.intent.category.DEFAULT");
        intents.setType("application/vnd.android.package-archive");
        intents.setData(apk);
        intents.setDataAndType(apk, "application/vnd.android.package-archive");
        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intents);
        //android.os.Process.killProcess(android.os.Process.myPid());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            BaseFragment curFg = mFHelper.getCurFragment();
            if(curFg != null&& !MyDialog.getInstance().isShow()){
                curFg.finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        //////stopService(new Intent(this,UpLoadService.class));
        if(mReceiver!=null){
            //注销广播监听
            unregisterReceiver(mReceiver);
        }
        if(mScreenOnOffReceiver!=null){
            unregisterReceiver(mScreenOnOffReceiver);
        }

        if (myTimerTask != null){
            myTimerTask.cancel();  //将原任务从队列中移除
        }
        timer.purge();
        timer.cancel();
        timer = null;
        super.onDestroy();
    }

    private void initView(){
        ll_back = (LinearLayout)findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_userName = (TextView)findViewById(R.id.tv_userName);
        tv_unUpload_count = (TextView)findViewById(R.id.tv_unUpload_count);
        tv_unUpload_count.setOnClickListener(this);
    }

    public void setTitle(String title){
        tv_title.setText(title);
    }

    /**
     * 未上传数量++
     */
    public synchronized void countIncrease(){
        /*int count= 0;
        if(Integer.parseInt(tv_unUpload_count.getText().toString())==0){
            /count += dbManager.YYB_NoUploadCount();//营业部所有未上传数量
        }else{
            count +=Integer.parseInt(tv_unUpload_count.getText().toString());
            count++;
        }
        tv_unUpload_count.setText(count+"");*/
    }

    /**
     * 未上传数量--
     */
    public synchronized void countDecrease(){
        /*int count= 0;
        if(Integer.parseInt(tv_unUpload_count.getText().toString())==0){
            count += dbManager.YYB_NoUploadCount();//营业部所有未上传数量
        }else{
            count +=Integer.parseInt(tv_unUpload_count.getText().toString());
            count--;
        }
        tv_unUpload_count.setText(count+"");*/
    }

    /**
     * 未上传统计
     */
    public synchronized void countStatistics(){
        /*int count= 0;
        count += dbManager.YYB_NoUploadCount();//营业部所有未上传数量
        tv_unUpload_count.setText(count+"");*/
    }

    public void enableClickCount(boolean clickable){
        tv_unUpload_count.setEnabled(clickable);
    }


    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }
        switch (v.getId()){
            case R.id.ll_back:
//                if(mFHelper.getCurFragment() instanceof MainFragment){
//                    mFHelper.getCurFragment().finish();
//                }else{
//                    mFHelper.transcateBack();
//                }
                if(!MyDialog.getInstance().isShow()){
                    mFHelper.getCurFragment().finish();
                }
                break;
            case R.id.tv_unUpload_count:
                //mFHelper.transcateFoward(FragmentFactory.UPloadData_Fragment);
                break;
        }
    }


    public class ScannerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mFHelper.getCurFragment().onReceiveScanData(context,intent);
        }
    }

    private void initData(){
        //用于显示登录用户名
        tv_userName.setText("");
        //tv_userName.setText("["+HisenseApplication.getApp().getUserName()+"]");
        FragmentHelper.initHelper(this, R.id.fl_main_content);
        mFHelper = FragmentHelper.getInstance();
        mFFactory = FragmentFactory.getInstance();

        Intent intent = getIntent();

        if(intent.getBooleanExtra("set",false)){
            mFHelper.transcateOnTab(0, mFFactory.getFragment(FragmentFactory.Set_Fragment));
        }else{
            mFHelper.transcateOnTab(0, mFFactory.getFragment(FragmentFactory.Main_Fragment));
        }

        //////startService(new Intent(this, UpLoadService.class));

        //		//注册广播监听
        mReceiver =  new ScannerReceiver();
        IntentFilter filter = new IntentFilter(ScannerHelper.ACTION_SCANNER_SEND_BARCODE);
        registerReceiver(mReceiver, filter);

        mScreenOnOffReceiver=new ScreenOnOffReceiver();
        IntentFilter recevierFilter=new IntentFilter();
        recevierFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mScreenOnOffReceiver, recevierFilter);

        timer = new Timer(true);//true启用守护线程，主线程停止后，守护线程也停止
        myTimerTask = new MyTimerTask() ;//每次都要重新建一个对象，否则报错
        timer.schedule(myTimerTask,500, 20000); //延时500ms后执行，20秒执行一次

    }

    public class ScreenOnOffReceiver extends BroadcastReceiver {

        static final String TAG = "ScreenOffReceiver";
        Context mContext;

        @Override
        public void onReceive(Context context, Intent intent) {
            LogHelper.i(TAG, "Receiver : " + intent.getAction());
            mContext = context;
            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                if(mFHelper.getCurFragment() instanceof ScanFragment){
                    ScannerHelper.sendBroadcast(mContext, ScannerHelper.ACTION_SCANNER_ENABLED, ScannerHelper.KEY_ENABLED, true);
                }
            }
        }

    }

    private Uri fileUri;

    /** Create a file Uri for saving an image */
    private static Uri getOutputMediaFileUri(String name){
        return Uri.fromFile(getOutputMediaFile(name));
    }

    /** Create a File for saving an image */
    private static File getOutputMediaFile(String name){
        File mediaStorageDir = null;
        try{
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "YCKYCamera");
        }catch (Exception e){
            e.printStackTrace();
        }
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + name + ".jpg");
        return mediaFile;
    }

    public void photo(String name){
        // 利用系统自带的相机应用:拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // create a file to save the image
        fileUri = getOutputMediaFileUri(name);
        // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
        // set the image file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode){
            if (RESULT_OK == resultCode){
                /*if(mICameraCallback==null){
                    return;
                }
                mICameraCallback.cameraCallback( fileUri);*/
            }
        }
    }
  /*  public void setICameraCallback(ICameraCallback mICameraCallback){
        this.mICameraCallback = mICameraCallback;
    }*/

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            // 需要做的事:发送消息
            mHandler.sendEmptyMessage(MSG_REFRESH);
        }

    }


}
