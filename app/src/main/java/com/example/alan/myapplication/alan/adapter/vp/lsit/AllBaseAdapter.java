package com.example.alan.myapplication.alan.adapter.vp.lsit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Alan on 2017/9/18.
 * Adpater的基类
 */

public abstract class AllBaseAdapter<T> extends BaseAdapter {
    public List<T> mList;
    public Context mContext;
    /**
     * item根布局
     */
    public View mRootView;

    public AllBaseAdapter(Context cxt){
            mContext = cxt;
    }

    public void setList(List<T> list ) {
        mList = list;
        notifyDataSetChanged();
    }

    public void clearList() {
        if (mList != null && mList.size() > 0) {
            mList.clear();
        }
        notifyDataSetChanged();
    }


    public void addList(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    public List<T> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseViewHolder viewHolder =null;
        if (convertView == null) {
            viewHolder = getHolder();//1.创建holder
            convertView = viewHolder.initView();//2.初始化Holder
            convertView.setTag(viewHolder);//3.设置给ContentView
        } else {
            viewHolder = (BaseViewHolder) convertView.getTag();//获取Holder
        }
        viewHolder.refreshView(position);//4.设置数据给view
        return convertView;
    }

    /** 1.获取Holder
     * @return BaseViewHolder
     */
    public abstract BaseViewHolder getHolder();


    /**
     * 基类的Holder
     */
    public static abstract  class BaseViewHolder{

        /**2.初始化控件
         * @return 根View
         */
        public abstract View initView();

        /**
         * 3.设置控件数据
         */
        public abstract void refreshView(int position);
    }
}
