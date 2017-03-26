package com.shinhan.shinhanband;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by IC-INTPC-087102 on 2017-03-24.
 */

public class ImageItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    TextView textView3;
    ImageView imageView;

    public ImageItemView(Context context) {
        super(context);
        init(context);
    }

    public ImageItemView(Context context, AttributeSet attrs) {
        super (context, attrs);
        init(context);
    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.image_item, this, true);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        imageView = (ImageView)findViewById(R.id.imageView);
    }

    public void setHwnno(String hwnno) {
        textView.setText(hwnno);
    }

    public void setImgkey(String imgkey) {
        textView2.setText(imgkey);
    }

    public void setCtnt(String ctnt) {
        textView3.setText(ctnt);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}