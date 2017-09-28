package com.example.leo.diarynew.constans;

import com.example.leo.diarynew.activity.BaseApplication;
import com.example.leo.diarynew.util.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/8/19.
 */

public class TagSpinner {

    //diary category
    public static final List<String> tagList = new ArrayList<>();

    public static void init(){
        tagList.clear();
        tagList.add("随笔");
        tagList.add("心情");
        getCategoriesFromSPAndAddToList();
    }

    public static void add(String tag){
        tagList.add(tag);
    }

    public static List<String> getTagList() {
        return tagList;
    }

    /**
     * Created by leo on 2017/9/25
     * get Categories from SharedPreference and add info to category list
     * when another add a new category , please follow xx:xx:xx
     */
    private static void getCategoriesFromSPAndAddToList(){
        SharedPreferenceUtils sp = new SharedPreferenceUtils(BaseApplication.getAppContext());
        String info = sp.getString(Constans.SP_CATEGORIES_KEY,Constans.NULL);
        if(!Constans.NULL.equals(info)){
            String[] categories = info.split(":");
            for(String str:categories){
                tagList.add(str);
            }
        }
    }
}
