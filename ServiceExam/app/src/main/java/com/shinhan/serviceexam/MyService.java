package com.shinhan.serviceexam;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";
    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        if(intent == null) {    // intent가 null일 경우
            return Service.START_STICKY;
        } else {        // intent가 정상일 경우
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");
            Log.d(TAG, "command:" + command + "name:" +name);

            Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
            showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                intent.FLAG_ACTIVITY_SINGLE_TOP |
                                intent.FLAG_ACTIVITY_CLEAR_TOP);
            showIntent.putExtra("command", command);
            showIntent.putExtra("name", name+ "form service");

            startActivity(showIntent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void processCommand (Intent intent) {
        String command = intent.getStringExtra("command");
        // Service에서 activity로 값을 넘기는것은 어떻게할 까요..
    }
}
