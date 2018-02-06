package com.example.alan.myapplication.alan.global;

import android.app.Application;
import android.content.Context;

import com.example.alan.myapplication.alan.service.InitializeService;
import com.example.alan.myapplication.alan.utils.Utils;


/**
 * Created by Alan on 2018/1/4.
 */

public class GlobalApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        InitializeService.start(getApplicationContext());
        Utils.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    public static Context getGlobalContext(){
        if (context != null) {
            return context;
        }
        return context;
    }
    public static Context context;
}
