package com.gkpoter.voiceShare.ui.AdapterUtil;

/**
 * Created by dy on 2016/11/6.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.util.PhotoCut;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by caolu on 2016/7/28.
 */
public class ImageLoader {

    private LruCache<String, Bitmap> mCache;
    private Set<MyAsyncTask> mTask;

    private PullToRefreshListView mListView;
    private boolean KEY;


    public ImageLoader(PullToRefreshListView listView,boolean KEY) {
        mListView = listView;
        this.KEY=KEY;

        mTask = new HashSet<MyAsyncTask>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            mCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        if (url == null)
            return null;
        return mCache.get(url);
    }

    public void showImage(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null && !KEY) {
            imageView.setBackgroundResource(R.drawable.top_back_1);
        } else {
            if (KEY) {
                imageView.setImageBitmap(PhotoCut.toRoundBitmap(bitmap));
            } else{
                BitmapDrawable bd = new BitmapDrawable(bitmap);
                imageView.setBackground(bd);
            }
        }

    }

    public Bitmap getBitmapFromURL(String urlString) {

        Bitmap bitmap = null;
        InputStream is = null;
        if (urlString == null) {
            return null;
        }
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return bitmap;
    }

    public void loadImages(int start, int end,String [] URLS) {
        for (int i = start; i < end; i++) {
            String url = URLS[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap == null) {
                MyAsyncTask task = new MyAsyncTask(url, i);
                task.execute(url);
                mTask.add(task);
            }
        }
    }


    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private String murl;
        private int mposition;

        public MyAsyncTask(String url, int position) {
            mposition = position;
            murl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String url = params[0];
            Bitmap bitmap = getBitmapFromURL(url);
            if (bitmap != null) {
                addBitmapToCache(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(murl + mposition);

            if (imageView != null && bitmap != null) {
                if (KEY) {
                    imageView.setImageBitmap(PhotoCut.toRoundBitmap(bitmap));
                } else{
                    BitmapDrawable bd = new BitmapDrawable(bitmap);
                    imageView.setBackground(bd);
                }
            } else Log.i("imageVIew", "null");
            mTask.remove(this);
        }
    }


    public void cancelAllTasks() {
        // TODO Auto-generated method stub
        if (mTask != null) {
            for (MyAsyncTask task : mTask) {
                task.cancel(false);
            }
        }
    }
}