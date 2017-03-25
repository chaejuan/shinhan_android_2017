package com.shinhan.shinhanband;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by IC-INTPC-087102 on 2017-03-24.
 */

public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<ImageItem> items = new ArrayList<ImageItem>();
    ImageAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(ImageItem item) {
        items.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItemView view = new ImageItemView(context);
        ImageItem item = items.get(position);
        view.setName(item.getName());
        view.setMobile(item.getMobile());
        view.setDate(item.getDate());
        view.setImage(item.getResId());

        return view;
    }
}
