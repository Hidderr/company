package com.example.alan.myapplication.alan.bean;

import java.util.List;

/**
 * Created by Alan on 2018/1/29.
 * 功能：影视筛选结果
 */

public class VideoScreeningResultBean {
    /**
     * code : 200
     * msg : success
     * data : [{"video_id":"aff12451264b8192907bbdc9d68eac2a","name":"西游伏妖篇","cover":"http://pic6.qiyipic.com/image/20170412/39/4d/v_111512587_m_601_m1_260_360.jpg"},{"video_id":"ff5af78c194b98ca533b1b3333deed86","name":"雄狮","cover":"http://pic3.qiyipic.com/image/20170814/ce/36/v_112880419_m_601_m1_260_360.jpg"},{"video_id":"cf92485606653e436f03cc1d10832411","name":"绝世高手","cover":"http://pic5.qiyipic.com/image/20170810/40/b7/v_112875550_m_601_m3_260_360.jpg"},{"video_id":"dfd08f73965e7847f57ec1b66d4bc58e","name":"速度与激情8","cover":"http://0img.hitv.com/preview/internettv/sp_images/ott/2017/dianying/308892/20170413100922902-new.jpg"},{"video_id":"f727d3b62930aa8685862d88dde35bc0","name":"冰雪大作战","cover":"http://pic8.qiyipic.com/image/20170607/70/35/v_112465703_m_601_m1_260_360.jpg"},{"video_id":"dc3f4f3ac5cbbd393d8f84ecb6e8dcc5","name":"决战食神","cover":"http://pic8.qiyipic.com/image/20170406/02/09/v_112087282_m_601_m2_260_360.jpg"},{"video_id":"e0a51c3ab503efc9cb8952cc2df2570f","name":"冈仁波齐","cover":"http://pic1.qiyipic.com/image/20170803/91/8f/v_112947387_m_601_m2_260_360.jpg"},{"video_id":"2b77ae37cae031b8a3c03c68b109318a","name":"逆时营救","cover":"http://2img.mgtv.com/preview/internettv/sp_images/ott/2017/dianying/314137/20170728140049856-new.jpg"},{"video_id":"9dcc46cd515832d60cb68cc8fd8fc549","name":"战狼","cover":"http://pic2.qiyipic.com/image/20160622/db/56/v_109159633_m_601_m7_260_360.jpg"},{"video_id":"9b9ccd18cd151b95a1f6ea2b3ed8cc15","name":"极速之巅","cover":"http://pic5.qiyipic.com/image/20170527/15/a7/v_112295378_m_601_m1_260_360.jpg"},{"video_id":"eb9e258957ead3577377d5aeb752dcf0","name":"记忆大师","cover":"http://pic7.qiyipic.com/image/20170724/eb/b5/v_112428330_m_601_m1_260_360.jpg"},{"video_id":"72d166434618f99ec9db6ef62eed1887","name":"攻壳机动队","cover":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/i/is7os79rewv1iuk.jpg"},{"video_id":"e0570592424f65c91f94ac6982848695","name":"摔跤吧！爸爸","cover":"http://pic9.qiyipic.com/image/20170703/60/8f/v_112643955_m_601_m3_260_360.jpg"},{"video_id":"783cc40ad9f971a01a15336ceb02cdf3","name":"超凡战队","cover":"http://pic0.qiyipic.com/image/20170712/06/78/v_112630662_m_601_m2_260_360.jpg"},{"video_id":"6a95ff919e0b547003d5b9f0a8725fb2","name":"金刚：骷髅岛","cover":"http://i.gtimg.cn/qqlive/img/jpgcache/files/qqvideo/7/71bctb897dwx46m.jpg"}]
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * video_id : aff12451264b8192907bbdc9d68eac2a
         * name : 西游伏妖篇
         * cover : http://pic6.qiyipic.com/image/20170412/39/4d/v_111512587_m_601_m1_260_360.jpg
         */

        public String video_id;
        public String name;
        public String cover;
    }
}
