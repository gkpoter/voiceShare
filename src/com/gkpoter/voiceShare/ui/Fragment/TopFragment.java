package com.gkpoter.voiceShare.ui.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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
import android.widget.*;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.listener.Listener;
import com.gkpoter.voiceShare.model.MainVideoModel;
import com.gkpoter.voiceShare.service.MainVideoService;
import com.gkpoter.voiceShare.ui.MainVideoActivity;
import com.gkpoter.voiceShare.ui.transformer.DepthPageTransformer;
import com.gkpoter.voiceShare.ui.transformer.ZoomOutPageTransformer;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private MainVideoModel data;
    private LinearLayout tvTop1,tvTop2,tvTop3;
    private TextView top1,top2,top3;

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
        getData();
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

        tvTop1= (LinearLayout) getView().findViewById(R.id.top_main_lin1);
        tvTop2= (LinearLayout) getView().findViewById(R.id.top_main_lin2);
        tvTop3= (LinearLayout) getView().findViewById(R.id.top_main_lin3);
        top1= (TextView) getView().findViewById(R.id.top_main_top1_title);
        top2= (TextView) getView().findViewById(R.id.top_main_top2_title);
        top3= (TextView) getView().findViewById(R.id.top_main_top3_title);
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
        leftText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftText.setAlpha((float) 1);
                rightText.setAlpha((float) 0.5);
                viewPager.setCurrentItem(0);
            }
        });
        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftText.setAlpha((float) 0.5);
                rightText.setAlpha((float) 1.0);
                viewPager.setCurrentItem(1);
            }
        });

        /**
        滚动top
         */
        imageView1=new ImageView(getActivity());
        imageView2=new ImageView(getActivity());
        imageView3=new ImageView(getActivity());
//        imageView1.setBackgroundResource(R.drawable.top_back_1);
//        imageView2.setBackgroundResource(R.drawable.top_back_1);
//        imageView3.setBackgroundResource(R.drawable.top_back_1);
//        viewFlipper.addView(imageView1);
//        viewFlipper.addView(imageView2);
//        viewFlipper.addView(imageView3);
        nextView();
        topClick();
    }

    private void topClick(){

        tvTop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtil util=new DataUtil("video_data",getActivity());
                util.clearData();
                util.saveData("video_path",data.getVideoData().get(0).getVideoPath());
                util.saveData("video_id",data.getVideoData().get(0).getVideoId()+"");
                util.saveData("user_id",data.getUserData().get(0).getUserId()+"");
                util.saveData("user_image",data.getUserData().get(0).getUserPhoto());
                util.saveData("user_name",data.getUserData().get(0).getUserName());
                startActivity(new Intent(getActivity(), MainVideoActivity.class));
            }
        });
        tvTop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtil util=new DataUtil("video_data",getActivity());
                util.clearData();
                util.saveData("video_path",data.getVideoData().get(1).getVideoPath());
                util.saveData("video_id",data.getVideoData().get(1).getVideoId()+"");
                util.saveData("user_id",data.getUserData().get(1).getUserId()+"");
                util.saveData("user_image",data.getUserData().get(1).getUserPhoto());
                util.saveData("user_name",data.getUserData().get(1).getUserName());
                startActivity(new Intent(getActivity(), MainVideoActivity.class));
            }
        });
        tvTop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtil util=new DataUtil("video_data",getActivity());
                util.clearData();
                util.saveData("video_path",data.getVideoData().get(2).getVideoPath());
                util.saveData("video_id",data.getVideoData().get(2).getVideoId()+"");
                util.saveData("user_id",data.getUserData().get(2).getUserId()+"");
                util.saveData("user_image",data.getUserData().get(2).getUserPhoto());
                util.saveData("user_name",data.getUserData().get(2).getUserName());
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

    private void getData() {
        RequestParams params=new RequestParams();
        params.put("VideoTop","third");
        MainVideoService service=new MainVideoService();
        service.get(getActivity(), "third", params, new Listener() {
            @Override
            public void onSuccess(Object object) {
                data= (MainVideoModel) object;
                try {
                    new photoAsyncTask(imageView1).execute(data.getVideoData().get(0).getImagePath());
                    new photoAsyncTask(imageView2).execute(data.getVideoData().get(1).getImagePath());
                    new photoAsyncTask(imageView3).execute(data.getVideoData().get(2).getImagePath());
                    top1.setText(data.getVideoData().get(0).getVideoInformation());
                    top2.setText(data.getVideoData().get(1).getVideoInformation());
                    top3.setText(data.getVideoData().get(2).getVideoInformation());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getActivity(), msg+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class photoAsyncTask extends AsyncTask<String,Void,Bitmap> {

        private ImageView imageView;

        public photoAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url=strings[0];
            Bitmap bitmap=null;
            URLConnection connection;
            InputStream inputStream;
            try {
                connection=new URL(url).openConnection();
                inputStream=connection.getInputStream();

                bitmap= BitmapFactory.decodeStream(inputStream);

                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            BitmapDrawable bd= new BitmapDrawable(bitmap);
            this.imageView.setBackground(bd);
            viewFlipper.addView(imageView);
        }
    }

}
