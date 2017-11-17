package com.example.leo.diarynew.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.adapter.CategoryDiaryAdapter;
import com.example.leo.diarynew.beans.DiaryBean;
import com.example.leo.diarynew.constans.Constans;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leo on 2017/9/26
 *  显示某个具体分类日记的活动
 */

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.rv_show_category)
    RecyclerView rcvCategory;
    @BindView(R.id.myToolBar)
    Toolbar toolbar;

    private String nowCategory;
    private CategoryDiaryAdapter adapter;
    private List<DiaryBean> mDataByTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);

        // get current category from intent
        nowCategory = getIntent().getStringExtra(Constans.EXTRA_CATEGORY);
        if(nowCategory.equals("")){
            nowCategory = "Unknown";
        }
        initToolbar();

        mDataByTag = new ArrayList<>();
        adapter = new CategoryDiaryAdapter(this,mDataByTag);
        rcvCategory.setLayoutManager(new LinearLayoutManager(this));
        rcvCategory.setAdapter(adapter);
        getDiaryListDataTagFromdb();
    }

    /**
     * get data from db only current tag
     */
    private void getDiaryListDataTagFromdb(){
        mDataByTag.clear();
        mDataByTag.addAll(DataSupport.where("tag = ?" ,nowCategory).find(DiaryBean.class));
        adapter.notifyDataSetChanged();
    }

    /**
     * init toolbar and set homeNavigation
     */
    private void initToolbar() {
        toolbar.setTitle(nowCategory);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
