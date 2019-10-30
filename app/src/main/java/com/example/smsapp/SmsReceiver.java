package com.example.smsapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(context.checkSelfPermission(Manifest.permission.RECEIVE_SMS)== PackageManager.PERMISSION_DENIED)
            Toast.makeText(context,"non riceve",Toast.LENGTH_SHORT).show();
        //if(context.checkSelfPermission(Manifest.permission.READ_SMS)== PackageManager.PERMISSION_DENIED)
            //Toast.makeText(context,"non legge",Toast.LENGTH_SHORT).show();
        Bundle bundle = intent.getExtras();
        String format=bundle.getString("format");
        Object[] pdus=(Object[])bundle.get("pdus");
        if(pdus!=null) {
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[0],format);
            if (sms != null) {
                SmsListener listener = new FirstLevelSmsHandler(context);
                if (listener != null) {
                    listener.handleSmS(sms);
                    Toast.makeText(context,"arriavto sms",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context,"NULL",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
