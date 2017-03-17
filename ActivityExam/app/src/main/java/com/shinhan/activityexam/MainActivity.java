package com.shinhan.activityexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButton1Clicked(View view) {
        //Toast.makeText(MainActivity.this, "1st Button Clicked!",
        //        Toast.LENGTH_SHORT).show();

        EditText editText = (EditText) findViewById(R.id.edittext);
        //editText.getText()
        // getText는 String이 아닌 객체이기 때문에 String으로 변환해준다.
        String string = editText.getText().toString();

        //Intent intent = new Intent(MainActivity.this, SubActivity.class);// Main이 Sub를 실행해라.
        // subactivity에 실행시키면서 동시에 자기가 넘겨받은 intent를 subactivity에 전달하고
        // 실행될 때 이미 intent를 받고 있다.
        intent.putExtra("String", string);      // string

        startActivityForResult(intent, 0);//startActivity(intent);
        // 어떤것들이 실행되었는지 확인한다. subactivity는 0번이다.
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data)
     {
            if(requestCode == 0)    // sub activity가 종료 되었으면
            {
                // finish 또는 back button
                if(resultCode == RESULT_OK)
                {
                    String result = data.getStringExtra("Result");
                    EditText editText = (EditText)findViewById(R.id.edittext);
                    editText.setText(result);
                }
            }
    }
}
