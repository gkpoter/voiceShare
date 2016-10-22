package com.gkpoter.voiceShare.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.MainVideoActivity;
import com.gkpoter.voiceShare.ui.transformer.DepthPageTransformer;
import com.gkpoter.voiceShare.ui.transformer.ZoomOutPageTransformer;

/**
 * Created by dy on 2016/10/19.
 */
public class TopFragment extends Fragment {

    private ViewPager viewPager;
    private Fragment[] fragments;
    private FragmentPagerAdapter adapter;
    private FragmentManager fragmentManager;
    private TextView leftText,rightText;
    private ViewFlipper viewFlipper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        showPagerView();

    }

    private void init() {
        viewPager= (ViewPager) getView().findViewById(R.id.top_main_viewPager);
        fragments=new Fragment[2];
        fragmentManager=getActivity().getSupportFragmentManager();
        fragments[0]=new TopFragmentLeft();
        fragments[1]=new TopFragmentRight();
        leftText= (TextView) getView().findViewById(R.id.top_main_leftText);
        rightText= (TextView) getView().findViewById(R.id.top_main_RightText);

        viewFlipper= (ViewFlipper) getView().findViewById(R.id.top_main_viewFlipper);

    }

    private void showPagerView() {
        adapter=new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}
            @Override
            public void onPageSelected(int i) {
                if(i==0){
                    leftText.setAlpha((float) 1);
                    rightText.setAlpha((float) 0.5);
                }else{
                    leftText.setAlpha((float) 0.5);
                    rightText.setAlpha((float) 1.0);
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {}
        });

        /**
        滚动top
         */
        ImageView imageView1=new ImageView(getActivity());
        ImageView imageView2=new ImageView(getActivity());
        ImageView imageView3=new ImageView(getActivity());
        imageView1.setBackgroundResource(R.drawable.top_back_1);
        imageView2.setBackgroundResource(R.drawable.top_back_2);
        imageView3.setBackgroundResource(R.drawable.top_back_3);
        viewFlipper.addView(imageView1);
        viewFlipper.addView(imageView2);
        viewFlipper.addView(imageView3);
        nextView();
        topClick(imageView1,imageView2,imageView3);
    }

    private void topClick(ImageView imageView1,ImageView imageView2,ImageView imageView3){
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainVideoActivity.class));
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainVideoActivity.class));
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainVideoActivity.class));
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9527) {
                viewFlipper.showNext();//向右滑动
            }
        }
    };
    public void nextView(){
        new Thread(){
            @Override
            public void run() {
                try {
                    while(true){
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(0x9527);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
