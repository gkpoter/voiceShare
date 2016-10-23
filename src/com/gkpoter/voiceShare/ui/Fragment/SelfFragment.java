package com.gkpoter.voiceShare.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.UserActivity;
import com.gkpoter.voiceShare.ui.self.*;

/**
 * Created by dy on 2016/10/19.
 */
public class SelfFragment extends Fragment implements OnClickListener{


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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.self_user_main:
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
                break;
        }
    }

    private void upVideo() {

    }
}
