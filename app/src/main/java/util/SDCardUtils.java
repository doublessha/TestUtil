package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

/**
 * 封装SDCard常用的工具类
 * 
 * @author Jimmy
 */
public class SDCardUtils {
	private static final String TAG = "SDCardUtils";
	/**
	 * 判断SDCard的加载情况
	 * 
	 * @return boolean true表示挂载成功 false挂载失败
	 */
	public static boolean isMounted() {
		// 获取当前挂在的状态(返回的是字符串)
		String state = Environment.getExternalStorageState();
		// 这是设备状态的状态(常量Environment.MEDIA_MOUNTED)
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取SDCard的根路径
	 * 
	 * @return file路径,磁盘映射的文件所在路径
	 */
	public static File getRootFilePath() {
		if (isMounted()) {
			// 返回实际有意义的SDCard的路径
			return Environment.getExternalStorageDirectory();
		}
		return null;
	}

	/**
	 * 获取SDCard的根路径
	 * 
	 * @return String路径,磁盘映射的文件所在File路径转换过来的
	 */
	public static String getRootStringPath() {
		if (getRootFilePath() != null) {
			return getRootFilePath().getAbsolutePath();
		}
		return null;
	}

	/**
	 * 获取SDCard的磁盘总的大小
	 * 
	 * @return int 磁盘空间的大小(默认采用:MB)
	 */
	public static long getTotalSize() {
		// 参数填写需要查看的文件目录
		if (getRootStringPath() != null) {
			StatFs statFs = new StatFs(getRootStringPath());
			// int size = statFs.getBlockSize(); // 每一块的大小
			// int count = statFs.getBlockCount(); // 有多少块
			long size = statFs.getBlockSizeLong();
			long count = statFs.getBlockCountLong();

			// statFs.getBlockCountLong(); //是18才能使用,之前都使用老的方法
			// 单位每次变大/1024就可以了
			// 都是用long类型的方法进行替换,SDCard的空间计算用int会溢出不够存(采用long类型的)
			return count * size / 1024 / 1024; // 单位是byte,可能需要单位转换(B->KB->MB->GB)
		}
		return 0;
	}

	/**
	 * 获取SDCard的磁盘可用大小
	 * 
	 * @return int 磁盘空间的大小(默认采用:MB)
	 */
	public static long getAvalibleSize() {
		// 参数填写需要查看的文件目录
		if (getRootStringPath() != null) {
			StatFs statFs = new StatFs(getRootStringPath());
			// int size = statFs.getBlockSize(); // 每一块的大小
			// int count = statFs.getAvailableBlocks(); // 有可用多少块,只要修改这里就可以了
			long size = statFs.getBlockSizeLong(); // 是18才能使用,之前都使用老的方法
			long count = statFs.getAvailableBlocksLong();
			// 单位每次变大/1024就可以了
			// 都是用long类型的方法进行替换,SDCard的空间计算用int会溢出不够存(采用long类型的)
			return count * size / 1024 / 1024; // 单位是byte,可能需要单位转换(B->KB->MB->GB)
		}
		return 0;
	}

	/**
	 * 封装SDCard写入
	 * 
	 * @param 文件的路径(自定义路径)
	 *            path
	 * @param 文件的名字
	 *            fileName
	 * @param 写入的内容data
	 *            byte[]类型
	 * @return true 表示写入成功, false失败
	 * 
	 */
	public static boolean write(String path, String fileName, byte[] data) {
		FileOutputStream fos = null;
		boolean isCreateDir = false;
		String newDir = getRootStringPath() + File.separator + path;
		if (!new File(newDir).exists()) { // 通过File判断路径是否存在,如果不存在依次创建目录
			// 创建文件路径中所有的目录
			isCreateDir = new File(newDir).mkdirs();
			Log.i(TAG, "创建文件目录-->" + isCreateDir);
		}
		try {
			// 构建FileOutputStream
			fos = new FileOutputStream(new File(newDir, fileName));
			// 写入数据
			fos.write(data);
			fos.flush();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					;
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	public static boolean writeBitmap(String path, String fileName, Bitmap bitmap) {
		FileOutputStream fos = null;
		boolean isCreateDir = false;
		if(!TextUtils.isEmpty(path)&&!TextUtils.isEmpty(fileName)&&bitmap!=null){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
			byte[] data = bos.toByteArray();
			String newDir = getRootStringPath() + File.separator + path;
			if (!new File(newDir).exists()) { // 通过File判断路径是否存在,如果不存在依次创建目录
				// 创建文件路径中所有的目录
				isCreateDir = new File(newDir).mkdirs();
				Log.i(TAG, "创建文件目录-->" + isCreateDir);
			}
			try {
				// 构建FileOutputStream
				fos = new FileOutputStream(new File(newDir, fileName));
				// 写入数据
				fos.write(data);
				fos.flush();
				return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (fos != null)
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	
	

	/**
	 * 封装SDCard读取
	 * 
	 * @param path
	 *            文件的相对路径(自定义的目录路径)
	 * @param fileName
	 *            读取文件的名字
	 * 
	 */
	public static byte[] readBytes(String path, String fileName) {
		String newPath = getRootStringPath() + File.separator + path + File.separator + fileName;
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		try {
			fis = new FileInputStream(newPath);
			byte[] buffer = new byte[8*1024];
			int len = 0;
			baos = new ByteArrayOutputStream();
			while ((len = fis.read(buffer)) != -1) {
				baos.write(buffer,0,len);
				baos.flush();
			}
			//获取所有的数据
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static boolean isHave(String path, String fileName){
		String newPath = getRootStringPath() + File.separator + path + File.separator + fileName;
		File file = new File(newPath);
		if(file.exists()){
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * 封装SDCard写入
	 */
	public static boolean write(String path,String fileName, String data) {
		return false;
	}

	/**
	 * 封装SDCard读取
	 */
	public static String readString(String path, String fileName) {

		return null;
	}
	
	
	/**
	 * SDCard提供公共的路径(调用公共路径比较方便,不用自己在拼接字符串)
	 * @param 具体类型的文件目录
	 * 	{@link #DIRECTORY_MUSIC}, {@link #DIRECTORY_PODCASTS},
     * 	{@link #DIRECTORY_RINGTONES}, {@link #DIRECTORY_ALARMS},
     * 	{@link #DIRECTORY_NOTIFICATIONS}, {@link #DIRECTORY_PICTURES},
     * 	{@link #DIRECTORY_MOVIES}, {@link #DIRECTORY_DOWNLOADS}, or
     * 	{@link #DIRECTORY_DCIM}
	 * @return 实际公共的路径
	 */
	public static String getPublicStringPath(String type){
		//比如下载的文件夹,具体的格式
		//String downloadPath = Environment.DIRECTORY_DOWNLOADS;
		if(isMounted()){
			return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
		}
		return null;
	}
	
	/**
	 * SDCard获取和当前app有关的file目录
	 * @param context 上下文环境
	 */
	public static String getMyAppFilePath(Context context,String type){
		 if(isMounted()){
			return context.getExternalFilesDir(type).getAbsolutePath();
		 }
		return null;
	}
	
	/**
	 * SDCard获取和当前app有关的cache目录
	 */
	public static String getMyAppCachePath(Context context){
		 if(isMounted()){
			return context.getExternalCacheDir().getAbsolutePath();
		 }
		 return null;
	}
	

}
