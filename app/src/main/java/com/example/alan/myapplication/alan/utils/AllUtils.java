package com.example.alan.myapplication.alan.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.alan.myapplication.alan.constants.Constants;
import com.example.alan.myapplication.alan.global.GlobalApplication;
import com.example.alan.myapplication.alan.ui.VideoDetailActivityNew;
import com.example.alan.myapplication.alan.utils.encryptutils.CryptUtil;


/**
 * Created by Alan on 2018/1/4.
 */

public class AllUtils {

    public static AllUtils mAllUtils;
    public static Context mContext = GlobalApplication.getGlobalContext();
    private AllUtils() {}
    public static AllUtils getInstance() {
        if (mAllUtils == null)
        {
            synchronized (AllUtils.class)
            {
                if (mAllUtils == null)
                {
                    mAllUtils = new AllUtils();
                }
            }
        }
        return mAllUtils;
    }



    /**
     * 判断是否有网络连接
     *
     * @return
     */
    public boolean isNetworkConnected() {
        if (mContext != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空
            if (networkInfo != null) return networkInfo.isAvailable();
        }
        return false;
    }


    /**
     * 用Rsa加密内容
     *
     * @param content
     * @return
     */
    public String enctyptPw(String content) {
        return Base64.encodeToString(CryptUtil.rsaEncrypt(content.getBytes(), CryptUtil.keyStrToPublicKey(Constants.PW_PRIVATEKEY)), Base64.DEFAULT);
    }


    public int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public int dpToPx(Context context,int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    /**
     * 获取app版本信息
     *
     * @param context
     * @return
     */
    public  String getappVersion(Context context) {
        String appVersion = "0.0.0";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            appVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    /**影视详情界面
     * @param activity
     * @param videoId
     */
    public void startVideoDetailActivity(Context activity,String videoId){
        Intent view = new Intent(activity, VideoDetailActivityNew.class);
        view.putExtra("video_id",videoId);
        activity.startActivity(view);
    }


    /**
     * 启动activity
     *
     * @param cxt
     * @param cls
     */
    public void startActivity(Context cxt, Class<?> cls) {
        cxt.startActivity(new Intent(cxt, cls));
    }

    public static void showToast(Context context,String content){
        Toast.makeText(context, content+"", Toast.LENGTH_SHORT).show();
    }
}
