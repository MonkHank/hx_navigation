package com.seuic.hisense.constant;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.EditText;

import com.seuic.hisense.utils.LoggUtils;
import com.seuic.hisense.utils.OperateProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2015/9/22.
 */
public class Common {
    public static final boolean debug = true;
    public static final String MID = "898310072994003";
    public static final String TID = "00000001";
    public static final int downStoreInfoPerCount = 500;//库存下载，每次500行数据
    public static final int SUCCESS = 4;

    public static String StroagePath = Environment.getExternalStorageDirectory() + "/hisense_daohang/";
    public static String ServiceURL = OperateProperties.getInstance().getValue("dataTransmissionUrl");//"http://sdb-test.ycgky.com:8083";
    //public static String ServiceURL3 = "http://140.206.155.109:8848";//OperateProperties.getInstance().getValue("dataTransmissionUrl");//"http://sdb-test.ycgky.com:8083";
    public static String ServiceURLWCF = "http://219.147.31.34:8006/HsNavWebSrv.dll/wsdl/IHsNavWebSrv";
    public static String ServiceURLWebService3 = OperateProperties.getInstance().getValue("dataTransmissionUrlWebService3");
    public static String updateServerUrl = OperateProperties.getInstance().getValue("updateUrl");

    //清单合并—多个个清单号含有同一个大票的情况下，输入数量时，要先选择清单号
    /*public static String qdhb_qdh = "";
    public static String qdhb_DESTINATION = "";//目的站点*/

    public static String get32Ramdon() {
        String task = java.util.UUID.randomUUID().toString();
        return task.replace("-", "");
    }

    /**
     *
     * @param force  强制重置服务器地址到正式环境
     */
    public static void setDefaultConfig(boolean force) {
        if (force || TextUtils.isEmpty(OperateProperties.getInstance().getValue("dataTransmissionUrlWebService"))) {
            OperateProperties.getInstance().setValue("updateUrl", "http://218.94.18.124:16002/SEUIC/YCKY.apk");
            OperateProperties.getInstance().setValue("dataTransmissionUrl", "http://sdb-test.ycgky.com:8070");
            //正式：http://bos.ycgwl.com:8092/BosInterface.asmx 测试：testpda.ycgwl.com:8681/BosInterface.asmx http://140.206.155.102:8681/BosInterface.asmx
            OperateProperties.getInstance().setValue("dataTransmissionUrlWebService","http://bos.ycgwl.com:8092/BosInterface.asmx");
        }
        Common.ServiceURL = OperateProperties.getInstance().getValue("dataTransmissionUrl");//"http://sdb-test.ycgky.com:8083";
        Common.updateServerUrl = OperateProperties.getInstance().getValue("updateUrl");
        Common.ServiceURLWCF = OperateProperties.getInstance().getValue("dataTransmissionUrlWebService");

        //第3套接口，装包、拆包
        if (force || TextUtils.isEmpty(OperateProperties.getInstance().getValue("dataTransmissionUrlWebService3"))) {
            OperateProperties.getInstance().setValue("dataTransmissionUrlWebService3","http://k8app3.ycgwl.com:8848");
        }
        Common.ServiceURLWebService3 = OperateProperties.getInstance().getValue("dataTransmissionUrlWebService3");

    }


    /**
     * 获得服务器版本号
     *
     * @return -1失败  其他成功
     */
    public static int getServerVersion() {
        String urlStr = "";
        try {
            urlStr = Common.updateServerUrl.substring(0, Common.updateServerUrl.lastIndexOf("/")) + "/YCKYVersion.txt";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //取得inputStream，并进行读取
            InputStream input = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
            return Integer.parseInt(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static String getStatusMessage(int status) {
        String result = "";
        switch (status) {
            case 0:
                result = "未知错误";
                break;
            case 1:
                result = "登录超时";
                break;
            case 2:
                result = "非法请求";
                break;
            case 3:
                result = "用户名或密码错误";
                break;
            case 4:
                result = "业务操作成功";
                break;
            case 5:
                result = "业务错操作失败";
                break;
            case 6:
                result = "请求数据校验失败";
                break;
            case 7:
                result = "内部错误";
                break;
            case 8:
                result = "检测到新版本";
                break;
            case 9:
                result = "当前最新版本";
                break;
            case 10:
                result = "网点不正确";
                break;
            case 11:
                result = "用户信息保存失效";
                break;

        }
        return result;
    }

    public static String getWebserviceStatusMessage(String status) {
        String result = "返回状态不存在";
        try {
            int iStatus = -110;
            iStatus = Integer.parseInt(status);
            switch (iStatus) {
                case -1:
                    result = "验证用户信息未通过";
                    break;
                case -2:
                    result = "请求参数错误";
                    break;
                case -3:
                    result = "当前清单号数据已被其它PDA上传";
                    break;
                case -4:
                    result = "异常信息";
                    break;
                case -5:
                    result = "盘点号 和 S/N号 未绑定";
                    break;
                case -6:
                    result = "未查到数据";
                    break;
                case -8:
                    result = "交接件数大于系统库存件数，请逐一确认";
                    break;
                case -9:
                    result = "该运单号没有库存";
                    break;
                case -10:
                    result = "运单号大小票不一致";
                    break;
                case -11:
                    result = "运单号不存在";
                    break;
                case 1:
                    result = "正确";
                    break;
                case 3:
                    result = "当前登录站点与清单下一站不符";
                    break;
                case 4:
                    result = "清单号被其他PDA设备绑定";
                    break;
                case 5:
                    result = "该清单已完成卸车";
                    break;
                case 6:
                    result = "第2个或后面的清单与第1个清单对应的车牌号不一致";
                    break;
                case 7:
                    result = "清单号不存在";
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getURLBase64(String data) {
        byte[] encode = Base64.encode(data.getBytes(), Base64.DEFAULT);
        return new String(encode).replace("+", "-").replace("/", "_");
    }

    //字符串进行Base64编码
    public static String encodeBase64(String data) {
        String encodedString = Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
        return encodedString;
    }

    //字符串进行Base64解码
    public static String decodeBase64(String data) {
        //String decodedString =new String(Base64.decode(data,Base64.DEFAULT));
        //return decodedString;//中文会出现乱码
        try
        {
            byte[] bytes = Base64.decode(data,Base64.DEFAULT);
            String decodedString =new String(bytes,"gb2312");//"gb2312" 或 "gbk"
            return decodedString;

        }catch (Exception ex){
            LoggUtils.info("decodeBase64 error：" + ex.getMessage());
            return "";
        }

    }


    public static Date GetNetWorkTime(){
        URL url;
        try {
            url = new URL("http://www.ntsc.ac.cn/");//http://www.bjtime.cn http://www.baidu.com
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect(); // 发出连接
            long ld = uc.getDate(); // 取得网站日期时间
            Date date = new Date(ld); // 转换为标准时间对象
            return date;

           /* SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHH");
            String dateStr = sDateFormat.format(date);
            return dateStr;*/

/*            // 分别取得时间中的小时，分钟和秒，并输出
            Log.d("multicast",
                    date + ", " + date.getHours() + "时" + date.getMinutes()
                            + "分" + date.getSeconds() + "秒");*/
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 取得资源对象
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ********日期时间start***********
     */
    public static String GetSysTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }

    public static String GetSysTime2() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sDateFormat.format(new Date());
        return date;
    }

    //用于组成本机的唯一单据号
    public static String GetSysTime3() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sDateFormat.format(new Date());
        return date;
    }

    //获取系统日期
    public static String GetSysDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        return date;
    }

    public static String dateToStr(String time) {
        return dateToStr(strToDate(time));
    }

    public static String dateToStr(String time, String format) {
        if (time.isEmpty()) {
            return "";
        }
        return dateToStr(strToDate(time), format);
    }


    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String dateToStr(Date dateDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
    /***********日期时间end***********/

    /**
     * 保留两位小数
     *
     * @param f
     * @return
     */
    public static double doubletoTwoDecimal(double f) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留一位小数
     *
     * @param f
     * @return
     */
    public static double doubletoOneDecimal(double f) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private static final String mobileregex = "^1[0-9]{10}$";

    public static boolean isMobile(String phone) {
        Pattern pattern = Pattern.compile(mobileregex);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    private static final String sub1barcoderegex = "^[0-9]{10}$";
    private static final String sub1_1barcoderegex = "^[0-9]{10}-[0-9]*$";//10位纯数字 加杠后纯数字
    private static final String sub2barcoderegex = "^[0-9]{12}$";
    private static final String sub3barcoderegex = "^K[0-9]{9}$";
    private static final String sub4barcoderegex = "^K[0-9]{9}-[0-9]*$";
    private static final String sub5barcoderegex = "^[0-9]{12}-[0-9]*$";
    private static final String sub6barcoderegex = "^[0-9]{10}-[0-9]*$";
    private static final String sub7barcoderegex = "^[0-9]{13}$";//13位纯数字
    private static final String sub8barcoderegex = "^[0-9]{13}-[0-9]*$";//13位纯数字 加杠后纯数字
    //private static final String sub15barcoderegex = "^[0-9]{15}$";//15位纯数字
    //private static final String sub15_1barcoderegex = "^[0-9]{15}-[0-9]*$";//15位纯数字 加杠后纯数字
    private static final String bhbarcoderegex = "^J[0-9]{11}$";

    /**
     * 验证远成子单和主单
     *
     * @param barcode
     * @return
     */
    public static boolean verifyMainSubBarcode(String barcode) {
        if(barcode == null || barcode.length()==0){
            return false;
        }


        // 禁止 以-开头或结尾、空格、连续两个'-'
        if(barcode.contains(" ") || barcode.contains("--") ||barcode.startsWith("-") || barcode.endsWith("-")){
            return false;
        }

        //除了订单、无订单提货那边，其他功能条码为50位以内任意条码
        if(barcode.length() > 50){//接口数据库里的条码长度才20位，如果超过了会报错，所以这边做了限制
            return false;
        }

        //12位纯数字
        Pattern pattern = Pattern.compile(sub2barcoderegex);
        Matcher matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //12位纯数字加 ‘-’后纯数字
        pattern = Pattern.compile(sub5barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //10位纯数字
        pattern = Pattern.compile(sub1barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //10位纯数字加 ‘-’后纯数字
        pattern = Pattern.compile(sub1_1barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }


        //13位纯数字
        pattern = Pattern.compile(sub7barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //13位纯数字加 ‘-’后纯数字
        pattern = Pattern.compile(sub8barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //15位特殊条码
        if(IsSpec1(barcode)){
            return true;
        }


        return false;

        /*if(barcode.length() > 20){//接口数据库里的条码长度才20位，如果超过了会报错，所以这边做了限制
            return false;
        }

        if(barcode.endsWith("-")){//不允许以-结尾
            return false;
        }
        Pattern pattern = Pattern.compile(sub1barcoderegex);
        Matcher matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub2barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub3barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub4barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub5barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub6barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        return false;*/
    }

    /**
     * 验证远成条码格式
     *
     * @param barcode
     * @return
     */
    public static boolean verifyBarcode(String barcode) {

        if(barcode == null || barcode.length()==0){
            return false;
        }

        // 禁止 以-开头或结尾、空格、连续两个'-'
        if(barcode.contains(" ") || barcode.contains("--") ||barcode.startsWith("-") || barcode.endsWith("-")){
            return false;
        }

        //除了订单、无订单提货那边，其他功能条码为50位以内任意条码
        if(barcode.length() > 50){//接口数据库里的条码长度才20位，如果超过了会报错，所以这边做了限制
            return false;
        }

        return true;

        /*Pattern pattern = Pattern.compile(sub1barcoderegex);
        Matcher matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub2barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub3barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }
        return false;*/
    }

    /**
     * 只针对有订单、无订单提货功能
     * @param barcode
     * @return
     */
    public static boolean verifyBarcode_ddsh(String barcode) {
        Pattern pattern = Pattern.compile(sub1barcoderegex);
        Matcher matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub2barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub3barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    //U递运单号验证
    public static boolean verifyBarcode_UD(String barcode) {
        //10位纯数字
        Pattern pattern = Pattern.compile(sub1barcoderegex);
        Matcher matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //12位纯数字
        pattern = Pattern.compile(sub2barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //13位纯数字
        pattern = Pattern.compile(sub7barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 扫描揽收
     * @param barcode
     * @return
     */
    public static boolean verifyBarcodeSMLS(String barcode) {

        //12位纯数字
        Pattern pattern = Pattern.compile(sub2barcoderegex);
        Matcher matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //12位纯数字加 ‘-’后纯数字
        pattern = Pattern.compile(sub5barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //10位纯数字
        pattern = Pattern.compile(sub1barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //10位纯数字加 ‘-’后纯数字
        pattern = Pattern.compile(sub1_1barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }


        //13位纯数字
        pattern = Pattern.compile(sub7barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //13位纯数字加 ‘-’后纯数字
        pattern = Pattern.compile(sub8barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }


        return false;
    }

    //装包、拆包
    public static boolean verifyBarcodeZBCB(String barcode) {

        Pattern pattern = Pattern.compile(sub2barcoderegex);
        Matcher matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub5barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        pattern = Pattern.compile(sub7barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //13位纯数字加 ‘-’后纯数字
        pattern = Pattern.compile(sub8barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //10位纯数字
        pattern = Pattern.compile(sub1barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        //10位纯数字加 ‘-’后纯数字
        pattern = Pattern.compile(sub1_1barcoderegex);
        matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }


        return false;
    }

    //包号规则验证
    public static boolean verifyBarcodeBH(String barcode) {

        Pattern pattern = Pattern.compile(bhbarcoderegex);
        Matcher matcher = pattern.matcher(barcode);
        if (matcher.matches()) {
            return true;
        }

        return false;
    }



    /**
     * 特殊条码 （这种类型的 唯一标准 就是第一个'-'前面是15位）
     * @param barcode
     * @return
     */
    public static boolean IsSpec1(String barcode){
        try {
            if(barcode.contains("-") && barcode.split("-")[0].length()==15){
                return true;
            }
        }catch (Exception ex){

        }

        return false;
    }

    public static boolean verifyCarNum(String CarNum) {
        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z0-9挂]{6}$");
        Matcher matcher = pattern.matcher(CarNum);
        return matcher.matches();
    }

    public static void setEditTextReadOnly(EditText mEditableView, boolean readonly) {
        mEditableView.setCursorVisible(!readonly);
        mEditableView.setFocusable(!readonly);
        mEditableView.setFocusableInTouchMode(!readonly);
    }

    //获取焦点
    public static void setEditTextFocus(EditText mEditableView) {
        mEditableView.setFocusable(true);
        mEditableView.setFocusableInTouchMode(true);
        mEditableView.requestFocus();
    }

    /**
     * 清空临时照片
     */
    public static void deletePhoto() {
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "YCKYCamera";
        delAllFile(directory);
    }


    //删除文件夹
    //param folderPath 文件夹完整绝对路径
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * traversal bundle with string values.
     *
     * @param bundle
     * @return
     */
    public static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder("");
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            sb.append(key).append(":").append(bundle.get(key)).append(";\n");
        }
        return sb.toString();
    }

    /**
     * 判断插件是否已安装
     *
     * @param context
     * @param packageName 插件包名
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            if (info == null) {
                return false;
            } else {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 判断SD卡
     *
     * @return
     */
    public static String hasSdCard() {
        String file_path = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            file_path = Environment.getExternalStorageDirectory() + "/mpos/tmp";
        } else {
            file_path = "/data/data/com.chinaums.mpos/files";
        }
        return file_path;
    }


    /**
     * 资源文件拷贝
     *
     * @param context
     * @param fileName
     * @param path
     * @return
     */
    public static boolean copyApkFromAssets(Context context, String fileName, String path) {
        boolean copyIsFinish = false;
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copyIsFinish;
    }

    /**
     * 安装新应用
     */
    public static void installApk(Context context, Uri apk) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(apk, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }



    /**
     * 判断字符串是否是整数 且大于0
     * @param number
     * @return
     */
    public static boolean verifyInteger(String number){
        int count = -1;
        try{
            count = Integer.parseInt(number);
            if(count>0){
               return true;
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return false;
    }

}
