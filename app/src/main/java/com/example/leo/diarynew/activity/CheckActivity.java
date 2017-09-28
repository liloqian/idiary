package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.beans.DiaryBean;
import com.example.leo.diarynew.constans.Constans;
import com.example.leo.diarynew.constans.TagSpinner;
import com.example.leo.diarynew.ui.LineEditText;
import com.example.leo.diarynew.util.GetTimeUtil;
import com.example.leo.diarynew.util.LitePalUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckActivity extends AppCompatActivity {

    @BindView(R.id.myToolBar)
    Toolbar toolbar;
    @BindView(R.id.tv_check_tag)
    TextView tv_tag;
    @BindView(R.id.tv_check_time)
    TextView tv_time;
    @BindView(R.id.tv_check_title)
    TextView tv_title;
    @BindView(R.id.let_check)
    LineEditText let_content;


    /**
     * 活动对外提供的接口
     */
    public static void startActivity(Context context, String title) {
        Intent intent = new Intent(context, CheckActivity.class);
        intent.putExtra(Constans.EXTRA_CHECKACTIVITY, title);
        context.startActivity(intent);
    }

    public static void startActivityByDiary(Context context, DiaryBean diary) {
        Intent intent = new Intent(context, CheckActivity.class);
        intent.putExtra(Constans.EXTRA_CHECKACTIVITY, diary);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        ButterKnife.bind(this);

        initToolBar();

        DiaryBean diray = getDiaryBean();
        tv_time.setText(diray.getDate());
        tv_tag.setText(diray.getTag());
        tv_title.setText(diray.getTitle());
        let_content.setText(diray.getContent());
    }

    /**
     * Created by leo on 2017/9/27
     * init toolbar
     */
    private void initToolBar() {
        toolbar.setTitle(R.string.str_recall);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });
    }

    public DiaryBean getDiaryBean() {
        DiaryBean ret;
        String title = getIntent().getStringExtra(Constans.EXTRA_CHECKACTIVITY);
        if(title != null) {
            ret = LitePalUtil.getDataFromDb(title);
        }else {
            ret = new DiaryBean(GetTimeUtil.getData() , "你好啊！","app出现了错误，请联系作者进行更改", TagSpinner.getTagList().get(0));
        }
        return ret;
    }

    @Override
    public void finish() {
        gotoMain();
    }

    private void gotoMain(){
        super.finish();
        MainActivity.startActivity(CheckActivity.this);
    }
}

