package com.example.alan.myapplication.alan.view;

import android.content.Context;
import android.support.annotation.ColorInt;

import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * Created by Alan on 2018/1/30.
 * 功能：
 */

public class DividerItemDecoration extends Y_DividerItemDecoration {
    public DividerItemDecoration(Context context, float lineWidthDp, @ColorInt int colorRGB) {
        super(context, lineWidthDp, colorRGB);
    }

    @Override
    public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
        //顺时针顺序:left, top, right, bottom
        boolean[] isOffset = {true, false, true, true};//默认只有bottom显示分割线
        return isOffset;
    }
}
