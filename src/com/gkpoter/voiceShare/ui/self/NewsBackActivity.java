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
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

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
