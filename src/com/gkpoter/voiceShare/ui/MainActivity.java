package com.gkpoter.voiceShare.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.gkpoter.voiceShare.R;

/**
 * Created by dy on 2016/10/19.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private Fragment[] mFragments;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        init();
    }

    private void init() {
        findViewById(R.id.fragment_1).setOnClickListener(this);
        findViewById(R.id.fragment_2).setOnClickListener(this);
        findViewById(R.id.fragment_3).setOnClickListener(this);
        findViewById(R.id.fragment_4).setOnClickListener(this);

        fragmentManager=getSupportFragmentManager();
        mFragments = new Fragment[4];

        mFragments[0] = fragmentManager.findFragmentById(R.id.fragment_main);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragment_top);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragment_collects);
        mFragments[3] = fragmentManager.findFragmentById(R.id.fragment_self);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
                .show(mFragments[0]).commit();
        findViewById(R.id.home_image).setBackgroundResource(R.drawable.home2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_1:
                //Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
                        .show(mFragments[0]).commit();
                findViewById(R.id.home_image).setBackgroundResource(R.drawable.home2);
                findViewById(R.id.top_image).setBackgroundResource(R.drawable.top1);
                findViewById(R.id.collects_image).setBackgroundResource(R.drawable.collect1);
                findViewById(R.id.self_image).setBackgroundResource(R.drawable.self1);
                break;
            case R.id.fragment_2:
                //Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(mFragments[0]).hide(mFragments[2]).hide(mFragments[3])
                        .show(mFragments[1]).commit();
                findViewById(R.id.home_image).setBackgroundResource(R.drawable.home1);
                findViewById(R.id.top_image).setBackgroundResource(R.drawable.top2);
                findViewById(R.id.collects_image).setBackgroundResource(R.drawable.collect1);
                findViewById(R.id.self_image).setBackgroundResource(R.drawable.self1);
                break;
            case R.id.fragment_3:
                //Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(mFragments[1]).hide(mFragments[0]).hide(mFragments[3])
                        .show(mFragments[2]).commit();
                findViewById(R.id.home_image).setBackgroundResource(R.drawable.home1);
                findViewById(R.id.top_image).setBackgroundResource(R.drawable.top1);
                findViewById(R.id.collects_image).setBackgroundResource(R.drawable.collect2);
                findViewById(R.id.self_image).setBackgroundResource(R.drawable.self1);
                break;
            case R.id.fragment_4:
                //Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[0])
                        .show(mFragments[3]).commit();
                findViewById(R.id.home_image).setBackgroundResource(R.drawable.home1);
                findViewById(R.id.top_image).setBackgroundResource(R.drawable.top1);
                findViewById(R.id.collects_image).setBackgroundResource(R.drawable.collect1);
                findViewById(R.id.self_image).setBackgroundResource(R.drawable.self2);
                break;
        }
    }
}
