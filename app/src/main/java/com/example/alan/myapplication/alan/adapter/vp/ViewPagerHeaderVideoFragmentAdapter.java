package com.example.alan.myapplication.alan.adapter.vp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alan.myapplication.R;
import com.example.alan.myapplication.alan.bean.VideoFragmentBannerBean;
import com.example.alan.myapplication.alan.picture.loadpicture.PictureManager;
import com.example.alan.myapplication.alan.utils.AllUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2018/1/25.
 * 功能：
 */

public class ViewPagerHeaderVideoFragmentAdapter extends PagerAdapter {

    private List<VideoFragmentBannerBean.DataBean.BannerListBean> banner_list = new ArrayList<>();
    private Context context;
    public VideoFragmentBannerBean.DataBean.BannerListBean mBannerListBean;

    public ViewPagerHeaderVideoFragmentAdapter(List<VideoFragmentBannerBean.DataBean.BannerListBean> banner_list,Context cxt ) {
        this.banner_list = banner_list;
        context = cxt;
    }

    public void setList(List<VideoFragmentBannerBean.DataBean.BannerListBean> list) {
        this.banner_list = list;
        notifyDataSetChanged();
    }

    public void setContext(Context cxt){
        this.context = cxt;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.a_vp_item_video_fragment, null);
        final int realPosition = getRealPosition(position);
        TextView title = (TextView) view.findViewById(R.id.tv_title_vp_item_video_fragment);
        TextView desc= (TextView) view.findViewById(R.id.tv_desc_vp_item_video_fragment);
        TextPaint tp = desc.getPaint();
        tp.setFakeBoldText(true);
        ImageView iv =  (ImageView) view.findViewById(R.id.iv_vp_item_video_fragment);
        if (banner_list.size()>0) {
            mBannerListBean = banner_list.get(realPosition);
            PictureManager.getInstance().loadServerPic(context, mBannerListBean.img, iv,R.drawable.icon_default,R.drawable.icon_default,PictureManager.ROUND_TYPE,8);
            title.setText(mBannerListBean.title);
            desc.setText(mBannerListBean.subtitle);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBannerListBean.type==1) {
                    AllUtils.getInstance().startVideoDetailActivity(context,mBannerListBean.detail);
                }
//                Toast.makeText(context, realPosition+"头布局", Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object o)
    {
        return view == o;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
        ViewPager viewPager = (ViewPager) container;
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = getFirstItemPosition();
        } else if (position == getCount() - 1) {
            position = getLastItemPosition();
        }
        viewPager.setCurrentItem(position, false);

    }

    private int getRealCount() {
        return banner_list.size();
    }

    private int getRealPosition(int position) {
        return position % getRealCount();
    }

    private int getFirstItemPosition() {
        return Integer.MAX_VALUE / getRealCount() / 2 * getRealCount();
    }

    private int getLastItemPosition() {
        return Integer.MAX_VALUE / getRealCount() / 2 * getRealCount() - 1;
    }
}

