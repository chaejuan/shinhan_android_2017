package com.shinhan.recieverexam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    public static final String TAG = "SmsReceiver";     // tag 정의가 필요하다.

    @Override
    public void onReceive(Context context, Intent intent) { //sms가 수신되어서 andriod os가 통지할 때
        Log.i(TAG, "OnRecieve()----------------------------------!!");
        // msg 수신은 중요한 정보다.. push를 받으시겠습니까와 같다는 것이다.
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);
        if (messages != null && messages.length > 0) {      // 수신된 메시지가 있으면
            String sender = messages[0].getOriginatingAddress();
            String contents = messages[0].getMessageBody().toString();

            Log.i(TAG, "sender : " + sender + ", contents : "  + contents);
        }
    }
    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i = 0; i < objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i], format);
            }
            else {  // pdu 객체가 들어오는것이고, class type으로 들어오는 것 이다.
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
            }
        }
        return messages;
    }
}
