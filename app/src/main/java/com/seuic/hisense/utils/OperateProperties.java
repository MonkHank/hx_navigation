package com.seuic.hisense.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2015/3/13.
 */
public class OperateProperties {
    /**
     * btMac   蓝牙地址
     * btPin   蓝牙pin码
     * siteCode  网点编号
     * userId    用户id
     * updateUrl  更新地址
     * dataTransmissionUrl  数据传输地址
     * adminPassword
     * communicationTime   通讯时间
     * communicationType   通讯方式
     */
    private static final String TAG = "OperateProperties";
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/configCenter.dat";
    private static  OperateProperties mOperateProperties;

    private OperateProperties(){
        initConfig();
    }

    public static OperateProperties getInstance(){
         if (mOperateProperties==null){
             mOperateProperties = new OperateProperties();
         }
        return mOperateProperties;
    }

    private boolean exist(String filePath){
        try {
            File file=new File(filePath);
            return file.exists();
        }catch (Exception ex){

        }
       return false;
    }

    private void initConfig(){
        if(!exist(PATH)){
            Properties prop = new Properties();

   /*         prop.put("updateUrl","http://218.94.18.124:16002/SEUIC/YCKY.apk");
            prop.put("dataTransmissionUrl","http://sdb-test.ycgky.com:8070");
            prop.put("dataTransmissionUrlWebService","http://bos.ycgwl.com:8092/BosInterface.asmx");

            prop.put("adminPassword","888888");*/
            prop.put("adminPassword","888888");
            saveConfig(PATH, prop);
        }
    }

    public void setValue(String key,String value){

        Properties prop = loadConfig(PATH);
        prop.put(key, value);
        saveConfig(PATH, prop);

    }

    public String getValue(String key){
        Properties prop = loadConfig(PATH);
        if(prop.get(key)==null){
            return "";
        }else{
            return (String)prop.get(key);
        }

    }

    private Properties loadConfig(String file) {
        Properties properties = new Properties();
        try {
            FileInputStream s = new FileInputStream(file);
            properties.load(s);
            s.close();
            } catch (Exception e) {
            e.printStackTrace();
            }
        return properties;
        }

    private void saveConfig(String file, Properties properties) {
        try {
            FileOutputStream s = new FileOutputStream(file, false);
            properties.store(s, "");
            s.close();
            } catch (Exception e){
            e.printStackTrace();
            }
     }


}
