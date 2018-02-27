package com.example.leo.diarynew.activity;

import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.constans.Constans;
import com.example.leo.diarynew.util.SharedPreferenceUtils;
import com.example.leo.diarynew.util.ToastUtils;


public class SplashActivity extends AppCompatActivity {

    //启动主活动
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(SplashActivity.this , MainActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPasswords();
    }

    /**
     * Created by leo on 2017/9/28
     * 在splashActivity活动中判断用户是否开启了密码锁功能，如果开启了密码锁功能，就让用户输入密码否则无法进入
     * 如果设置了密码 ， 当密码错误或者取消都会推出app  TODO 简单但是不合理
     */
    private void checkPasswords() {
        SharedPreferenceUtils sp = new SharedPreferenceUtils(this);
        String isHavePassword = sp.getString(Constans.SP_HAVE_PASSWORDS,Constans.NULL);
        if(!isHavePassword.equals(Constans.NULL)){
            if(isHavePassword.equalsIgnoreCase(Constans.TRUE)){
                final String password = sp.getString(Constans.SP_DIARY_PASSWORDS);
                final EditText et = new EditText(this);
                new AlertDialog.Builder(SplashActivity.this).setTitle("请输入密码")
                        .setView(et)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String str = et.getText().toString();
                                if(str.equalsIgnoreCase(password)){
                                    handler.sendEmptyMessageDelayed(0,0);
                                }else {
                                    ToastUtils.showLongMsg(SplashActivity.this , "密码错误 ， 请重新尝试");
                                    SplashActivity.this.finish();
                                    SplashActivity.this.startActivity(new Intent(SplashActivity.this,SplashActivity.class));
                                }
                            }
                        })
                        .setNegativeButton("退出应用程序", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SplashActivity.this.finish();
                            }
                        })
                        .show();

            }
        }else {
            handler.sendEmptyMessageDelayed(0,1500);
        }
    }
}
