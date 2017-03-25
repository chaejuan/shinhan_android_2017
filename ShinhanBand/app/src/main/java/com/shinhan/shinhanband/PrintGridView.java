package com.shinhan.shinhanband;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class PrintGridView extends AppCompatActivity {
    EditText editText;
    Button printButton;
    GridView gridView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_grid_view);

        gridView = (GridView) findViewById(R.id.gridView);
        printButton = (Button) findViewById(R.id.listButton);

        adapter = new ImageAdapter(this);
        //String hwnno; String name; String mobile; Date date; String ctnt; int cmnty; int br_grp_g; int resId;
        adapter.addItem(new ImageItem
                ("15101193", "채주안", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon1));
        adapter.addItem(new ImageItem
                ("15101193", "채주안", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon2));
        adapter.addItem(new ImageItem
                ("15101193", "채주안", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon3));
        adapter.addItem(new ImageItem
                ("15101193", "채주안", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon4));
        adapter.addItem(new ImageItem
                ("15101193", "채주안", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon5));
        adapter.addItem(new ImageItem
                ("15101193", "채주안", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon6));

        gridView.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.editText);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem item = (ImageItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getHwnno(), Toast.LENGTH_LONG).show();
            }
        });

    }
    public void onButtonDrClicked(View view) {

    }

    public void onListButtonClicked(View view) {
        //Toast.makeText(getApplicationContext(), "선택 : LIST VIEW" + printButton.getText().toString(), Toast.LENGTH_LONG).show();
        if(printButton.getText().toString().equals("LIST VIEW")) {
            //Toast.makeText(getApplicationContext(), "선택 : LIST VIEW" , Toast.LENGTH_LONG).show();
            //adapter = new ImageAdapter(this);
            gridView.setNumColumns(1);
            printButton.setText("GRID VIEW");
            //gridView.setAdapter(adapter);
        }
        else if(printButton.getText().toString().equals("GRID VIEW")) {
            //Toast.makeText(getApplicationContext(), "선택 : GRID VIEW" , Toast.LENGTH_LONG).show();
            //adapter = new ImageAdapter(this);
            gridView.setNumColumns(2);
            printButton.setText("LIST VIEW");
            //gridView.setAdapter(adapter);
        }
    }
}
