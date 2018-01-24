package com.example.alan.myapplication.alan.picture.loadpicture;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Alan on 2018/1/4.
 */

public class PictureManager {
    /**
     * 正常图片
     */
    public static final int NORMAL = -1;
    public static PictureManager mPictureManager;
    /**
     * 圆形图片
     */
    public static final int CIRCLE_TYPE = 0;
    /**
     * 圆角图片
     */
    public static final int ROUND_TYPE = 1;
    private PictureManager(){}
    public static PictureManager getInstance(){
        if (mPictureManager == null)
        {
            synchronized (PictureManager.class)
            {
                if (mPictureManager == null)
                {
                    mPictureManager = new PictureManager();
                }
            }
        }
        return mPictureManager;
    }


    /**从网络加载图片
     * @param cxt
     * @param url
     * @param view
     * @param placeHolderPic
     * @param errorPic
     * @param roundType 图片类型
     * @param roudeAngle 圆角图片角度
     */
    public void loadServerPic(Context cxt, String url, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
       GlideLoad.getInstance().loadServerPic(cxt, url, view, placeHolderPic, errorPic, roundType, roudeAngle);
    }





    public void loadFilePic(Context cxt, File file, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        GlideLoad.getInstance().loadFilePic(cxt, file, view, placeHolderPic, errorPic, roundType, roudeAngle);
    }





    public void loadResourcePic(Context cxt, int rsource, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        GlideLoad.getInstance().loadResourcePic(cxt, rsource, view, placeHolderPic, errorPic, roundType, roudeAngle);
    }





    public void loadBytePic(Context cxt, byte[] rsource, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        GlideLoad.getInstance().loadBytePic(cxt, rsource, view, placeHolderPic, errorPic, roundType, roudeAngle);
    }





    public void loadServerPicNoCache(Context cxt, String rsource, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        GlideLoad.getInstance().loadServerPicNoCache(cxt, rsource, view, placeHolderPic, errorPic, roundType, roudeAngle);
    }

}
