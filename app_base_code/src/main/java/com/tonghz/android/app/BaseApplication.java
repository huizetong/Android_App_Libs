package com.tonghz.android.app;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yolanda.nohttp.NoHttp;

/**
 * Application基类
 * Created by TongHuiZe on 2016/3/30.
 */
public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        /*
         * 初始化NoHttp
         */
        NoHttp.initialize(this);

        /*
         * 初始化ImageLoader
		 */
        initImageLoader(getApplicationContext());
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化ImageLoader
     *
     * @param context 上下文对象
     */
    private void initImageLoader(Context context) {
        /**
         * This configuration tuning is custom. You can tune every option, you may tune some of them, or you can create default
         * configuration by ImageLoaderConfiguration.createDefault(this); method.
         */
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
