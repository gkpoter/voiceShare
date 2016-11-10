package com.gkpoter.voiceShare.ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.model.RemarkModel;
import com.gkpoter.voiceShare.ui.AdapterUtil.ImageLoader;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dy on 2016/10/20.
 */
public class VideoNewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private RemarkModel remark_data;
    private Context context;

    private int mStart, mEnd;

    public boolean mFirstIn;
    private static String [] URLS;
    private ImageLoader mImageLoader;

    public VideoNewsAdapter(RemarkModel remark_data, Context context, PullToRefreshListView newsListview){
        this.remark_data=remark_data;
        this.context=context;

        URLS = new String[remark_data.getRemarkData().size()];
        for (int i =0;i<remark_data.getRemarkData().size();i++){
            URLS[i]=remark_data.getRemarkData().get(i).getUserPhoto();
        }
        mImageLoader = new ImageLoader(newsListview,true);
        mFirstIn = true;
        newsListview.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return remark_data.getRemarkData().size();
    }

    @Override
    public Object getItem(int i) {
        return remark_data.getRemarkData().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.video_newslist,null);
            viewHolder = new ViewHolder();

            viewHolder.userImage= (ImageView) view.findViewById(R.id.video_news_userImage);
            viewHolder.videoinfor= (TextView) view.findViewById(R.id.video_news_information);
            viewHolder.userName= (TextView) view.findViewById(R.id.video_news_userName);
            viewHolder.uptime= (TextView) view.findViewById(R.id.video_news_video_uptime);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.userImage.setTag(URLS[i]+i);
        mImageLoader.showImage(viewHolder.userImage,URLS[i]);

        viewHolder.videoinfor.setText("     "+remark_data.getRemarkData().get(i).getRemarkInformation());
        viewHolder.userName.setText(remark_data.getRemarkData().get(i).getUserName());
        viewHolder.uptime.setText(remark_data.getRemarkData().get(i).getRemarkTime());
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int scrollState) {
        // TODO Auto-generated method stub

        if (scrollState == SCROLL_STATE_IDLE) {
            mImageLoader.loadImages(mStart, mEnd,URLS);
        } else {
            mImageLoader.cancelAllTasks();
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int arg3) {
        // TODO Auto-generated method stub
        mStart = firstVisibleItem;
        mEnd = Math.min(firstVisibleItem + visibleItemCount,remark_data.getRemarkData().size());

        if (mFirstIn && visibleItemCount > 0) {
            mImageLoader.loadImages(mStart, mEnd,URLS);
            mFirstIn = false;
        }
    }

    public class ViewHolder{
        public ImageView userImage;
        public TextView videoinfor,userName,uptime;
    }
}
