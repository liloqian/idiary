package com.example.leo.diarynew.constans;

import com.example.leo.diarynew.BuildConfig;
import com.example.leo.diarynew.R;
import com.example.leo.diarynew.activity.BaseApplication;

/**
 * Created by leo on 2017/9/25.
 */

public class Constans {
    /**
     * key of sp
     * */
    public static final String SP_CATEGORIES_KEY = "SP_CATEGORIES_KEY";
    public static final String SP_HAVE_PASSWORDS = "SP_HAVE_PASSWORDS";
    public static final String SP_DIARY_PASSWORDS = "SP_DIARY_PASSWORDS";

    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    public static final String EXTRA_CHECKACTIVITY = "EXTRA_CHECKACTIVITY";

    public static final String NULL = "NULL";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static final String PIC_BIYING_DAY = "https://api.dujin.org/bing/1920.php";

    public static final String COLOR[] = {
            "#FFEFD5", "#FFA500" , "#FF6347",
            "#F0FFFF", "#EEDC82" , "#EE82EE",
            "#D9D9D9", "#9BCD9B" , "\t#5F9EA0"
    };

    public static final String APPINFO = BaseApplication.getAppContext().getString(R.string.app_name)+" - "+ BuildConfig.VERSION_NAME;
    public static final String AUTHORINFO = BaseApplication.getAppContext().getString(R.string.str_author);
}
