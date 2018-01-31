package com.example.alan.myapplication.alan.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.alan.myapplication.alan.bean.VideoDetailBean;
import com.example.alan.myapplication.alan.bean.VideoPlayHistoryBean;
import com.example.alan.myapplication.alan.gimi.LogUtil;
import com.example.alan.myapplication.alan.gimi.RecordDao;
import com.example.alan.myapplication.alan.listener.OnControlSqlFinishListener;

import java.util.List;

/**
 * Created by Alan on 2018/1/31.
 * 功能：数据库操作类
 */

public class SqlUtils {
    RecordDao dao;
    public static SqlUtils mSqlUtils;
    public List<VideoPlayHistoryBean> mPlayHistoryBeanList;
    private OnControlSqlFinishListener onControlSqlFinishListener;

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

    /**向数据库添加播放记录
     * @param video_id 影片ID用于向服务器获取详情
     * @param playsource 播放来源名称
     * @param sourceicon 播放来源图片
     * @param bean 数据源
     * @param cxt
     */
//    public void addPlayHistory2Sql(final String video_id, final String playsource, final String sourceicon , final VideoDetailBean.DataBean bean, final Context cxt) {
//       new Thread(){
//           @Override
//           public void run() {
//               if (dao == null) {
//                   dao = new RecordDao(cxt);
//               }
//               if (bean != null && dao!=null) {
//                   VideoDetailBean.DataBean data= bean;
//                   if (TextUtils.isEmpty(data.image) &&  TextUtils.isEmpty(data.title)) {
//                       List<VideoPlayHistoryBean> playHistoryBeanList = dao.queryVideoPlayHistory();
//                       for (VideoPlayHistoryBean videoPlayHistoryBean : playHistoryBeanList) {
//                           if (videoPlayHistoryBean.video_id.equals(video_id)) {
//                               dao.deleteRepeatVideoPlayHistory(videoPlayHistoryBean);
//                           }
//                       }
//                       dao.addPlayHistory(new VideoPlayHistoryBean(data.title,data.image,data.category,data.duration/60,data.year,playsource,sourceicon,video_id));
//                   }
//               }
//
//           }
//       }.start();
//    }


    public void addPlayHistory2Sql(final String video_id, final String playsource, final String sourceicon , final VideoDetailBean.DataBean bean, final Context cxt) {
                if (dao == null) {
                    dao = new RecordDao(cxt);
                }
                if (bean != null && dao!=null) {
                    VideoDetailBean.DataBean data= bean;
                    if (!TextUtils.isEmpty(data.image) &&  !TextUtils.isEmpty(data.title)) {
                        List<VideoPlayHistoryBean> playHistoryBeanList = dao.queryVideoPlayHistory();
                        for (VideoPlayHistoryBean videoPlayHistoryBean : playHistoryBeanList) {
                            if (videoPlayHistoryBean.video_id.equals(video_id)) {
                                dao.deleteRepeatVideoPlayHistory(videoPlayHistoryBean);
                            }
                        }
                        dao.addPlayHistory(new VideoPlayHistoryBean(data.title,data.image,data.category,data.duration/60,data.year,playsource,sourceicon,video_id));
                    }
                }

    }


    /**获取历史播放记录
     * @param cxt
     * @return
     */
    public List<VideoPlayHistoryBean> queryVideoPlayHistory2Show(final Context cxt){
        LogUtil.w("SQL","查询完成000000");
        new Thread(){
            @Override
            public void run() {
                if (dao == null) {
                    dao = new RecordDao(cxt);
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
        if (mPlayHistoryBeanList != null && mPlayHistoryBeanList.size()==0) {
            return  null;
        }
        return mPlayHistoryBeanList;
    }

    /**数据操作完成回调
     * @param onControlSqlFinishListener
     */
    public void setOnControlSqlFinishListener(OnControlSqlFinishListener onControlSqlFinishListener){
        this.onControlSqlFinishListener = onControlSqlFinishListener;
    }
}
