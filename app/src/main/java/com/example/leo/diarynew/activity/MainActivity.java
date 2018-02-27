package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.leo.diarynew.R;
import com.example.leo.diarynew.adapter.CategoryDividedAdapter;
import com.example.leo.diarynew.adapter.DiaryAdapter;
import com.example.leo.diarynew.adapter.MainViewPager;
import com.example.leo.diarynew.beans.DiaryBean;
import com.example.leo.diarynew.beans.User;
import com.example.leo.diarynew.beans.UserInfo;
import com.example.leo.diarynew.constans.Constans;
import com.example.leo.diarynew.constans.TagSpinner;
import com.example.leo.diarynew.util.SharedPreferenceUtils;
import com.example.leo.diarynew.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.myToolBar)
    protected Toolbar toolbar;
    //three navigation bar
    @BindView(R.id.ctv_diary)
    protected CheckedTextView ctvDiary;
    @BindView(R.id.ctv_divide)
    protected CheckedTextView ctvDivide;
    @BindView(R.id.ctv_todo)
    protected CheckedTextView ctvTodo;
    //main ViewPager
    @BindView(R.id.vp_main)
    protected ViewPager vpMain;

    //diary view
    private RecyclerView rvMain;
    private FloatingActionButton fab;
    //divided view
    private RecyclerView rcyDivide;
    //setting view
    private Button btn_login;
    private ImageView ivSettingHeadBg;
    private CheckedTextView ctvPassword;
    private CheckedTextView ctvReadme;
    private CheckedTextView cvtUpdate;
    private CheckedTextView ctvAbout;
    private CheckedTextView ctvAddCategory;
    private CheckedTextView ctvDataUpdate;
    private CheckedTextView ctvDataDownload;

    //store main view , such as diary , divided and setting
    private List<View> mListView = new ArrayList<>();
    //diary data list
    private List<DiaryBean> mDiaryList = new ArrayList<>();
    private DiaryAdapter diaryAdapter = null;
    private CategoryDividedAdapter divideAdapter = null;
    private MainViewPager viewPagerAdapter = null;
    //User is login
    private boolean isUserLogin = false;
    /**
     * 活动对外提供的接口
     * */
    public static void startActivity(Context context){
        Intent intent = new Intent(context , MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注入式注解初始化
        ButterKnife.bind(this);
        //EventBus注册
        EventBus.getDefault().register(this);
        //init
        initToorBar();
        initViewPager();
        initOnclick();

        //main view init
        diaryAdapter = new DiaryAdapter(this,mDiaryList);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(diaryAdapter);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDiaryListDataFromdb();
        updateIsLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * init toolbar
     */
    private void initToorBar() {
        toolbar.setTitle("idiary" );
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    /**
     * init viewPager
     */
    private void initViewPager() {
        LayoutInflater ll = LayoutInflater.from(MainActivity.this);
        View diaryView = ll.inflate(R.layout.activity_main_diary , null);
        View divideView = ll.inflate(R.layout.activity_main_divide,null);
        View todoView = ll.inflate(R.layout.anctivity_main_todo , null);
        //diary页面
        rvMain = (RecyclerView) diaryView.findViewById(R.id.main_rv_show_diary);
        fab = (FloatingActionButton) diaryView.findViewById(R.id.main_fab_enter_edit);
        //divide页面
        rcyDivide = (RecyclerView) divideView.findViewById(R.id.rcv_divide);
        //setting页面
        btn_login = (Button) todoView.findViewById(R.id.setting_btn_login);
        ivSettingHeadBg = (ImageView) todoView.findViewById(R.id.iv_setting_bg);
        ctvPassword = (CheckedTextView) todoView.findViewById(R.id.ctv_password);
        ctvAbout = (CheckedTextView) todoView.findViewById(R.id.ctv_about);
        ctvReadme = (CheckedTextView) todoView.findViewById(R.id.ctv_readme);
        cvtUpdate = (CheckedTextView) todoView.findViewById(R.id.ctv_update);
        ctvAddCategory = (CheckedTextView) todoView.findViewById(R.id.ctv_add_category);
        ctvDataUpdate = (CheckedTextView) todoView.findViewById(R.id.ctv_data_update );
        ctvDataDownload = (CheckedTextView) todoView.findViewById(R.id.ctv_data_download);

        mListView.add(diaryView);
        mListView.add(divideView);
        mListView.add(todoView);
        //init ViewPager
        viewPagerAdapter = new MainViewPager(mListView);
        vpMain.setAdapter(viewPagerAdapter);
    }

    /**
     * the navigation bar set listener in bottom
     */
    private void initOnclick() {
        ctvDiary.setOnClickListener(this);
        ctvDivide.setOnClickListener(this);
        ctvTodo.setOnClickListener(this);
    }

    /**
     *  check navigation bar do something
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ctv_diary:
                vpMain.setCurrentItem(0);
                getDiaryListDataFromdb();
                ctvDiary.setChecked(true);
                ctvDivide.setChecked(false);
                ctvTodo.setChecked(false);
                break;
            case R.id.ctv_divide:
                vpMain.setCurrentItem(1);
                initDivide();
                ctvDiary.setChecked(false);
                ctvDivide.setChecked(true);
                ctvTodo.setChecked(false);
                break;
            case R.id.ctv_todo:
                vpMain.setCurrentItem(3);
                initSetting();
                ctvDiary.setChecked(false);
                ctvDivide.setChecked(false);
                ctvTodo.setChecked(true);
                break;
            case R.id.main_fab_enter_edit:
                AddDiaryActivity.startActivity(MainActivity.this);
                break;
            case R.id.ctv_about:
                new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(Constans.APPINFO)
                        .setContentText(Constans.AUTHORINFO)
                        .show();
                break;
            case R.id.ctv_password:
                LockActivity.startActivity(MainActivity.this);
                break;
            case R.id.ctv_update:
                ToastUtils.showShortMsg(this,"网络功能还未上线，敬请期待~~");
                break;
            case R.id.ctv_readme:
                HelpActivity.startActivity(MainActivity.this);
                break;
            case R.id.ctv_add_category:
                AddCategoryActivity.startActivity(this);
                break;
            case R.id.ctv_data_update:
                diaryUpload();
                break;
            case R.id.ctv_data_download:
                diaryDownload();
                break;
            case R.id.setting_btn_login:
                if(isUserLogin){
                    new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("警告")
                            .setContentText("你确定要退出登录？")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    SharedPreferenceUtils sp = new SharedPreferenceUtils(MainActivity.this);
                                    sp.putString(Constans.SP_USER_ISLOGIN, Constans.NULL);
                                    btn_login.setText("请登录");
                                    isUserLogin = false;
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setCancelText("点错了")
                            .show();
                }else {
                    LoginActivity.startActivity(this);
                }
                break;
        }
    }

    /**
     * diary data download to sync to local phone from service
     * */
    private void diaryDownload() {
        SharedPreferenceUtils sp = new SharedPreferenceUtils(MainActivity.this);
        String isLogin = sp.getString(Constans.SP_USER_ISLOGIN,Constans.NULL);
        if(!isLogin.equalsIgnoreCase(Constans.NULL)){
            final String userName = sp.getString(Constans.SP_USER_NAME,Constans.NULL);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = Constans.URL_DOWNLOAD+Constans.USER_NAME+userName;
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                                        .url(url)
                                        .build();
                    try {
                        Response response = client.newCall(request).execute();
                        if(response.isSuccessful()){
                            String responseHeader = response.header(Constans.DOWNLOAD_RESPONSE);
                            if(responseHeader.equalsIgnoreCase(Constans.DOWNLOAD_RESPONSE_SUCCESS)){
                                UserInfo userInfo = (UserInfo) JSON.parseObject(response.body().string(),UserInfo.class);
                                Log.e(TAG, "download: "+userInfo );
                                List<DiaryBean> downloadList = userInfo.getDiaryBeans();
                                List<DiaryBean> localList = DataSupport.findAll(DiaryBean.class);
                                boolean isRepeat = false;
                                for(DiaryBean diary : downloadList){
                                    isRepeat = false;
                                    for(DiaryBean d : localList){
                                        if(d.getTitle().equals(diary.getTitle())){
                                            isRepeat = true;
                                            break;
                                        }
                                    }
                                    if(!isRepeat){
                                        diary.save();
                                    }
                                }
                                getDiaryListDataFromdb();
                                ToastUtils.showShortMsg(MainActivity.this,"数据同步成功");
                            }else if(responseHeader.equalsIgnoreCase(Constans.DOWNLOAD_RESPONSE_FAILURE)){
                                ToastUtils.showShortMsg(MainActivity.this,"你还没有上传文件,上传后才可以进行同步");
                            }
                        }else {
                            ToastUtils.showShortMsg(MainActivity.this,"出现了错误，请稍后尝试");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * diary data upload to service
     * Warn : once do it will delete last upload in service
     * */
    private void diaryUpload() {
        SharedPreferenceUtils sp = new SharedPreferenceUtils(MainActivity.this);
        String isLogin = sp.getString(Constans.SP_USER_ISLOGIN,Constans.NULL);
        if(!isLogin.equalsIgnoreCase(Constans.NULL)) {
            final String userName = sp.getString(Constans.SP_USER_NAME,Constans.NULL);
            String userPassword = sp.getString(Constans.SP_USER_PASSWORD,Constans.NULL);
            List<DiaryBean> diaryList = DataSupport.findAll(DiaryBean.class);
            if(diaryList.size() == 0){
                ToastUtils.showShortUI(MainActivity.this,"当前没有日记，记录日记后再进行上传");
                return;
            }
            final UserInfo userInfo = new UserInfo(new User(userName,userPassword),diaryList);
            // post a json to service
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                                                .add("name",userName)
                                                .add("json",JSON.toJSONString(userInfo))
                                                .build();
                    Request re = new Request.Builder()
                                    .post(requestBody)
                                    .url(Constans.URL_UPDATE)
                                    .build();
                    try {
                        Response response = client.newCall(re).execute();
                        if(response.isSuccessful() &&
                                !response.header(Constans.UPDATE_RESPONSE).equalsIgnoreCase(Constans.UPDATE_RESPONSE_FAILURE)){
                            ToastUtils.showShortMsg(MainActivity.this,"数据上传成功");
                        }else {
                            ToastUtils.showShortMsg(MainActivity.this,"出现了错误，请稍后尝试");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else {
            ToastUtils.showShortUI(MainActivity.this, "请登录后再进行数据上传");
            LoginActivity.startActivity(MainActivity.this);
        }
    }

    /**
     * init setting view
     */
    private void initSetting() {
        Glide.with(MainActivity.this).load(Constans.PIC_BIYING_DAY).placeholder(R.drawable.main_bg).into(ivSettingHeadBg);
        btn_login.setOnClickListener(this);
        ctvReadme.setOnClickListener(this);
        ctvAbout.setOnClickListener(this);
        ctvPassword.setOnClickListener(this);
        cvtUpdate.setOnClickListener(this);
        ctvAddCategory.setOnClickListener(this);
        ctvDataDownload.setOnClickListener(this);
        ctvDataUpdate.setOnClickListener(this);
    }

    /**
     * when enter divided view , do something
     */
    private void initDivide() {
        divideAdapter = new CategoryDividedAdapter(TagSpinner.getTagList(),this);
        rcyDivide.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcyDivide.setAdapter(divideAdapter);
    }

    /**
     * 从数据库中获取数据进行显示
     */
    private void getDiaryListDataFromdb() {
        mDiaryList.clear();
        mDiaryList.addAll(DataSupport.findAll(DiaryBean.class ));
        diaryAdapter.notifyDataSetChanged();
    }

    /**
     * judge is login
     * */
    private void updateIsLogin(){
        SharedPreferenceUtils sp = new SharedPreferenceUtils(this);
        String isLogin = sp.getString(Constans.SP_USER_ISLOGIN,Constans.NULL);
        // if  user is login by get value in sp ,show user name
        if(isLogin.equalsIgnoreCase(String.valueOf(true))){
            String userName = sp.getString(Constans.SP_USER_NAME,Constans.NULL);
            btn_login.setText("欢迎您,"+userName);
            isUserLogin = true;
        }
    }

    /**
     * 通过观察者中的一个模式来进行事件的传递
     * */
    @Subscribe
    public void startUpdateActivity(DiaryBean diary){
        UpdateDiaryActivity.startActivity(MainActivity.this , diary.getTitle() , diary.getContent() , diary.getTag());
        Log.i(TAG, "startUpdateActivity: " + diary.getTag() );
    }
}
