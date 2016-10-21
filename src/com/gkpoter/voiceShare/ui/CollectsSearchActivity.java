package com.gkpoter.voiceShare.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.gkpoter.voiceShare.R;

/**
 * Created by dy on 2016/10/21.
 */
public class CollectsSearchActivity extends Activity {
    private Button back_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        init();

        backMain();
    }

    private void init() {
        back_left= (Button) findViewById(R.id.search_back);
    }

    private void backMain() {
        back_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
