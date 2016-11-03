package com.gkpoter.voiceShare.ui.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.listener.Listener;
import com.gkpoter.voiceShare.model.Model;
import com.gkpoter.voiceShare.service.Service;
import com.gkpoter.voiceShare.ui.UserActivity;
import com.gkpoter.voiceShare.ui.self.*;
import com.gkpoter.voiceShare.util.DataUtil;
import com.gkpoter.voiceShare.util.HttpRequest;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.*;
import java.net.URI;

/**
 * Created by dy on 2016/10/19.
 */
public class SelfFragment extends Fragment implements OnClickListener{

    private ProgressBar progressBar;
    private RelativeLayout layout,layout_show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {
        getView().findViewById(R.id.self_user_main).setOnClickListener(this);
        getView().findViewById(R.id.self_to_about).setOnClickListener(this);
        getView().findViewById(R.id.self_information).setOnClickListener(this);
        getView().findViewById(R.id.self_mysterious_).setOnClickListener(this);
        getView().findViewById(R.id.self_news_Back_saying).setOnClickListener(this);
        getView().findViewById(R.id.self_setting).setOnClickListener(this);
        getView().findViewById(R.id.self_up_uservideo).setOnClickListener(this);
        progressBar= (ProgressBar) getView().findViewById(R.id.up_uservideo_progressbar);
        layout= (RelativeLayout) getView().findViewById(R.id.self_up_uservideo_progressbar);
        layout_show=(RelativeLayout) getView().findViewById(R.id.self_up_uservideo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.self_user_main:
                DataUtil util = new DataUtil("user", getActivity());
                DataUtil util_=new DataUtil("user_focus",getActivity());
                util_.clearData();
                util_.saveData("user_id",util.getData("user_id","")+"");
                util_.saveData("user_name",util.getData("user_name","")+"");
                util_.saveData("user_photo",util.getData("user_photo","")+"");
                util_.saveData("user_signature",util.getData("user_signature","")+"");
                util_.saveData("user_selfbackgroung",util.getData("user_selfbackgroung","")+"");
                util_.saveData("user_focus",util.getData("user_focus","")+"");
                util_.saveData("user_vip",util.getData("user_vip","")+"");
                util_.saveData("user_logday",util.getData("user_logday","")+"");
                util_.saveData("user_level",util.getData("user_level","")+"");
                startActivity(new Intent(getActivity(), UserActivity.class));
                break;
            case R.id.self_to_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.self_information:
                startActivity(new Intent(getActivity(), InformationActivity.class));
                break;
            case R.id.self_mysterious_:
                startActivity(new Intent(getActivity(),MysteriousActivity.class));
                break;
            case R.id.self_news_Back_saying:
                startActivity(new Intent(getActivity(), NewsBackActivity.class));
                break;
            case R.id.self_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.self_up_uservideo:
                upVideo();
                layout_show.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private final int IMAGE_CODE=1;
    private void upVideo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "选择视频"), IMAGE_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != NewsBackActivity.RESULT_OK) {
            layout_show.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            return;
        }
        Uri uri = data.getData();
        if (requestCode == IMAGE_CODE) {
            try {
                File file=new File(new URI(uri.toString()));
                RequestParams params=new RequestParams();
                DataUtil util=new DataUtil("user",getActivity());
                params.put("UserVideo",file);
                params.put("UserId",util.getData("user_id",""));
                HttpRequest.post(getActivity(), "up_video", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Log.d("1231323",new String(bytes));
                        try {
                            Model model = new Gson().fromJson(new String(bytes), Model.class);
                            if (model.getState() == 1) {
                                Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), model.getMsg() + "", Toast.LENGTH_SHORT).show();
                                getView().findViewById(R.id.self_up_uservideo).setVisibility(View.VISIBLE);
                                layout.setVisibility(View.GONE);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(long bytesWritten, long totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                        int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                        Log.d("information", count+"");
                        progressBar.setProgress(count);
                        if(count == 100){
                            layout_show.setVisibility(View.VISIBLE);
                            layout.setVisibility(View.GONE);
                        }
                    }
                });
            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }
    }
}
