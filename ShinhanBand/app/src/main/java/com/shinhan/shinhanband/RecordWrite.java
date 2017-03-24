package com.shinhan.shinhanband;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;

public class RecordWrite extends AppCompatActivity {
    EditText editText;

    GridView gridVew;
    SingerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_write);
    }
}
