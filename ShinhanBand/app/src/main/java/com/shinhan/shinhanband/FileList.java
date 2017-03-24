package com.shinhan.shinhanband;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.List;

public class FileList extends AppCompatActivity {
    private File file;
    private List myList;
    // https://developer.android.com/reference/android/widget/ListView.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
    }
}

