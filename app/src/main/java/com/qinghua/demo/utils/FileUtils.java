package com.qinghua.demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * ================================================
 * 文件操作相关
 * <p>
 * Created by yqh on 2018/8/24 8:44
 * ================================================
 */
public class FileUtils extends CommonFileUtil {
	/** 图片文件目录 */
	public static final String IMAGE_DIR = "BigBrain/image";

	/**
	 * 得到本机SD卡的存储目录--需要判断空间
	 * 
	 * @return
	 */
	public static String getPath_Space(Context context) {
		if (getExternalPath() != null && FileUtils.getExternalStorageSize() > 0) {
			File dir = new File(getExternalPath() + "/glyh");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return getExternalPath() + "/glyh";
		} else {
			// 设置手机程序内部存储空间目录
			return getInternalPath_Space(context);
		}
	}

	/**
	 * 获得图片路径
	 * 
	 * @return
	 */
	public static String getBigBrainPicPath(String filename) {
		String filepath = mkDirs(IMAGE_DIR) + "/" + filename;
		return filepath;
	}

	/**
	 * 得到本机SD卡的目录
	 * 
	 * @return
	 */
	public static String getExternalPath() {
		return (FileUtils.getExternalStorageDirectory() == null) ? null
				: FileUtils.getExternalStorageDirectory().getPath();
	}

	/**
	 * 得到本机程序内部的存储目录--需要判断空间
	 * 
	 * @return
	 */
	public static String getInternalPath_Space(Context context) {
		if (FileUtils.getInternalStorageSizeByPath(context.getFilesDir()
				.getParent()) >= MIN_SPACE_SIZE) {
			return context.getFilesDir().getPath();
		} else {
			return null;
		}
	}

	/**
	 * 缓存目录
	 * @param context
	 * @return
	 */
	public static String getCacheDir(Context context) {
		String path = "";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
			try {
				path = context.getExternalCacheDir().getAbsolutePath();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (TextUtils.isEmpty(path)) {
				path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
			}
		} else {
			path = context.getCacheDir().getAbsolutePath();
		}
		return path;
	}
	/**
	 * 得到本机程序内部的存储目录--不需要判断空间
	 * 
	 * @return
	 */
	public static String getInternalPath_NoSpace(Context context) {
		return context.getFilesDir().getPath();
	}

	public static String saveMyBitmap(String bitName, Bitmap mBitmap)
			throws IOException {
		String img_path = "";
		img_path = FileUtils.getBigBrainPicPath("") + "/" + "bigbrain_image_"
				+ System.currentTimeMillis() + ".png";
		File f = new File(img_path);
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img_path;
	}

	/**
	 * 获取文件后缀名
	 * @param file
	 * @return
	 */
	private static String getSuffix(File file) {
		if (file == null || !file.exists() || file.isDirectory()) {
			return null;
		}
		String fileName = file.getName();
		if (fileName.equals("") || fileName.endsWith(".")) {
			return null;
		}
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			return fileName.substring(index + 1).toLowerCase(Locale.US);
		} else {
			return null;
		}
	}

	/**
	 * 获取文件类型
	 * @param file
	 * @return
	 */
	public static String getMimeType(File file){
		String suffix = getSuffix(file);
		if (suffix == null) {
			return "file/*";
		}
		String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
		if (type != null || !type.isEmpty()) {
			return type;
		}
		return "file/*";
	}
}
