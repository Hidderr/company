package com.example.alan.myapplication.alan.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.alan.myapplication.alan.constants.AppUrl;
import com.example.alan.myapplication.alan.global.GlobalApplication;
import com.example.alan.myapplication.alan.utils.AllUtils;
import com.example.alan.myapplication.alan.utils.encryptutils.EasyAES;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.tag;

/**
 * Created by Alan on 2018/1/4.
 * 网络请求框架
 */

public class HttpFrame {

    private String TOKEN ;
    public OkHttpClient mOkHttpClient;
    public static HttpFrame mInstance;
    public Handler mHandler;
    public Gson mGson;
  public Context mContext = GlobalApplication.getGlobalContext();

    private HttpFrame(){initHttp();}

    public static  HttpFrame getInstance(){
        if (mInstance == null)
        {
            synchronized (HttpFrame.class)
            {
                if (mInstance == null)
                {
                    mInstance = new HttpFrame();
                }
            }
        }
        return mInstance;
    }


    public void setToken(String token){
        TOKEN = token;
    }


    private void initHttp() {
        File cache = mContext.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)//连接超时(单位:秒)
                .writeTimeout(20, TimeUnit.SECONDS)//写入超时(单位:秒)
                .readTimeout(20, TimeUnit.SECONDS)//读取超时(单位:秒)
                .pingInterval(20, TimeUnit.SECONDS) //websocket轮训间隔(单位:秒)
                .cache(new Cache(cache.getAbsoluteFile(), cacheSize))//设置缓存
                .build();
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }


    public Gson getGson(){
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }








    public void getFromServer(String url, ServerCallBack callBack, Object obj){
        LinkedHashMap<String,String>  paramas =  new LinkedHashMap<>();
        try {
            url = url+"?param="+getEncodeParam(paramas);
            Log.w("TAG","url:getFromServer:  ................,,,,,,,,,,     "+url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mOkHttpClient != null) {
            Request request = new Request.Builder()
                    .get()
                    .tag(obj)
                    .cacheControl(AllUtils.getInstance().isNetworkConnected()==false? CacheControl.FORCE_CACHE: CacheControl.FORCE_NETWORK)
                    .url(AppUrl.BASE_URL+url)
                    .build();
            httpCall(request,callBack);
        }

    }




    public void getFromServerHasParamas(String url, LinkedHashMap<String,String> paramas, ServerCallBack callBack, Object obj){

        try {
            url = url+"?param="+getEncodeParam(paramas);
            Log.w("TAG","url:getFromServerHasParamas:  ................,,,,,,,,,,     "+url);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Log.w("TAG","url:getFromServerHasParamas:  ................,,,,,,,,,,     "+url);
        if (mOkHttpClient != null) {
            Request request = new Request.Builder()
                    .get()
                    .tag(obj)
                    .cacheControl(AllUtils.getInstance().isNetworkConnected()==false? CacheControl.FORCE_CACHE: CacheControl.FORCE_NETWORK)
                    .url(AppUrl.BASE_URL+url)
                    .build();
            httpCall(request,callBack);
        }

    }



    public  String getEncodeParam(LinkedHashMap<String,String> params) throws Exception {

        JSONObject j = new JSONObject();
        try {


            Iterator<String> keys = params.keySet().iterator();
            Iterator<String> values = params.values().iterator();

            for (int i=0;i<params.size();i++ ) {
                if (true) {
                    String key = keys.next();
                    String value = values.next();
                    if ("page".equals(key)) {
                        j.put("page", Integer.valueOf(value));
                    } else if ("category_id".equals(key)) {
                        j.put("category_id", Integer.valueOf(value));
                    } else if ("id".equals(key)) {
                        j.put("id", Integer.valueOf(value));
                    }else if("user_id".equals(key)){
                        j.put("user_id", Integer.valueOf(value));
                    }
                    else if("mac_id".equals(key)){
                        j.put("mac_id", Integer.valueOf(value));
                    }
                    else {
                        j.put(key, value);
                    }
                }
            }

            j.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String res = URLEncoder.encode(EasyAES.getInstance().encrypt(j.toString()), "UTF-8");
//        XGIMILOG.E("\n未加密参数 : " + j.toString()
//                + "\n 加密参数 : " + EasyAES.getInstance().encrypt(j.toString()).trim()
//                + "\nurlEncode : " + res
//        );

//        System.out.println("url:json:  .......................................  "+j.toString());
        Log.w("TAG","url:json:  ................,,,,,,,,,,     "+j.toString());
        String s = EasyAES.getInstance().encrypt(j.toString().trim());

        return getURLEncoderString(s);
    }

    /**
     * URL 转码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:10:28
     */
    public  String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result =     URLEncoder.encode( str, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }






    public void postFromServer(String url, ServerCallBack callBack, HashMap<String,String> map, Object obj){
        if (mOkHttpClient != null && map!=null) {
            FormBody.Builder builder = new FormBody.Builder();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                builder.add(entry.getKey()+"",entry.getValue()+"");
            }
            FormBody formBody = builder.build();

            Request request = new Request.Builder()
                    .header("token",TOKEN+"")
                    .tag(obj)
                    .post(formBody)
                    .cacheControl(AllUtils.getInstance().isNetworkConnected()==false? CacheControl.FORCE_CACHE: CacheControl.FORCE_NETWORK)
                    .url(AppUrl.BASE_URL+url)
                    .build();
            httpCall(request,callBack);
        }

    }








    /**传送字符串给服务器
     * @param url
     * @param callBack
     * @param jsonContent
     */
    public void postStringToServer(String url, ServerCallBack callBack, String jsonContent, Object obj){
        if (mOkHttpClient != null && jsonContent!=null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), jsonContent);
            Request request = new Request.Builder()
                    .header("token",TOKEN+"")
                    .tag(obj)
                    .post(requestBody)
                    .cacheControl(AllUtils.getInstance().isNetworkConnected()==false? CacheControl.FORCE_CACHE: CacheControl.FORCE_NETWORK)
                    .url(AppUrl.BASE_URL+url)
                    .build();
            httpCall(request,callBack);
        }
    }








    /**上传文件
     * @param url
     * @param callBack
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @param listener 文件进度监听
     */
    public void postFileToServer(String url, ServerCallBack callBack, String filePath, String fileName, final FileProgressListener listener, Object obj){
        if (mOkHttpClient != null ) {
            RequestBody requestBody;
            File file = new File(filePath, fileName);
            if (!file.exists()){
                return;
            }else{
                requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            }
            CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
                @Override
                public void onRequestProgress(long byteWritted, long contentLength) {
                    if (listener != null) {
                        listener.fileProgress(byteWritted,contentLength);
                    }
                }
            });

            Request request = new Request.Builder()
                    .header("token",TOKEN+"")
                    .tag(obj)
                    .post(countingRequestBody)
                    .cacheControl(AllUtils.getInstance().isNetworkConnected()==false? CacheControl.FORCE_CACHE: CacheControl.FORCE_NETWORK)
                    .url(AppUrl.BASE_URL+url)
                    .build();
            httpCall(request,callBack);
        }
    }


    /**提交表单
     * @param url
     * @param callBack
     * @param fileKey 文件key
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param listener 文件上传进度监听
     * @param map 参数
     */
    public void postMultiFormToServer(String url, ServerCallBack callBack, String fileKey, String filePath,
                                      String fileName, final FileProgressListener listener, HashMap<String,String> map, Object obj){
        if (mOkHttpClient != null && map != null) {
            RequestBody requestBody;
            File file = new File(filePath, fileName);
            if (!file.exists()){
                return;
            }

           requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
                @Override
                public void onRequestProgress(long byteWritted, long contentLength) {
                    if (listener != null) {
                        listener.fileProgress(byteWritted,contentLength);
                    }
                }
            });
           MultipartBody.Builder builder =  new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);//一定要设置这句

            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                builder.addFormDataPart(entry.getKey()+"",entry.getValue()+"");
            }

            builder.addFormDataPart(fileKey, fileName, countingRequestBody);
            RequestBody muiltipartBody = builder.build();

            Request request = new Request.Builder()
                    .header("token",TOKEN+"")
                    .post(muiltipartBody)
                    .tag(obj)
                    .cacheControl(AllUtils.getInstance().isNetworkConnected()==false? CacheControl.FORCE_CACHE: CacheControl.FORCE_NETWORK)
                    .url(AppUrl.BASE_URL+url)
                    .build();
            httpCall(request,callBack);
        }
    }




    /**发起请求的Call
     * @param request
     */
    private void httpCall(final Request request, final ServerCallBack callBack) {
        if (mOkHttpClient != null && callBack !=null) {
            Call call = mOkHttpClient.newCall(request);
            //异步调用,并设置回调函数
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callBack.netWorkFailure(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final int code = response.code();
                    final String json = response.body().string();
                    Log.w("TAG","JSON:  ................,,,,,,,,,,     "+json);

                    if(json.contains("SUCCESS")){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.responseSucessful(json);
                            }
                        });

                    }else if(code>=400 && code<500){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.responseClientFailure(json,code);
                            }
                        });

                    }else if(code>=500){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.responseServerFailure(json,code);
                            }
                        });

                    }

                }
            });
        }

    }


    /**根据标签取消某个请求
     * @param obj
     */
    public void cancelCallByTag(Object obj){
        if (mOkHttpClient != null && obj!=null) {
            List<Call> queuedCalls = mOkHttpClient.dispatcher().queuedCalls();
            if (queuedCalls != null) {
                if (queuedCalls.size()>0) {
                    for(Call call : queuedCalls) {
                        if(call.request().tag().equals(tag))
                            call.cancel();
                    }
                }
            }
            List<Call> runningCalls = mOkHttpClient.dispatcher().runningCalls();
            if (runningCalls != null) {
                if (runningCalls.size()>0) {
                    for(Call call : runningCalls) {
                        if(call.request().tag().equals(tag))
                            call.cancel();
                    }
                }
            }

        }
    }











    /**
     * 网络请求回调
     */
    public interface ServerCallBack{
        void responseSucessful(String json);
        void responseClientFailure(String json, int code);
        void responseServerFailure(String json, int code);
        void netWorkFailure(String error);
    }


    public interface FileProgressListener{
        void fileProgress(long byteWritted, long contentLength);
    }
}
