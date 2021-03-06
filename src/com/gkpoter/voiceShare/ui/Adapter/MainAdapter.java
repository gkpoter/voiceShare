package com.gkpoter.voiceShare.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.model.MainVideoModel;
import com.gkpoter.voiceShare.ui.AdapterUtil.ImageLoader;
import com.gkpoter.voiceShare.ui.MainVideoActivity;
import com.gkpoter.voiceShare.ui.UserActivity;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dy on 2016/10/19.
 */
public class MainAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private int mStart, mEnd;
    private MainVideoModel data;
    private Context context;
    public boolean mFirstIn;
    private static String [] URLS,URLS_;
    private ImageLoader mImageLoader,userImageloader;

    public void setData(MainVideoModel data) {
        this.data = data;
        URLS = new String[data.getVideoData().size()];
        URLS_ = new String[data.getUserData().size()];
        for (int i =0;i<data.getVideoData().size();i++){
//            URLS = new String[data.getVideoData().size()];
//            URLS_ = new String[data.getUserData().size()];
            URLS[i]=data.getVideoData().get(i).getImagePath();
            URLS_[i]=data.getUserData().get(i).getUserPhoto();
        }
    }

    public MainAdapter(MainVideoModel data, Context context, PullToRefreshListView listView){
        this.data=data;
        this.context=context;
        URLS = new String[data.getVideoData().size()];
        URLS_ = new String[data.getUserData().size()];
        for (int i =0;i<data.getVideoData().size();i++){
            URLS[i]=data.getVideoData().get(i).getImagePath();
            URLS_[i]=data.getUserData().get(i).getUserPhoto();
        }
        mImageLoader = new ImageLoader(listView,false);
        userImageloader = new ImageLoader(listView,true);
        mFirstIn = true;
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return data.getVideoData().size();
    }

    @Override
    public Object getItem(int i) {
        return data.getVideoData().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_adapter_main,null);
            viewHolder = new ViewHolder();

            viewHolder.imageView= (ImageView) view.findViewById(R.id.video_main);
            viewHolder.videoTitle= (TextView) view.findViewById(R.id.video_title);
            viewHolder.userImage= (ImageView) view.findViewById(R.id.main_data_userImage);
            viewHolder.userName= (TextView) view.findViewById(R.id.main_data_userName);
            viewHolder.layout= (RelativeLayout) view.findViewById(R.id.video_layout);
            viewHolder.videoView= (VideoView) view.findViewById(R.id.video_main_);
            viewHolder.videodate= (TextView) view.findViewById(R.id.main_data_date);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.videoView.resume();
            viewHolder.layout.setVisibility(View.VISIBLE);
            viewHolder.videoView.setVisibility(View.GONE);
        }
        touchClick(view,i);

        viewHolder.imageView.setTag(URLS[i]+i);
        mImageLoader.showImage(viewHolder.imageView,URLS[i]);
        viewHolder.userImage.setTag(URLS_[i]+i);
        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtil util_=new DataUtil("user_focus",context);
                util_.clearData();
                util_.saveData("user_id",data.getUserData().get(i).getUserId()+"");
                util_.saveData("user_name",data.getUserData().get(i).getUserName()+"");
                util_.saveData("user_photo",data.getUserData().get(i).getUserPhoto()+"");
                util_.saveData("user_signature",data.getUserData().get(i).getSignature()+"");
                util_.saveData("user_selfbackgroung",data.getUserData().get(i).getSelfBackgroung()+"");
                util_.saveData("user_focus",data.getUserData().get(i).getFocus()+"");
                util_.saveData("user_vip",data.getUserData().get(i).getVip()+"");
                util_.saveData("user_logday",data.getUserData().get(i).getLogDay()+"");
                util_.saveData("user_level",data.getUserData().get(i).getLevel()+"");
                context.startActivity(new Intent(context, UserActivity.class));
            }
        });
        userImageloader.showImage(viewHolder.userImage,URLS_[i]);

        viewHolder.videoTitle.setText(data.getVideoData().get(i).getVideoInformation());
        viewHolder.userName.setText(data.getUserData().get(i).getUserName());
        viewHolder.videodate.setText("上传时间 : "+data.getVideoData().get(i).getUpTime());

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalViewHolder.layout.setVisibility(View.GONE);
                finalViewHolder.videoView.setVisibility(View.VISIBLE);
                finalViewHolder.videoView.setVideoURI(Uri.parse(data.getVideoData().get(i).getVideoPath()));
                MediaController mediaController = new MediaController(context);
                finalViewHolder.videoView.setMediaController(mediaController);
                finalViewHolder.videoView.start();
            }
        });

        return view;
    }

    private void touchClick(View view,int i){
        view.findViewById(R.id.news_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtil util=new DataUtil("video_data",context);
                util.clearData();
                util.saveData("video_path",data.getVideoData().get(i).getVideoPath());
                util.saveData("video_id",data.getVideoData().get(i).getVideoId()+"");
                util.saveData("user_id",data.getUserData().get(i).getUserId()+"");
                util.saveData("user_image",data.getUserData().get(i).getUserPhoto());
                util.saveData("user_name",data.getUserData().get(i).getUserName());
                context.startActivity(new Intent(context,MainVideoActivity.class));
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int scrollState) {
        // TODO Auto-generated method stub

        if (scrollState == SCROLL_STATE_IDLE) {
            mImageLoader.loadImages(mStart, mEnd,URLS);
            userImageloader.loadImages(mStart, mEnd,URLS_);
        } else {
            mImageLoader.cancelAllTasks();
            userImageloader.cancelAllTasks();
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int arg3) {
        // TODO Auto-generated method stub
        mStart = firstVisibleItem;
        mEnd = Math.min(firstVisibleItem + visibleItemCount,data.getVideoData().size());

        if (mFirstIn && visibleItemCount > 0) {
            mImageLoader.loadImages(mStart, mEnd,URLS);
            userImageloader.loadImages(mStart, mEnd,URLS_);
            mFirstIn = false;
        }
    }

    public class ViewHolder{
        public ImageView imageView,userImage;
        public TextView videoTitle,userName,videodate;
        public RelativeLayout layout;
        public VideoView videoView;
    }
}
