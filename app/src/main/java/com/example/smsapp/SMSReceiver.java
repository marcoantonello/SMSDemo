package com.example.smsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    public static OnSmsReceivedListener listener;

    @Override
    public void onReceive(Context context, Intent intent){
        Bundle extras = intent.getExtras();
        if (extras!= null){
            String format= extras.getString("format");
            Object[] pdus = (Object[]) extras.get("pdus");
            for (int i = 0; i<pdus.length; ++i){
                SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[i]);
                String testo = sms.getMessageBody();
                String numero = sms.getOriginatingAddress();
                Log.d("SMSReceiver","E' arrivato un messaggio " + testo + " da " + numero);
                //Toast.makeText(context, testo, Toast.LENGTH_LONG).show();
                listener.onSmsReceived(numero, testo);
            }
        }
    }
}