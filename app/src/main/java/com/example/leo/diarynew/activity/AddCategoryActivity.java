package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.adapter.CategoryAdapter;
import com.example.leo.diarynew.constans.Constans;
import com.example.leo.diarynew.constans.TagSpinner;
import com.example.leo.diarynew.util.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leo on 2017/9/26
 *
 *  增加日记类别的活动
 */

public class AddCategoryActivity extends AppCompatActivity {
    private static final String TAG = "AddCategoryActivity";

    @BindView(R.id.btn_add_new_category)
    Button btnAddNewCategory;
    @BindView(R.id.rcv_show_category)
    RecyclerView rcvShowCategory;
    @BindView(R.id.et_new_category)
    EditText etNew;
    @BindView(R.id.myToolBar)
    Toolbar toolbar;

    public static void startActivity(Context context){
        Intent intent = new Intent(context , AddCategoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        ButterKnife.bind(this);

        initToolBar();
        
        CategoryAdapter adapter = new CategoryAdapter(TagSpinner.getTagList(),this);
        rcvShowCategory.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcvShowCategory.setAdapter(adapter);

        //增加新的日记分类
        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = etNew.getText().toString();
                if(!TextUtils.isEmpty(category)){
                    //先获取以前的value
                    SharedPreferenceUtils sp = new SharedPreferenceUtils(BaseApplication.getAppContext());
                    String info = sp.getString(Constans.SP_CATEGORIES_KEY,Constans.NULL);
                    //拼凑出新的value存入sp
                    if(info.equals(Constans.NULL)){
                        info = category;
                    }else {
                        info += (":" + category);
                    }
                    sp.putString(Constans.SP_CATEGORIES_KEY,info);
                    TagSpinner.init();      // re-init category when add a new category to sp
                    AddCategoryActivity.this.finish();
                }
            }
        });
    }

    /**
     * init toolbar and set navigationIcon
     */
    private void initToolBar() {
        toolbar.setTitle("添加新的日记分类");
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
