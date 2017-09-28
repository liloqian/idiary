package com.example.leo.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.constans.Constans;
import com.example.leo.diarynew.util.SharedPreferenceUtils;
import com.example.leo.diarynew.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockActivity extends AppCompatActivity {

    @BindView(R.id.myToolBar)
    Toolbar toolbar;
    @BindView(R.id.iv_lock_state)
    ImageView ivState;
    @BindView(R.id.et_lock_down)
    EditText etDown;
    @BindView(R.id.et_lock_up)
    EditText etUp;
    @BindView(R.id.tv_lock_up)
    TextView tvUp;
    @BindView(R.id.tv_lock_down)
    TextView tvDown;
    @BindView(R.id.tv_lock_tip)
    TextView tvTip;
    @BindView(R.id.btn_lock_ok)
    Button btnOK;
    @BindView(R.id.btn_lock_cancel)
    Button btnCancel;

    /**
     * 活动对外提供的接口
     * */
    public static void startActivity(Context context){
        Intent intent = new Intent(context , LockActivity.class);
        context.startActivity(intent);
    }

    /**
     * Created by leo on 2017/9/28
     * 当存在密码  SP_HAVE_PASSWORDS  TRUE
     * 不存在密码  SP_HAVE_PASSWORDS  NULL
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        ButterKnife.bind(this);

        SharedPreferenceUtils sp = new SharedPreferenceUtils(this);
        String currentIsLock = sp.getString(Constans.SP_HAVE_PASSWORDS,Constans.NULL);
        if(currentIsLock.equalsIgnoreCase(Constans.TRUE)){
            showUIHavePasswords();
        }else if(currentIsLock.equalsIgnoreCase(Constans.NULL)){
            showUINoPasswords();
        }

        initToolBar();

    }

    private void initToolBar() {
        toolbar.setTitle("保护你的隐私");
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
     * Created by leo on 2017/9/28
     * 不存在密码
     */
    private void showUINoPasswords() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String onePasswords = etUp.getText().toString();
                String twoPasswords = etDown.getText().toString();
                if(onePasswords.equals(twoPasswords)) {
                    SharedPreferenceUtils sp;
                    sp = new SharedPreferenceUtils(LockActivity.this);
                    sp.putString(Constans.SP_HAVE_PASSWORDS, Constans.TRUE);
                    sp.putString(Constans.SP_DIARY_PASSWORDS, onePasswords);
                    finish();
                }else {
                    ToastUtils.showShortUI(LockActivity.this,"输入不正确，请重新输入");
                }
            }
        });
        btnCancel.setVisibility(View.INVISIBLE);
        ivState.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.unlock));
        btnOK.setText("开启密码功能");
        tvUp.setText("输入密码: ");
        tvDown.setText("重复输入密码: ");
        tvTip.setText("当前日记不存在密码保护，要开启密码保护功能吗？输入密码点击开启密码功能可以开启密码功能");
    }

    /**
     * Created by leo on 2017/9/28
     * 存在密码
     */
    private void showUIHavePasswords() {
        final SharedPreferenceUtils sp = new SharedPreferenceUtils(LockActivity.this);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.putString(Constans.SP_HAVE_PASSWORDS,Constans.NULL);
                sp.putString(Constans.SP_DIARY_PASSWORDS,"");
                LockActivity.this.finish();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldRealPassword = sp.getString(Constans.SP_DIARY_PASSWORDS);
                String oldInputPassword = etUp.getText().toString();
                String newPassword = etDown.getText().toString();
                if(oldInputPassword.equals(oldRealPassword)){
                    sp.putString(Constans.SP_DIARY_PASSWORDS , newPassword);
                    LockActivity.this.finish();
                }else {
                    ToastUtils.showShortUI(LockActivity.this,"密码输入错误，无法修改密码 ");
                }
            }
        });
    }
}
