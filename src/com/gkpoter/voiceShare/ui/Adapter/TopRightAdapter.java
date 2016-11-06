package com.gkpoter.voiceShare.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.model.MainVideoModel;
import com.gkpoter.voiceShare.ui.AdapterUtil.ImageLoader;
import com.gkpoter.voiceShare.ui.UserActivity;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by dy on 2016/10/21.
 */
public class TopRightAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private MainVideoModel data;
    private Context context;

    private int mStart, mEnd;
    public boolean mFirstIn;
    private static String [] URLS,URLS_;
    private ImageLoader mImageLoader,userImageloader;

    public void setData(MainVideoModel data) {
        this.data = data;
    }

    public TopRightAdapter(MainVideoModel data, Context context, PullToRefreshListView listView){
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
            view = LayoutInflater.from(context).inflate(R.layout.listview_adapter_topright,null);
            viewHolder = new ViewHolder();

            viewHolder.imageView= (ImageView) view.findViewById(R.id.top_rightList_video_bg);
            viewHolder.userImage= (ImageView) view.findViewById(R.id.top_rightList_userImage);
            viewHolder.userName= (TextView) view.findViewById(R.id.top_rightList_username);
            viewHolder.num= (TextView) view.findViewById(R.id.top_rightList_num);
            viewHolder.numstate= (TextView) view.findViewById(R.id.top_rightList_num_state);
            viewHolder.layout= (LinearLayout) view.findViewById(R.id.top_rightList_image);
            viewHolder.video_infor= (TextView) view.findViewById(R.id.top_rightList_video_infor);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.userName.setText(data.getUserData().get(i).getUserName());
        viewHolder.num.setText(data.getVideoData().get(i).getVideoMonthTop()+"");
        viewHolder.video_infor.setText(data.getVideoData().get(i).getVideoInformation());
        if(data.getVideoData().get(i).getStarState()) {
            viewHolder.numstate.setText("+");
        }else{
            viewHolder.numstate.setText("-");
        }

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

        return view;
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
        public LinearLayout layout;
        public ImageView imageView,userImage;
        public TextView num,userName,numstate,video_infor;
    }
}
