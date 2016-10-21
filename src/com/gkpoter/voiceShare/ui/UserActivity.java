package com.gkpoter.voiceShare.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.Adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016/10/21.
 */
public class UserActivity extends Activity {

    private ImageView backtoCollects;
    private List<String> data;
    private ListView listView;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        init();
        backCollects();
    }

    private void backCollects() {
        backtoCollects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        backtoCollects= (ImageView) findViewById(R.id.user_main_back);
        listView= (ListView) findViewById(R.id.user_main_listView);
        data=new ArrayList<>();
        for(int i=0;i<10;i++) data.add(i+"");
        adapter=new UserAdapter(data,getApplicationContext());
        listView.setAdapter(adapter);
    }
}
