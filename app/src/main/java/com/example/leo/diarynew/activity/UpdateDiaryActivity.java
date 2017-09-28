package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.beans.DiaryBean;
import com.example.leo.diarynew.constans.TagSpinner;
import com.example.leo.diarynew.ui.LineEditText;
import com.example.leo.diarynew.util.GetTimeUtil;
import com.example.leo.diarynew.util.LitePalUtil;
import com.example.leo.diarynew.util.ToastUtils;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by leo on 2017/9/26
 *
 *  更新日记的活动
 */

public class UpdateDiaryActivity extends AppCompatActivity {

    private static final String TAG = "UpdateDiaryActivity";

    public static final String UP_TITLE = "UP_TITLE";
    public static final String UP_CONTENT = "UP_CONTENT";
    public static final String UP_TAG = "UP_TAG";

    @BindView(R.id.update_diary_tv_date)
    TextView mUpdateDiaryTvDate;
    @BindView(R.id.update_diary_et_title)
    EditText mUpdateDiaryEtTitle;
    @BindView(R.id.update_diary_et_content)
    LineEditText mUpdateDiaryEtContent;
    @BindView(R.id.update_diary_fab_back)
    FloatingActionButton mUpdateDiaryFabBack;
    @BindView(R.id.update_diary_fab_add)
    FloatingActionButton mUpdateDiaryFabAdd;
    @BindView(R.id.update_diary_fab_delete)
    FloatingActionButton mUpdateDiaryFabDelete;
    @BindView(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @BindView(R.id.spinner_tag)
    Spinner spinner_tag;
    @BindView(R.id.myToolBar)
    Toolbar toolbar;

    private String tag ;
    private String title;
    private String oldTitle;
    private String content;

    /**
     * 提供给其他人使用的接口
     * */
    public static void startActivity(Context context , String title , String content , String t){
        Intent intent = new Intent(context , UpdateDiaryActivity.class);
        intent.putExtra(UP_TITLE , title);
        intent.putExtra(UP_CONTENT , content);
        intent.putExtra(UP_TAG , t);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);

        ButterKnife.bind(this);
        initToolbar();

        Intent intent = getIntent();
        oldTitle = intent.getStringExtra(UP_TITLE);
        mUpdateDiaryEtTitle.setText(oldTitle);
        mUpdateDiaryEtContent.setText(intent.getStringExtra(UP_CONTENT));
        tag = intent.getStringExtra(UP_TAG);
        initSpinner();
        mUpdateDiaryTvDate.setText("今天, "+GetTimeUtil.getData());
    }

    /**
     * Created by leo on 2017/9/27
     * init spinner and set current tag
     */
    private void initSpinner() {
        final ArrayAdapter<String > adapter = new ArrayAdapter<String>(UpdateDiaryActivity.this,
                android.R.layout.simple_spinner_item , TagSpinner.getTagList());
        spinner_tag.setAdapter(adapter);
        spinner_tag.setSelection(TagSpinner.getTagList().indexOf(tag),true);
        Log.e(TAG, "initSpinner: "+TagSpinner.getTagList().indexOf(tag) + "  "+tag );
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

    private void initToolbar() {
        toolbar.setTitle("更改日记");
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

    @OnClick({ R.id.update_diary_fab_back ,R.id.update_diary_fab_add, R.id.update_diary_fab_delete})
    public void onClick(View view){
        title = mUpdateDiaryEtTitle.getText().toString();
        content = mUpdateDiaryEtContent.getText().toString();

        switch (view.getId()){
            //后退
            case R.id.update_diary_fab_back:
                gotoMain();
                break;
            //更新日记
            case R.id.update_diary_fab_add:
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)){
                    DiaryBean diary = new DiaryBean(GetTimeUtil.getData() , title , content , tag);
                    Log.e(TAG, "onClick: "+tag );
                    diary.updateAll("title = ?" , oldTitle);
                    ToastUtils.showShortUI(this,"更新日记  " + title + "  更新成功");
                    gotoMain();
                }
                break;
            //删除日记
            case R.id.update_diary_fab_delete:
                new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("删除")
                        .setContentText("您确定要删除这条日记吗？")
                        .setConfirmText("确定删除")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                LitePalUtil.deleteDataByTitle(oldTitle);
                                gotoMain();
                            }
                        })
                        .setCancelText("取消删除")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                gotoMain();
                            }
                        })
                        .show();
                break;
        }
    }

    /**
     *  释放该活动 ，进入主界面
     * */
    private void gotoMain(){
        finish();
        MainActivity.startActivity(UpdateDiaryActivity.this);
    }

}
