package com.example.alan.myapplication.alan.bean;

import android.util.Log;

import com.example.alan.myapplication.alan.gimi.EasyAES;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

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


    /**加密数据类转换
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T AESjson2Class(String json,Class<T> tClass) {
        T instance = null;
        String name = "";
        try {
            instance = tClass.newInstance();
            name = tClass.getSimpleName();
            JSONObject j = new JSONObject(json);
            if ("VideoScreeningActivityHeaderBean".equals(name)) {
                ((VideoScreeningActivityHeaderBean)instance).msg = j.getString("msg");
                ((VideoScreeningActivityHeaderBean)instance).code = j.getInt("code");
                String js = EasyAES.getInstance().decrypt(j.getString("data"));
                ((VideoScreeningActivityHeaderBean)instance).data = new Gson().fromJson(js, VideoScreeningActivityHeaderBean.DataBean.class);

            }else if("VideoScreeningResultBean".equals(name)){
                ((VideoScreeningResultBean)instance).msg = j.getString("msg");
                ((VideoScreeningResultBean)instance).code = j.getInt("code");
                String js = EasyAES.getInstance().decrypt(j.getString("data"));
                Type type = new TypeToken<List<VideoScreeningResultBean.DataBean>>(){}.getType();
                ((VideoScreeningResultBean)instance).data = new Gson().fromJson(js, type);
            }else if("CollectBean".equals(name)){
                ((CollectBean)instance).msg = j.getString("msg");
                ((CollectBean)instance).code = j.getInt("code");
                String js = EasyAES.getInstance().decrypt(j.getString("data"));
                LogUtil.w("TAG","判断是否收藏成功 data： "+js);
                ((CollectBean)instance).data = new Gson().fromJson(js, CollectBean.DataBean.class);
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
