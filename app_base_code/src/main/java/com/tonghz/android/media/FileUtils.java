package com.tonghz.android.media;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.tonghz.android.app.BaseApplication;
import com.tonghz.android.utils.LogUtils;
import com.tonghz.android.utils.SDCardUtils;
import com.tonghz.android.utils.StringUtils;
import com.tonghz.android.widgets.ToastUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class FileUtils {
    /**
     * 编码格式
     */
    private static final String CHARSET = "UTF-8";

    /**
     * 图片
     */
    public static final String FILE_TYPE_IMAGE = "IMAGE";
    public static final String IMAGE_FORMAT_JPEG = "jpg";
    public static final String IMAGE_SUFFIX_PNG = "png";
    public static final String IMAGE_SUFFIX_JPEG = ".jpg";

    /**
     * 音频
     */
    public static final String FILE_TYPE_AUDIO = "AUDIO";
    public static final String AUDIO_FORMAT = "amr";
    public static final String AUDIO_SUFFIX = ".amr";

    /**
     * 视频
     */
    public static final String FILE_TYPE_VIDEO = "VIDEO";
    public static final String VIDEO_FORMAT = "mp4";
    public static final String VIDEO_SUFFIX = ".mp4";

    /**
     * 下载
     */
    public static final String FILE_TYPE_DOWNLOAD = "DOWNLOAD";

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 文件存储（写入内部存储）
     *
     * @param context     上下文对象
     * @param fileName    文件名
     * @param dataSources 待写入内容
     */
    public static void writeToFile(Context context, String fileName, String dataSources) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(dataSources.getBytes(CHARSET));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件（从内部存储中读取）
     *
     * @param context  上下文对象
     * @param fileName 文件名
     * @return String字符串
     */
    public static String readFromFile(Context context, String fileName) {
        String result = null;
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            result = baos.toString();

            fis.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取Assets目录下文件
     *
     * @param context  上下文对象
     * @param fileName 文件名
     * @return 数据
     */
    public static String readFileFromAssets(Context context, String fileName) {
        if (context == null)
            context = BaseApplication.getContext();

        if (StringUtils.isEmpty(fileName))
            return null;
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader reader = new BufferedReader(inputReader);
            String line;
            String result = "";
            while ((line = reader.readLine()) != null)
                result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 读取文件到byte[]
     *
     * @param filePath 文件路径
     * @return byte数组
     */
    public static byte[] getByteArrayByFilePath(String filePath) {
        byte[] byteArray = null;
        ByteArrayOutputStream baos = null;
        File file = new File(filePath);
        /**
         * 判断外部存储是否可用
         */
        if (!SDCardUtils.isSDCardAvailable()) {
            return null;
        }

        /**
         * 判断文件是否存在
         */
        if (!file.exists()) {
            return null;
        }

        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            return null;
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }

            fis.close();
            byteArray = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return byteArray;
    }

    /**
     * 创建文件存储目录（外部存储）
     *
     * @param context  上下文对象
     * @param fileType 文件类型
     * @return 文件目录
     */
    public static File createFileStorageDir(Context context, String fileType) {
        File fileStorageDir = null;
        /**
         * 判断外部存储设备是否可用
         */
        if (!SDCardUtils.isSDCardAvailable()) {
            ToastUtils.showToast(context, "External storage is not mounted READ/WRITE!");
            LogUtils.d(TAG, "External storage is not mounted READ/WRITE!");
        } else {
            if (TextUtils.equals(FILE_TYPE_IMAGE, fileType)) {
                fileStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            } else if (TextUtils.equals(FILE_TYPE_AUDIO, fileType)) {
                fileStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            } else if (TextUtils.equals(FILE_TYPE_VIDEO, fileType)) {
                fileStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            } else if (TextUtils.equals(FILE_TYPE_DOWNLOAD, fileType)) {
                fileStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            }

            if (fileStorageDir != null) {
                if (!fileStorageDir.exists()) {
                    if (!fileStorageDir.mkdirs()) {
                        LogUtils.e(TAG, "Failed to create directory!");
                        return null;
                    }
                }
            }
        }
        return fileStorageDir;
    }

    /**
     * 创建多媒体文件
     *
     * @param context  上下文对象
     * @param fileType 文件类型
     * @param fileName 文件名
     * @return 文件
     */
    public static File createMediaFile(Context context, String fileType, String fileName) {
        String timeStamp = null;
        if (StringUtils.isEmpty(fileName)) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        }
        File fileDir;// 文件目录
        File mediaFile = null;// 文件

        if (TextUtils.equals(FILE_TYPE_IMAGE, fileType)) {
            fileDir = createFileStorageDir(context, FILE_TYPE_IMAGE);
            StringBuilder imgPath = new StringBuilder();
            imgPath.append("IMG_").append(StringUtils.isEmpty(fileName) ? timeStamp : fileName);
            imgPath.append(FileUtils.IMAGE_SUFFIX_JPEG);
            mediaFile = new File(fileDir, imgPath.toString());
        } else if (TextUtils.equals(FILE_TYPE_AUDIO, fileType)) {
            fileDir = createFileStorageDir(context, FILE_TYPE_AUDIO);
            StringBuilder audioPath = new StringBuilder();
            audioPath.append("AUD_").append(StringUtils.isEmpty(fileName) ? timeStamp : fileName);
            audioPath.append(FileUtils.AUDIO_SUFFIX);
            mediaFile = new File(fileDir, audioPath.toString());
        } else if (TextUtils.equals(FILE_TYPE_VIDEO, fileType)) {
            fileDir = createFileStorageDir(context, FILE_TYPE_VIDEO);
            StringBuilder videoPath = new StringBuilder();
            videoPath.append("VID_").append(StringUtils.isEmpty(fileName) ? timeStamp : fileName);
            videoPath.append(FileUtils.VIDEO_FORMAT);
            mediaFile = new File(fileDir, videoPath.toString());
        } else if (TextUtils.equals(FILE_TYPE_DOWNLOAD, fileType)) {
            fileDir = createFileStorageDir(context, FILE_TYPE_VIDEO);
            StringBuilder downloadPath = new StringBuilder();
            downloadPath.append("DOW_").append(StringUtils.isEmpty(fileName) ? timeStamp : fileName);
            downloadPath.append(FileUtils.VIDEO_SUFFIX);
            mediaFile = new File(fileDir, downloadPath.toString());
        }

        if (mediaFile != null) {
            if (!mediaFile.exists()) {
                try {
                    mediaFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.e(TAG, e.getMessage());
                }
            }
        }

        return mediaFile;
    }

}
