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
import com.gkpoter.voiceShare.util.PhotoCut;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dy on 2016/10/20.
 */
public class VideoNewsAdapter extends BaseAdapter {
    private RemarkModel remark_data;
    private Context context;

    public VideoNewsAdapter(RemarkModel remark_data,Context context){
        this.remark_data=remark_data;
        this.context=context;
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

        new photoAsyncTask(viewHolder.userImage).execute(remark_data.getRemarkData().get(i).getUserPhoto());
        viewHolder.videoinfor.setText("     "+remark_data.getRemarkData().get(i).getRemarkInformation());
        viewHolder.userName.setText(remark_data.getRemarkData().get(i).getUserName());
        viewHolder.uptime.setText(remark_data.getRemarkData().get(i).getRemarkTime());
        return view;
    }

    public class ViewHolder{
        public ImageView userImage;
        public TextView videoinfor,userName,uptime;
    }

    class photoAsyncTask extends AsyncTask<String,Void,Bitmap> {

        private ImageView imageView;
        public photoAsyncTask(ImageView imageView) {
            this.imageView=imageView;
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
            this.imageView.setImageBitmap(PhotoCut.toRoundBitmap(bitmap));
        }
    }

}
