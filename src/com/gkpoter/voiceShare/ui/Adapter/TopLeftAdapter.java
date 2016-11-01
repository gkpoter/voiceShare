package com.gkpoter.voiceShare.ui.Adapter;

import android.content.Context;
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
import com.gkpoter.voiceShare.util.PhotoCut;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by dy on 2016/10/21.
 */
public class TopLeftAdapter extends BaseAdapter {
    private MainVideoModel data;
    private Context context;

    public void setData(MainVideoModel data) {
        this.data = data;
    }

    public TopLeftAdapter(MainVideoModel data, Context context){
        this.data=data;
        this.context=context;
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
            view = LayoutInflater.from(context).inflate(R.layout.listview_adapter_topleft,null);
            viewHolder = new ViewHolder();

            viewHolder.imageView= (ImageView) view.findViewById(R.id.top_leftList_video_bg);
            viewHolder.userImage= (ImageView) view.findViewById(R.id.top_leftList_userImage);
            viewHolder.userName= (TextView) view.findViewById(R.id.top_leftList_username);
            viewHolder.num= (TextView) view.findViewById(R.id.top_leftList_num);
            viewHolder.numstate= (TextView) view.findViewById(R.id.top_leftList_num_state);
            viewHolder.layout= (LinearLayout) view.findViewById(R.id.top_leftList_image);
            viewHolder.video_infor= (TextView) view.findViewById(R.id.top_leftList_video_infor);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.userName.setText(data.getUserData().get(i).getUserName());
        viewHolder.num.setText(data.getVideoData().get(i).getVideoYearTop()+"");
        viewHolder.video_infor.setText(data.getVideoData().get(i).getVideoInformation());
        if(data.getVideoData().get(i).getStarState()) {
            viewHolder.numstate.setText("+");
        }else{
            viewHolder.numstate.setText("-");
        }
        new photoAsyncTask(viewHolder.imageView,false).execute(data.getVideoData().get(i).getImagePath());
        new photoAsyncTask(viewHolder.userImage,true).execute(data.getUserData().get(i).getUserPhoto());
        return view;
    }

    public class ViewHolder{
        public LinearLayout layout;
        public ImageView imageView,userImage;
        public TextView num,userName,numstate,video_infor;
    }

    class photoAsyncTask extends AsyncTask<String,Void,Bitmap> {

        private ImageView imageView;
        private boolean key;
        public photoAsyncTask(ImageView imageView,boolean key) {
            this.imageView=imageView;
            this.key=key;
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
            if(key) {
                this.imageView.setImageBitmap(PhotoCut.toRoundBitmap(bitmap));
            }else{
                BitmapDrawable bd= new BitmapDrawable(bitmap);
                this.imageView.setBackground(bd);
            }
        }
    }
}
