package com.example.alan.myapplication.alan.bean;

/**
 * Created by Alan on 2018/2/6.
 * 功能：影视详情判断是否收藏的
 */

public class CollectBean {

    /**
     * code : 200
     * msg : success
     * data : {"msg":"had"}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * msg : had
         */

        public String msg;
    }
}
