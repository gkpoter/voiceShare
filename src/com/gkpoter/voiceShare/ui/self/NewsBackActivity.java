package com.gkpoter.voiceShare.ui.self;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.model.Model;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.HttpRequest;
import com.gkpoter.voiceShare.util.PictureUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by dy on 2016/10/22.
 */
public class NewsBackActivity extends Activity {


    private ImageView image;
    private EditText bug_text;
    private TextView up_bug;
    private Bitmap bitmap;
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsback_activity);

        init();
        showImage();
        upBug();

    }

    private void init() {
        bug_text= (EditText) findViewById(R.id.bug_text);
        up_bug= (TextView) findViewById(R.id.bug_up);
        image= (ImageView) findViewById(R.id.self_news_Back_upPhoto);
        progress= (ProgressBar) findViewById(R.id.bug_progress);
        findViewById(R.id.self_news_Back_).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 回调图片
     */
    private final String IMAGE_TYPE="image/*";
    private final int IMAGE_CODE=1;
    //private String Path;
    private void showImage() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(IMAGE_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "选择图片"), IMAGE_CODE);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != NewsBackActivity.RESULT_OK) {
            Log.i("photopath","fail");
            return;
        }

        if (requestCode == IMAGE_CODE) {
            Log.i("photopath","success");
            try {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                if (bitmap == null){
                    Log.i("bitmap","null");
                    return;
                }

                image.setImageBitmap(bitmap);

            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }
    }

    private void upBug(){
        DataUtil util=new DataUtil("user",getApplicationContext());
        up_bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                RequestParams params=new RequestParams();
                if(bitmap!=null) {
                    new PictureUtil().savePicture(bitmap,Environment.getExternalStorageDirectory().getPath()+"/voiceshare","voice_bug.jpg");
                    File file=new File(Environment.getExternalStorageDirectory().getPath()+"/voiceshare/voice_bug.jpg");
                    try {
                        params.put("BugImage", file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                params.put("BugText", bug_text.getText().toString() + "");
                params.put("UserId", util.getData("user_id",""));
                HttpRequest.post(getApplication(), "up_bug", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Model model=new Gson().fromJson(new String(bytes),Model.class);
                        if(new Integer(1).equals(model.getState())){
                            bug_text.setText("");
                            image.setImageBitmap(null);
                            progress.setVisibility(View.GONE);
                            Toast.makeText(NewsBackActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
                        }else{
                            bug_text.setText("");
                            image.setImageBitmap(null);
                            Toast.makeText(NewsBackActivity.this,model.getMsg() + "", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(NewsBackActivity.this,"网络请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(long bytesWritten, long totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                        int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                        Log.d("information", count+"");
                        progress.setProgress(count);
                    }
                });

            }
        });
    }
}
