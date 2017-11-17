package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.myToolBar)
    protected Toolbar toolbar;
    @BindView(R.id.login_et_account)
    protected EditText et_account;
    @BindView(R.id.login_et_password)
    protected EditText et_password;
    @BindView(R.id.login_btn_sign)
    protected Button btn_login;
    @BindView(R.id.login_btn_register)
    protected Button btn_register;

    private boolean isRegister;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        initToolbar();
        initView();
    }

    /**
     * if sp have value, show it
     * */
    private void initView() {
        SharedPreferenceUtils sp = new SharedPreferenceUtils(this);
        String spName = sp.getString(Constans.SP_USER_NAME,Constans.NULL);
        String spPassword = sp.getString(Constans.SP_USER_PASSWORD, Constans.NULL);
        if(!spName.equalsIgnoreCase(Constans.NULL) && !spPassword.equals(Constans.NULL)){
            et_account.setText(spName);
            et_password.setText(spPassword);
        }
        isRegister = !sp.getString(Constans.SP_USER_NAME, Constans.NULL).equalsIgnoreCase(Constans.NULL);
    }


    @OnClick({R.id.login_btn_sign,R.id.login_btn_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_btn_sign:
                String inputName = et_account.getText().toString();
                String inputPassword = et_password.getText().toString();
                if(!TextUtils.isEmpty(inputName) && !TextUtils.isEmpty(inputPassword)){
                    login(inputName,inputPassword);
                }else {
                    ToastUtils.showShortUI(this, "请输入账号和密码，从新尝试登录");
                }
                break;
            case R.id.login_btn_register:
                if(isRegister) {
                    ToastUtils.showShortUI(LoginActivity.this,"你应经注册过了");
                }else {
                    RegisterActivity.startActivity(this);
                }
                break;
        }
    }

    /**
     * net login verify
     * */
    private void login(final String name, String password) {
        final String url_login = Constans.URL_UESER_LOGIN + Constans.USER_NAME + name+"&"+Constans.USER_PASSWORD+password;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url_login)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        String retHeader = response.header(Constans.LOGIN_RESPONSE);
                        if(retHeader.equalsIgnoreCase(Constans.LOGIN_RESPONSE_SUCCESS)){
                            Log.e(TAG, "user:"+name +" login ok" );
                            SharedPreferenceUtils sp = new SharedPreferenceUtils(LoginActivity.this);
                            sp.putString(Constans.SP_USER_ISLOGIN,String.valueOf(true));
                            gotoMain();
                        }else if(retHeader.equalsIgnoreCase(Constans.LOGIN_RESPONSE_FAILURE)){
                            ToastUtils.showShortMsg(LoginActivity.this, "账号或密码错误");
                        }
                    }else {
                        ToastUtils.showShortMsg(LoginActivity.this, "服务器可能出现故障,请稍后再试或联系作者");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initToolbar() {
        toolbar.setTitle("登录");
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

    private void gotoMain(){
        super.finish();
        MainActivity.startActivity(LoginActivity.this);
    }
}
