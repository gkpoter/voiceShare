package com.gkpoter.voiceShare.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.Adapter.VideoNewsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016/10/20.
 */
public class MainVideoActivity extends Activity {

    private ImageView back_main;
    private ListView newsListview;
    private ImageView top;
    private boolean topState = true;
    private VideoNewsAdapter adapter;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        init();
        backHome();
        showNews();
    }

    private void init() {
        data=new ArrayList<>();
        back_main= (ImageView) findViewById(R.id.video_main_back);
        newsListview= (ListView) findViewById(R.id.video_main_newsList);

        top= (ImageView) findViewById(R.id.video_main_top);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topState){
                    top.setBackgroundResource(R.drawable.top2);
                    topState=false;
                }else{
                    top.setBackgroundResource(R.drawable.top1);
                    topState=true;
                }
            }
        });
    }

    private void showNews() {
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        adapter=new VideoNewsAdapter(data,MainVideoActivity.this);
        newsListview.setAdapter(adapter);
    }

    private void backHome() {
        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
