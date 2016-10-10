package util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/10/9 0009.
 */

public class SDCardUtil {

    /**
     *  判断是否有sd卡
      */
    public static boolean isMounted(){
        // 获取sd卡的挂载状态
        String state = Environment.getExternalStorageState();
        // 如果状态是挂载状态就返回true，如果没有的话就返回false
        if(state.equals(Environment.MEDIA_MOUNTED) ){
            return true;
        }
        return false;
    }

    /**
     *  获取sd卡的路径
      */
    public static File getRootFilePath(){

        if(isMounted()){
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            return externalStorageDirectory;
        }
        return null;
    }

    /**
     * 
     * @return
     */
    public static String getRootStringFilePath(){
        if(getRootFilePath()!=null){
            String absolutePath = getRootFilePath().getAbsolutePath();
            return absolutePath;
        }
        return null;
    }

}
