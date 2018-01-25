package com.example.alan.myapplication.alan.bean;

import java.util.List;

/**
 * Created by Alan on 2018/1/24.
 * 影视页VideoFragment的轮播图的bean
 */

public class VideoFragmentBannerBean {
    /**
     * code : 200
     * message : SUCCESS
     * mItemDataList : {"total_page":2,"banner_list":[{"img":"wpzs/201707/19/596ef3529721738555.jpeg","title":"少林问道： 豆瓣8.6分的高口碑剧","subtitle":"今日推荐","type":1,"detail":""},{"img":"wpzs/201708/04/5983ef79ed2db12085.jpeg","title":"河神：龙诀班底力作！津门天团水下探奇案","subtitle":"今日推荐","type":1,"detail":""}]}
     */

    public int code;
    public String message;
    public DataBean data;

    public static class DataBean {
        /**
         * total_page : 2
         * banner_list : [{"img":"wpzs/201707/19/596ef3529721738555.jpeg","title":"少林问道： 豆瓣8.6分的高口碑剧","subtitle":"今日推荐","type":1,"detail":""},{"img":"wpzs/201708/04/5983ef79ed2db12085.jpeg","title":"河神：龙诀班底力作！津门天团水下探奇案","subtitle":"今日推荐","type":1,"detail":""}]
         */

        public int total_page;
        public List<BannerListBean> banner_list;

        public static class BannerListBean {
            /**
             * img : wpzs/201707/19/596ef3529721738555.jpeg
             * title : 少林问道： 豆瓣8.6分的高口碑剧
             * subtitle : 今日推荐
             * type : 1
             * detail :
             */

            public String img;
            public String title;
            public String subtitle;
            public int type;
            public String detail;
        }
    }
}
