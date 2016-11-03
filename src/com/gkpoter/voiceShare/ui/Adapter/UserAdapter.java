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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.model.VideoModel;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.gkpoter.voiceShare.util.PictureUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by dy on 2016/10/21.
 */
public class UserAdapter extends BaseAdapter {
    private VideoModel data;
    private Context context;
    private PictureUtil pictureUtil;

    public UserAdapter(VideoModel data,Context context){
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
        pictureUtil = new PictureUtil();
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

        Bitmap bitmap = pictureUtil.getPicture(Environment.getExternalStorageDirectory().getPath()+"/voiceshare", (data.getVideoData().get(i).getImagePath()).replaceAll("/","_"));
        if (bitmap == null) {
            new photoAsyncTask(viewHolder.image_bg,i).execute(data.getVideoData().get(i).getImagePath());
        } else {
            BitmapDrawable bd= new BitmapDrawable(bitmap);
            viewHolder.image_bg.setBackground(bd);
        }
        viewHolder.star_num.setText(data.getVideoData().get(i).getVideoYearTop()+"");
        if(data.getVideoData().get(i).getStarState()) {
            viewHolder.star_state.setText("+");
        }else{
            viewHolder.star_state.setText("-");
        }
        return view;
    }

    public class ViewHolder{
        public ImageView image_bg;
        public TextView star_num,star_state;
    }

    class photoAsyncTask extends AsyncTask<String,Void,Bitmap> {

        private ImageView imageView;
        private boolean key;
        private PictureUtil pictureUtil;
        private int i;
        public photoAsyncTask(ImageView imageView, int i) {
            this.imageView=imageView;
            this.key=false;
            pictureUtil = new PictureUtil();
            this.i=i;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap=null;
            URLConnection connection = null;
            InputStream inputStream = null;
            try {
                connection = new URL(url).openConnection();
                inputStream = connection.getInputStream();
                bitmap= BitmapFactory.decodeStream(inputStream);

                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            File file=new File(Environment.getExternalStorageDirectory().getPath()+"/voiceshare");
            if(!file.exists()){
                try {
                    file.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pictureUtil.savePicture(bitmap,Environment.getExternalStorageDirectory().getPath()+"/voiceshare",(data.getVideoData().get(i).getImagePath()).replaceAll("/","_"));
            if(key) {
                this.imageView.setImageBitmap(PhotoCut.toRoundBitmap(bitmap));
            }else{
                BitmapDrawable bd= new BitmapDrawable(bitmap);
                this.imageView.setBackground(bd);
            }
        }
    }
}
