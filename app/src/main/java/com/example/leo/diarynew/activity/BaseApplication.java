package com.example.leo.diarynew.activity;

import android.content.Context;

import com.example.leo.diarynew.constans.Constans;
import com.example.leo.diarynew.constans.TagSpinner;
import com.example.leo.diarynew.util.CrashUtils;
import com.example.leo.diarynew.util.SharedPreferenceUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;


/**
 * Created by leo on 2017/8/18.
 */

public class BaseApplication extends LitePalApplication {

    private static Context context;

    public static Context getAppContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        //LitePal init
        LitePal.initialize(this);
        //crash info deal
        // TODO: 2017/9/25   send info to net
        CrashUtils.getInstance(this).init();
        //get categories from sp
        TagSpinner.init();
    }
}
