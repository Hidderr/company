package com.example.alan.myapplication.alan.jsclass;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.example.alan.myapplication.alan.global.GlobalApplication;
import com.example.alan.myapplication.alan.http.HttpManager;
import com.example.alan.myapplication.alan.http.ServerCallBack;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.LinkedHashMap;

import static com.example.alan.myapplication.alan.constants.AppUrl.COLLECT_FORM;

/**
 * Created by Alan on 2018/2/5.
 * 功能：片单、影视收藏、分享JS调本地
 */

public class Collection {
    /**
     * 片单ID
     */
    private final String listId;
    private Context contextC = GlobalApplication.getGlobalContext();
    private  int userId = 13299;
    private String collect_success ="收藏成功";
    private String collect_fail="收藏失败";

    public  Collection(String listId){
        this.listId = listId;
    }
    /**
     * JS调用本地收藏片单
     */
    @JavascriptInterface
    public void collectForm(){
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        collectFormToServer();
    }

    private void collectFormToServer() {
        LinkedHashMap<String, String> paramas = new LinkedHashMap<>();
        paramas.put("user_id",userId+"");
        paramas.put("list_id", listId + "");
        HttpManager.getInstance().postCall(COLLECT_FORM, new ServerCallBack() {
            @Override
            public void responseSucessful(String json) {
                AllUtils.showToast(contextC,collect_success);
            }

            @Override
            public void responseClientFailure(String json, int code) {
                AllUtils.showToast(contextC,collect_fail);
            }

            @Override
            public void responseServerFailure(String json, int code) {
                AllUtils.showToast(contextC,collect_fail);
            }

            @Override
            public void netWorkFailure(String error) {
                AllUtils.showToast(contextC,collect_fail);

            }
        },paramas,"Collection");
    }

    /**JS调用本地收藏影视
     * @param id
     */
    @JavascriptInterface
    public void collectVideo(String id){
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    /**JS调用本地分享
     * @param title
     */
    @JavascriptInterface
    public void share(String title){
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
