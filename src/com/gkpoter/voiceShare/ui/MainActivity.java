package com.gkpoter.voiceShare.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.Fragment.*;
import com.gkpoter.voiceShare.util.FinishListActivity;
import com.gkpoter.voiceShare.viewpagertransformer.*;

/**
 * Created by dy on 2016/10/19.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private Fragment[] mFragments;
    private FragmentPagerAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FinishListActivity.getInstance().addActivity(this);

        init();

        showView();
    }

    private void showView() {
        adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments[i];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //super.destroyItem(container, position, object);
            }
        };
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                lightMenu(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true,new ScaleInOutTransformer());
    }

    private void init() {
        viewPager= (ViewPager) findViewById(R.id.main_activity_viewpager);
        findViewById(R.id.fragment_1).setOnClickListener(this);
        findViewById(R.id.fragment_2).setOnClickListener(this);
        findViewById(R.id.fragment_3).setOnClickListener(this);
        findViewById(R.id.fragment_4).setOnClickListener(this);

        mFragments = new Fragment[4];
        mFragments[0]=new MainFragment();
        mFragments[1]=new TopFragment();
        mFragments[2]=new CollectsFragment();
        mFragments[3]=new SelfFragment();

        findViewById(R.id.home_image).setBackgroundResource(R.drawable.home2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_1:
                viewPager.setCurrentItem(0);
                lightMenu(0);
                break;
            case R.id.fragment_2:
                viewPager.setCurrentItem(1);
                lightMenu(1);
                break;
            case R.id.fragment_3:
                viewPager.setCurrentItem(2);
                lightMenu(2);
                break;
            case R.id.fragment_4:
                viewPager.setCurrentItem(3);
                lightMenu(3);
                break;
        }
    }

    private void lightMenu(int page){
        switch (page){
            case 0:
                findViewById(R.id.home_image).setBackgroundResource(R.drawable.home2);
                findViewById(R.id.top_image).setBackgroundResource(R.drawable.top1);
                findViewById(R.id.collects_image).setBackgroundResource(R.drawable.collect1);
                findViewById(R.id.self_image).setBackgroundResource(R.drawable.self1);
                break;
            case 1:
                findViewById(R.id.home_image).setBackgroundResource(R.drawable.home1);
                findViewById(R.id.top_image).setBackgroundResource(R.drawable.top2);
                findViewById(R.id.collects_image).setBackgroundResource(R.drawable.collect1);
                findViewById(R.id.self_image).setBackgroundResource(R.drawable.self1);
                break;
            case 2:
                findViewById(R.id.home_image).setBackgroundResource(R.drawable.home1);
                findViewById(R.id.top_image).setBackgroundResource(R.drawable.top1);
                findViewById(R.id.collects_image).setBackgroundResource(R.drawable.collect2);
                findViewById(R.id.self_image).setBackgroundResource(R.drawable.self1);
                break;
            case 3:
                findViewById(R.id.home_image).setBackgroundResource(R.drawable.home1);
                findViewById(R.id.top_image).setBackgroundResource(R.drawable.top1);
                findViewById(R.id.collects_image).setBackgroundResource(R.drawable.collect1);
                findViewById(R.id.self_image).setBackgroundResource(R.drawable.self2);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent= new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
