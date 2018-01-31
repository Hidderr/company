package com.example.alan.myapplication.alan.bean;

import java.util.List;

/**
 * Created by Alan on 2018/1/31.
 * 功能：我的影视根bean
 */

public class UserVideoRootBean {
    public List<RooBean> data;

    public void setData(List<RooBean> data){
        this.data =data;
    }

    public static class RooBean{
        public String titleName;
        public RooBean(String name){
            this.titleName = name;
        }
    }
}
