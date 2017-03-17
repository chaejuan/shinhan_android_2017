package com.shinhan.sband;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {   //
    @Override
    protected void onCreate(Bundle savedInstanceState) {    // activity 실행 시점에 한번 실행된다.
        super.onCreate(savedInstanceState);                  // onCreate 상속 함수 실행
        setContentView(R.layout.activity_main);             // main.xml을 나의 contents view로 쓰겠다.

        // id를 찾아서 객체화한다.
        Button button = (Button)findViewById(R.id.button);
        TextView textView = (TextView)findViewById(R.id.textview);

        textView.setText("Bye, Shinhan!");
        /* button event click event */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* msg가 왔을 때 금방 사라지는 것 예. 메시지가 왔습니다. */
                Toast.makeText(MainActivity.this, "Button Clicked!",
                                                        Toast.LENGTH_SHORT).show();
            }


        });
    }
    /* Button2에 대해서 생성 후 2nd Button Clicked method를 실행한다. */
    public void onButton2Clicked(View view) {
        Toast.makeText(MainActivity.this, "2nd Button Clicked!",
                Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this, view.)
    }

    public void onButton3Clicked(View view) {
        Toast.makeText(MainActivity.this, "google 연동 !",
                Toast.LENGTH_SHORT).show();
        /* http url 홈페이지 주소를 의미하는 구나 판단한다. web browser가 본다. */
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.shinhan.com"));

        startActivity(myIntent);
    }
    /*
    * Intent는 모든 정보를 주고 받을 수 있다. activity에게 전달을 했습니다. OS가 Intent를 받고
    * "그 app에 Intent를 준다고 보면 된다." "thread의 개념"
    */
    public void onButton4Clicked(View view) {
        Toast.makeText(MainActivity.this, "전화 걸기 call!",
                Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-6744-8658"));
        //Toast.makeText(MainActivity.this, view.)

        startActivity(myIntent);
    }
}
