package com.nj.xufeng.xfutils.tool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.UUID;

/**
 * Created by xufeng on 15/11/9.
 */
public class HanderEditorTool {

    public static final int TAKE_PICTURE_FROM_CAMERA = 100;//选相机
    public static final int TAKE_PICTURE_FROM_GALLERY = 200;//选图库
    public static String FILE_PATH = Environment.getExternalStorageDirectory()
            + "/PHOTO_ZGN/HEADER/";

    static {
        File dir = new File(FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static Uri openCamera(Activity activity) {
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
        // 根据文件地址创建文件
        File file = new File(FILE_PATH + getFileName("", ".JPEG"));
//        if (file.exists()) {
//             file.delete();
//        }
        Uri uri = Uri.fromFile(file);
        // 设置系统相机拍摄照片完成后图片文件的存放地址
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 开启系统拍照的Activity
        activity.startActivityForResult(cameraIntent, TAKE_PICTURE_FROM_CAMERA);
        return uri;
    }

    public static void openAlbum(Activity activity){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        i.setPackage("xxxx");// xxxx是具体报名，系统默认的一般是com.android.xx
        activity.startActivityForResult(i, TAKE_PICTURE_FROM_GALLERY);
    }


    public static void onActivityResultJump(Activity activity, Uri uri, int resultCode, int requestCode, Intent data, HandleCropListener hcl) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE_FROM_CAMERA:// 相机返回图片，再进入图片发表页面
                {
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                        return;
                    }
                    // gridImageAdapter.add(uri.getPath());
                    if (null != uri) {
//                        Logger.d("uri:" + uri.getPath());
                        beginCrop(activity, uri);
                    }
                    break;
                }
                case TAKE_PICTURE_FROM_GALLERY: {
                    Uri selectedImage = data.getData();
                    beginCrop(activity, selectedImage);
                    break;
                }
                case Crop.REQUEST_CROP: {
                    handleCrop(activity, resultCode, data, hcl);
                    break;
                }
                default:
                    break;
            }
        }
    }

    public interface HandleCropListener {
        void onSuccess(Uri handleCropUri);

        void onFail(String msg);
    }

    private static void beginCrop(Activity act, Uri source) {
        Uri destination = Uri.fromFile(new File(act.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().withMaxSize(300,300).start(act);
    }

    private static void handleCrop(Activity act, int resultCode, Intent result, HandleCropListener hcl) {
        if (resultCode == Activity.RESULT_OK) {
            hcl.onSuccess(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            hcl.onFail(Crop.getError(result).getMessage());
        }
    }

    public static String getFileName(String name, String type) {
        String fileName = UUID.randomUUID().toString() + name + type;
        return fileName;
    }

}
