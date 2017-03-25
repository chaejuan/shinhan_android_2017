package com.shinhan.shinhanband;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class ReadItem extends AppCompatActivity {
    Intent pintent;
    Intent intent;
    private String hwnno;   private int cmnty;  private int br_grp_g;
    private double xPoint;  private double yPoint;
    private String img_key;

    TextView pointText;
    EditText editText;
    ImageView iv;
    BitmapDrawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_item);

        setup();
        Marker marker = new Marker(ReadItem.this);
        SQLiteDatabase database = marker.getWritableDatabase();  // 쓰기용 SQLiteDatabase 열기
        ContentValues values = new ContentValues(); // content vlues에 담기
        values.put("img_key", img_key);

        Resources res = getResources();

        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.leondon1);
        int bitmapWidth = bitmap.getIntrinsicWidth(); int bitmapHeight = bitmap.getIntrinsicHeight();
        // 이미지 뷰를 새로운 이미지로 세팅
        iv.setImageDrawable(bitmap); // 이미지 뷰의 크기를 지정하지 않을 경우 미지가 자동으로 축소됨.
        iv.getLayoutParams().width = bitmapWidth;
        iv.getLayoutParams().height = bitmapHeight;

        // 이미지 뷰를 새로운 이미지로 세팅
        iv.setImageDrawable(bitmap);
        // 이미지 뷰의 크기를 지정하지 않을 경우 미지가 자동으로 축소됨.
        iv.getLayoutParams().width = bitmapWidth;
        iv.getLayoutParams().height = bitmapHeight;

    }

    private void setup() {
        pointText = (TextView)findViewById(R.id.pointText);
        editText = (EditText)findViewById(R.id.editText);
        iv = (ImageView)findViewById(R.id.iv);
        intent = getIntent();    // 처음 실행될 때 인텐트 수신
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
            hwnno = intent.getStringExtra("HWNNO"); cmnty = intent.getIntExtra("CMNTY", 0); br_grp_g =  intent.getIntExtra("BR_GRP_G", 0);
            //xPoint = intent.getDoubleExtra("XPOINT", 0); yPoint = intent.getDoubleExtra("YPOINT", 0);
            img_key = intent.getStringExtra("IMGKEY");
            //long x = (long)xPoint;  long y = (long)yPoint;

            Log.d("TAG", "readitem hwnno : " + hwnno); Log.d("TAG", "readitem cmty : " + cmnty); Log.d("TAG", "readitem br_grp_g : " + br_grp_g);
            //Log.d("TAG", "xPoint : " + xPoint); Log.d("TAG", "yPoint : " + yPoint);
            Log.d("TAG", "readitem imgkey : " + img_key);
        }
        else {}
    }
    public void onButtonClicked(View view) {
        finish();
    }
}
