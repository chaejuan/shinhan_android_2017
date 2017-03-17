package com.shinhan.linearlayoutexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }

    public void onButtonClicked(View view) {
        ImageView   imageView1 = (ImageView)findViewById(R.id.imageview1);
        ImageView   imageView2 = (ImageView)findViewById(R.id.imageview2);

        Button button = (Button)view;
        // if (button.getId() == R.id.button1)
        if(button.getText().toString().equals("BUTTON1")) {         // button
            //imageView1.setImageResource(R.drawable.imgbmw);;
            imageView1.setBackgroundResource(R.drawable.imgbmw);   // Background
            //imageView2.setImageResource(R.drawable.imgbmw);
            imageView2.setBackgroundResource(R.drawable.imgbmw);   // Background
        }
        else {
            //imageView1.setImageResource(R.drawable.imgaudi);;
            imageView1.setBackgroundResource(R.drawable.imgaudi);   // Background
            //imageView2.setImageResource(R.drawable.imgaudi);
            imageView2.setBackgroundResource(R.drawable.imgaudi);   // Background
        }
        /*
        Intent intent = new Intent();
        EditText editText = (EditText)findViewById(R.id.subedittext);
        String result = editText.getText().toString();

        intent.putExtra("Result", result);

        setResult(RESULT_OK, intent);

        finish();
        */
    }
}
