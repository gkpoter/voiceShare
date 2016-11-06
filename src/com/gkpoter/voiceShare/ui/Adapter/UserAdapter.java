package com.gkpoter.voiceShare.ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.model.VideoModel;
import com.gkpoter.voiceShare.ui.AdapterUtil.ImageLoader;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.gkpoter.voiceShare.util.PictureUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by dy on 2016/10/21.
 */
public class UserAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private VideoModel data;
    private Context context;
    private int mStart, mEnd;

    public boolean mFirstIn;
    private static String [] URLS;
    private ImageLoader mImageLoader;

    public UserAdapter(VideoModel data, Context context, PullToRefreshListView listView){
        this.data=data;
        this.context=context;
        URLS = new String[data.getVideoData().size()];
        for (int i =0;i<data.getVideoData().size();i++){
            URLS[i]=data.getVideoData().get(i).getImagePath();
        }
        mImageLoader = new ImageLoader(listView,false);
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
            view = LayoutInflater.from(context).inflate(R.layout.listview_adapter_userself,null);
            viewHolder = new ViewHolder();
            viewHolder.image_bg= (ImageView) view.findViewById(R.id.list_userself_video_bg);
            viewHolder.star_num= (TextView) view.findViewById(R.id.list_userself_video_num);
            viewHolder.star_state= (TextView) view.findViewById(R.id.list_userself_video_state);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.image_bg.setTag(URLS[i]+i);
        mImageLoader.showImage(viewHolder.image_bg,URLS[i]);

        viewHolder.star_num.setText(data.getVideoData().get(i).getVideoYearTop()+"");
        if(data.getVideoData().get(i).getStarState()) {
            viewHolder.star_state.setText("+");
        }else{
            viewHolder.star_state.setText("-");
        }
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
        mEnd = Math.min(firstVisibleItem + visibleItemCount,data.getVideoData().size());

        if (mFirstIn && visibleItemCount > 0) {
            mImageLoader.loadImages(mStart, mEnd,URLS);
            mFirstIn = false;
        }
    }

    public class ViewHolder{
        public ImageView image_bg;
        public TextView star_num,star_state;
    }
}
