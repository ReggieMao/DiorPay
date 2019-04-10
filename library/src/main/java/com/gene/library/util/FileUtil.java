package com.gene.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by MaoLJ on 2018/7/18.
 * 文件工具类
 */

public class FileUtil {

    private static final String TAG = "FileUtil";

    private FileUtil() {

    }

    /**
     * 缓存文件根目录名
     */
    private static final String FILE_DIR = "DiorPay";

    /**
     * 上传的头像文件路径
     */
    private static final String UPLOAD_FILE = "Avatar";

    /**
     * SD卡是否存在
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取上传的路径
     */
    public static String getUploadPath(Context context) {
        if (isSDCardExist()) {
            String path = Environment.getExternalStorageDirectory() + File.separator + FILE_DIR + File.separator + UPLOAD_FILE + File.separator;
            File directory = new File(path);
            if (!directory.exists()) directory.mkdirs();
            return path;
        } else {
            File directory = new File(context.getCacheDir(), FILE_DIR + File.separator + UPLOAD_FILE);
            if (!directory.exists()) directory.mkdirs();
            return directory.getAbsolutePath();
        }
    }

    /**
     * 获取头像
     */
    public static Bitmap getAvatar(String path) {
        Bitmap bitmap;
        try {
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存头像
     */
    public static void saveAvatar(Context context, String path) {
        try {
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(getUploadPath(context) + "avatar.jpg");
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹下所有文件,适当放到子线程中执行
     */
    public static void deleteFiles(File file) {
        if (file == null || !file.exists()) return;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (!f.isDirectory()) {
                    f.delete();
                }
            }
        } else {
            file.delete();
        }
    }

    /**
     * 判断某文件是否存在
     */
    public static boolean fileIsExists(Context context) {
        try {
            File f = new File(getUploadPath(context) + "avatar.jpg");
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
