package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.leo.diarynew.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * a activity to show four image that help people to use this app
 * */

public class HelpActivity extends AppCompatActivity {

    @BindView(R.id.myToolBar)
    Toolbar toolbar;

    public static void startActivity(Context context){
        Intent intent = new Intent(context , HelpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle("帮助");
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
