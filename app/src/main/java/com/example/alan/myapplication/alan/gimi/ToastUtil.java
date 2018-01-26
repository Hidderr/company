package com.example.alan.myapplication.alan.gimi;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by XGIMI on 2017/8/30.
 */

public class ToastUtil {
    private static Toast mToast;
    private static Toast mToast2;

    public static Toast getToast(String text, Context context) {

        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);

        return mToast;
    }

    public static Toast makeText(Context context, String text, int delay) {
        if (mToast2 != null) {
            mToast2.cancel();
        }

        mToast2 = Toast.makeText(context, text, delay);

        return mToast2;
    }
}
