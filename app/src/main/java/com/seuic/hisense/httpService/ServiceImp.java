package com.seuic.hisense.httpService;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.seuic.hisense.constant.Common;
import com.seuic.hisense.utils.HttpClientUtils;
import com.seuic.hisense.utils.LoggUtils;


/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/11.
 */
public class ServiceImp {

    private String nameSpace="http://tempuri.org/";//和wcf服务契约特性的Namespace是一样的
    //svc服务地址
    private String soapAction="urn:HsNavWebSrvIntf-IHsNavWebSrv#INavOperateIntf";//操作地址
    private String methodName="INavOperateIntf";//方法名称
    private String endPoint = Common.ServiceURLWCF;

    private static ServiceImp mServiceImp;
    private HttpTransportSE httpTransportSE;

    public ServiceImp(){
        //http://219.147.31.34:8006/HsNavWebSrv.dll/wsdl/IHsNavWebSrv
        endPoint = endPoint.replace("/wsdl/","/soap/");//关键，否则访问不了
        httpTransportSE = new HttpTransportSE(endPoint, 10 * 1000);//10秒超时
    }


    public static ServiceImp getInstance() {
        if(mServiceImp==null){
            mServiceImp = new ServiceImp();
        }
        return  mServiceImp;
    }

    public void setNull(){
        mServiceImp = null;
    }


    public String INavOperateIntf(int psOperateTypeCode, String psDataType, String psXMLData){
        SoapObject outObject=new SoapObject(nameSpace,methodName);
        //输出参数
        outObject.addProperty("psOperateTypeCode", psOperateTypeCode);
        outObject.addProperty("psDataType",psDataType);
        outObject.addProperty("psXMLData",psXMLData);

        SoapSerializationEnvelope serializationEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);//设置soap版本
        serializationEnvelope.bodyOut=outObject;
        serializationEnvelope.dotNet=true;//调用.NET的服务
       /* HttpTransportSE transportSE=new HttpTransportSE(url);
        transportSE.debug=true;//采用调试*/

        try{
            httpTransportSE.call(soapAction, serializationEnvelope);//调用服务
            SoapObject result=(SoapObject)serializationEnvelope.bodyIn;//获取调用结果
            ////Base64解密 并且以 GBK2312编码，否则中文会乱码
            if(result != null && result.getPropertyCount()>0){
                //decodeBase64
                String str = result.getProperty(0).toString();
                str = Common.decodeBase64(str);
                return str;
            }

        }catch(IOException ex){
            Log.v("sad", "IO异常");
            LoggUtils.error(ex.getMessage());
        }catch(XmlPullParserException ex){
            Log.v("sad", "xml解析异常");
            LoggUtils.error(ex.getMessage());
        }catch(Exception ex){
            Log.v("sad", "服务调用异常异常");
            LoggUtils.error(ex.getMessage());
        }
        return null;
    }

    /*public boolean addUser(User mUser){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user",JSON.toJSONString(mUser));
            map.put("method","addUser");
            String result = getHttpResponse(map, Common.serviceUrl+"UserServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }*/

    public  String login(String grid,String pass){

        ///Login?grid=%s&pass=%s"
        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("grid", grid));
            params.add(new BasicNameValuePair("pass", pass));

            String url = "http://bos.ycgwl.com:8092/BosInterface.asmx/Login";
            String result = getHttpResponse(params, url);
            /*String baseURL = String.format("http://bos.ycgwl.com:8092/BosInterface.asmx/Login?grid=%s&pass=%s", grid, pass);
            String result = getHttpResponse(baseURL);*/
            return result;

        }catch (Exception e){
            LoggUtils.info("error：" + e.getMessage());
            return null;
        }
    }


    public  String INavOperateIntf2(int psOperateTypeCode, String psDataType, String psXMLData){

        ///Login?grid=%s&pass=%s"
        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("psOperateTypeCode", psOperateTypeCode+""));
            params.add(new BasicNameValuePair("psDataType", psDataType));
            params.add(new BasicNameValuePair("psXMLData", psXMLData));

            //http://219.147.31.34:8006/HsNavWebSrv.dll/soap/IHsNavWebSrv
            //http://219.147.31.34:8006/HsNavWebSrv.dll/wsdl/IHsNavWebSrv
            String url = "http://219.147.31.34:8006/HsNavWebSrv.dll/wsdl/IHsNavWebSrv/INavOperateIntf";
            String result = getHttpResponse(params, url);
            /*String baseURL = String.format("http://bos.ycgwl.com:8092/BosInterface.asmx/Login?grid=%s&pass=%s", grid, pass);
            String result = getHttpResponse(baseURL);*/
            return result;

        }catch (Exception e){
            LoggUtils.info("error：" + e.getMessage());
            return null;
        }
    }


    //GET方式
    public String getHttpResponse(String URL){
        String result=null;
        for(int i = 0;i<2;i++) {
            result = HttpClientUtils.requestResult3(HttpClientUtils.GET_METHOD, URL,
                    null, null);
            if (result != null) {
                break;
            }
        }

        return result;
    }

    //POST方式
    public String getHttpResponse(List<NameValuePair> params,String URL){
        String result=null;
        for(int i = 0;i<2;i++) {
            result = HttpClientUtils.requestResult3(HttpClientUtils.POST_METHOD, URL,
                    null, params);
            if (result != null) {
                break;
            }
        }

        return result;
    }

}
