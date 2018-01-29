package com.example.alan.myapplication.alan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

import com.example.alan.myapplication.R;

/**
 * Created by Alan on 2018/1/29.
 * 功能：可被listview单选的TextView
 */

public class CheckedTextView extends TextView implements Checkable {
    private boolean isChecked = false;
    public CheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckedTextView(Context context) {
        super(context);
    }

    public CheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        changeColor(checked);
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        this.isChecked = !this.isChecked;
        changeColor(this.isChecked);
    }
    private void changeColor(boolean isChecked) {
        //根据check的状态切换颜色
        if (isChecked) {
            setBackgroundResource(R.drawable.shape_selcted_flow_layout_bg);
            setTextColor(getResources().getColor(R.color.fragment_video_text_blue_nav));
        } else {
            setBackgroundResource(R.drawable.shape_whrite_rectangle);
            setTextColor(getResources().getColor(R.color.fragment_video_text_title_blue));
        }
    }

}
