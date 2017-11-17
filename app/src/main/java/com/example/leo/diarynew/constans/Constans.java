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
    public static final String SP_USER_ISLOGIN = "SP_USER_ISLOGIN";
    public static final String SP_USER_NAME = "SP_USER_NAME";
    public static final String SP_USER_PASSWORD = "SP_USER_PASSWORD";

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

    //URL
    public static final String URL_ROOT = "http://www.ioqian.top:8080/idiary";
    public static final String URL_REGISTER = URL_ROOT+"/ServletRegister?";
    public static final String URL_UESER_LOGIN = URL_ROOT+"/ServletUserLogin?";
    public static final String URL_UPDATE = URL_ROOT+"/ServletUpdate";
    public static final String URL_DOWNLOAD = URL_ROOT+"/ServletDownload?";
    public static final String USER_NAME = "name=";
    public static final String USER_PASSWORD = "password=";
    //注册
    public static final String REGISTER_RESPONSE = "REGISTER_RESPONSE";
    public static final String REGISTER_RESPONSE_SUCCESS = "REGISTER_RESPONSE_SUCCESS";
    public static final String REGISTER_RESPONSE_REPEAT = "REGISTER_RESPONSE_REPEAT";
    //登录
    public static final String LOGIN_RESPONSE = "LOGIN_RESPONSE";
    public static final String LOGIN_RESPONSE_SUCCESS = "LOGIN_RESPONSE_SUCCESS";
    public static final String LOGIN_RESPONSE_FAILURE = "LOGIN_RESPONSE_FAILURE";
    //更新
    public static final String UPDATE_RESPONSE = "UPDATE_RESPONSE";
    public static final String UPDATE_RESPONSE_SUCCESS_FIRST = "UPDATE_RESPONSE_SUCCESS_FIRST";
    public static final String UPDATE_RESPONSE_SUCCESS_NO_FIRST = "UPDATE_RESPONSE_SUCCESS_NO_FIRST";
    public static final String UPDATE_RESPONSE_FAILURE = "UPDATE_RESPONSE_FAILURE";
    //下载
    public static final String DOWNLOAD_RESPONSE = "DOWNLOAD_RESPONSE";
    public static final String DOWNLOAD_RESPONSE_SUCCESS = "DOWNLOAD_RESPONSE_SUCCESS";
    public static final String DOWNLOAD_RESPONSE_FAILURE = "DOWNLOAD_RESPONSE_FAILURE";

}
