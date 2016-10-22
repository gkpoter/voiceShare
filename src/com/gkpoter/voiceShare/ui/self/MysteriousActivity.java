package com.gkpoter.voiceShare.ui.self;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.gkpoter.voiceShare.R;

/**
 * Created by dy on 2016/10/22.
 */
public class MysteriousActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysterious_activity);

        init();
    }

    private void init() {
        findViewById(R.id.self_mysterious_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webView= (WebView) findViewById(R.id.self_mysterious_webView);
        WebSettings setting=webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
