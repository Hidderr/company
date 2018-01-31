package com.example.alan.myapplication.alan.bean;

import java.util.List;

/**
 * Created by Alan on 2018/1/31.
 * 功能：
 */

public class UserVideoBean {
    /**
     * code : 200
     * msg : success
     * data : {"collect_video_list":{"type":1,"video_list":[{"content_id":"3838001","name":"战争史诗巨制","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","tag":"战争,硬汉,动作"},{"content_id":"3838002","name":"极寒之地","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","tag":"动作,冒险,科幻"}]},"collect_video":{"collect":[{"content_id":"7562f4fd138033a4bf6bec5f3f88a5eb","name":"主厨的餐桌：法国篇","img":"http://pic4.qiyipic.com/image/20170622/f9/59/a_100057238_m_601_260_360.jpg"},{"content_id":"6c71c198e31bf2a9cb4b8e147952602b","name":"骑行中国","img":"http://pic6.qiyipic.com/image/20161031/16/38/a_100035432_m_601_m1_260_360.jpg"},{"content_id":"69ee1f2b875305e103ad6aa612d50187","name":"百集纪录片中国艺术家影视文献库《艺术中国》","img":"http://pic8.qiyipic.com/image/20170802/08/f9/a_100094729_m_601_260_360.jpg"}],"complement":[{"content_id":"4617ef1c12498b0bb63bfd6e88afa7f0","name":"魔法老师（国语）","img":"http://pic0.qiyipic.com/image/20161025/c8/70/v_111171393_m_601_m1_260_360.jpg"}]},"recommend_video":[{"content_id":"4617ef1c12498b0bb63bfd6e88afa7f0","name":"魔法老师（国语）","img":"http://pic0.qiyipic.com/image/20161025/c8/70/v_111171393_m_601_m1_260_360.jpg"}]}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * collect_video_list : {"type":1,"video_list":[{"content_id":"3838001","name":"战争史诗巨制","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","tag":"战争,硬汉,动作"},{"content_id":"3838002","name":"极寒之地","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","tag":"动作,冒险,科幻"}]}
         * collect_video : {"collect":[{"content_id":"7562f4fd138033a4bf6bec5f3f88a5eb","name":"主厨的餐桌：法国篇","img":"http://pic4.qiyipic.com/image/20170622/f9/59/a_100057238_m_601_260_360.jpg"},{"content_id":"6c71c198e31bf2a9cb4b8e147952602b","name":"骑行中国","img":"http://pic6.qiyipic.com/image/20161031/16/38/a_100035432_m_601_m1_260_360.jpg"},{"content_id":"69ee1f2b875305e103ad6aa612d50187","name":"百集纪录片中国艺术家影视文献库《艺术中国》","img":"http://pic8.qiyipic.com/image/20170802/08/f9/a_100094729_m_601_260_360.jpg"}],"complement":[{"content_id":"4617ef1c12498b0bb63bfd6e88afa7f0","name":"魔法老师（国语）","img":"http://pic0.qiyipic.com/image/20161025/c8/70/v_111171393_m_601_m1_260_360.jpg"}]}
         * recommend_video : [{"content_id":"4617ef1c12498b0bb63bfd6e88afa7f0","name":"魔法老师（国语）","img":"http://pic0.qiyipic.com/image/20161025/c8/70/v_111171393_m_601_m1_260_360.jpg"}]
         */

        public CollectVideoListBean collect_video_list;
        public CollectVideoBean collect_video;
        public List<RecommendVideoBean> recommend_video;

        public static class CollectVideoListBean {
            /**
             * type : 1
             * video_list : [{"content_id":"3838001","name":"战争史诗巨制","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","tag":"战争,硬汉,动作"},{"content_id":"3838002","name":"极寒之地","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","tag":"动作,冒险,科幻"}]
             */

            public int type;
            public List<VideoListBean> video_list;

            public static class VideoListBean {
                /**
                 * content_id : 3838001
                 * name : 战争史诗巨制
                 * img : https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d
                 * tag : 战争,硬汉,动作
                 */

                public String content_id;
                public String name;
                public String img;
                public String tag;
            }
        }

        public static class CollectVideoBean {
            public List<CollectBean> collect;
            public List<ComplementBean> complement;

            public static class CollectBean {
                /**
                 * content_id : 7562f4fd138033a4bf6bec5f3f88a5eb
                 * name : 主厨的餐桌：法国篇
                 * img : http://pic4.qiyipic.com/image/20170622/f9/59/a_100057238_m_601_260_360.jpg
                 */

                public String content_id;
                public String name;
                public String img;
            }

            public static class ComplementBean {
                /**
                 * content_id : 4617ef1c12498b0bb63bfd6e88afa7f0
                 * name : 魔法老师（国语）
                 * img : http://pic0.qiyipic.com/image/20161025/c8/70/v_111171393_m_601_m1_260_360.jpg
                 */

                public String content_id;
                public String name;
                public String img;
            }
        }

        public static class RecommendVideoBean {
            /**
             * content_id : 4617ef1c12498b0bb63bfd6e88afa7f0
             * name : 魔法老师（国语）
             * img : http://pic0.qiyipic.com/image/20161025/c8/70/v_111171393_m_601_m1_260_360.jpg
             */

            public String content_id;
            public String name;
            public String img;
            public String area;
            public String directors;
            public String actors;
            public String showtime;
            public double score;
        }
    }
}
