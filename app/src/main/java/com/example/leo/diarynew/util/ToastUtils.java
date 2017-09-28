package com.example.leo.diarynew.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.widget.Toast;

/**
 * Created by leo on 2017/8/17.
 */

public class ToastUtils {

    private ToastUtils(){
        throw new UnsupportedOperationException("Toast Utils class can not need init");
    }

    private static Handler mMainhandler = new Handler(Looper.getMainLooper());

    /**
     * 可以在非UI线程使用
     * @param msg 文本
     */
    public static void showShortMsg(final Context context , final String msg ){
        mMainhandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context , msg , Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 可以在非UI线程使用
     * @param msg 文本
     */
    public static void showLongMsg(final Context context ,final String msg){
        mMainhandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 在UI线程使用
     * @param msg 文本
     */
    @UiThread
    public static void showLongUI(final Context context ,final String msg){
        Toast.makeText(context , msg , Toast.LENGTH_LONG).show();
    }

    /**
     * 在UI线程使用
     * @param msg 文本
     */
    @UiThread
    public static void showShortUI(final Context context ,final String msg){
        Toast.makeText(context , msg , Toast.LENGTH_SHORT).show();
    }
}
