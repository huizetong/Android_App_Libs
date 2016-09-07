package com.tonghz.app.image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tonghz.android.media.FileUtils;
import com.tonghz.android.utils.LogUtils;
import com.tonghz.app.R;

import java.io.File;

/**
 * 图片菜单：1.拍照；2.从相册选择（系统相册）
 * Created by TongHuiZe on 2016/2/20.
 */
public class ImageMenu extends DialogFragment implements OnClickListener {
    private static Activity mActivity;
    private static Fragment mFragment;

    /**
     * 拍照的图片文件
     */
    public File mTakePhotoFile;

    /**
     * 裁剪的图片文件
     */
    public File mCropPhotoFile;

    /**
     * 图片相关操作
     */
    public static final int REQUEST_CODE_CAMERA = 1001;// 拍照
    public static final int REQUEST_CODE_GALLERY = 1002;// 从相册选择
    public static final int REQUEST_CODE_CROP = 1003;// 裁剪

    private static final String TAG = ImageMenu.class.getSimpleName();

    public static ImageMenu newInstance(Activity activity) {
        mActivity = activity;
        mFragment = null;
        return new ImageMenu();
    }

    public static ImageMenu newInstance(Fragment fragment) {
        mFragment = fragment;
        mActivity = null;
        return new ImageMenu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setStyle(ImageMenu.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_menu, container, false);
        rootView.findViewById(R.id.btn_camera).setOnClickListener(this);// 拍照
        rootView.findViewById(R.id.btn_gallery).setOnClickListener(this);// 从相册选择
        rootView.findViewById(R.id.btn_cancel).setOnClickListener(this);// 取消

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BF777777")));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_camera) {
            // 拍照
            takePicture();
        } else if (v.getId() == R.id.btn_gallery) {
            // 从相册选择
            selectPhoto();
        } else if (v.getId() == R.id.btn_cancel) {
            // 取消对话框
            dismissDialog();
        }
    }

    /**
     * 拍照
     */
    private void takePicture() {
        /*
         * 创建照片
         */
        File takePhotoFile = FileUtils.createMediaFile(getActivity(), FileUtils.FILE_TYPE_IMAGE, null);
        LogUtils.d(TAG, "拍照创建的照片文件：" + takePhotoFile);
        mTakePhotoFile = takePhotoFile;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(takePhotoFile));
        }

//        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
        if (mActivity == null && mFragment != null)
            mFragment.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
        else if (mActivity != null && mFragment == null)
            mActivity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
    }

    /**
     * 从相册选择
     */
    private void selectPhoto() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

//        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
        if (mActivity == null && mFragment != null)
            mFragment.startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
        else if (mActivity != null && mFragment == null)
            mActivity.startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
    }

    /**
     * 裁剪图片
     *
     * @param originalUri 原图片URI
     * @param aspectX     裁剪框比例——宽
     * @param aspectY     裁剪框比例——高
     * @param outputX     图片输出大小——宽
     * @param outputY     图片输出大小——高
     */
    public void cropPictures(Uri originalUri, int aspectX, int aspectY, int outputX, int outputY) {
        File cropPhotoFile = FileUtils.createMediaFile(getActivity(), FileUtils.FILE_TYPE_IMAGE, null);
        LogUtils.d(TAG, "裁剪创建的照片文件：" + cropPhotoFile);
        mCropPhotoFile = cropPhotoFile;
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(originalUri, "image/*");
        cropIntent.putExtra("crop", "true");// 可裁减
        /* 裁剪框比例 */
        cropIntent.putExtra("aspectX", aspectX);
        cropIntent.putExtra("aspectY", aspectY);
        /* 输出图片的大小 */
        cropIntent.putExtra("outputX", outputX);
        cropIntent.putExtra("outputY", outputY);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("return-data", false);// 设为false，通过图片URI获取
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropPhotoFile));
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        cropIntent.putExtra("noFaceDetection", false);

//        startActivityForResult(cropIntent, REQUEST_CODE_CROP);
        if (mActivity == null && mFragment != null)
            mFragment.startActivityForResult(cropIntent, REQUEST_CODE_CROP);
        else if (mActivity != null && mFragment == null)
            mActivity.startActivityForResult(cropIntent, REQUEST_CODE_CROP);
    }

    /**
     * 对话框消失
     */
    public void dismissDialog() {
        if (isVisible() && isCancelable()) {
            dismiss();
        }
    }

}
