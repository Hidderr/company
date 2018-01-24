package com.example.alan.myapplication.alan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;

import com.example.alan.myapplication.alan.constants.Constants;
import com.example.alan.myapplication.alan.global.GlobalApplication;
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

}
