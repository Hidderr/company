package com.example.alan.myapplication.alan.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.alan.myapplication.alan.bean.UserVideoPlayHistoryBean;
import com.example.alan.myapplication.alan.bean.VideoDetailBean;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.gimi.RecordDao;
import com.example.alan.myapplication.alan.global.GlobalApplication;
import com.example.alan.myapplication.alan.listener.OnControlSqlFinishListener;

import java.util.List;

/**
 * Created by Alan on 2018/1/31.
 * 功能：数据库操作类
 */

public class SqlUtils {
    RecordDao dao;
    public static SqlUtils mSqlUtils;
    public List<UserVideoPlayHistoryBean> mPlayHistoryBeanList;
    private OnControlSqlFinishListener onControlSqlFinishListener;
    public Context applicationContext = GlobalApplication.getGlobalContext();

    private SqlUtils() {}
    public static SqlUtils getInstance() {
        if (mSqlUtils == null)
        {
            synchronized (SqlUtils.class)
            {
                if (mSqlUtils == null)
                {
                    mSqlUtils = new SqlUtils();
                }
            }
        }
        return mSqlUtils;
    }




    public void addPlayHistory2Sql(final String video_id, final String playsource, final String sourceicon , final VideoDetailBean.DataBean bean, final Context cxt) {
                if (dao == null) {
                    dao = new RecordDao(applicationContext);
                }
                if (bean != null && dao!=null) {
                    VideoDetailBean.DataBean data= bean;
                    if (!TextUtils.isEmpty(data.image) &&  !TextUtils.isEmpty(data.title)) {
                        List<UserVideoPlayHistoryBean> playHistoryBeanList = dao.queryVideoPlayHistory();
                        for (UserVideoPlayHistoryBean userVideoPlayHistoryBean : playHistoryBeanList) {
                            if (userVideoPlayHistoryBean.video_id.equals(video_id)) {
                                dao.deleteRepeatVideoPlayHistory(userVideoPlayHistoryBean);
                            }
                        }
                        dao.addPlayHistory(new UserVideoPlayHistoryBean(data.title,data.image,data.category,data.duration/60,data.year,playsource,sourceicon,video_id));
                    }
                }

    }


    /**获取历史播放记录
     * @param cxt
     * @return
     */
    public List<UserVideoPlayHistoryBean> queryVideoPlayHistory2Show(final Context cxt){
        LogUtil.w("SQL","查询完成000000");
            try{
                new Thread(){
                    @Override
                    public void run() {
                        if (dao == null) {
                            dao = new RecordDao(applicationContext);
                        }
                        mPlayHistoryBeanList = dao.queryVideoPlayHistory();
                        LogUtil.w("SQL","查询完成111");
                        if (onControlSqlFinishListener != null) {
                            Activity activity =   (Activity)cxt;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (onControlSqlFinishListener != null) {
                                        LogUtil.w("SQL","查询完成22222222");
                                        onControlSqlFinishListener.onQuerySqlFinishListener(mPlayHistoryBeanList);
                                    }
                                }
                            });
                        }
                    }
                }.start();

            }catch(Exception e){

            }
        return mPlayHistoryBeanList;
    }

    /**删除数据库中影视记录
     * @param bean true 删除成功 false 删除失败
     * @return
     */
    public boolean deleteVideoPlayHisory(UserVideoPlayHistoryBean bean){
        try{
            if (dao == null) {
                dao = new RecordDao(applicationContext);
            }
            dao.deleteRepeatVideoPlayHistory(bean);
            return true;
        }catch(Exception e){

        }
        return false;
    }

    /**数据操作完成回调
     * @param onControlSqlFinishListener
     */
    public void setOnControlSqlFinishListener(OnControlSqlFinishListener onControlSqlFinishListener){
        this.onControlSqlFinishListener = onControlSqlFinishListener;
    }
}
