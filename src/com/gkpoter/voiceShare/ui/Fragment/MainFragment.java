package com.gkpoter.voiceShare.ui.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.Adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016/10/19.
 */
public class MainFragment extends Fragment {

    private ListView listView;
    private MainAdapter adapter;
    private List<String> data=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        listView= (ListView) getView().findViewById(R.id.listView_main);
        adapter=new MainAdapter(data,getActivity());
        listView.setAdapter(adapter);
    }


}
