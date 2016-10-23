package com.gkpoter.voiceShare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.gkpoter.voiceShare.ui.LoginActivity;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }.start();
    }
}
