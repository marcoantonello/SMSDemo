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
        String format=bundle.getString("format");
        Object[] pdus=(Object[])bundle.get("pdus");
        if(pdus!=null) {
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[0],format);
            if (sms != null) {
                SmsListener listener = FirstLevelSmsHandler.getDefaultLitener();
                if (listener != null)
                    listener.handleSmS(sms);
            }
        }
    }
}
