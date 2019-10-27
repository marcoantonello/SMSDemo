package com.example.smsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    SMSListener listener;

    public SMSReceiver(SMSListener listener){
        this.listener=listener;
    }

    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();

        String format;

        SmsMessage recMsg;
        byte[] data;

        if (bundle != null)
        {
            format = (String)bundle.get("format");
            Object[] pdus = (Object[]) bundle.get("pdus");

            if(pdus.length>0){

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    recMsg = SmsMessage.createFromPdu((byte[]) pdus[0],format);//android M or newer
                }
                else {
                    recMsg = SmsMessage.createFromPdu((byte[]) pdus[0]);
                }

                data = recMsg.getUserData();

                if (data != null)
                {
                    listener.onSMSReceived(recMsg);
                }
            }
        }
    }
}
