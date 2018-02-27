package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.beans.DiaryBean;
import com.example.leo.diarynew.constans.TagSpinner;
import com.example.leo.diarynew.ui.LineEditText;
import com.example.leo.diarynew.util.GetTimeUtil;
import com.example.leo.diarynew.util.ToastUtils;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by leo on 2017/9/26
 *
 *   写新日记的活动
 */

public class AddDiaryActivity extends AppCompatActivity {

    public static final String ADD_TITLE = "ADD_TITLE";
    public static final String ADD_CONTENT = "ADD_CONTENT";

    @BindView(R.id.myToolBar)
    Toolbar toolbar;
    @BindView(R.id.add_diary_tv_date)
    TextView mAddDiaryTvDate;
    @BindView(R.id.add_diary_et_title)
    EditText mAddDiaryEtTitle;
    @BindView(R.id.add_diary_et_content)
    LineEditText mAddDiaryEtContent;
    @BindView(R.id.add_diary_fab_back)
    FloatingActionButton mAddDiaryFabBack;
    @BindView(R.id.add_diary_fab_add)
    FloatingActionButton mAddDiaryFabAdd;
    @BindView(R.id.spinner_tag)
    Spinner spinner_tag;
    @BindView(R.id.right_labels)
    FloatingActionsMenu mRightLabels;

    private String tag;

    /**
     * 提供给其他人使用的接口
     * */
    public static void startActivity(Context context){
        Intent intent = new Intent(context , AddDiaryActivity.class);
        context.startActivity(intent);
    }
    /**
     * 提供给其他人使用的接口
     * */
    public static void startActivity(Context context,String title , String content){
        Intent intent = new Intent(context , AddDiaryActivity.class);
        intent.putExtra(ADD_TITLE , title);
        intent.putExtra(ADD_CONTENT , content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        ButterKnife.bind(this);

        initToolBar();
        initIntent();
        initSpinner();

        mAddDiaryTvDate.setText("今天,"+GetTimeUtil.getData());
    }

    /**
     * init toolbar , set title color , homeIcon
     */
    private void initToolBar() {
        toolbar.setTitle(R.string.Str_motto);
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

    /**
     * when getIntent have date , set up
     */
    private void initIntent() {
        Intent intent = getIntent();
        mAddDiaryEtTitle.setText(intent.getStringExtra(ADD_TITLE));
        mAddDiaryEtContent.setText(intent.getStringExtra(ADD_CONTENT));
    }

    /**
     * init spinner, get new category
     */
    private void initSpinner() {
        final ArrayAdapter<String > adapter = new ArrayAdapter<String>(AddDiaryActivity.this,
                android.R.layout.simple_spinner_item, TagSpinner.getTagList());
        spinner_tag.setAdapter(adapter);
        spinner_tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = (String) parent.getAdapter().getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tag = TagSpinner.getTagList().get(0);
            }
        });
    }

    @OnClick({ R.id.add_diary_fab_back, R.id.add_diary_fab_add})
    public void onClick(View view){
        final String currentTime = GetTimeUtil.getData();
        final String title = mAddDiaryEtTitle.getText().toString();
        final String content = mAddDiaryEtContent.getText().toString();
        switch (view.getId()){
            case R.id.add_diary_fab_add:
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)){
                    DiaryBean diary = new DiaryBean(currentTime ,
                            title , content , tag);
                    diary.save();
                    finish();
                    MainActivity.startActivity(AddDiaryActivity.this);
                }else {
                    ToastUtils.showShortUI(AddDiaryActivity.this , "请写入内容后在进行保存");
                }
                break;
            case R.id.add_diary_fab_back:
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddDiaryActivity.this);
                    builder.setMessage("当前存在编辑的内容，是否保存? ")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DiaryBean diary = new DiaryBean(currentTime ,
                                            title , content , tag);
                                    diary.save();
                                    finish();
                                    MainActivity.startActivity(AddDiaryActivity.this);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    MainActivity.startActivity(AddDiaryActivity.this);
                                }
                            })
                            .show();
                }else {
                    finish();
                    MainActivity.startActivity(AddDiaryActivity.this);
                }
                break;
        }
    }
}
