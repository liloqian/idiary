package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.leo.diarynew.R;
import com.example.leo.diarynew.adapter.CategoryDividedAdapter;
import com.example.leo.diarynew.adapter.DiaryAdapter;
import com.example.leo.diarynew.adapter.MainViewPager;
import com.example.leo.diarynew.beans.DiaryBean;
import com.example.leo.diarynew.constans.Constans;
import com.example.leo.diarynew.constans.TagSpinner;
import com.example.leo.diarynew.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
    private ImageView ivSettingHeadBg;
    private CheckedTextView ctvPassword;
    private CheckedTextView ctvReadme;
    private CheckedTextView cvtUpdate;
    private CheckedTextView ctvAbout;
    private CheckedTextView ctvAddCategory;

    //store main view , such as diary , divided and setting
    private List<View> mListView = new ArrayList<>();
    //diary data list
    private List<DiaryBean> mDiaryList = new ArrayList<>();
    private DiaryAdapter diaryAdapter = null;
    private CategoryDividedAdapter divideAdapter = null;
    private MainViewPager viewPagerAdapter = null;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Created by leo on 2017/9/26
     * init toolbar
     */
    private void initToorBar() {
        toolbar.setTitle("idiary" );
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    /**
     * Created by leo on 2017/9/26
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
        ivSettingHeadBg = (ImageView) todoView.findViewById(R.id.iv_setting_bg);
        ctvPassword = (CheckedTextView) todoView.findViewById(R.id.ctv_password);
        ctvAbout = (CheckedTextView) todoView.findViewById(R.id.ctv_about);
        ctvReadme = (CheckedTextView) todoView.findViewById(R.id.ctv_readme);
        cvtUpdate = (CheckedTextView) todoView.findViewById(R.id.ctv_update);
        ctvAddCategory = (CheckedTextView) todoView.findViewById(R.id.ctv_add_category);

        mListView.add(diaryView);
        mListView.add(divideView);
        mListView.add(todoView);
        //init ViewPager
        viewPagerAdapter = new MainViewPager(mListView);
        vpMain.setAdapter(viewPagerAdapter);
    }

    /**
     * Created by leo on 2017/9/26
     * the navigation bar set listener in bottom
     */
    private void initOnclick() {
        ctvDiary.setOnClickListener(this);
        ctvDivide.setOnClickListener(this);
        ctvTodo.setOnClickListener(this);
    }

    /**
     * Created by leo on 2017/9/26
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

        }
    }

    /**
     * Created by leo on 2017/9/26
     * init setting
     */
    private void initSetting() {
        Glide.with(MainActivity.this).load(Constans.PIC_BIYING_DAY).placeholder(R.drawable.main_bg).into(ivSettingHeadBg);
        ctvReadme.setOnClickListener(this);
        ctvAbout.setOnClickListener(this);
        ctvPassword.setOnClickListener(this);
        cvtUpdate.setOnClickListener(this);
        ctvAddCategory.setOnClickListener(this);
    }

    /**
     * Created by leo on 2017/9/26
     * when enter divided view , do something
     */
    private void initDivide() {
        divideAdapter = new CategoryDividedAdapter(TagSpinner.getTagList(),this);
        rcyDivide.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcyDivide.setAdapter(divideAdapter);
    }


    /**从数据库中获取数据进行显示*/
    private void getDiaryListDataFromdb() {
        mDiaryList.clear();
        mDiaryList.addAll(DataSupport.findAll(DiaryBean.class ));
        diaryAdapter.notifyDataSetChanged();
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
