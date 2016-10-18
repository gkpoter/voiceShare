package com.gkpoter.voiceShare.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.util.FinishListActivity;

/**
 * Created by dy on 2016/10/18.
 */
public class LoginActivity extends Activity{

    private LinearLayout linearLayout;
    private EditText editEmail,editPass;
    private TextView hintEmail,hintPass,forget,register;
    private Button login;
    float state = (float) 0.1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        FinishListActivity.getInstance().addActivity(this);

        init();
        userHint();
        forgetAndRegister();
        Login();
    }

    public void init(){
        editEmail= (EditText) findViewById(R.id.login_userEmail);
        editPass= (EditText) findViewById(R.id.login_userPassword);
        hintEmail= (TextView) findViewById(R.id.login_hint_email);
        hintPass= (TextView) findViewById(R.id.login_hint_password);

        forget= (TextView) findViewById(R.id.forget_password);
        register= (TextView) findViewById(R.id.login_to_register);
        login= (Button) findViewById(R.id.login_submit);
        linearLayout= (LinearLayout) findViewById(R.id.login_linearLayout);
        new Thread(){
            @Override
            public void run() {
                try {
                    while(state <= 1) {
                        sleep(80);
                        linearLayout.setAlpha(state);
                        state += 0.05;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    public void userHint(){
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email=editEmail.getText().toString();
                if(email.length()>0){
                    hintEmail.setText("邮箱");
                }else{
                    hintEmail.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        editPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password=editPass.getText().toString();
                if(password.length()>0){
                    hintPass.setText("密码");
                }else{
                    hintPass.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    public void forgetAndRegister(){
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 100;
                startActivity(new Intent(LoginActivity.this,ForgetActivity1.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 100;
                startActivity(new Intent(LoginActivity.this,RegisterActivity1.class));
            }
        });
    }

    private void Login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinishListActivity.getInstance().exit();
            }
        });
    }
}
