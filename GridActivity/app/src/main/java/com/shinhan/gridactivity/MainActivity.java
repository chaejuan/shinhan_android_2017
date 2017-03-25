package com.shinhan.gridactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    GridView gridView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);

        adapter = new ImageAdapter(this);
        //String hwnno; String name; String mobile; Date date; String ctnt; int cmnty; int br_grp_g; int resId;
        adapter.addItem(new ImageItem
                ("15101193", "ICT", "01067448658", "20150213", "#부행장님#ICT본부", 7, 0, R.drawable.itbanker));
        adapter.addItem(new ImageItem
                ("15101193", "ICT", "01067448658", "20150213", "#부행장님#ICT본부", 7, 0, R.drawable.itbanker2));
        adapter.addItem(new ImageItem
                ("15101193", "ICT", "01067448658", "20150213", "#부행장님#ICT본부", 7, 0, R.drawable.itbanker3));
        adapter.addItem(new ImageItem
                ("15101193", "ICT", "01067448658", "20150213", "#본부장님#ICT본부", 7, 0, R.drawable.itbanker4));

        gridView.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.editText);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem item = (ImageItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void onButtonDrClicked(View view) {

    }
}
