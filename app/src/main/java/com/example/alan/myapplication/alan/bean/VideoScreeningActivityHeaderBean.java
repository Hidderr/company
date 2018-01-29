package com.example.alan.myapplication.alan.bean;

import java.util.List;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视筛选条件
 */

public class VideoScreeningActivityHeaderBean {
    /**
     * code : 200
     * msg : success
     * data : {"type":[{"id":1,"category_id":1,"name":"爱情"},{"id":2,"category_id":1,"name":"喜剧"},{"id":3,"category_id":1,"name":"动作"},{"id":4,"category_id":1,"name":"科幻"},{"id":5,"category_id":1,"name":"动画"},{"id":6,"category_id":1,"name":"恐怖"},{"id":7,"category_id":1,"name":"犯罪"},{"id":8,"category_id":1,"name":"惊悚"},{"id":9,"category_id":1,"name":"悬疑"},{"id":10,"category_id":1,"name":"其他"}],"year":[{"name":2018},{"name":"2017-2011"},{"name":"2010-2000"},{"name":"90年代"},{"name":"80年代"}],"area":[{"category_id":1,"name":"华语"},{"category_id":1,"name":"美国"},{"category_id":1,"name":"欧洲"},{"category_id":1,"name":"韩国"},{"category_id":1,"name":"日本"},{"category_id":1,"name":"泰国"},{"category_id":1,"name":"其他"}]}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        public List<TypeBean> type;
        public List<YearBean> year;
        public List<AreaBean> area;

        public static class TypeBean {
            /**
             * id : 1
             * category_id : 1
             * name : 爱情
             */

            public int id;
            public int category_id;
            public String name;
        }

        public static class YearBean {
            /**
             * name : 2018
             */

            public int name;
        }

        public static class AreaBean {
            /**
             * category_id : 1
             * name : 华语
             */

            public int category_id;
            public String name;
        }
    }
}
