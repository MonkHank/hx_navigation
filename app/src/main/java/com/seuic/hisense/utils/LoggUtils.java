package com.seuic.hisense.utils;

import android.os.Environment;
import android.os.StatFs;

import com.seuic.hisense.activitys.HisenseApplication;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日志记录工具类
 * 本工具只提供四个级别的日志记录，如果需要增加可自行对代码进行修改。
 * @author
 *
 */
public class LoggUtils {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 获得SD卡目录 */
    private static File SDPATH = Environment.getExternalStorageDirectory();
    /** 日志存放目录 */
    private static String LOGFILENAME = SDPATH.getAbsolutePath()+"/YCKY/logs/";
    // 得到是否存在SD卡
    private static boolean sdExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    /**
     * verbose级别
     * @param data 需要打印的数据
     */
    public static void verbose (String data){
        String[] infos = getAutoJumpLogInfos();
        data = data +"; " + infos[1] + infos[2]; // 拼装数据
        LogHelper.i(infos[0], data);
        writeLog(getFormat(FORMAT)+" [VERBOSE] "+infos[0]+": "+data);
    }


    /**
     * Info级别
     * @param data 需要打印的数据
     */
    public static void info(String data) {
        if(data != null){
            data = data + " 版本号：" + HisenseApplication.getApp().getVersion();
        }
        String[] infos = getAutoJumpLogInfos();
        data = data +"; " + infos[1] + infos[2];
        LogHelper.i(infos[0],data);
        writeLog(getFormat(FORMAT)+" [INFO] "+infos[0]+": "+data);
    }

    /**
     * Debug级别
     * @param data 需要打印的数据
     */
    public static void debug(String data) {
        String[] infos = getAutoJumpLogInfos();
        data = data +"; " + infos[1] + infos[2];
        LogHelper.i(infos[0],data);
        writeLog(getFormat(FORMAT)+" [DEBUG] "+infos[0]+": "+data);
    }

    /**
     * Warn级别
     * @param data 需要打印的数据
     */
    public static void warn(String data) {
        String[] infos = getAutoJumpLogInfos();
        data = data +"; " + infos[1] + infos[2];
        LogHelper.i(infos[0],data);
        writeLog(getFormat(FORMAT)+" [WARN] "+infos[0]+": "+data);
    }

    /**
     * Error级别
     * @param data 需要打印的数据
     */
    public static void error(String data) {
        if(data != null){
            data = data + " 版本号：" + HisenseApplication.getApp().getVersion();
        }

        String[] infos = getAutoJumpLogInfos();
        data = data +"; " + infos[1] + infos[2];
        LogHelper.i(infos[0], data);
        writeLog(getFormat(FORMAT)+" [ERROR] "+infos[0]+": "+data);
    }


    public static String getFilePathToday(){
        return  LOGFILENAME+"logo_"+getDateToday()+".txt";
    }
    /**
     * 输出到SD卡
     * @param data 输出的信息
     */
    private static void writeLog(String data){
        // 没有SD卡则不写入日志
        if(sdExist){
//                        String date = getFormat("yyyyMMddHHmmss");
            try {
                if(getAvailableMemory()<3){
                    writeFile(LOGFILENAME+"logo_"+getDateToday()+".txt","SD卡空间不足");
                    return;
                }
                writeFile(LOGFILENAME+"logo_"+getDateToday()+".txt",data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LogHelper.i("LoggUtils", "SD卡不存在");
        }

    }

    /**
     * 今天的日志文件路径
     * @return
     */
    public static String getLogPath(){
        return LOGFILENAME+"logo_"+getDateToday()+".txt";
    }

    /**
     * 得到时间格式
     * @return 返回yyyy-MM-dd HH:mm:ss格式时间
     */
    private static String getFormat(String format) {
        Date de = new Date();
        DateFormat df = new SimpleDateFormat(format);
        return df.format(de);
    }

    private static long getAvailableMemory() {
        try {
            File sdFile = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(sdFile.getPath());
            long size = statfs.getBlockSize();// 获得SD卡大小
            long available = statfs.getAvailableBlocks();// 可用大小
            return available * size / 1024 / 1024;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取今天的日期字符
     * @return
     */
    private static String getDateToday() {
        SimpleDateFormat simDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simDateFormat.format(new Date());
    }

    /**
     * 今天的日志文件路径
     * @return
     */
    public static String getLogPathYesterday(){
        String dateString = getDateYesterday();
        if(dateString.length() == 0){
            return "";
        }
        return LOGFILENAME+"logo_"+dateString+".txt";
    }

    /**
     * 获取昨天的日期字符
     * @return
     */
    private static String getDateYesterday() {
        try{
            Date date=new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
            date=calendar.getTime(); //这个时间就是日期往后推一天的结果
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String dateString = formatter.format(date);
            return dateString;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";

    }

    /**
     * 真正的写入SD卡
     * @param filepath 文件路径
     * @param data 写入的信息
     * @throws IOException 抛出IO异常
     */
    private static void writeFile(String filepath, String data)
            throws IOException {

        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;
        File file = new File(filepath);
        try {
            if (!file.isFile()) { // 文件目录不存在则创建
                String pa = filepath.substring(0, filepath.lastIndexOf("/"));
                new File(pa).mkdirs();
                file.createNewFile();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        getFileDir(LOGFILENAME);
        if(paths.size()>0){
            for(String item:paths){
                String strDate = item.substring(item.lastIndexOf("_")+1,item.lastIndexOf("_")+9);
                SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
                try{
                    Date date2 = format.parse(strDate);
                    if(System.currentTimeMillis()-date2.getTime()>7*24*60*60*1000){//删除7天前的日志
                        File file1 = new File(item);
                        file1.delete();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

        try { // 输出到SD卡文件中
            fOut = new FileOutputStream(file, true);

            osw = new OutputStreamWriter(fOut, "UTF-8");
            osw.write(data + "\r\n");
            osw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(osw != null)
                    osw.close();
                if(fOut != null)
                    fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取打印信息所在方法名，行号等信息
     * @return
     */
    private static String[] getAutoJumpLogInfos() {
        // 下标0对应的是类，下标1对应的是所在方法，下标2对应的是所在的类名全路径的行数
        String[] infos = new String[] { "", "", "" };
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length < 5) {
            LogHelper.i("MyLogger", "Stack is too shallow!!!");
            return infos;
        } else {
            infos[0] = elements[4].getClassName().substring(
                    elements[4].getClassName().lastIndexOf(".") + 1);
            infos[1] = "Method："+elements[4].getMethodName() + "()";// 得到所在的方法
            infos[2] = "; Class And Line Number: "+ elements[4].getClassName() + ".java:"
                    + elements[4].getLineNumber();
            return infos;
        }
    }

    private static List<String> items = null;//存放名称
    private static List<String> paths = null;//存放路径

    public static void getFileDir(String filePath) {
        try{
            items = new ArrayList<String>();
            paths = new ArrayList<String>();
            File f = new File(filePath);
            File[] files = f.listFiles();// 列出所有文件
            // 将所有文件存入list中
            if(files != null){
                int count = files.length;// 文件个数
                for (int i = 0; i < count; i++) {
                    File file = files[i];
                    items.add(file.getName());
                    paths.add(file.getPath());
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
