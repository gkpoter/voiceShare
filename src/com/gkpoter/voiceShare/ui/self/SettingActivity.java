package com.gkpoter.voiceShare.ui.self;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.LoginActivity;
import com.gkpoter.voiceShare.util.FinishListActivity;

/**
 * Created by dy on 2016/10/22.
 */
public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        FinishListActivity.getInstance().addActivity(this);

        init();
    }

    private void init() {

        findViewById(R.id.setting_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.setting_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinishListActivity.getInstance().exit();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            }
        });
    }
}
