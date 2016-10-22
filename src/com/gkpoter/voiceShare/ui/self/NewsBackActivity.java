package com.gkpoter.voiceShare.ui.self;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.gkpoter.voiceShare.R;

import java.io.File;

/**
 * Created by dy on 2016/10/22.
 */
public class NewsBackActivity extends Activity {


    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsback_activity);

        init();
        showImage();
    }

    private void init() {
        image= (ImageView) findViewById(R.id.self_news_Back_upPhoto);
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
                intent.putExtra("crop", "true");    // crop=true 有这句才能出来最后的裁剪页面.
                intent.putExtra("aspectX", 1);      // 这两项为裁剪框的比例.
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                //输出地址
                intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"/voiceShare.jpg")));
                intent.putExtra("outputFormat", "JPEG");//返回格式
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
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE) {
            Log.i("photopath","success");
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/voiceShare.jpg", options);
                //Path=Environment.getExternalStorageDirectory().getPath()+"/voiceShare.jpg";

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
}
