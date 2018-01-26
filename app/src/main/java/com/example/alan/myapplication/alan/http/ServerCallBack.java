package com.example.alan.myapplication.alan.http;

/**
 * Created by Alan on 2018/1/26.
 * 功能：网络请求回调
 */

public interface ServerCallBack {
    void responseSucessful(String json);
    void responseClientFailure(String json, int code);
    void responseServerFailure(String json, int code);
    void netWorkFailure(String error);
}
