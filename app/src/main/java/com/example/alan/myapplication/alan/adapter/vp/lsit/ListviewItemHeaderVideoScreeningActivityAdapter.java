package com.example.alan.myapplication.alan.adapter.vp.lsit;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.VideoScreeningActivityHeaderBean;

/**
 * Created by Alan on 2018/1/29.
 * 功能：
 */

public class ListviewItemHeaderVideoScreeningActivityAdapter extends AllBaseAdapter<VideoScreeningActivityHeaderBean.DataBean.TypeBean> {
    public ListviewItemHeaderVideoScreeningActivityAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    public BaseViewHolder getHolder() {
        return new ListviewItemHeaderVideoScreeningActivity();
    }

    public class ListviewItemHeaderVideoScreeningActivity extends BaseViewHolder {

        TextView tv_car_pinpai_list_item_shop_car_activity;

        @Override
        public View initView() {
            View view = View.inflate(mContext, R.layout.a_list_item_tv_video_screening_activity, null);
            tv_car_pinpai_list_item_shop_car_activity = (TextView) view.findViewById(R.id.tv_item_header_video_screening_activity);

            return view;
        }

        @Override
        public void refreshView(int position) {
            if (mList.size()>0) {
                VideoScreeningActivityHeaderBean.DataBean.TypeBean typeBean = mList.get(position);
                if (typeBean != null) {
                    tv_car_pinpai_list_item_shop_car_activity.setText(typeBean.name+"");
                }
            }

        }
    }
}
