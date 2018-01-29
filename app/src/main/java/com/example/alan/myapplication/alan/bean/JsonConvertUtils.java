package com.example.alan.myapplication.alan.bean;

import android.util.Log;

import com.example.alan.myapplication.alan.gimi.EasyAES;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alan on 2018/1/29.
 * 功能：
 */

public class JsonConvertUtils {

    /**将影视详情加密的json数据解密，并转换成对应的bean
     * @param json
     * @param bean
     * @return
     */
    public static VideoDetailBean  AesJson2Json( String json,VideoDetailBean bean){
        try {
            JSONObject j = new JSONObject(json);
            bean.msg = j.getString("msg");
            bean.code = j.getInt("code");
            String js = EasyAES.getInstance().decrypt(j.getString("data")).replace("\\/", "/");
            bean.data = new Gson().fromJson(js, VideoDetailBean.DataBean.class);
            Log.w("TAG","影视详情："+js);
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
