package com.shinhan.shinhanband;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by IC-INTPC-087102 on 2017-03-24.
 */

public class ImageAdapter extends BaseAdapter {
    ArrayList<ImageItem> items = new ArrayList<ImageItem>();
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public void addItem(ImageItem item) {
        items.add(item);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItemView view = new ImageItemView
        return null;
    }
}
