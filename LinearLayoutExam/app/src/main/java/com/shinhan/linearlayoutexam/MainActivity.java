package com.shinhan.linearlayoutexam;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();    // 처음 실행될 때 인텐트 수신
        processIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {     // 이미 실행중일때 인텐트 수신
        //processIntent()
        processIntent(intent);
        super.onNewIntent(intent);
    }
    private void processIntent(Intent intent) {
        if (intent != null){
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");
            Toast.makeText(MainActivity.this, "command:" + command+", name : " + name,
            Toast.LENGTH_LONG).show();
        }
        else {

        }
    }

    public void onButtonClicked(View view) {
        Toast.makeText(MainActivity.this, "전화 걸기 call!",
                Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-6744-8658"));
        //Toast.makeText(MainActivity.this, view.)

        startActivity(myIntent);
    }

}