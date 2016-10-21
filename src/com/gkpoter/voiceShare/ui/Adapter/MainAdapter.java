package com.gkpoter.voiceShare.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gkpoter.voiceShare.R;
import com.gkpoter.voiceShare.ui.MainVideoActivity;

import java.util.List;

/**
 * Created by dy on 2016/10/19.
 */
public class MainAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;

    public MainAdapter(List<String> data,Context context){
        this.data=data;
        this.context=context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.listview_adapter_main,null);
        touchClick(view);

        return view;
    }

    private void touchClick(View view){
        view.findViewById(R.id.news_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,MainVideoActivity.class));
            }
        });
    }

}
