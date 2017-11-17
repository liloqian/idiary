package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.constans.Constans;
import com.example.leo.diarynew.util.SharedPreferenceUtils;
import com.example.leo.diarynew.util.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  Created by leo on 2017/11/17.
 *  fun: user register activity
 * */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.myToolBar)
    protected Toolbar toolbar;
    @BindView(R.id.register_et_name)
    protected EditText et_name;
    @BindView(R.id.register_et_password)
    protected EditText et_password;
    @BindView(R.id.register_btn)
    protected Button btn_register;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        initToolbar();
        btn_register.setOnClickListener(this);
    }

    private void initToolbar() {
        toolbar.setTitle("注册");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                final String userName = et_name.getText().toString();
                final String userPassword = et_password.getText().toString();
                if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)){
                    final String url = Constans.URL_REGISTER+Constans.USER_NAME+userName+"&"+Constans.USER_PASSWORD+userPassword;
                    Log.e(TAG, "register_url:  "+url );
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            try {
                                Response call = client.newCall(request).execute();
                                String responseStr = call.header(Constans.REGISTER_RESPONSE);
                                if(!call.isSuccessful()){ //when error
                                    ToastUtils.showShortMsg(RegisterActivity.this, "服务器可能出现故障,请稍后再试或联系作者");
                                    gotoMain();
                                    return;
                                }
                                if(responseStr.equalsIgnoreCase(Constans.REGISTER_RESPONSE_SUCCESS)){      //user register success
                                    SharedPreferenceUtils sp = new SharedPreferenceUtils(RegisterActivity.this);
                                    sp.putString(Constans.SP_USER_NAME, userName);
                                    sp.putString(Constans.SP_USER_PASSWORD, userPassword);
                                    ToastUtils.showShortMsg(RegisterActivity.this, "注册成功，请登录");
                                    finish();
                                }else if(responseStr.equalsIgnoreCase(Constans.REGISTER_RESPONSE_REPEAT)){   //user register repeat
                                    ToastUtils.showShortMsg(RegisterActivity.this, "用户名重复，请重新输入用户名");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }else {
                    ToastUtils.showShortMsg(this, "请输入账号和密码 ");
                }
                break;
        }
    }

    private void gotoMain(){
        super.finish();
        MainActivity.startActivity(RegisterActivity.this);
    }

}
