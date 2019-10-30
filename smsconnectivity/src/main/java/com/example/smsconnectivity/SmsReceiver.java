package com.example.smsconnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        SmsMessage sms=(SmsMessage)bundle.get("message");
        if(sms!=null) {
            SmsListener listener = FirstLevelSmsHandler.getDefaultLitener();
            if(listener!=null)
                listener.handleSmS(sms);
        }
    }
}
