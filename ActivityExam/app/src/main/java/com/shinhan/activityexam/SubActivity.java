package com.shinhan.activityexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        /* 실행 될 때 intent를 받은 것이다 */
        Intent intent = getIntent();
        String string = intent.getStringExtra("String");
        Toast.makeText(SubActivity.this, string, Toast.LENGTH_SHORT).show();
        EditText subeditText = (EditText)findViewById(R.id.subedittext);
        //subeditText.setText(string, );
        subeditText.setText(string);
    }

    public void onCloseButtonClicked(View v) {
        //Intent intent = new Intent(SubActivity.this, MainActivity.class);   /* Main이 Sub를 실행한다.*/
        //endActivity(intent);
        Intent intent = new Intent();
        EditText editText = (EditText)findViewById(R.id.subedittext);
        String result = editText.getText().toString();

        intent.putExtra("Result", result);

        setResult(RESULT_OK, intent);

        finish();                   /* back button 과 같은 것이다. 다시 종료하고 돌아갈때는 */
    }
}
