package com.example.alan.myapplication.alan.bean;

import java.util.List;

/**
 * Created by Alan on 2018/2/1.
 * 功能：我的影视 -收藏片单
 */

public class UserVideoCassificationFormBean {
    /**
     * code : 200
     * msg : success
     * data : {"video_list":[{"list_id":"3838001","name":"战争史诗巨制","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","tag":"战争,硬汉,动作"},{"list_id":"3838002","name":"极寒之地","img":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d","tag":"动作,冒险,科幻"}]}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        public List<VideoListBean> video_list;

        public static class VideoListBean {
            /**
             * list_id : 3838001
             * name : 战争史诗巨制
             * img : https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d
             * tag : 战争,硬汉,动作
             */

            public String content_id;
            public String name;
            public String img;
            public String tag;
            public boolean isSelcted;
            public boolean allChooseTrue;
            public boolean allChooseFalse;
        }
    }
}
