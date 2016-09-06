package com.tonghz.android.media;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 网络图片加载工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class ImageLoaderUtils {
    private volatile static ImageLoaderUtils mInstance;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private ImageLoader mImageLoader;

    private ImageLoaderUtils() {
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 获取ImageLoaderUtils实例
     *
     * @return ImageLoaderUtils实例
     */
    public static ImageLoaderUtils getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 加载图片
     *
     * @param imgUri    图片uri
     * @param imageView 图片ImageView
     */
    public void displayImage(String imgUri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        mImageLoader.displayImage(imgUri, imageView, options);
    }

    /**
     * 加载图片
     *
     * @param imgUri       图片uri
     * @param imageView    图片ImageView
     * @param loadingResId 加载中图片资源
     * @param emptyResId   空链接图片资源
     * @param failResId    加载失败图片资源
     */
    public void displayImage(String imgUri, ImageView imageView, int loadingResId, int emptyResId, int failResId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(loadingResId)
                .showImageForEmptyUri(emptyResId).showImageOnFail(failResId).cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        mImageLoader.displayImage(imgUri, imageView, options);
    }

    /**
     * 加载圆角图片
     *
     * @param imgUri       图片uri
     * @param imageView    ImageView对象
     * @param roundedValue 圆角值
     */
    public void displayRoundedImage(String imgUri, ImageView imageView, int roundedValue) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(roundedValue)).build();
        mImageLoader.displayImage(imgUri, imageView, options);
    }

    /**
     * 加载图片（圆角）
     *
     * @param imgUri       图片uri
     * @param imageView    图片ImageView
     * @param loadingResId 加载中图片资源
     * @param emptyResId   空链接图片资源
     * @param failResId    加载失败图片资源
     * @param roundedValue 圆角值
     */
    public void displayRoundedImage(String imgUri, ImageView imageView, int loadingResId, int emptyResId, int failResId,
                                    int roundedValue) {
        mImageLoader.displayImage(imgUri, imageView, getRoundedOptions(loadingResId, emptyResId, failResId, roundedValue));
    }

    /**
     * 图片加载（带有动画效果）
     *
     * @param imgUri       图片uri
     * @param imageView    图片ImageView
     * @param loadingResId 加载中图片资源
     * @param emptyResId   空链接图片资源
     * @param failResId    加载失败图片资源
     * @param roundedValue 圆角值
     */
    @Deprecated
    public void displayImageWithAnimateListener(String imgUri, ImageView imageView, int loadingResId, int emptyResId,
                                                int failResId, int roundedValue) {
        mImageLoader.displayImage(imgUri, imageView, getRoundedOptions(loadingResId, emptyResId, failResId, roundedValue),
                animateFirstListener);
    }

    /**
     * 加载大图片
     *
     * @param imgUri    图片uri
     * @param imageView 图片ImageView
     */
    public void displayBigImage(String imgUri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true).cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        mImageLoader.displayImage(imgUri, imageView, options);
    }

    /**
     * 图片大加载
     *
     * @param imgUri       图片uri
     * @param imageView    图片ImageView
     * @param loadingResId 加载中图片资源
     * @param emptyResId   空链接图片资源
     * @param failResId    加载失败图片资源
     */
    public void displayBigImage(String imgUri, ImageView imageView, int loadingResId, int emptyResId, int failResId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(loadingResId)
                .showImageForEmptyUri(emptyResId).showImageOnFail(failResId).resetViewBeforeLoading(true).cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        mImageLoader.displayImage(imgUri, imageView, options);
    }

    /**
     * 获取圆角图片显示选项
     *
     * @param loadingResId 加载中图片资源
     * @param emptyResId   空链接图片资源
     * @param failResId    加载失败图片资源
     * @param roundedValue 圆角值
     * @return DisplayImageOptions对象
     */
    private DisplayImageOptions getRoundedOptions(int loadingResId, int emptyResId, int failResId, int roundedValue) {
        return new DisplayImageOptions.Builder().showImageOnLoading(loadingResId)
                .showImageForEmptyUri(emptyResId).showImageOnFail(failResId).cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(roundedValue)).build();
    }

    /**
     * 加载动画效果（第一次有效）
     */
    static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public void pause() {
        if (mImageLoader != null)
            mImageLoader.pause();
    }

    public void resume() {
        if (mImageLoader != null)
            mImageLoader.resume();
    }

    public void stop() {
        if (mImageLoader != null)
            mImageLoader.stop();
    }
}
