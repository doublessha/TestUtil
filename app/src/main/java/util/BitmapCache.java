package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.w3c.dom.Text;

import android.graphics.Bitmap;
import android.text.TextUtils;

public class BitmapCache {
	private FileOutputStream fos;
	private ByteArrayOutputStream bos;
	private ByteArrayInputStream bis;
	
	public boolean setBitmapCache(Bitmap bitmap,String path,String name){
		if(!TextUtils.isEmpty(path)&&!TextUtils.isEmpty(name)&&bitmap!=null){
			File pathDir = new File(path);
			pathDir.mkdirs();
			File file = new File(path+File.separator+name);
			//不存在就创建，存在了就直接获取输入流
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
			bis = new ByteArrayInputStream(bos.toByteArray());
			
			byte[] buf = new byte[8*1024];
			int len = -1;
			try {
				while((len = bis.read(buf))!=-1){
					fos.write(buf, 0, len);
				}
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return false;
	}
	
	
	
	public boolean setByteCache(byte[] buffer,String path,String name){
		if(!TextUtils.isEmpty(path)&&!TextUtils.isEmpty(name)&&buffer!=null){
			File pathDir = new File(path);
			pathDir.mkdirs();
			File file = new File(path+File.separator+name);
			//不存在就创建，存在了就直接获取输入流
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			bis = new ByteArrayInputStream(buffer);
			byte[] buf = new byte[1024];
			int len = -1;
			try {
				while((len = bis.read(buf))!=-1){
					fos.write(buf, 0, len);
				}
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
}
