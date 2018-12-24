package com.seuic.hisense.utils;

import android.content.Context;
import com.seuic.hisense.constant.Common;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016-09-26.
 */
public class AssetsHelper {
    private Context mContext;
    private String mAppFileName = "";

    public AssetsHelper(Context context, String appFileName) {
        mContext = context;
        mAppFileName = appFileName;
    }

    public  String Checkfile()//public  boolean Checkfile()
    {
        File file1 = new File(Common.StroagePath);
        if(file1.exists() == false){
            file1.mkdirs();
        }
      /*  String filePath = Common.StroagePath + mAppFileName;//Common.StroagePath + "/CallCard_signed.apk";
        File file = new File(filePath);*/
        //存在，则拷贝
        if( copyFile()){
            String filePath = Common.StroagePath + mAppFileName;//Common.StroagePath + "/CallCard_signed.apk";
            File file = new File(filePath);
            if(file.exists()){
                return filePath;
            }
        }

        return "";

    }

    /**
     * 拷贝文件到指定目录
     */
    private boolean copyFile() {
        try {
            InputStream myInput = mContext.getAssets().open( mAppFileName);
            String outFileName = Common.StroagePath + mAppFileName;
            File file = new File(outFileName);
            file.createNewFile();
            OutputStream myOutput = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
