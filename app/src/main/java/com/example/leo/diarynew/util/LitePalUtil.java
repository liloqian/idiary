package com.example.leo.diarynew.util;

import com.example.leo.diarynew.beans.DiaryBean;
import org.litepal.crud.DataSupport;

/**
 * Created by leo on 2017/8/19.
 */

public class LitePalUtil {

    public static void deleteDataByTitle(String title) {
        DataSupport.deleteAll(DiaryBean.class , "title = ?" , title);
    }
    public static DiaryBean getDataFromDb(String title){
        DiaryBean ret = (DiaryBean) DataSupport.where("title = ?",title).find(DiaryBean.class).get(0);
        return ret;
    }
}
