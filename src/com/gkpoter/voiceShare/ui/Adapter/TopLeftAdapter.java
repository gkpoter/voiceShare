package com.gkpoter.voiceShare.ui.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gkpoter.voiceShare.R;

import java.util.List;

/**
 * Created by dy on 2016/10/21.
 */
public class TopLeftAdapter extends BaseAdapter {
    private List<String> data;
    private Context context;

    public TopLeftAdapter(List<String> data,Context context){
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
        view = LayoutInflater.from(context).inflate(R.layout.listview_adapter_topleft,null);
        showTop(i,view);
        return view;
    }

    private void showTop(int i,View view){
        if(i==0){
            view.findViewById(R.id.top_leftList_image).setBackgroundResource(R.drawable.blue_fire);
            TextView textView= (TextView) view.findViewById(R.id.top_leftList_username);
            textView.setTextColor(Color.rgb(255, 255, 255));
            TextView textView_= (TextView) view.findViewById(R.id.top_leftList_num);
            textView_.setTextColor(Color.rgb(255, 255, 255));
        }
    }
}
