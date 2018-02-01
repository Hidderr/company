package com.example.alan.myapplication.alan.bean;

/**
 * Created by Alan on 2018/1/31.
 * 功能：播放历史记录
 */

public class UserVideoPlayHistoryBean {
    /**
     * 影片标题
     */
    public String title;
    /**
     * 影片图片
     */
    public String image;
    /**
     * 影片ID用于从服务器获取数据的参数
     */
    public String video_id;
    /**
     * 影片类别
     */
    public String category;
    /**
     * 影片时长
     */
    public int duration;
    /**
     * 影片上映时间
     */
    public String year;
    /**
     * 影片播放来源名字
     */
    public String playsource;
    /**
     * 影片播放来源的icon
     */
    public String sourceicon;
    /**
     * 是否选中
     */
    public boolean isSelcted;
    public UserVideoPlayHistoryBean(String title, String image, String category, int duration, String year, String playsource, String sourceicon, String video_id){
        this.title = title;
        this.image = image;
        this.category = category;
        this.duration = duration;
        this.year = year;
        this.playsource = playsource;
        this.sourceicon = sourceicon;
        this.video_id = video_id;

    }

}
