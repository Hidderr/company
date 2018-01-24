package com.example.alan.myapplication.alan.http;

import java.util.HashMap;

/**
 * Created by Alan on 2018/1/4.
 * 网络请求管理类
 */

public class HttpManager {
    public static HttpManager mHttpManager;
    private HttpManager(){}

    public static  HttpManager getInstance(){
        if (mHttpManager == null)
        {
            synchronized (HttpManager.class)
            {
                if (mHttpManager == null)
                {
                    mHttpManager = new HttpManager();
                }
            }
        }
        return mHttpManager;
    }

    /**get网络请求
     * @param url
     * @param callBack
     */
    public void getCall(String url, HttpFrame.ServerCallBack callBack, Object obj){
        HttpFrame.getInstance().getFromServer(url,callBack,obj);
    }


    /**post网络请求
     * @param url
     * @param callBack
     * @param map
     */
    public void postCall(String url, HttpFrame.ServerCallBack callBack, HashMap<String,String> map, Object obj){
        HttpFrame.getInstance().postFromServer(url,callBack,map,obj);
    }


    /**上传字符串给服务器
     * @param url
     * @param callBack
     * @param jsonContent
     */
    public void postStringToServer(String url, HttpFrame.ServerCallBack callBack, String jsonContent, Object obj){
        HttpFrame.getInstance().postStringToServer(url,callBack,jsonContent,obj);
    }



    /**上传文件
     * @param url
     * @param callBack
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @param listener 文件进度监听
     */
    public void postFileToServer(String url, HttpFrame.ServerCallBack callBack, String filePath,
                                 String fileName, final HttpFrame.FileProgressListener listener, Object obj){
        HttpFrame.getInstance().postFileToServer(url, callBack, filePath, fileName, listener,obj);
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
    public void postMultiFormToServer(String url, HttpFrame.ServerCallBack callBack, String fileKey, String filePath,
                                      String fileName, final HttpFrame.FileProgressListener listener, HashMap<String,String> map, Object obj){
        HttpFrame.getInstance().postMultiFormToServer(url, callBack, fileKey, filePath, fileName, listener, map,obj);
    }


    /**根据标签取消某个请求
     * @param obj
     */
    public void cancelCallByTag(Object obj){
        HttpFrame.getInstance().cancelCallByTag(obj);
    }
}
