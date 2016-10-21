package com.gkpoter.voiceShare.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.Adapter.TopLeftAdapter;
import com.gkpoter.voiceShare.ui.MainVideoActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dy on 2016/10/20.
 */
public class TopFragmentLeft extends Fragment {

    private ListView listView;
    private List<String> data;
    private TopLeftAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_left, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        viewOnClick();
    }

    private void viewOnClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), MainVideoActivity.class));
            }
        });
    }

    private void init() {
        listView= (ListView) getView().findViewById(R.id.top_leftList);
        data=new ArrayList<>();
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        adapter=new TopLeftAdapter(data,getActivity());
        listView.setAdapter(adapter);
    }

}
