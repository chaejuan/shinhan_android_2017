package com.shinhan.serviceexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "최초 실행");

        Intent intent = getIntent();
        processIntent(intent);
    }

    public void onButtonClicked(View v) {
        EditText editText = (EditText)findViewById(R.id.edittext);
        String name = editText.getText().toString();

        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("command", "command");
        intent.putExtra("name", name);

        startService(intent);       // start service
    }

    @Override
    protected void onNewIntent(Intent intent) {
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
}
