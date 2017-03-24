package com.shinhan.sband;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


    }

    public void onButtonClicked (View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        Intent intent = new Intent(LoginActivity.this, PrivateSetting.class);
        // editText의 toString
        // getText는 String으로 변환하여 행원번호를 받아온다.
        String hwnno = editText.getText().toString();

        //Intent intent = new Intent(MainActivity.this, SubActivity.class);// Main이 Sub를 실행해라.
        // subactivity에 실행시키면서 동시에 자기가 넘겨받은 intent를 subactivity에 전달하고
        // 실행될 때 이미 intent를 받고 있다.
        intent.putExtra("HWNNO", hwnno);      // putExtra hwnno를 넣은 값으로 HWNNO를 intent 객체로 전달한다.
    }
}
