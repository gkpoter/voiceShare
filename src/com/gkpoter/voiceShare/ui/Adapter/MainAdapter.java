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
import com.gkpoter.voiceShare.ui.MainVideoActivity;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.PhotoCut;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dy on 2016/10/19.
 */
public class MainAdapter extends BaseAdapter {

    private MainVideoModel data;
    private Context context;

    public void setData(MainVideoModel data) {
        this.data = data;
    }

    public MainAdapter(MainVideoModel data, Context context){
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
            view = LayoutInflater.from(context).inflate(R.layout.listview_adapter_main,null);
            viewHolder = new ViewHolder();

            viewHolder.imageView= (ImageView) view.findViewById(R.id.video_main);
            viewHolder.videoTitle= (TextView) view.findViewById(R.id.video_title);
            viewHolder.userImage= (ImageView) view.findViewById(R.id.main_data_userImage);
            viewHolder.userName= (TextView) view.findViewById(R.id.main_data_userName);
            viewHolder.layout= (RelativeLayout) view.findViewById(R.id.video_layout);
            viewHolder.videoView= (VideoView) view.findViewById(R.id.video_main_);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.videoView.resume();
            viewHolder.layout.setVisibility(View.VISIBLE);
            viewHolder.videoView.setVisibility(View.GONE);
        }
        touchClick(view,i);

        if(viewHolder.image_bitmap==null){
            new photoAsyncTask(viewHolder.imageView,viewHolder,false).execute(data.getVideoData().get(i).getImagePath());
        }else{
            BitmapDrawable bd= new BitmapDrawable(viewHolder.image_bitmap);
            viewHolder.imageView.setBackground(bd);
        }
//        if(viewHolder.user_bitmap==null) {
            new photoAsyncTask(viewHolder.userImage, viewHolder, true).execute(data.getUserData().get(i).getUserPhoto());
//        }else{
//            viewHolder.userImage.setImageBitmap(PhotoCut.toRoundBitmap(viewHolder.user_bitmap));
//        }
        viewHolder.videoTitle.setText(data.getVideoData().get(i).getVideoInformation());
        viewHolder.userName.setText(data.getUserData().get(i).getUserName());

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

    public class ViewHolder{
        public ImageView imageView,userImage;
        public TextView videoTitle,userName;
        public RelativeLayout layout;
        public VideoView videoView;
        public Bitmap user_bitmap,image_bitmap;
    }

    class photoAsyncTask extends AsyncTask<String,Void,Bitmap> {

        private ViewHolder viewHolder;
        private ImageView imageView;
        private boolean key;
        public photoAsyncTask(ImageView imageView,ViewHolder viewHolder,boolean key) {
            this.imageView=imageView;
            this.viewHolder=viewHolder;
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
                viewHolder.user_bitmap=bitmap;
                this.imageView.setImageBitmap(PhotoCut.toRoundBitmap(bitmap));
            }else{
                viewHolder.image_bitmap=bitmap;
                BitmapDrawable bd= new BitmapDrawable(bitmap);
                this.imageView.setBackground(bd);
            }
        }
    }

}
