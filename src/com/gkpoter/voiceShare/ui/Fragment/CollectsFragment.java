package com.gkpoter.voiceShare.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.Adapter.CollectsAdapter;
import com.gkpoter.voiceShare.ui.CollectsSearchActivity;
import com.gkpoter.voiceShare.ui.UserActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016/10/19.
 */
public class CollectsFragment extends Fragment {

    private ImageView searchFriend;
    private ListView listView;
    private List<String> data;
    private CollectsAdapter adapter;
    private LinearLayout userSelf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collects, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        listClick();
    }

    private void listClick() {
        userSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserActivity.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), UserActivity.class));
            }
        });
    }

    private void init() {
        userSelf= (LinearLayout) getView().findViewById(R.id.collects_main_userSelf);
        searchFriend= (ImageView) getView().findViewById(R.id.collects_main_search);
        searchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CollectsSearchActivity.class));
            }
        });
        data=new ArrayList<>();
        for(int i=0;i<20;i++) data.add(i+"");
        adapter=new CollectsAdapter(data,getActivity());
        listView= (ListView) getView().findViewById(R.id.collects_main_listView);
        listView.setAdapter(adapter);
    }
}
