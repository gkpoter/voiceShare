package com.gkpoter.voiceShare.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.listener.Listener;
import com.gkpoter.voiceShare.model.VideoModel;
import com.gkpoter.voiceShare.service.VideoService;
import com.gkpoter.voiceShare.ui.Adapter.UserAdapter;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.gkpoter.voiceShare.util.PictureUtil;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016/10/21.
 */
public class UserActivity extends Activity {

    private ImageView backtoCollects;
    private ImageView image_bg,image_user;
    private TextView focus;
    private VideoModel data;
    private ListView listView;
    private UserAdapter adapter;

    private DataUtil util;
    private PictureUtil pictureUtil;

    private interface CallBack{
        public void back();
    }
    private CallBack call=new CallBack() {
        @Override
        public void back() {
            adapter=new UserAdapter(data,getApplication());
            listView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        init();
        backCollects();
        getData();
    }

    private void backCollects() {
        backtoCollects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        image_bg= (ImageView) findViewById(R.id.userself_bg_image);
        image_user= (ImageView) findViewById(R.id.userself_user_image);
        focus= (TextView) findViewById(R.id.userself_user_focuse);
        initView();

        backtoCollects= (ImageView) findViewById(R.id.user_main_back);
        listView= (ListView) findViewById(R.id.user_main_listView);

    }

    private void initView() {
        util=new DataUtil("user_focus",getApplication());
        pictureUtil = new PictureUtil();
        Bitmap bitmap = pictureUtil.getPicture(Environment.getExternalStorageDirectory().getPath()+"/voiceshare", util.getData("user_name", "")+"_voiceShare");
        if (bitmap == null) {
            new photoAsyncTask(image_user,"_voiceShare").execute(util.getData("user_photo", ""));
        } else {
            BitmapDrawable bd= new BitmapDrawable(bitmap);
            image_user.setBackground(bd);
        }
        Bitmap bitmap_bg = pictureUtil.getPicture(Environment.getExternalStorageDirectory().getPath()+"/voiceshare", util.getData("user_name", "")+"_voiceShare_bg");
        if (bitmap_bg == null) {
            new photoAsyncTask(image_bg,"_voiceShare_bg").execute(util.getData("user_selfbackgroung", ""));
        } else {
            BitmapDrawable bd= new BitmapDrawable(bitmap_bg);
            image_bg.setBackground(bd);
        }
        focus.setText(util.getData("user_focus","")+" 人已关注");
    }

    public void getData(){
        VideoService service=new VideoService();
        RequestParams params=new RequestParams();
        params.put("UserId",util.getData("user_id",""));
        service.post(getApplicationContext(), "user_video", params, new Listener() {
            @Override
            public void onSuccess(Object object) {
                data = (VideoModel) object;
                call.back();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getApplicationContext(),msg + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class photoAsyncTask extends AsyncTask<String,Void,Bitmap> {

        private ImageView imageView;
        private boolean key;
        private String name;
        public photoAsyncTask(ImageView imageView,String name) {
            this.imageView=imageView;
            this.key=false;
            this.name=name;
        }
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
            File file=new File(Environment.getExternalStorageDirectory().getPath()+"/voiceshare");
            if(!file.isFile()){
                try {
                    file.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pictureUtil.savePicture(bitmap, Environment.getExternalStorageDirectory().getPath()+"/voiceshare",util.getData("user_name","")+name);
            if(key) {
                this.imageView.setImageBitmap(PhotoCut.toRoundBitmap(bitmap));
            }else{
                BitmapDrawable bd= new BitmapDrawable(bitmap);
                this.imageView.setBackground(bd);
            }
        }
    }
}
