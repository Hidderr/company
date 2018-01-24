package com.example.alan.myapplication.alan.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.alan.myapplication.alan.http.HttpFrame;


/**
 * Created by Alan on 2018/1/8.
 */

public class InitializeService extends IntentService {
    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.daishuhaoche.daishuserverapp.service.action.INIT";
    private static Context mContext;

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        mContext = context;
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {

        HttpFrame.getInstance();//初始化网络请求

//        BoxingMediaLoader.getInstance().init(new BoxingGlideLoader()); // 需要实现IBoxingMediaLoader
//        BoxingCrop.getInstance().init(new BoxingUcropCircle());  // 需要实现 IBoxingCrop
//
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(mContext);
//        Constants.JPUSH_REGISTERID = JPushInterface.getRegistrationID(mContext);




    }



    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
