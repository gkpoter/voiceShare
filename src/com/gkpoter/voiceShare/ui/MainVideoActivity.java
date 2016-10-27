package com.gkpoter.voiceShare.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.listener.Listener;
import com.gkpoter.voiceShare.model.RemarkModel;
import com.gkpoter.voiceShare.service.RemarkService;
import com.gkpoter.voiceShare.service.Service;
import com.gkpoter.voiceShare.ui.Adapter.VideoNewsAdapter;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

/**
 * Created by dy on 2016/10/20.
 */
public class MainVideoActivity extends Activity {

    private ImageView back_main,top,userImage;
    private TextView userName;
    private ListView newsListview;
    private boolean topState = true;
    private VideoNewsAdapter adapter;
    private RemarkModel remark_data;
    private VideoView video_view;
    private DataUtil util;

    private TextView upremark;
    private EditText editremark;

    private interface AsyncBack{
        public void onBack();
    }
    private AsyncBack call=new AsyncBack() {
        @Override
        public void onBack() {
            adapter = new VideoNewsAdapter(remark_data, getApplicationContext());
            newsListview.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        init();

        backHome();
        showNews();
    }

    private void play_video() {
        video_view.setVideoURI(Uri.parse(util.getData("video_path","")));
        MediaController mediaController = new MediaController(this);
        video_view.setMediaController(mediaController);
        video_view.start();
    }

    private void view(){
        new photoAsyncTask(userImage).execute(util.getData("user_image",""));
        userName.setText(util.getData("user_name",""));
    }

    private void init() {
        util=new DataUtil("video_data",getApplicationContext());
        video_view= (VideoView) findViewById(R.id.video_activity_video);
        play_video();

        userImage= (ImageView) findViewById(R.id.video_main_userImage);
        userName= (TextView) findViewById(R.id.video_main_userName);
        view();

        back_main= (ImageView) findViewById(R.id.video_main_back);
        upremark= (TextView) findViewById(R.id.video_main_up_news);
        editremark= (EditText) findViewById(R.id.video_main_news);
        upremark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service=new Service();
                RequestParams params=new RequestParams();
                if("".equals(editremark.getText().toString()+"")){
                    Toast.makeText(MainVideoActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    params.put("UserId",util.getData("user_id",""));
                    params.put("VideoId",util.getData("video_id",""));
                    params.put("RemarkInformation",editremark.getText().toString()+"");
                    Calendar c = Calendar.getInstance();
                    String time=c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
                    params.put("RemarkTime",time);
                    service.post(getApplicationContext(), "remark", params, new Listener() {
                        @Override
                        public void onSuccess(Object object) {
                            editremark.setText("");
                            Toast.makeText(MainVideoActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            showNews();
                        }

                        @Override
                        public void onError(String msg) {
                            Toast.makeText(MainVideoActivity.this, msg+"", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        top= (ImageView) findViewById(R.id.video_main_top);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topState){
                    Service service=new Service();
                    RequestParams params=new RequestParams();
                    params.put("VideoTop","+");
                    params.put("VideoId",util.getData("video_id",""));
                    service.post(getApplicationContext(), "video_top", params, new Listener() {
                        @Override
                        public void onSuccess(Object object) {}
                        @Override
                        public void onError(String msg) {}
                    });
                    top.setBackgroundResource(R.drawable.top2);
                    topState=false;
                }else{
                    Service service=new Service();
                    RequestParams params=new RequestParams();
                    params.put("VideoTop","-");
                    params.put("VideoId",util.getData("video_id",""));
                    service.post(getApplicationContext(), "video_top", params, new Listener() {
                        @Override
                        public void onSuccess(Object object) {}
                        @Override
                        public void onError(String msg) {}
                    });
                    top.setBackgroundResource(R.drawable.top1);
                    topState=true;
                }
            }
        });
    }

    private void showNews() {
        newsListview= (ListView) findViewById(R.id.video_main_newsList);
        RemarkService service=new RemarkService();
        RequestParams params=new RequestParams();
        params.put("VideoId",util.getData("video_id",""));
        service.get(getApplicationContext(), "getremark", params, new Listener() {
            @Override
            public void onSuccess(Object object) {
                remark_data= (RemarkModel) object;
                call.onBack();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(MainVideoActivity.this, msg+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void backHome() {
        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
