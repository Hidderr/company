package com.example.alan.myapplication.alan.bean;

import java.util.List;

/**
 * Created by Alan on 2018/2/1.
 * 功能：我的影视-收藏的影视
 */

public class UserVideoClassificationVideoBean {
    /**
     * code : 200
     * msg : success
     * data : {"video":[{"content_id":"15d70d81985bb9d6daa13a5d4b7d8521","name":"变形金刚4：绝迹重生","img":"http://pic3.qiyipic.com/image/20160512/0f/29/v_50714674_m_601_m4_260_360.jpg","area":"美国","directors":"迈克尔·贝","actors":"马克·沃尔伯格,妮可拉·佩尔茨,凯尔希·格兰莫,李冰冰,杰克·莱诺,斯坦利·图齐","showtime":"2014-06-29","score":6.5},{"content_id":"22bc66bb92928fba766d74f6eefcbd04","name":"河神","img":"http://pic4.qiyipic.com/image/20170718/84/10/a_100048036_m_601_m1_260_360.jpg","area":"","directors":"田里","actors":"李现,张铭恩,王紫璇CiCi,陈芋米","showtime":"2017-07-19","score":8.3},{"content_id":"7e712ef9d77e60cc589eb31e9767d1b1","name":"深海利剑","img":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/7/7ox6sjk6saqpju0.jpg","area":"内地","directors":"赵宝刚","actors":"高旻睿,王阳,徐洋,刘璐","showtime":"2017","score":5.2},{"content_id":"9dcc46cd515832d60cb68cc8fd8fc549","name":"战狼","img":"http://pic2.qiyipic.com/image/20160622/db/56/v_109159633_m_601_m7_260_360.jpg","area":"中国大陆","directors":"吴京","actors":"吴京,余男,斯科特·阿特金斯,Kevin Lee,倪大红","showtime":"2015-04-02","score":6.8},{"content_id":"dc3f4f3ac5cbbd393d8f84ecb6e8dcc5","name":"决战食神","img":"http://pic8.qiyipic.com/image/20170406/02/09/v_112087282_m_601_m2_260_360.jpg","area":"香港 / 中国大陆","directors":"叶伟民","actors":"葛优,谢霆锋,唐嫣,郑容和,白冰,杜海涛,王太利,黄秋生,詹瑞文,海鸣威,罗兰","showtime":"2017-02-10","score":4.7},{"content_id":"e3cb2d932622bc64d5a0db72130c83a6","name":"哈利·波特2：哈利·波特与密室","img":"http://pic5.qiyipic.com/image/20160929/d2/4a/v_50076813_m_601_m4_260_360.jpg","area":"美国 / 英国 / 德国","directors":"克里斯·哥伦布","actors":"丹尼尔·雷德克里夫,艾玛·沃森,鲁伯特·格林特,理查德·格雷弗斯,哈利·米尔林,Alan Sidney Patrick Rickman,理查德·哈里斯,米瑞安·玛格莱斯,肯尼思·布拉纳夫,沃维克·戴维斯","showtime":"2002-11-14","score":8.2}]}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        public List<VideoBean> video;

        public static class VideoBean {
            /**
             * content_id : 15d70d81985bb9d6daa13a5d4b7d8521
             * name : 变形金刚4：绝迹重生
             * img : http://pic3.qiyipic.com/image/20160512/0f/29/v_50714674_m_601_m4_260_360.jpg
             * area : 美国
             * directors : 迈克尔·贝
             * actors : 马克·沃尔伯格,妮可拉·佩尔茨,凯尔希·格兰莫,李冰冰,杰克·莱诺,斯坦利·图齐
             * showtime : 2014-06-29
             * score : 6.5
             */

            public String content_id;
            public String name;
            public String img;
            public String area;
            public String directors;
            public String actors;
            public String showtime;
            public double score;
            public boolean isSelcted;
            public boolean allChooseTrue;
            public boolean allChooseFalse;
        }
    }
}
