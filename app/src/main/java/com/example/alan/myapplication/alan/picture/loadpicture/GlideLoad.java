package com.example.alan.myapplication.alan.picture.loadpicture;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;


/**
 * Created by Alan on 2018/1/4.
 */

public class GlideLoad {

    public static GlideLoad mGlideLoad;

    private GlideLoad(){}
    public static GlideLoad getInstance(){
        if (mGlideLoad == null)
        {
            synchronized (GlideLoad.class)
            {
                if (mGlideLoad == null)
                {
                    mGlideLoad = new GlideLoad();
                }
            }
        }
        return mGlideLoad;
    }





    public void loadServerPic(Context cxt, String url, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        switch (roundType){
            case PictureManager.CIRCLE_TYPE:
                Glide.with(cxt).load(url)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideCircleTransform(cxt))
                        .into(view);
                break;
            case PictureManager.ROUND_TYPE:
                Glide.with(cxt).load(url)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideRoundTransform(cxt,roudeAngle))
                        .into(view);
                break;
            case PictureManager.NORMAL:
                Glide.with(cxt).load(url)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .into(view);
                break;
        }


    }





    public void loadFilePic(Context cxt, File file, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        switch (roundType){
            case PictureManager.CIRCLE_TYPE:
                Glide.with(cxt).load(file)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideCircleTransform(cxt))
                        .into(view);
                break;
            case PictureManager.ROUND_TYPE:
                Glide.with(cxt).load(file)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideRoundTransform(cxt,roudeAngle))
                        .into(view);
                break;
            default:
                Glide.with(cxt).load(file)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .into(view);
                break;
        }
    }





    public void loadResourcePic(Context cxt, int rsource, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        switch (roundType){
            case PictureManager.CIRCLE_TYPE:
                Glide.with(cxt).load(rsource)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideCircleTransform(cxt))
                        .into(view);
                break;
            case PictureManager.ROUND_TYPE:
                Glide.with(cxt).load(rsource)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideRoundTransform(cxt,roudeAngle))
                        .into(view);
                break;
            default:
                Glide.with(cxt).load(rsource)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .into(view);
                break;
        }
    }





    public void loadBytePic(Context cxt, byte[] rsource, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        switch (roundType){
            case PictureManager.CIRCLE_TYPE:
                Glide.with(cxt).load(rsource)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideCircleTransform(cxt))
                        .into(view);
                break;
            case PictureManager.ROUND_TYPE:
                Glide.with(cxt).load(rsource)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideRoundTransform(cxt,roudeAngle))
                        .into(view);
                break;
            default:
                Glide.with(cxt).load(rsource)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .into(view);
                break;
        }
    }





    public void loadServerPicNoCache(Context cxt, String rsource, ImageView view, int placeHolderPic, int errorPic, int roundType, int roudeAngle){
        switch (roundType){
            case PictureManager.CIRCLE_TYPE:
                Glide.with(cxt).load(rsource)
                        . skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideCircleTransform(cxt))
                        .into(view);
                break;
            case PictureManager.ROUND_TYPE:
                Glide.with(cxt).load(rsource)
                        . skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .transform( new GlideRoundTransform(cxt,roudeAngle))
                        .into(view);
                break;
            default:
                Glide.with(cxt).load(rsource)
                        . skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(placeHolderPic)
                        .error(errorPic)
                        .into(view);
                break;
        }
    }






}
