package com.example.alan.myapplication.alan.constants;

/**
 * Created by Alan on 2018/1/4.
 * 网络请求地址类
 */

public class AppUrl {

    /**
     * 正式根域名
     */
//    public static final String BASE_URL = "https://screenapi.xgimi.com"; // 正式域名
    /**
     * 测试根域名
     */
    public static final String BASE_URL = "http://10.40.33.172"; // 测试域名


    /**
     * 影视的bannerGet
     */
    public static final String HOMEPAGE_BANNER = "/v4/home_page_banner";
    /**
     * 影视专题Get
     */
    public static final String HOMEPAGE_SUBJECT = "/v4/home_page_subject";
    /**
     * 口味研究所Get
     */
    public static final String HOME_PAGE_INDIVIDUALITY = "/v4/home_page_individuality";
    /**
     * 获取影视详情
     */
    public static final String VIDEO_DETAIL = "/v3/video_detail";
    /**
     * 影视分类
     */
    public static final String VIDEO_CLASSIFICATION = "/v4/video_classification_list";
    /**
     * 影视筛选条件获取
     */
    public static final String VIDEO_CLASSIFICATION_CONDITION_GET = "/v3/video_screen_condition";
    /**
     * 影视筛选结果
     */
    public static final String VIDEO_CLASSIFICATION_RESULT = "/v3/video_screen_list";
    /**
     * 我的影视
     */
    public static final String VIDEO_USER_VIDEO = "/v4/collection_list";
}
