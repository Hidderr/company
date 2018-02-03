package com.example.alan.myapplication.alan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.alan.myapplication.alan.constants.Constants;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.global.GlobalApplication;
import com.example.alan.myapplication.alan.ui.VideoDetailActivityNew;
import com.example.alan.myapplication.alan.utils.encryptutils.CryptUtil;

import java.util.List;

import static com.example.alan.myapplication.alan.global.GlobalApplication.context;


/**
 * Created by Alan on 2018/1/4.
 */

public class AllUtils {

    /**
     * 滑动方向
     */
    public static final String HORIZONTAL  = "horizontal";
    public static final String VERTICAL  = "vertical";
    public static AllUtils mAllUtils;
    public static Context mContext = GlobalApplication.getGlobalContext();
    public boolean hasStart;

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

    /**启动Activity
     * @param cxt
     * @param cls
     * @param key
     * @param value
     */
    public void startActivityWithParamas(Context cxt, Class<?> cls,String[] key,String[] value) {
        Intent view = new Intent(cxt, cls);
        if (key != null && key.length>0 && value!=null && value.length>0 && key.length==value.length) {
            for (int i = 0; i < key.length; i++) {
                view.putExtra(key[i],value[i]);
            }
        }

        cxt.startActivity(view);
    }

    public static void showToast(Context context,String content){
        Toast.makeText(context, content+"", Toast.LENGTH_SHORT).show();
    }


    /**跳转影视详情
     * @param video_id 影视详情的id
     * @param Adpater
     */
    public void startVideoDetailActivityAll(final String video_id, BaseQuickAdapter Adpater) {
        Adpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtil.w("TAG", "position: :  " + position);
                if (!TextUtils.isEmpty(video_id)) {
                   startVideoDetailActivity(context, video_id);
                }

            }
        });
    }



    /**跳转到到我的影视详情里
     * @param cls
     * @param view 点击事件
     */
   public void startActivityWithView(final Class<?> cls, View view, final Context cxt, final String[] key, final String[] value) {
       view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithParamas(cxt,cls,key,value);

            }
        });
    }


    /**判断RecyclerView是否滑动到底，然后进行跳转Activity
     * @param recyclerView
     * @param cxt
     * @param claz 跳转的Activity
     * @param direction RecyclerView可以滑动的方向
     * @param key 传递的参数
     * @param value 传递的参数
     */
    public  void scrollEndEnterActivity(RecyclerView recyclerView , Context cxt, Class<?> claz,String direction,String[] key,String[] value){
        switch (direction){
            case HORIZONTAL:
                if (!recyclerView.canScrollHorizontally(1) && !hasStart ) {//判断不能左滑，即互动到底
                        startActivityWithParamas(cxt,claz,key,value);
                    hasStart = true;

                }
                break;
            case VERTICAL:
                if (!recyclerView.canScrollVertically(1)) {//判断不能上滑，即互动到底
                    startActivity(cxt,claz);
                }
                break;

        }

    }


    /**设置TextView字体加粗
     * @param tv
     */
    public void setTextBold(TextView tv) {
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
    }

    /**
     * 判断 Activity 是否存在栈中
     *
     * @param clz Activity 类
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isActivityExistsInStack(@NonNull final Class<?> clz) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity aActivity : activities) {
            if (aActivity.getClass().equals(clz)) {
                return true;
            }
        }
        return false;
    }



}
