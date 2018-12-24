package com.seuic.hisense.utils;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.ContentBody;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

public class HttpClientUtils {
	public static final String TAG = "HttpClientUtils";
	
	public static final String QUESTION_SIGN = "?";
	public static final String AND_SIGN = "&";
	public static final String EQUAL_SIGN = "=";
	public static final String GET_METHOD = "GET";
	public static final String POST_METHOD = "POST";
	public static final String PUT_METHOD = "PUT";

	public static final int TIMEOUT_MILLIS = 20*60000;//20分钟   15000 15秒
	public static final int TIMEOUT_MILLIS3 = 60000;//60秒   15000 15秒
    //private static String JSESSIONID; //定义一个静态的字段，保存sessionID


	protected static HttpResponse httpResponseWithGetMethod2(String baseURI){
		HttpResponse httpResponse = null;
		HttpGet httpGet = new HttpGet(baseURI);
		HttpClient httpClient = getHttpClient();
		try {

			httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode==HttpStatus.SC_OK){
				return httpResponse;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			httpGet = null;
//			httpClient.getConnectionManager().shutdown();
		}
		return httpResponse;
	}
	
	/**
	 * httpResponseWithPostMethod
	 * @param baseURI
	 * @param json
	 * @return
	 */
	protected static HttpResponse httpResponseWithPostMethod(String baseURI, String json){
		HttpResponse httpResponse = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		try {
			httpPost = new HttpPost(baseURI);
			//httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(json, "UTF-8"));
			httpClient = getHttpClient();
			httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "statusCode="+statusCode);
			if(statusCode==HttpStatus.SC_OK){
				return httpResponse;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return httpResponse;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return httpResponse;
		} catch (IOException e) {
			e.printStackTrace();
			return httpResponse;
		} finally{
			httpPost = null;
//			httpClient.getConnectionManager().shutdown();
//			httpClient = null;
		}
		return httpResponse;
	}



	protected static HttpResponse httpResponseWithPostMethod2(String baseURI, List<NameValuePair> params){
		HttpResponse httpResponse = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		try {

			httpPost = new HttpPost(baseURI);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=GBK");
			httpPost.setEntity(new UrlEncodedFormEntity(params, "GBK"));
			httpClient = getHttpClient();

			httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "statusCode="+statusCode);
			if(statusCode==HttpStatus.SC_OK){
				return httpResponse;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return httpResponse;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return httpResponse;
		} catch (IOException e) {
			e.printStackTrace();
			return httpResponse;
		} finally{
			httpPost = null;
//			httpClient.getConnectionManager().shutdown();
//			httpClient = null;
		}
		return httpResponse;
	}

	protected static HttpResponse httpResponseWithPostMethod3(String baseURI, List<NameValuePair> params){
		HttpResponse httpResponse = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		try {

			httpPost = new HttpPost(baseURI);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpClient = getHttpClient3();//专门为第3个服务器接口定制的连接超时时间

			httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "statusCode="+statusCode);
			if(statusCode==HttpStatus.SC_OK){
				return httpResponse;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return httpResponse;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return httpResponse;
		} catch (IOException e) {
			e.printStackTrace();
			return httpResponse;
		} finally{
			httpPost = null;
//			httpClient.getConnectionManager().shutdown();
//			httpClient = null;
		}
		return httpResponse;
	}
	
	protected static HttpResponse httpResponseWithPutMethod(String baseURI, String json){
		HttpResponse httpResponse = null;
		HttpPut httpPut = null;
		HttpClient httpClient = null;
		try {
			httpPut = new HttpPut(baseURI);
			httpPut.setHeader("Content-Type", "application/json");
			httpPut.setEntity(new StringEntity(json, "UTF-8"));
			httpClient = getHttpClient();
			httpResponse = httpClient.execute(httpPut);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "statusCode="+statusCode);
			if(statusCode==HttpStatus.SC_OK){
				return httpResponse;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return httpResponse;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return httpResponse;
		} catch (IOException e) {
			e.printStackTrace();
			return httpResponse;
		} finally{
			httpPut = null;
//			httpClient.getConnectionManager().shutdown();
//			httpClient = null;
		}
		return httpResponse;
	}
	

	public static String requestResult2(String method,String baseURI,String json,List<NameValuePair> params){
		if(baseURI==null||"".equals(baseURI)){
			return null;
		}
		String result = null;
		HttpResponse httpResponse = null;
		HttpEntity resultEntity = null;
		//String entityJSONObject = null;
		if(HttpGet.METHOD_NAME.equals(method)){
			httpResponse = httpResponseWithGetMethod2(baseURI);
		}else if(HttpPost.METHOD_NAME.equals(method)){
			if(TextUtils.isEmpty(json)&&params==null){
				return null;
			}
			if(!TextUtils.isEmpty(json)){
				httpResponse = httpResponseWithPostMethod(baseURI, json);
			}else{
				httpResponse = httpResponseWithPostMethod2(baseURI, params);
			}

		}else if(HttpPut.METHOD_NAME.equals(method)){
			httpResponse = httpResponseWithPutMethod(baseURI, json);
		}else{
			return null;
		}
		if (httpResponse!=null) {
			try {
				resultEntity = httpResponse.getEntity();
				result = EntityUtils.toString(resultEntity,"GBK");
				//JSONObject entityJSONObject = new JSONObject(result);
				return result;
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				httpResponse = null;
			}
		}
		return null;
	}

	public static String requestResult3(String method,String baseURI,String json,List<NameValuePair> params){
		if(baseURI==null||"".equals(baseURI)){
			return null;
		}
		String result = null;
		HttpResponse httpResponse = null;
		HttpEntity resultEntity = null;
		//String entityJSONObject = null;
		if(HttpGet.METHOD_NAME.equals(method)){
			httpResponse = httpResponseWithGetMethod2(baseURI);
		}else if(HttpPost.METHOD_NAME.equals(method)){
			if(TextUtils.isEmpty(json)&&params==null){
				return null;
			}
			if(!TextUtils.isEmpty(json)){
				httpResponse = httpResponseWithPostMethod(baseURI, json);
			}else{
				httpResponse = httpResponseWithPostMethod3(baseURI, params);
			}

		}else if(HttpPut.METHOD_NAME.equals(method)){
			httpResponse = httpResponseWithPutMethod(baseURI, json);
		}else{
			return null;
		}
		if (httpResponse!=null) {
			try {
				resultEntity = httpResponse.getEntity();
				result = EntityUtils.toString(resultEntity, "UTF-8");
				//JSONObject entityJSONObject = new JSONObject(result);
				return result;
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				httpResponse = null;
			}
		}
		return null;
	}
	
	protected static DefaultHttpClient getHttpClient(){
		BasicHttpParams httpParams = new BasicHttpParams();
		ConnManagerParams.setTimeout(httpParams, TIMEOUT_MILLIS);//这定义了从ConnectionManager管理的连接池中取出连接的超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000);//这定义了通过网络与服务器建立连接的超时时间。Httpclient包中通过一个异步线程去创建与服务器的socket连接，这就是该socket连接的超时时间
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLIS);//读写超时
		SchemeRegistry schemeRegistry =new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager clientConnectionManager =new ThreadSafeClientConnManager(httpParams, schemeRegistry);
		//DefaultHttpClient httpClient = new DefaultHttpClient(clientConnectionManager, httpParams);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}

	protected static DefaultHttpClient getHttpClient3(){
		BasicHttpParams httpParams = new BasicHttpParams();
		ConnManagerParams.setTimeout(httpParams, 15000);//这定义了从ConnectionManager管理的连接池中取出连接的超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000);//这定义了通过网络与服务器建立连接的超时时间。Httpclient包中通过一个异步线程去创建与服务器的socket连接，这就是该socket连接的超时时间
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLIS3);//读写超时
		SchemeRegistry schemeRegistry =new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager clientConnectionManager =new ThreadSafeClientConnManager(httpParams, schemeRegistry);
		//DefaultHttpClient httpClient = new DefaultHttpClient(clientConnectionManager, httpParams);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}
	
	protected static JSONObject getResponseJSONObject(DefaultHttpClient httpClient,HttpUriRequest request)
			throws ClientProtocolException, IOException{
		JSONObject responseJSONObject = null;
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if(executionCount>5){
					return false;
				}
				if(exception instanceof NoHttpResponseException){
					return true;
				}
				if(exception instanceof SSLHandshakeException){
					return false;
				}
				HttpRequest httpRequest = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
				if(!(httpRequest instanceof HttpEntityEnclosingRequest)){
					return true;
				}
				return false;
			}
		};
		ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
			@Override
			public JSONObject handleResponse(HttpResponse response)throws ClientProtocolException, IOException {
				int statusCode = response.getStatusLine().getStatusCode();
				if(statusCode==HttpStatus.SC_OK){
					HttpEntity httpEntity = response.getEntity();
					if(httpEntity!=null){
						String result = EntityUtils.toString(httpEntity, "UTF-8");
						try {
							return new JSONObject(result);
						} catch (JSONException e) {
							e.printStackTrace();
							return null;
						}
					}
				}
				return null;
			}
		};
		httpClient.setHttpRequestRetryHandler(httpRequestRetryHandler);
		responseJSONObject = httpClient.execute(request, responseHandler);
		releaseHttpRequest(request);
		return responseJSONObject;
	}
	
	protected static void releaseHttpRequest(HttpUriRequest request){
		if(request!=null&&request.isAborted()){
			request.abort();
		}
	}
	
	protected static void releaseHttpClient(DefaultHttpClient httpClient){
		if(httpClient!=null){
			httpClient.getConnectionManager().shutdown(); 
		}
	}

	//文件传输
    /*public static String post(String pathToOurFile, String urlServer)
            throws ClientProtocolException, IOException, JSONException {
        HttpClient httpclient = new DefaultHttpClient();
        // 设置通信协议版本
        httpclient.getParams().setParameter(
                CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        String sign = MD5UtilNew.code32(YCKYApplication.getApp().getPdaID()+key,"UTF-8");
        sign = sign.replace("\n","");

        HttpPost httppost = new HttpPost(urlServer+"?imeiVersion="+sign);
        File file = new File(pathToOurFile);

        MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
        ContentBody cbFile = new FileBody(file);
        mpEntity.addPart("file", cbFile); // <input type="file" name="file" />
        httppost.setEntity(mpEntity);
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println(response.getStatusLine());// 通信Ok
        String json = "";
        String path = "";
        if (resEntity != null) {
            json = EntityUtils.toString(resEntity, "utf-8");
            return json;
        }
        if (resEntity != null) {
            resEntity.consumeContent();
        }

        httpclient.getConnectionManager().shutdown();
        return path;
    }*/

}
