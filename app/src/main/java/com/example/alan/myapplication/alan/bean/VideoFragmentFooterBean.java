package com.example.alan.myapplication.alan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Alan on 2018/1/24.
 *影视页VideoFragment的footer口味研究所的bean
 */

public class VideoFragmentFooterBean {
    /**
     * code : 200
     * msg : SUCCESS
     * mItemDataList : {"total_page":1,"individuality":[{"type":2,"content_id":"3838002","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","name":"极寒之地","tag":"动作,冒险,科幻"},{"type":1,"content_id":"66e2f8965a095c9897883ade9942a9f5","img":"http://pic2.qiyipic.com/image/20150316/4b/7a/1f/a_50000313_m_601_m2_260_360.jpg","name":"寒战1","area":"","directors":"\u0081梁乐民,陆剑青 ","actors":"郭富城,梁家辉,李治廷,彭于晏,杨采妮 \u0090","showtime":"2012-11-08","score":9.2},{"type":1,"content_id":"66e2f8965a095c9897883ade9942a9f5","img":"http://pic2.qiyipic.com/image/20150316/4b/7a/1f/a_50000313_m_601_m2_260_360.jpg","name":"寒战2","area":"","directors":"\u0081梁乐民,陆剑青 ","actors":"郭富城,梁家辉,李治廷,彭于晏,杨采妮\u0090","showtime":"2016-07-08","score":8.7},{"type":1,"content_id":"66e2f8965a095c9897883ade9942a9f5","img":"http://pic2.qiyipic.com/image/20150316/4b/7a/1f/a_50000313_m_601_m2_260_360.jpg","name":"寒战3","area":"","directors":"\u0081梁乐民,陆剑青 ","actors":"郭富城,梁家辉,李治廷,彭于晏,杨采妮","showtime":"2017-11-08","score":8.9}]}
     */

    public int code;
    public String msg;
    public DataBean data;



    public static class DataBean {
        /**
         * total_page : 1
         * individuality : [{"type":2,"content_id":"3838002","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","name":"极寒之地","tag":"动作,冒险,科幻"},{"type":1,"content_id":"66e2f8965a095c9897883ade9942a9f5","img":"http://pic2.qiyipic.com/image/20150316/4b/7a/1f/a_50000313_m_601_m2_260_360.jpg","name":"寒战1","area":"","directors":"\u0081梁乐民,陆剑青 ","actors":"郭富城,梁家辉,李治廷,彭于晏,杨采妮 \u0090","showtime":"2012-11-08","score":9.2},{"type":1,"content_id":"66e2f8965a095c9897883ade9942a9f5","img":"http://pic2.qiyipic.com/image/20150316/4b/7a/1f/a_50000313_m_601_m2_260_360.jpg","name":"寒战2","area":"","directors":"\u0081梁乐民,陆剑青 ","actors":"郭富城,梁家辉,李治廷,彭于晏,杨采妮\u0090","showtime":"2016-07-08","score":8.7},{"type":1,"content_id":"66e2f8965a095c9897883ade9942a9f5","img":"http://pic2.qiyipic.com/image/20150316/4b/7a/1f/a_50000313_m_601_m2_260_360.jpg","name":"寒战3","area":"","directors":"\u0081梁乐民,陆剑青 ","actors":"郭富城,梁家辉,李治廷,彭于晏,杨采妮","showtime":"2017-11-08","score":8.9}]
         */

        public int total_page;
        public List<IndividualityBean> individuality;

        public static class IndividualityBean implements MultiItemEntity {
            /**
             * type : 2
             * content_id : 3838002
             * img : https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d
             * name : 极寒之地
             * tag : 动作,冒险,科幻
             * area :
             * directors : 梁乐民,陆剑青
             * actors : 郭富城,梁家辉,李治廷,彭于晏,杨采妮 
             * showtime : 2012-11-08
             * score : 9.2
             */

            public int type;
            public String content_id;
            public String img;
            public String name;
            public String tag;
            public String area;
            public String directors;
            public String actors;
            public String showtime;
            public double score;


            /**
             * 片单类型
             */
            public static final int TYPE_FORM = 2;
            /**
             * 影视类型
             */
            public static final int TYPE_DETAIL = 1;

            @Override
            public int getItemType() {
                return type;
            }
        }
    }
}
